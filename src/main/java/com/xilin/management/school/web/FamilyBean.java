package com.xilin.management.school.web;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.validator.LengthValidator;

import org.apache.log4j.Logger;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.component.spinner.Spinner;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.json.JSONObject;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.jsf.FacesContextUtils;

import com.xilin.management.school.model.Family;
import com.xilin.management.school.model.FamilyRepository;
import com.xilin.management.school.model.Registration;
import com.xilin.management.school.model.Student;
import com.xilin.management.school.model.StudentRepository;
import com.xilin.management.school.web.util.MessageFactory;
import com.xilin.management.school.web.util.TransientUser;
import com.xilin.management.school.web.util.Utils;

@ManagedBean(name = "familyBean")
@SessionScoped
@Configurable
public class FamilyBean implements Serializable {

	private static final Logger logger = Logger.getLogger(FamilyBean.class);
	
	@Autowired
    FamilyRepository familyRepository;

	@Autowired
    StudentRepository studentRepository;
	
	private String name = "Familys";

	private Family family;
	
	private Student student;

	//private List<Family> allFamilys;

	//private boolean dataVisible = false;

	//private List<String> columns;

	//private HtmlPanelGrid createPanelGrid;

	//private HtmlPanelGrid editPanelGrid;

	//private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	private List<Registration> selectedRegistrations;

	private List<Student> selectedStudents;

	private String familyLogin;
	private String familyPassword;
	private String familyConfirmPassword;
	private Date dobDate;
	
	private String strSearchTerm = "";
	private LazyDataModel<Family> familyLazyModel;
	private List<Family> filteredFamily;
	
	@PostConstruct
    public void init() {
		
		FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
		.getAutowireCapableBeanFactory().autowireBean(this);
		
    }

	public String getName() {
        return name;
    }

	public String findAllFamilys() {
        //allFamilys = familyRepository.findAll();
        familyLazyModel = new MyLazyFamilyDataModel(familyRepository, null, null);

        //dataVisible = !allFamilys.isEmpty();
        return "/pages/admin/family";
    }
	
	public Family getFamily() {
        if (family == null) {
            family = new Family();
        }
        return family;
    }

	public void setFamily(Family family) {
        this.family = family;
    }

	public Student getStudent() {
		if (student == null) {
			student = new Student();
        }
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Registration> getSelectedRegistrations() {
        return selectedRegistrations;
    }

	public void setSelectedRegistrations(List<Registration> selectedRegistrations) {
        if (selectedRegistrations != null) {
            family.setRegistrations(new HashSet<Registration>(selectedRegistrations));
        }
        this.selectedRegistrations = selectedRegistrations;
    }

	public List<Student> getSelectedStudents() {
        return selectedStudents;
    }

	public void setSelectedStudents(List<Student> selectedStudents) {
        if (selectedStudents != null) {
            family.setStudents(new HashSet<Student>(selectedStudents));
        }
        this.selectedStudents = selectedStudents;
    }

	public String onEdit() {
        /*if (family != null && family.getRegistrations() != null) {
            selectedRegistrations = new ArrayList<Registration>(family.getRegistrations());
        }
        if (family != null && family.getStudents() != null) {
            selectedStudents = new ArrayList<Student>(family.getStudents());
        }*/
        return null;
    }
	
	public String onEditStudent() {
		dobDate = this.student.getDob().getTime();
        /*if (family != null && family.getRegistrations() != null) {
            selectedRegistrations = new ArrayList<Registration>(family.getRegistrations());
        }
        if (family != null && family.getStudents() != null) {
            selectedStudents = new ArrayList<Student>(family.getStudents());
        }*/
        return null;
    }
	

	public boolean isCreateDialogVisible() {
        return createDialogVisible;
    }

	public void setCreateDialogVisible(boolean createDialogVisible) {
        this.createDialogVisible = createDialogVisible;
    }

	public String displayList() {
        createDialogVisible = false;
        strSearchTerm = "";
        familyLazyModel = new MyLazyFamilyDataModel(familyRepository, null, null);

        return "/pages/admin/family";
    }

	public String searchFamilies() {
		familyLazyModel = new MyLazyFamilyDataModel(familyRepository, strSearchTerm, null);
		return "/pages/admin/family";
	}
	
	public String displayCreateDialog() {
        family = new Family();
        createDialogVisible = true;
        return "/pages/admin/family";
    }

	public String persistUserChangePassword() {
        String message = "";
        String pwd = familyPassword;
        //String loginId = family.getLoginId();
        
        // REST update the user site
        if(Utils.updateUserPwdJson(family.getExternaluserid(), pwd)){
        	message = "message_successfully_updated";
		}
        else {
        	message = "message_insuccessfully_updated";
        }
       
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('changePasswordDialogWidget').hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Family User");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return null;
    }
	
	public String persist() {
        String message = "";
        String loginUsername = Utils.retrieveLoginUsername();
		
        if (family.getId() != null) {
        	int status = validFamilyInput();
    		
    		if(status == Utils.STATUS_NO_PARENT_INFO) {
    			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
    					" At least on parent/guardian information is needed!", "");
    			FacesContext.getCurrentInstance().addMessage("editForm:fatherfirstnameedit", facesMessage);
    			return null;
    	    }
    		else if(status == Utils.STATUS_NO_PHONE_INFO) {
    			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
    					" At least one contct phone number is needed!", "");
    			FacesContext.getCurrentInstance().addMessage("editForm:homephoneedit", facesMessage);
    			return null;
    	    }
    		
    		//Check whether the family possibly already existing??
			List<Family> result = familyRepository.findByEmailIgnoreCase(family.getEmail());
			if (result.size() > 1) {
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
    					"More than one Family with the provided email has existed!", "");
				FacesContext.getCurrentInstance().addMessage("editForm:familyemailedit", facesMessage);
    			return null;
			}
			
