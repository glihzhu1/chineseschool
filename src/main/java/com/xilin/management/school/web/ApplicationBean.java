package com.xilin.management.school.web;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.xilin.management.school.web.util.Utils;

@ManagedBean
@SessionScoped
@Configurable
public class ApplicationBean  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(ApplicationBean.class);
	
	private String username;
	
    private String password;

    private String strConfirmPassword;
    
    private String userEmail;
    
    private UserDetails userDetails;
    
    public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStrConfirmPassword() {
		return strConfirmPassword;
	}

	public void setStrConfirmPassword(String strConfirmPassword) {
		this.strConfirmPassword = strConfirmPassword;
	}

	public String login() throws ServletException, IOException {
        //String url = "/resources/j_spring_security_check?j_username=" + username + "&j_password=" + password;
        
        String strRedirect="/pages/public/login";
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(this.username, this.password);
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
	        if (auth != null) {
	        	userDetails = (UserDetails)auth.getPrincipal();
	            
	        	//Collection<?extends GrantedAuthority> granted = auth.getAuthorities();
	    		if (hasRole(auth, Utils.ROLE_XILINADMIN))
	    		{
	    			strRedirect = "/pages/admin/main";
	    		}
	    		else if (hasRole(auth, Utils.ROLE_XILINFAMILY))
	    		{
	    			strRedirect = "/pages/family/main";
	    		}
	    		
	        }
        } catch (ServletException e) {
        	e.printStackTrace();
            logger.error("user: " + username + " login failed!", e);
            context.addMessage(null, new FacesMessage("Login failed, check your username/password."));
            //return "error";
        }
        return strRedirect;
        //return null;
	}

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
        	if (auth != null){    
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
        	
            //request.logout();
            context.addMessage(null, new FacesMessage("Your logout is successful!"));
            reset();
            
            return "/pages/public/login";
        } catch (Exception e) {
        	logger.error("user: " + username + " logout failed!", e);
            context.addMessage(null, new FacesMessage("Your logout failed!"));
            return "error";
        }
    }
    
    /*public String changePassword() {
		FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
    	if(request.getUserPrincipal() != null) {
    		String login = request.getUserPrincipal().getName();
    		List<NamadUser> namadUsers = namadUserRepository.findAllByLoginId(login);
            
            if(namadUsers != null && namadUsers.size() >0) {
            	NamadUser user = namadUsers.get(0);
            	//reset its password here
            	user.setPasswordHash(PasswordService.encrypt(password));
            	user.setPasswordDate(new Date());
            	user.setLastUpdateDate(GregorianCalendar.getInstance());
            	
            	namadUserRepository.save(user);
            }
            
            RequestContext reqContext = RequestContext.getCurrentInstance();
            reqContext.execute("PF('editUserDialogWidget').hide()");
            
            String message = "message_successfully_updated";
            FacesMessage facesMessage = MessageFactory.getMessage(message, "User");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    	}
    	return null;
    }*/
    
    public String getColumnName(String column) {
        if (column == null || column.length() == 0) {
            return column;
        }
        final Pattern p = Pattern.compile("[A-Z][^A-Z]*");
        final Matcher m = p.matcher(Character.toUpperCase(column.charAt(0)) + column.substring(1));
        final StringBuilder builder = new StringBuilder();
        while (m.find()) {
            builder.append(m.group()).append(" ");
        }
        return builder.toString().trim();
    }

	private MenuModel menuModel;
	private MenuModel taskMenuModel;
	private MenuModel classMenuModel;
	
	@PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        menuModel = new DefaultMenuModel();
        DefaultSubMenu submenu;
        DefaultMenuItem item;
        
        submenu = new DefaultSubMenu("Personnel");
        submenu.setId("personnelSubmenu");
        
        item = new DefaultMenuItem("Create Family");
        item.setId("createFamilyMenuItem");
        item.setCommand("#{familyBean.displayCreateDialog}");
        item.setIcon("ui-icon ui-icon-document");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.addElement(item);
        
        item = new DefaultMenuItem("Create Personnel");
        item.setId("createPersonnelMenuItem");
        item.setCommand("#{personnelBean.displayCreateDialog}");
        item.setIcon("ui-icon ui-icon-document");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.addElement(item);
        
        item = new DefaultMenuItem("List Family");
        item.setId("listFamilyMenuItem");
        item.setCommand("#{familyBean.displayList}");
        //item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
        //item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{familyBean.displayList}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-folder-open");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.addElement(item);
        
        item = new DefaultMenuItem("List Personnel");
        item.setId("listPersonnelMenuItem");
        item.setCommand("#{personnelBean.displayList}");
        item.setIcon("ui-icon ui-icon-folder-open");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.addElement(item);
        
        menuModel.addElement(submenu);
        
        
        taskMenuModel = new DefaultMenuModel();
        DefaultSubMenu taskSubmenu;
        DefaultMenuItem taskItem;
        
        taskSubmenu = new DefaultSubMenu("Tasks");
        taskSubmenu.setId("taskSubmenu");
        
        taskItem = new DefaultMenuItem("New Semester");
        taskItem.setId("createSemesterMenuItem");
        taskItem.setCommand("#{semesterBean.displayCreateDialog}");
        taskItem.setIcon("ui-icon ui-icon-document");
        taskItem.setAjax(false);
        taskItem.setAsync(false);
        taskItem.setUpdate(":dataForm:data");
        taskSubmenu.addElement(taskItem);
        
        taskItem = new DefaultMenuItem("Add Book");
        taskItem.setId("createBookitemMenuItem");
        taskItem.setCommand("#{bookitemBean.displayCreateDialog}");
        taskItem.setIcon("ui-icon ui-icon-document");
        taskItem.setAjax(false);
        taskItem.setAsync(false);
        taskItem.setUpdate(":dataForm:data");
        taskSubmenu.addElement(taskItem);
        
        taskItem = new DefaultMenuItem("List Semester");
        taskItem.setId("listSemesterMenuItem");
        taskItem.setCommand("#{semesterBean.displayList}");
        taskItem.setIcon("ui-icon ui-icon-folder-open");
        taskItem.setAjax(false);
        taskItem.setAsync(false);
        taskItem.setUpdate(":dataForm:data");
        taskSubmenu.addElement(taskItem);
        
        taskItem = new DefaultMenuItem("List Books");
        taskItem.setId("listBookitemMenuItem");
        taskItem.setCommand("#{bookitemBean.displayList}");
        taskItem.setIcon("ui-icon ui-icon-folder-open");
        taskItem.setAjax(false);
        taskItem.setAsync(false);
        taskItem.setUpdate(":dataForm:data");
        taskSubmenu.addElement(taskItem);
        
        taskMenuModel.addElement(taskSubmenu);
        
        classMenuModel = new DefaultMenuModel();
        DefaultSubMenu classSubmenu;
        DefaultMenuItem classItem;
        
        classSubmenu = new DefaultSubMenu("Classes");
        classSubmenu.setId("classSubmenu");
        
        classItem = new DefaultMenuItem("New Class");
        classItem.setId("createClassMenuItem");
        classItem.setCommand("#{semestercourseBean.displayCreateDialog}");
        classItem.setIcon("ui-icon ui-icon-document");
        classItem.setAjax(false);
        classItem.setAsync(false);
        classItem.setUpdate(":dataForm:data");
        classSubmenu.addElement(classItem);
        
        classItem = new DefaultMenuItem("Add Course");
        classItem.setId("createCourseMenuItem");
        classItem.setCommand("#{courseinformationBean.displayCreateDialog}");
        classItem.setIcon("ui-icon ui-icon-document");
        classItem.setAjax(false);
        classItem.setAsync(false);
        classItem.setUpdate(":dataForm:data");
        classSubmenu.addElement(classItem);
        
        classItem = new DefaultMenuItem("List Classes");
        classItem.setId("listClassMenuItem");
        classItem.setCommand("#{semestercourseBean.displayList}");
        classItem.setIcon("ui-icon ui-icon-folder-open");
        classItem.setAjax(false);
        classItem.setAsync(false);
        classItem.setUpdate(":dataForm:data");
        classSubmenu.addElement(classItem);
        
        classItem = new DefaultMenuItem("List Courses");
        classItem.setId("listCourseMenuItem");
        classItem.setCommand("#{courseinformationBean.displayList}");
        classItem.setIcon("ui-icon ui-icon-folder-open");
        classItem.setAjax(false);
        classItem.setAsync(false);
        classItem.setUpdate(":dataForm:data");
        classSubmenu.addElement(classItem);
        
        
        classMenuModel.addElement(classSubmenu);
        /*
        submenu = new Submenu();
        submenu.setId("registrationSubmenu");
        submenu.setLabel("Registration");
        item = new MenuItem();
        item.setId("createRegistrationMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{registrationBean.displayCreateDialog}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-document");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        item = new MenuItem();
        item.setId("listRegistrationMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{registrationBean.displayList}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-folder-open");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        menuModel.addSubmenu(submenu);
        
        submenu = new Submenu();
        submenu.setId("semesterSubmenu");
        submenu.setLabel("Semester");
        item = new MenuItem();
        item.setId("createSemesterMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{semesterBean.displayCreateDialog}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-document");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        item = new MenuItem();
        item.setId("listSemesterMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{semesterBean.displayList}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-folder-open");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        menuModel.addSubmenu(submenu);
        
        submenu = new Submenu();
        submenu.setId("studentSubmenu");
        submenu.setLabel("Student");
        item = new MenuItem();
        item.setId("createStudentMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{studentBean.displayCreateDialog}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-document");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        item = new MenuItem();
        item.setId("listStudentMenuItem");
        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{studentBean.displayList}", String.class, new Class[0]));
        item.setIcon("ui-icon ui-icon-folder-open");
        item.setAjax(false);
        item.setAsync(false);
        item.setUpdate(":dataForm:data");
        submenu.getChildren().add(item);
        menuModel.addSubmenu(submenu);
        
        
        */
    }

	public MenuModel getMenuModel() {
        return menuModel;
    }

	public MenuModel getTaskMenuModel() {
		return taskMenuModel;
	}

	public MenuModel getClassMenuModel() {
		return classMenuModel;
	}
	
	public String getAppName() {
        return "School";
    }
	
	private void reset() {
		this.userEmail = "";
		this.username = "";
		this.password = "";
		this.strConfirmPassword = "";
		this.userDetails = null;
	}
	
	private boolean hasRole(Authentication auth, String role) {
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
				auth.getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			hasRole = authority.getAuthority().trim().equalsIgnoreCase(role);
			if (hasRole) {
				break;
			}	
		}
		return hasRole;
	}
}