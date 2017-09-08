package com.xilin.management.school.web;
import java.io.Serializable;
import java.util.GregorianCalendar;
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

import com.xilin.management.school.model.Courseinformation;
import com.xilin.management.school.model.CourseinformationRepository;
import com.xilin.management.school.web.util.MessageFactory;
import com.xilin.management.school.web.util.Utils;

@ManagedBean(name = "courseinformationBean")
@SessionScoped
@Configurable
public class CourseinformationBean implements Serializable {

	@Autowired
    CourseinformationRepository courseinformationRepository;

	private String name = "Courseinformations";

	private Courseinformation courseinformation;

	private List<Courseinformation> allCourseinformations;

	private boolean createDialogVisible = false;

	//private List<Courseinformationcourse> selectedCourseinformationcourse;

	//private Date startDate;
	
	
	private List<Courseinformation> filteredCourseinformations;
	
	@PostConstruct
    public void init() {
		FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
		.getAutowireCapableBeanFactory().autowireBean(this);
		
    }

	public String getName() {
        return name;
    }


	public List<Courseinformation> getAllCourseinformations() {
        return allCourseinformations;
    }

	public void setAllCourseinformations(List<Courseinformation> allCourseinformations) {
        this.allCourseinformations = allCourseinformations;
    }

	public String findAllCourseinformations() {
        //allCourseinformations = courseinformationRepository.findAll();
        allCourseinformations = courseinformationRepository.findAll();
        return "/pages/admin/courseinformation";
    }

	public Courseinformation getCourseinformation() {
        if (courseinformation == null) {
            courseinformation = new Courseinformation();
        }
        return courseinformation;
    }

	public void setCourseinformation(Courseinformation courseinformation) {
        this.courseinformation = courseinformation;
    }

	public String onEdit() {
		//startDate = this.courseinformation.getStartdate().getTime();
		
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
        findAllCourseinformations();
        return "/pages/admin/courseinformation";
    }

	public String displayCreateDialog() {
        courseinformation = new Courseinformation();
        
        createDialogVisible = true;
        return "/pages/admin/courseinformation";
    }

	public String persist() {
        String message = "";
        String loginUsername = Utils.retrieveLoginUsername();
        //Calendar calendar = GregorianCalendar.getInstance();
        
        //calendar.setTime(startDate);
        //courseinformation.setStartdate(calendar);
        
        if (courseinformation.getId() != null) {
        	//Check whether the courseinformation possibly already existing??
			/*List<Courseinformation> result = courseinformationRepository.findByEmailIgnoreCase(courseinformation.getEmail());
			if (result.size() > 1) {
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
    					"More than one courseinformation with the provided email has existed!", "");
				FacesContext.getCurrentInstance().addMessage("editForm:courseinformationemailedit", facesMessage);
    			return null;
			}*/
			
			courseinformation.setUpdatedby(loginUsername);
    		courseinformation.setUpdatedtime(GregorianCalendar.getInstance());
    		courseinformationRepository.save(courseinformation);
    		
            message = "message_successfully_updated";
        } else {
        	
        	courseinformation.setUpdatedby(Utils.retrieveLoginUsername());
        	courseinformation.setUpdatedtime(GregorianCalendar.getInstance());
        	courseinformation = courseinformationRepository.save(courseinformation);
	        
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('createDialogWidget').hide()");
        context.execute("PF('editDialogWidget').hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Courseinformation");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllCourseinformations();
    }

	public String deactivateCourseinformation() {
		courseinformation.setUpdatedby(Utils.retrieveLoginUsername());
		courseinformation.setUpdatedtime(GregorianCalendar.getInstance());
		courseinformationRepository.save(courseinformation);
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_updated", "Courseinformation");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllCourseinformations();
    }
	
	public String delete() {
		courseinformationRepository.delete(courseinformation);
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Courseinformation");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllCourseinformations();
    }

	public void reset() {
        courseinformation = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	public List<Courseinformation> getFilteredCourseinformations() {
		return filteredCourseinformations;
	}

	public void setFilteredCourseinformations(List<Courseinformation> filteredCourseinformations) {
		this.filteredCourseinformations = filteredCourseinformations;
	}

	private static final long serialVersionUID = 1L;
}