			//Also update the user information thru REST (loginId and email)
			if(Utils.updateUserJson(family.getExternaluserid(), 
					family.getLoginId().trim(), family.getEmail().trim())){
				family.setUpdatedBy(loginUsername);
    			family.setUpdatedtime(GregorianCalendar.getInstance());
				familyRepository.save(family);
	            
			}
			
			//Create or update father and mother as students 
            //A little lessss-- No need for now.
            
            message = "message_successfully_updated";
        } else {
            if(!familyLogin.isEmpty() && !familyPassword.isEmpty()
        			&& !family.getEmail().isEmpty()) {
        		int status = validFamilyInput();
        		
        		if(status == Utils.STATUS_NO_PARENT_INFO) {
        			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
        					" At least on parent/guardian information is needed!", "");
        			FacesContext.getCurrentInstance().addMessage("createForm:fatherfirstname", facesMessage);
        			return null;
        	    }
        		else if(status == Utils.STATUS_NO_PHONE_INFO) {
        			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
        					" At least one contct phone number is needed!", "");
        			FacesContext.getCurrentInstance().addMessage("createForm:homephone", facesMessage);
        			return null;
        	    }
        		
        		try {
        			//Check whether the family possibly already existing??
    				List<Family> result = familyRepository.findByEmailOrLoginIdIgnoreCase(family.getEmail(), familyLogin);
    				if (!result.isEmpty()) {
    					FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
            					" Family with the provided email or username has existed!", "");
    					FacesContext.getCurrentInstance().addMessage("createForm:familyemail", facesMessage);
            			return null;
    				}
    				
        			TransientUser auser = Utils.createUserJson(familyLogin, familyPassword, 
        						family.getEmail(), Utils.ROLE_XILINFAMILY);
        			if(auser != null) {
	        			family.setExternaluserid(auser.getId());
	        			family.setLoginId(auser.getLoginId());
	        			family.setActive(true);
	        			family.setAddress2("");
	        			family.setState("IL");
	        			family.setType('F');
	        			
	        			//below field to use the login username
	        			family.setUpdatedBy(loginUsername);
	        			family.setFathermiddlename("");
	        			family.setMothermiddlename("");
	        			family.setUpdatedtime(GregorianCalendar.getInstance());
	        			family = familyRepository.save(family);
	        			
	        			//Create father and mother as students
	        			//Add later -- no need to be here.
        			}
        			
        		}
        		catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('createDialogWidget').hide()");
        context.execute("PF('editDialogWidget').hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Family");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllFamilys();
    }

	public String deactivateFamily() {
		family.setActive(false);
		family.setUpdatedBy(Utils.retrieveLoginUsername());
		family.setUpdatedtime(GregorianCalendar.getInstance());
        familyRepository.save(family);
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_updated", "Family");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllFamilys();
    }
	
	public String deactivateStudent() {
		student.setActive(false);
		student.setUpdatedby(Utils.retrieveLoginUsername());
		student.setUpdatedtime(GregorianCalendar.getInstance());
		studentRepository.save(student);
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_updated", "Student");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllFamilys();
    }
	
	public String delete() {
        familyRepository.delete(family);
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Family");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllFamilys();
    }
	
	public String persistStudent() {
        String message = "";
        Calendar calendar = GregorianCalendar.getInstance();
        if (student.getId() != null) {
        	calendar.setTime(dobDate);
        	student.setDob(calendar);
        	//student.setFamilyid(family);
        	student.setUpdatedby(Utils.retrieveLoginUsername());
        	student.setUpdatedtime(GregorianCalendar.getInstance());
            studentRepository.save(student);
            message = "message_successfully_updated";
        } else {
        	calendar.setTime(dobDate);
        	student.setDob(calendar);
        	student.setFamilyid(family);
        	student.setActive(true);
        	student.setUpdatedby(Utils.retrieveLoginUsername());
        	student.setUpdatedtime(GregorianCalendar.getInstance());
            studentRepository.save(student);
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('addStudentWidget').hide()");
        context.execute("PF('editStudentDialogWidget').hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Student");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllFamilys();
    }
	
	public void onRowToggle(ToggleEvent event) {
		family = (Family) event.getData();
		selectedStudents = studentRepository.findByFamilyid(family);
		
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected Family", "LoginId:" + ((Family) event.getData()).getLoginId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	private int validFamilyInput() {
		if(family.getFatherfirstname().isEmpty() 
				&& family.getFatherlastname().isEmpty()
				&& family.getMotherfirstname().isEmpty()
				&&family.getMotherlastname().isEmpty()) {
			return Utils.STATUS_NO_PARENT_INFO;
		}
		
		if(family.getCellphone().isEmpty() 
				&& family.getHomephone().isEmpty()) {
			return Utils.STATUS_NO_PHONE_INFO;
		}
		return Utils.STATUS_OK;
	}
	
	public void reset() {
        family = null;
        selectedRegistrations = null;
        selectedStudents = null;
        createDialogVisible = false;
        student = null;
        dobDate = null;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	public String getFamilyLogin() {
		return familyLogin;
	}

	public void setFamilyLogin(String familyLogin) {
		this.familyLogin = familyLogin;
	}

	public String getFamilyPassword() {
		return familyPassword;
	}

	public void setFamilyPassword(String familyPassword) {
		this.familyPassword = familyPassword;
	}

	public String getFamilyConfirmPassword() {
		return familyConfirmPassword;
	}

	public void setFamilyConfirmPassword(String familyConfirmPassword) {
		this.familyConfirmPassword = familyConfirmPassword;
	}

	public String getStrSearchTerm() {
		return strSearchTerm;
	}

	public void setStrSearchTerm(String strSearchTerm) {
		this.strSearchTerm = strSearchTerm;
	}

	public LazyDataModel<Family> getFamilyLazyModel() {
		return familyLazyModel;
	}

	public void setFamilyLazyModel(LazyDataModel<Family> familyLazyModel) {
		this.familyLazyModel = familyLazyModel;
	}

	public List<Family> getFilteredFamily() {
		return filteredFamily;
	}

	public void setFilteredFamily(List<Family> filteredFamily) {
		this.filteredFamily = filteredFamily;
	}

	public Date getDobDate() {
		return dobDate;
	}

	public void setDobDate(Date dobDate) {
		this.dobDate = dobDate;
	}

	private static final long serialVersionUID = 1L;
}
