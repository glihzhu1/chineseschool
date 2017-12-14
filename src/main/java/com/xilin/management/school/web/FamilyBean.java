package com.xilin.management.school.web;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.jsf.FacesContextUtils;

import com.xilin.management.school.model.Bookitem;
import com.xilin.management.school.model.BookitemRepository;
import com.xilin.management.school.model.Family;
import com.xilin.management.school.model.FamilyRepository;
import com.xilin.management.school.model.Familybilling;
import com.xilin.management.school.model.FamilybillingRepository;
import com.xilin.management.school.model.Familytransaction;
import com.xilin.management.school.model.FamilytransactionRepository;
import com.xilin.management.school.model.MyCustomSchoolService;
import com.xilin.management.school.model.Registration;
import com.xilin.management.school.model.Semester;
import com.xilin.management.school.model.SemesterRepository;
import com.xilin.management.school.model.Semestercourse;
import com.xilin.management.school.model.SemestercourseRepository;
import com.xilin.management.school.model.Semesterfamilypod;
import com.xilin.management.school.model.SemesterfamilypodRepository;
import com.xilin.management.school.model.Semesterpod;
import com.xilin.management.school.model.SemesterpodRepository;
import com.xilin.management.school.model.Semesterweek;
import com.xilin.management.school.model.SemesterweekRepository;
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
	
	@Autowired
    SemestercourseRepository semestercourseRepository;

	@Autowired
	private SemesterRepository semesterRepository;
	
	@Autowired
	private SemesterweekRepository semesterweekRepository;
	
	@Autowired
	private SemesterpodRepository semesterpodRepository;
	
	@Autowired
	private SemesterfamilypodRepository semesterfamilypodRepository;
	
	@Autowired
	private BookitemRepository bookitemRepository;
	
	@Autowired
	private FamilytransactionRepository familytransactionRepository;
	
	@Autowired
	private FamilybillingRepository familybillingRepository;
	
	@Autowired
	private MyCustomSchoolService myCustomSchoolService;
	
	
	private String name = "Familys";

	private Family family;
	
	private Student student;

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
	
	//private boolean skip;
	private Semestercourse semestercourse;
	private List<Semestercourse> filteredSemestercourses;
	private List<Semestercourse> allSemestercourses;
	
	private Familytransaction familytransaction;
	// List of regitered classes
	private List<Familytransaction> registeredFamilytransactions;
	//private List<Familytransaction> deregisteredFamilytransactions;
	private List<Familybilling> familybillings;
	private Familybilling familybilling;
	
	private Bookitem bookitem;
	private List<Bookitem> filteredBookitems;
	private List<Bookitem> allBookitems;
	
	private List<Familytransaction> buybookFamilytransactions;
	private List<Familytransaction> removebookFamilytransactions;

	private Semesterfamilypod semesterfamilypod;
	private Set<Semesterweek> allSemesterweeks;
	private boolean checkedPod;
	
	private Semester latestSemester;
	private boolean showChecknum;
	
	private DashboardModel boardModel;
	
	@PostConstruct
    public void init() {
		
		FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
		.getAutowireCapableBeanFactory().autowireBean(this);
		
		boardModel = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        //DashboardColumn column3 = new DefaultDashboardColumn();
         
        column1.addWidget("actions");
        column2.addWidget("accountSetting");
 
        boardModel.addColumn(column1);
        boardModel.addColumn(column2);
        //boardModel.addColumn(column3);
        
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        if(request.isUserInRole(Utils.ROLE_XILINFAMILY)) {
        	String loginUser = Utils.retrieveLoginUsername();
     		List<Family> loginFamilies = familyRepository.findByLoginIdIgnoreCase(loginUser);
     		
     		if(loginFamilies != null && loginFamilies.size() > 0) {
     			family = loginFamilies.get(0);
     			selectedStudents = studentRepository.findByFamilyid(family);
     		}
        }
        
       
		
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
        //context.execute("PF('createDialogWidget').hide()");
        //context.execute("PF('editDialogWidget').hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Family");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        
        // admin and family navigate differently
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        if(request.isUserInRole(Utils.ROLE_XILINADMIN)) {
        	return findAllFamilys();
        }
        else 
        	return null;
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
	
	/*public String deactivateStudent() {
		student.setActive(false);
		student.setUpdatedby(Utils.retrieveLoginUsername());
		student.setUpdatedtime(GregorianCalendar.getInstance());
		studentRepository.save(student);
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_updated", "Student");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllFamilys();
    }*/
	
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
        //context.execute("PF('editStudentDialogWidget').hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Student");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        if(request.isUserInRole(Utils.ROLE_XILINFAMILY)) {
        	String loginUser = Utils.retrieveLoginUsername();
     		List<Family> loginFamilies = familyRepository.findByLoginIdIgnoreCase(loginUser);
     		
     		if(loginFamilies != null && loginFamilies.size() > 0) {
     			family = loginFamilies.get(0);
     			selectedStudents = studentRepository.findByFamilyid(family);
     		}
     		return null;
        }
        else if (request.isUserInRole(Utils.ROLE_XILINADMIN)) {
        	reset();
            return findAllFamilys();
        }
        else
        	return null;
    }
	
	public void onRowToggle(ToggleEvent event) {
		family = (Family) event.getData();
		selectedStudents = studentRepository.findByFamilyid(family);
		
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected Family", "LoginId:" + ((Family) event.getData()).getLoginId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void onChangeFamilyAction() {
		Map<String,String> params =
			FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		String familyrowid = params.get("familyrowid");
		
		family = familyRepository.findOne(Integer.valueOf(familyrowid));
	}
	
	/*public void onChangeCheckRegistered(ValueChangeEvent e) {
		boolean chosen = (boolean)e.getNewValue();
		
	}*/
	
	public String regiterStudent() {
		//Add the selected ones into registered classes list
		List<Familytransaction> newAddedCls = new ArrayList<Familytransaction>();
		for(Semestercourse cls : allSemestercourses) {
			if(cls.isSelected() && 
					!clsRegisteredAlready(cls, registeredFamilytransactions)) {
				Familytransaction newCls = new Familytransaction();
				newCls.setSemestercourseid(cls);
				newCls.setFamilyid(student.getFamilyid());
				newCls.setStudentid(student);
				newCls.setSemesterid(cls.getSemesterid());
				newCls.setTransactiontype(Utils.FAMILY_TRANSACTION_REGISTER);
				
				if(cls.isSelectedbook()) {
					newCls.setBookitemid(cls.getBookitemid());
					newCls.setSelectedbook(true);
					newCls.setStatus(Utils.BOOK_TRANSACTION_ORERED);
				}
				else {
					newCls.setBookitemid(null);
					newCls.setStatus("");
					newCls.setSelectedbook(false);
				}
				
				newCls.setActive(true);
				newCls.setRegistereddate(GregorianCalendar.getInstance());
				newCls.setUpdatedby(Utils.retrieveLoginUsername());
				newCls.setUpdatedtime(GregorianCalendar.getInstance());
				
				newAddedCls.add(newCls);
			}
		}
		//if(!newAddedCls.isEmpty())
		//	registeredFamilytransactions.addAll(newAddedCls);
		if(!newAddedCls.isEmpty()) {
			myCustomSchoolService.saveStudentRegisteredFamilytransactions(newAddedCls);
			registeredFamilytransactions.addAll(newAddedCls);  //Just refresh GUI
		}
		
		String message = "message_successfully_updated";
		FacesMessage facesMessage = MessageFactory.getMessage(message, "Student Classes");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
	}
	
	public String onProcessPayment() {
		Semester top1sem = semesterRepository.findFirstByOrderByRegisterstartdateDesc();
		if(top1sem != null)
			latestSemester = top1sem;
		
		registeredFamilytransactions = 
			familytransactionRepository.findBySemesteridAndFamilyidOrderByStudentidAsc(latestSemester, family);
			
		familybillings =
			familybillingRepository.findBySemesteridAndFamilyidOrderByBillingtypeDesc(latestSemester, family);
			
		return null;
	}
	
	public String onRegisterStudent() {
		// retrieve all the registered classes and available classes
		//deregisteredFamilytransactions = new ArrayList<Familytransaction>();
		
		Semester top1sem = semesterRepository.findFirstByOrderByRegisterstartdateDesc();
		if(top1sem != null)
			latestSemester = top1sem;
		
		allSemestercourses = semestercourseRepository.findBySemesteridOrderBySemestercoursecodeAsc(latestSemester);
        
		registeredFamilytransactions = 
			familytransactionRepository.findBySemesteridAndStudentidAndTransactiontype(latestSemester, student, Utils.FAMILY_TRANSACTION_REGISTER);
		
		for(Semestercourse cls : allSemestercourses) {
			for(Familytransaction registeredCls : registeredFamilytransactions) {
				if(registeredCls.getSemestercourseid().getId().equals(cls.getId())) {
					//the class has been registered
					cls.setSelected(true);
					if(registeredCls.getBookitemid() != null){
						registeredCls.setSelectedbook(true);
					}
					else {
						registeredCls.setSelectedbook(false);
					}
					continue;
				}
			}
			//default order book for all class with book
			if(cls.getBookitemid() != null) {
				cls.setSelectedbook(true);
			}
		}
		return null;
	}
	
	public void deleteBookRegisteredCls() {
		//myCustomSchoolService.changeRegistrationBookSelection(familytransaction);
		String loginUser = Utils.retrieveLoginUsername();
		Calendar currentTime = GregorianCalendar.getInstance();
		
		familytransaction.setUpdatedby(loginUser);
		familytransaction.setUpdatedtime(currentTime);
		familytransaction.setBookitemid(null);
		familytransaction.setStatus("");
		
		familytransactionRepository.save(familytransaction);
	}
	
	public void addBookRegisteredCls() {
		String loginUser = Utils.retrieveLoginUsername();
		Calendar currentTime = GregorianCalendar.getInstance();
		
		familytransaction.setUpdatedby(loginUser);
		familytransaction.setUpdatedtime(currentTime);
		familytransaction.setBookitemid(familytransaction.getSemestercourseid().getBookitemid());
		familytransaction.setStatus(Utils.BOOK_TRANSACTION_ORERED);
		
		familytransactionRepository.save(familytransaction);
	}
	
	public void deleteRegisteredCls() {
		familytransaction.getSemestercourseid().setSelected(false);
		//deregisteredFamilytransactions.add(familytransaction);
		
		myCustomSchoolService.deleteRegisteredCls(familytransaction);
		registeredFamilytransactions.remove(familytransaction);
		
	}
	
	public void deleteFromPaymentRegisteredCls() {
		myCustomSchoolService.deleteRegisteredCls(familytransaction);
		registeredFamilytransactions.remove(familytransaction);
	}
	
	public String orderBooks() {
		//Add the selected ones into buy list
		List<Familytransaction> newAddedBooks = new ArrayList<Familytransaction>();
		for(Bookitem book : allBookitems) {
			if(book.isSelected()) {
				Familytransaction newBuy = new Familytransaction();
				newBuy.setFamilyid(family);
				newBuy.setSemesterid(latestSemester);
				newBuy.setTransactiontype(Utils.FAMILY_TRANSACTION_BUYBOOK);
				newBuy.setBookitemid(book);
				newBuy.setStatus(Utils.BOOK_TRANSACTION_ORERED);
				
				newBuy.setActive(true);
				newBuy.setRegistereddate(GregorianCalendar.getInstance());
				newBuy.setUpdatedby(Utils.retrieveLoginUsername());
				newBuy.setUpdatedtime(GregorianCalendar.getInstance());
				
				newAddedBooks.add(newBuy);
				
			}
			book.setSelected(false); //for later re-select OK to buy > 1
		}
		
		myCustomSchoolService.saveBoughtBooksFamilytransactions(newAddedBooks);
		buybookFamilytransactions.addAll(newAddedBooks); // just for GUI refresh
		
		String message = "message_successfully_updated";
		FacesMessage facesMessage = MessageFactory.getMessage(message, "Student Classes");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
	}
	
	public String onBuyBook() {
		removebookFamilytransactions = new ArrayList<Familytransaction>();
		
		Map<String,String> params =
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		String familyrowid = params.get("familyrowid");
			
		family = familyRepository.findOne(Integer.valueOf(familyrowid));
			
		Semester top1sem = semesterRepository.findFirstByOrderByRegisterstartdateDesc();
		if(top1sem != null)
			latestSemester = top1sem;
		
		allBookitems = bookitemRepository.findAllByOrderByBookcodeAsc();
        
		// all books either thru class registration or book only
		buybookFamilytransactions = 
			familytransactionRepository.findBySemesteridAndFamilyidAndBookitemidNotNullOrderByStatusAsc(latestSemester, family);
		
		return null;
	}
	
	public void deleteBoughtBook() {
		if(buybookFamilytransactions != null &&
				buybookFamilytransactions.size() > 0)
			buybookFamilytransactions.remove(familytransaction);
		//removebookFamilytransactions.add(familytransaction);
		
		//Perform delete/update for this action
		myCustomSchoolService.deleteBoughtBook(familytransaction);
		
	}
	
	public void deleteBoughtBookFromPayment() {
		if(registeredFamilytransactions != null &&
				registeredFamilytransactions.size() > 0 &&
				familytransaction.getTransactiontype().equalsIgnoreCase(Utils.FAMILY_TRANSACTION_BUYBOOK))
			registeredFamilytransactions.remove(familytransaction);
		
		myCustomSchoolService.deleteBoughtBook(familytransaction);
		
	}
	
	public void processBoughtBook() {
		myCustomSchoolService.processBoughtBook(familytransaction);
		
	}
	
	public void returnBoughtBook() {
		myCustomSchoolService.returnBoughtBook(familytransaction);
		
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
        registeredFamilytransactions = null;
        allSemestercourses = null;
        buybookFamilytransactions = null;
        removebookFamilytransactions = null;
        
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

	public FamilyRepository getFamilyRepository() {
		return familyRepository;
	}

	public void setFamilyRepository(FamilyRepository familyRepository) {
		this.familyRepository = familyRepository;
	}

	public StudentRepository getStudentRepository() {
		return studentRepository;
	}

	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public SemestercourseRepository getSemestercourseRepository() {
		return semestercourseRepository;
	}

	public void setSemestercourseRepository(
			SemestercourseRepository semestercourseRepository) {
		this.semestercourseRepository = semestercourseRepository;
	}

	public SemesterRepository getSemesterRepository() {
		return semesterRepository;
	}

	public void setSemesterRepository(SemesterRepository semesterRepository) {
		this.semesterRepository = semesterRepository;
	}

	public BookitemRepository getBookitemRepository() {
		return bookitemRepository;
	}

	public void setBookitemRepository(BookitemRepository bookitemRepository) {
		this.bookitemRepository = bookitemRepository;
	}

	public List<Semestercourse> getAllSemestercourses() {
		return allSemestercourses;
	}

	public void setAllSemestercourses(List<Semestercourse> allSemestercourses) {
		this.allSemestercourses = allSemestercourses;
	}

	public List<Familytransaction> getRegisteredFamilytransactions() {
		return registeredFamilytransactions;
	}

	public void setRegisteredFamilytransactions(
			List<Familytransaction> registeredFamilytransactions) {
		this.registeredFamilytransactions = registeredFamilytransactions;
	}

	public Semester getLatestSemester() {
		return latestSemester;
	}

	public void setLatestSemester(Semester latestSemester) {
		this.latestSemester = latestSemester;
	}

	public FamilytransactionRepository getFamilytransactionRepository() {
		return familytransactionRepository;
	}

	public void setFamilytransactionRepository(
			FamilytransactionRepository familytransactionRepository) {
		this.familytransactionRepository = familytransactionRepository;
	}

	public Semestercourse getSemestercourse() {
		return semestercourse;
	}

	public void setSemestercourse(Semestercourse semestercourse) {
		this.semestercourse = semestercourse;
	}

	public List<Semestercourse> getFilteredSemestercourses() {
		return filteredSemestercourses;
	}

	public void setFilteredSemestercourses(
			List<Semestercourse> filteredSemestercourses) {
		this.filteredSemestercourses = filteredSemestercourses;
	}
	
	public Familytransaction getFamilytransaction() {
		return familytransaction;
	}

	public void setFamilytransaction(Familytransaction familytransaction) {
		this.familytransaction = familytransaction;
	}

	public List<Familytransaction> getBuybookFamilytransactions() {
		return buybookFamilytransactions;
	}

	public void setBuybookFamilytransactions(
			List<Familytransaction> buybookFamilytransactions) {
		this.buybookFamilytransactions = buybookFamilytransactions;
	}

	public List<Familytransaction> getRemovebookFamilytransactions() {
		return removebookFamilytransactions;
	}

	public void setRemovebookFamilytransactions(
			List<Familytransaction> removebookFamilytransactions) {
		this.removebookFamilytransactions = removebookFamilytransactions;
	}


	public Bookitem getBookitem() {
		return bookitem;
	}

	public void setBookitem(Bookitem bookitem) {
		this.bookitem = bookitem;
	}

	public List<Bookitem> getFilteredBookitems() {
		return filteredBookitems;
	}

	public void setFilteredBookitems(List<Bookitem> filteredBookitems) {
		this.filteredBookitems = filteredBookitems;
	}

	public List<Bookitem> getAllBookitems() {
		return allBookitems;
	}

	public void setAllBookitems(List<Bookitem> allBookitems) {
		this.allBookitems = allBookitems;
	}

	public FamilybillingRepository getFamilybillingRepository() {
		return familybillingRepository;
	}

	public void setFamilybillingRepository(
			FamilybillingRepository familybillingRepository) {
		this.familybillingRepository = familybillingRepository;
	}

	public MyCustomSchoolService getMyCustomSchoolService() {
		return myCustomSchoolService;
	}

	public void setMyCustomSchoolService(MyCustomSchoolService myCustomSchoolService) {
		this.myCustomSchoolService = myCustomSchoolService;
	}

	public List<Familybilling> getFamilybillings() {
		return familybillings;
	}

	public void setFamilybillings(List<Familybilling> familybillings) {
		this.familybillings = familybillings;
	}

	public Familybilling getFamilybilling() {
		return familybilling;
	}

	public void setFamilybilling(Familybilling familybilling) {
		this.familybilling = familybilling;
	}

	private boolean clsRegisteredAlready(Semestercourse cls, List<Familytransaction> registeredFamilytransactions) {
		for(Familytransaction rgs : registeredFamilytransactions) {
			if(rgs.getSemestercourseid().getId()==cls.getId()) {
				return true;
			}
		}
		return false;
	}

	public BigDecimal getFamilyDue() {
		BigDecimal familyDue = new BigDecimal(0);
		if(registeredFamilytransactions == null 
			|| registeredFamilytransactions.size() == 0) {
			return familyDue;
		}
		
		familyDue = familyDue.add(getTotalTransactionfee());
		familyDue = familyDue.subtract(getBillingBalance());
		
		familyDue = familyDue.add(latestSemester.getPodfee());
		familyDue = familyDue.add(latestSemester.getRegistrationfee());
		
		if(isQualifyMultiClassDiscount()) {
			familyDue = familyDue.subtract(latestSemester.getDiscountamount());
		}
		
		if(isQualifyEarlyPayDiscountFortoday()) {
			familyDue = familyDue.subtract(latestSemester.getRegistrationfee());
		}
		
		return familyDue;
    }

	public BigDecimal getBillingBalance() {
		BigDecimal billingBalance = new BigDecimal(0);
		for(Familybilling bill : familybillings) {
			if(bill.getBillingtype().equalsIgnoreCase(Utils.BILLING_TYPE_PAYMENT) 
				|| bill.getBillingtype().equalsIgnoreCase(Utils.BILLING_TYPE_CREDIT)) {
				billingBalance = billingBalance.add(bill.getAmount());
			}
			
			//refund can be calculated similar to fine in terms of balance due.
			if(bill.getBillingtype().equalsIgnoreCase(Utils.BILLING_TYPE_FINE)
				|| bill.getBillingtype().equalsIgnoreCase(Utils.BILLING_TYPE_REFUND)) {
				billingBalance = billingBalance.subtract(bill.getAmount());
			}
		}
		return billingBalance;
    }
	
	public BigDecimal getTotalTransactionfee() {
		BigDecimal total = new BigDecimal(0);
		for(Familytransaction trans : registeredFamilytransactions) {
			total = total.add(trans.getFee());
		}
		return total;
    }

	public boolean isQualifyMultiClassDiscount() {
		boolean qualifies = false;
		if(registeredFamilytransactions.size() > 1) {
			for(Familytransaction trans : registeredFamilytransactions) {
				String courseCode = trans.getSemestercourseid().getSemestercoursecode().toUpperCase();
				if(courseCode.startsWith("CHN") || 
						courseCode.startsWith("MTH")) {
					qualifies = true;
					break;
				}
			}
		}
		return qualifies;
    }
	
	public boolean isQualifyEarlyPayDiscountFortoday() {
		GregorianCalendar todayCalendar= new GregorianCalendar();
		if(latestSemester.getDiscountdate().compareTo(todayCalendar) > 0) { //before Discountdate
			return true;
		}
		
		if(familybillings != null && familybillings.size() > 0) {
			for(Familybilling bill: familybillings) {
				if(latestSemester.getDiscountdate().compareTo(bill.getProcesstime()) > 0) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public String onMakePayment() {
        familybilling = new Familybilling();
        familybilling.setFamilyid(family);
        familybilling.setSemesterid(latestSemester);
        familybilling.setBillingtype(Utils.BILLING_TYPE_PAYMENT);
        familybilling.setProcesstime(GregorianCalendar.getInstance());
        
        showChecknum = true;
        
        return null;
    }
	
	public String persistPayment() {
        String message = "";
        String loginUsername = Utils.retrieveLoginUsername();
		
        if (familybilling.getId() != null) {
        	familybilling.setUpdatedby(loginUsername);
        	familybilling.setUpdatedtime(GregorianCalendar.getInstance());
        	if(familybilling.getBillingtype().equalsIgnoreCase(Utils.BILLING_TYPE_FINE) ||
        			familybilling.getBillingtype().equalsIgnoreCase(Utils.BILLING_TYPE_CREDIT)) {
        		familybilling.setChecknum("");
        	}
        	
        	familybillingRepository.save(familybilling);
	        
            message = "message_successfully_updated";
        } else {
        	familybilling.setUpdatedby(loginUsername);
    		familybilling.setUpdatedtime(GregorianCalendar.getInstance());
    		familybilling.setStatus(Utils.BILLING_STATUS_PROCESSED);
    		familybillingRepository.save(familybilling);
    		
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('createPaymentDialogWidget').hide()");
        context.execute("PF('editPaymentDialogWidget').hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Billing");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        familybillings =
        		familybillingRepository.findBySemesteridAndFamilyidOrderByBillingtypeDesc(latestSemester, family);
        return null;
    }
	
	/*public void deletePayment() {
		String loginUser = Utils.retrieveLoginUsername();
		Calendar currentTime = GregorianCalendar.getInstance();
		
		familybilling.setUpdatedby(loginUser);
		familybilling.setUpdatedtime(currentTime);
		familybilling.setStatus(Utils.BILLING_STATUS_DEACTIVATED);
		familybillingRepository.save(familybilling);
	}*/
	
	public void listenerShowChecknum() {
		showChecknum = false;
		if(familybilling.getBillingtype().equalsIgnoreCase(Utils.BILLING_TYPE_PAYMENT) ||
			familybilling.getBillingtype().equalsIgnoreCase(Utils.BILLING_TYPE_REFUND)) {
			showChecknum = true;
		}
	}
	
	/*public String displayFamilyStudents() {
		String loginUser = Utils.retrieveLoginUsername();
		List<Family> loginFamilies = familyRepository.findByLoginIdIgnoreCase(loginUser);
		
		if(loginFamilies != null && loginFamilies.size() > 0) {
			family = loginFamilies.get(0);
			selectedStudents = studentRepository.findByFamilyid(family);
		}
		else {
			family = null;
			selectedStudents = null;
		}
		
        return null;
    }*/
	
	public String queryFamilyInfo() {
		String loginUser = Utils.retrieveLoginUsername();
		List<Family> loginFamilies = familyRepository.findByLoginIdIgnoreCase(loginUser);
		
		if(loginFamilies != null && loginFamilies.size() > 0) {
			family = loginFamilies.get(0);
		}
		else {
			family = null;
		}
		
        return null;
    }
	
	public String queryFamilyInfoAndAddStudent() {
		String loginUser = Utils.retrieveLoginUsername();
		List<Family> loginFamilies = familyRepository.findByLoginIdIgnoreCase(loginUser);
		
		if(loginFamilies != null && loginFamilies.size() > 0) {
			family = loginFamilies.get(0);
		}
		else {
			// error msg
			
			return null;
		}
		
		student = new Student();
		student.setFamilyid(family);
		
        return null;
    }
	
	public String queryFamilyInfoAndManagebooks() {
		String loginUser = Utils.retrieveLoginUsername();
		List<Family> loginFamilies = familyRepository.findByLoginIdIgnoreCase(loginUser);
		
		if(loginFamilies != null && loginFamilies.size() > 0) {
			family = loginFamilies.get(0);
			
			removebookFamilytransactions = new ArrayList<Familytransaction>();
			Semester top1sem = semesterRepository.findFirstByOrderByRegisterstartdateDesc();
			if(top1sem != null)
				latestSemester = top1sem;
			
			allBookitems = bookitemRepository.findAllByOrderByBookcodeAsc();
		    buybookFamilytransactions = 
				familytransactionRepository.findBySemesteridAndFamilyidAndBookitemidNotNullOrderByStatusAsc(latestSemester, family);
			
		}
		else {
			// error msg
			
			return null;
		}
		
        return null;
    }
	
	public String reserveFamilyPod() {
		if(semesterfamilypod != null) {
			semesterfamilypodRepository.delete(semesterfamilypod);
			//also update pod filled number et al
			Integer filled = semesterfamilypod.getSemesterpodid().getFilled();
			filled = filled - 1;
			semesterfamilypod.getSemesterpodid().setFilled(filled);
			
			semesterpodRepository.save(semesterfamilypod.getSemesterpodid());
		}
		semesterfamilypod = new Semesterfamilypod();
		for(Semesterweek wk: allSemesterweeks) {
			for(Semesterpod pod: wk.getSemesterpods()) {
				if(pod.isSelected()) {
					semesterfamilypod.setFamilyid(family);
					semesterfamilypod.setPerformed(false);
					semesterfamilypod.setSemesterpodid(pod);
					semesterfamilypod.setUpdatedby(Utils.retrieveLoginUsername());
					semesterfamilypod.setUpdatedtime(Calendar.getInstance());
					
					semesterfamilypodRepository.save(semesterfamilypod);
					
					//also update pod filled number et al
					Integer filled = semesterfamilypod.getSemesterpodid().getFilled();
					filled = filled + 1;
					semesterfamilypod.getSemesterpodid().setFilled(filled);
					
					semesterpodRepository.save(semesterfamilypod.getSemesterpodid());
				}
			}
		}
		return null;
	}
	
	public void updatePodSelected(AjaxBehaviorEvent event) {
		UIComponent component = event.getComponent();
		Semesterpod selectedPod = (Semesterpod)component.getAttributes().get("pod");
	    
		for(Semesterweek wk: allSemesterweeks) {
			for(Semesterpod pod: wk.getSemesterpods()) {
				if(!pod.getId().equals(selectedPod.getId())) {
					pod.setSelected(false);
				}
			}
		}
	}
	
	public String queryFamilyInfoAndReservePod() {
		String loginUser = Utils.retrieveLoginUsername();
		List<Family> loginFamilies = familyRepository.findByLoginIdIgnoreCase(loginUser);
		
		if(loginFamilies != null && loginFamilies.size() > 0) {
			family = loginFamilies.get(0);
			Semester top1sem = semesterRepository.findFirstByOrderByRegisterstartdateDesc();
			if(top1sem != null)
				latestSemester = top1sem;
			
			allSemesterweeks = semesterweekRepository.queryAllSemesterweeksOfSemester(latestSemester);
		
			Set<Semesterfamilypod> familypods = semesterfamilypodRepository.findFamilyPodBySemesterAndFamily(latestSemester, family);
			if(familypods != null && familypods.size()>0) {
				for(Semesterfamilypod fpod : familypods) {
					semesterfamilypod = fpod;
					break;
				}
			}
			
			for(Semesterweek wk: allSemesterweeks) {
				for(Semesterpod pod: wk.getSemesterpods()) {
					if(semesterfamilypod != null &&
						pod.getId().equals(semesterfamilypod.getSemesterpodid().getId())) {
						pod.setSelected(true);
					}
				}
			}
		}
		else {
			// error msg
			
			return null;
		}
		
        return null;
	}
	
	public String queryFamilyInfoAndProcessPayment() {
		String loginUser = Utils.retrieveLoginUsername();
		List<Family> loginFamilies = familyRepository.findByLoginIdIgnoreCase(loginUser);
		
		if(loginFamilies != null && loginFamilies.size() > 0) {
			family = loginFamilies.get(0);
			Semester top1sem = semesterRepository.findFirstByOrderByRegisterstartdateDesc();
			if(top1sem != null)
				latestSemester = top1sem;
			
			registeredFamilytransactions = 
				familytransactionRepository.findBySemesteridAndFamilyidOrderByStudentidAsc(latestSemester, family);
				
			familybillings =
				familybillingRepository.findBySemesteridAndFamilyidOrderByBillingtypeDesc(latestSemester, family);
			
		}
		else {
			// error msg
			
			return null;
		}
		
        return null;
    }
	
	public boolean isShowChecknum() {
		return showChecknum;
	}

	public void setShowChecknum(boolean showChecknum) {
		this.showChecknum = showChecknum;
	}

	public DashboardModel getBoardModel() {
		return boardModel;
	}

	public void setBoardModel(DashboardModel boardModel) {
		this.boardModel = boardModel;
	}

	public Semesterfamilypod getSemesterfamilypod() {
		return semesterfamilypod;
	}

	public void setSemesterfamilypod(Semesterfamilypod semesterfamilypod) {
		this.semesterfamilypod = semesterfamilypod;
	}

	public Set<Semesterweek> getAllSemesterweeks() {
		return allSemesterweeks;
	}

	public void setAllSemesterweeks(Set<Semesterweek> allSemesterweeks) {
		this.allSemesterweeks = allSemesterweeks;
	}

	public boolean isCheckedPod() {
		return checkedPod;
	}

	public void setCheckedPod(boolean checkedPod) {
		this.checkedPod = checkedPod;
	}

	private static final long serialVersionUID = 1L;
}
