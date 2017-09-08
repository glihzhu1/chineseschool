package com.xilin.management.school.web;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.jsf.FacesContextUtils;

import com.xilin.management.school.model.Semester;
import com.xilin.management.school.model.SemesterRepository;
import com.xilin.management.school.model.Semestercourse;
import com.xilin.management.school.web.util.MessageFactory;
import com.xilin.management.school.web.util.Utils;

@ManagedBean(name = "semesterBean")
@SessionScoped
@Configurable
public class SemesterBean implements Serializable {

	@Autowired
    SemesterRepository semesterRepository;

	private String name = "Semesters";

	private Semester semester;

	private List<Semester> allSemesters;

	/*private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;*/

	private boolean createDialogVisible = false;

	private List<Semestercourse> selectedSemestercourse;

	private Date startDate;
	private Date endDate;
	private Date registerStartDate;
	private Date registerEndDate;
	private Date payStartDate;
	private Date payEndDate;
	private Date discountDate;
	
	
	private List<Semester> filteredSemesters;
	
	@PostConstruct
    public void init() {
		FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
		.getAutowireCapableBeanFactory().autowireBean(this);
		
    }

	public String getName() {
        return name;
    }


	public List<Semester> getAllSemesters() {
        return allSemesters;
    }

	public void setAllSemesters(List<Semester> allSemesters) {
        this.allSemesters = allSemesters;
    }

	public String findAllSemesters() {
        //allSemesters = semesterRepository.findAll();
        allSemesters = semesterRepository.findAll();
        return "/pages/admin/semester";
    }

	public Semester getSemester() {
        if (semester == null) {
            semester = new Semester();
        }
        return semester;
    }

	public void setSemester(Semester semester) {
        this.semester = semester;
    }

	public List<Semestercourse> getSelectedSemestercourse() {
        return selectedSemestercourse;
    }

	public void setSelectedSemestercourse(List<Semestercourse> selectedSemestercourse) {
        if (selectedSemestercourse != null) {
            semester.setSemestercourse(new HashSet<Semestercourse>(selectedSemestercourse));
        }
        this.selectedSemestercourse = selectedSemestercourse;
    }

	public String onEdit() {
		startDate = this.semester.getStartdate().getTime();
		endDate = this.semester.getEnddate().getTime();
		registerStartDate = this.semester.getRegisterstartdate().getTime();
		registerEndDate = this.semester.getRegisterenddate().getTime();
		payStartDate = this.semester.getPaystartdate().getTime();
		payEndDate = this.semester.getPayenddate().getTime();
		discountDate = this.semester.getDiscountdate().getTime();
		
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
        findAllSemesters();
        return "/pages/admin/semester";
    }

	public String displayCreateDialog() {
        semester = new Semester();
        semester.setActive(true);
        semester.setRegistrationfee(new BigDecimal(15));
        semester.setDiscountamount(new BigDecimal(15));
        semester.setPodfee(new BigDecimal(15));
        semester.setPodrefundamount(new BigDecimal(15));
        semester.setReturnedcheckfee(new BigDecimal(35));
        
        createDialogVisible = true;
        return "/pages/admin/semester";
    }

	public String persist() {
        String message = "";
        String loginUsername = Utils.retrieveLoginUsername();
        Calendar calendar = GregorianCalendar.getInstance();
        
        calendar.setTime(startDate);
        semester.setStartdate(calendar);
        calendar.setTime(endDate);
        semester.setEnddate(calendar);
        calendar.setTime(registerStartDate);
        semester.setRegisterstartdate(calendar);
        calendar.setTime(registerEndDate);
        semester.setRegisterenddate(calendar);
        calendar.setTime(payStartDate);
        semester.setPaystartdate(calendar);
        calendar.setTime(payEndDate);
        semester.setPayenddate(calendar);
        calendar.setTime(discountDate);
        semester.setDiscountdate(calendar);
        
        if (semester.getId() != null) {
        	//Check whether the semester possibly already existing??
			/*List<Semester> result = semesterRepository.findByEmailIgnoreCase(semester.getEmail());
			if (result.size() > 1) {
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
    					"More than one semester with the provided email has existed!", "");
				FacesContext.getCurrentInstance().addMessage("editForm:semesteremailedit", facesMessage);
    			return null;
			}*/
			
			semester.setUpdatedby(loginUsername);
    		semester.setUpdatedtime(GregorianCalendar.getInstance());
    		semesterRepository.save(semester);
    		
            message = "message_successfully_updated";
        } else {
        	
        	
        	semester.setActive(true);
        	semester.setUpdatedby(Utils.retrieveLoginUsername());
        	semester.setUpdatedtime(GregorianCalendar.getInstance());
        	semester = semesterRepository.save(semester);
	        
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('createDialogWidget').hide()");
        context.execute("PF('editDialogWidget').hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Semester");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllSemesters();
    }

	public String deactivateSemester() {
		semester.setActive(false);
		semester.setUpdatedby(Utils.retrieveLoginUsername());
		semester.setUpdatedtime(GregorianCalendar.getInstance());
		semesterRepository.save(semester);
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_updated", "Semester");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllSemesters();
    }
	
	public String delete() {
		semesterRepository.delete(semester);
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Semester");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllSemesters();
    }

	public void reset() {
        semester = null;
        selectedSemestercourse = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	public List<Semester> getFilteredSemesters() {
		return filteredSemesters;
	}

	public void setFilteredSemesters(List<Semester> filteredSemesters) {
		this.filteredSemesters = filteredSemesters;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getRegisterStartDate() {
		return registerStartDate;
	}

	public void setRegisterStartDate(Date registerStartDate) {
		this.registerStartDate = registerStartDate;
	}

	public Date getRegisterEndDate() {
		return registerEndDate;
	}

	public void setRegisterEndDate(Date registerEndDate) {
		this.registerEndDate = registerEndDate;
	}

	public Date getPayStartDate() {
		return payStartDate;
	}

	public void setPayStartDate(Date payStartDate) {
		this.payStartDate = payStartDate;
	}

	public Date getPayEndDate() {
		return payEndDate;
	}

	public void setPayEndDate(Date payEndDate) {
		this.payEndDate = payEndDate;
	}

	public Date getDiscountDate() {
		return discountDate;
	}

	public void setDiscountDate(Date discountDate) {
		this.discountDate = discountDate;
	}

	private static final long serialVersionUID = 1L;
}

