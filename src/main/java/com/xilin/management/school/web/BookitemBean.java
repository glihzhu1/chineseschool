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

import com.xilin.management.school.model.Bookitem;
import com.xilin.management.school.model.BookitemRepository;
import com.xilin.management.school.web.util.MessageFactory;
import com.xilin.management.school.web.util.Utils;

@ManagedBean(name = "bookitemBean")
@SessionScoped
@Configurable
public class BookitemBean implements Serializable {

	@Autowired
    BookitemRepository bookitemRepository;

	private String name = "Bookitems";

	private Bookitem bookitem;

	private List<Bookitem> allBookitems;

	private boolean createDialogVisible = false;

	//private List<Bookitemcourse> selectedBookitemcourse;

	//private Date startDate;
	
	
	private List<Bookitem> filteredBookitems;
	
	@PostConstruct
    public void init() {
		FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
		.getAutowireCapableBeanFactory().autowireBean(this);
		
    }

	public String getName() {
        return name;
    }


	public List<Bookitem> getAllBookitems() {
        return allBookitems;
    }

	public void setAllBookitems(List<Bookitem> allBookitems) {
        this.allBookitems = allBookitems;
    }

	public String findAllBookitems() {
        //allBookitems = bookitemRepository.findAll();
        allBookitems = bookitemRepository.findAll();
        return "/pages/admin/bookitem";
    }

	public Bookitem getBookitem() {
        if (bookitem == null) {
            bookitem = new Bookitem();
        }
        return bookitem;
    }

	public void setBookitem(Bookitem bookitem) {
        this.bookitem = bookitem;
    }

	public String onEdit() {
		//startDate = this.bookitem.getStartdate().getTime();
		
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
        findAllBookitems();
        return "/pages/admin/bookitem";
    }

	public String displayCreateDialog() {
        bookitem = new Bookitem();
        bookitem.setActive(true);
        //bookitem.setRegistrationfee(new BigDecimal(15));
        
        createDialogVisible = true;
        return "/pages/admin/bookitem";
    }

	public String persist() {
        String message = "";
        String loginUsername = Utils.retrieveLoginUsername();
        //Calendar calendar = GregorianCalendar.getInstance();
        
        //calendar.setTime(startDate);
        //bookitem.setStartdate(calendar);
        
        if (bookitem.getId() != null) {
        	//Check whether the bookitem possibly already existing??
			/*List<Bookitem> result = bookitemRepository.findByEmailIgnoreCase(bookitem.getEmail());
			if (result.size() > 1) {
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
    					"More than one bookitem with the provided email has existed!", "");
				FacesContext.getCurrentInstance().addMessage("editForm:bookitememailedit", facesMessage);
    			return null;
			}*/
			
			bookitem.setUpdatedby(loginUsername);
    		bookitem.setUpdatedtime(GregorianCalendar.getInstance());
    		bookitemRepository.save(bookitem);
    		
            message = "message_successfully_updated";
        } else {
        	
        	
        	bookitem.setActive(true);
        	bookitem.setUpdatedby(Utils.retrieveLoginUsername());
        	bookitem.setUpdatedtime(GregorianCalendar.getInstance());
        	bookitem = bookitemRepository.save(bookitem);
	        
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('createDialogWidget').hide()");
        context.execute("PF('editDialogWidget').hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Bookitem");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllBookitems();
    }

	public String deactivateBookitem() {
		bookitem.setActive(false);
		bookitem.setUpdatedby(Utils.retrieveLoginUsername());
		bookitem.setUpdatedtime(GregorianCalendar.getInstance());
		bookitemRepository.save(bookitem);
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_updated", "Bookitem");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllBookitems();
    }
	
	public String delete() {
		bookitemRepository.delete(bookitem);
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Bookitem");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllBookitems();
    }

	public void reset() {
        bookitem = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	public List<Bookitem> getFilteredBookitems() {
		return filteredBookitems;
	}

	public void setFilteredBookitems(List<Bookitem> filteredBookitems) {
		this.filteredBookitems = filteredBookitems;
	}

	private static final long serialVersionUID = 1L;
}

