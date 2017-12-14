package com.xilin.management.school.web;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.jsf.FacesContextUtils;

import com.xilin.management.school.model.Family;
import com.xilin.management.school.model.Familytransaction;
import com.xilin.management.school.model.MyCustomSchoolService;
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
import com.xilin.management.school.web.util.MessageFactory;
import com.xilin.management.school.web.util.Utils;

@ManagedBean(name = "semesterBean")
@SessionScoped
@Configurable
public class SemesterBean implements Serializable {

	@Autowired
    SemesterRepository semesterRepository;
	
	@Autowired
    SemestercourseRepository semestercourseRepository;

	@Autowired
	SemesterweekRepository semesterweekRepository;
	
	@Autowired
	SemesterpodRepository semesterpodRepository;
	
	@Autowired
	SemesterfamilypodRepository semesterfamilypodRepository;
	
	@Autowired
    MyCustomSchoolService myCustomSchoolService;
	
	private String name = "Semesters";

	private Semester semester;

	private List<Semester> allSemesters;

	private boolean createDialogVisible = false;

	private List<Semestercourse> selectedSemestercourse;

	private List<SelectItem> semesterDropdown;
	private Integer semesterId;
	
	private List<Semester> filteredSemesters;
	
	private List<Semesterweek> semesterweeks;
	private Semesterweek semesterweek;
	private List<Semesterpod> selectedPods;
	private Semesterpod pod;
	
	private List<Semesterfamilypod> semesterfamilypods;
	private Semesterfamilypod familypod;
	
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
		//startDate = this.semester.getStartdate().getTime();
		//endDate = this.semester.getEnddate().getTime();
		//registerStartDate = this.semester.getRegisterstartdate().getTime();
		//registerEndDate = this.semester.getRegisterenddate().getTime();
		//payStartDate = this.semester.getPaystartdate().getTime();
		//payEndDate = this.semester.getPayenddate().getTime();
		//discountDate = this.semester.getDiscountdate().getTime();
		
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
        
        Calendar startDateCal = null;
	    
        // some deafult dates
	    Date date= new Date();
	    Calendar calToday = Calendar.getInstance();
	    calToday.setTime(date);
	    int monthToday = calToday.get(Calendar.MONTH);
	    int yearToday = calToday.get(Calendar.YEAR);
	    
	    if(monthToday > 7) {
	    	//start January next year on the second Sunday
	    	startDateCal = Calendar.getInstance();
	    	startDateCal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
	    	startDateCal.set(Calendar.MONTH, Calendar.JANUARY);
	    	startDateCal.set(Calendar.WEEK_OF_MONTH, 3);
	    	startDateCal.set(Calendar.YEAR, yearToday+1);
	    	semester.setStartdate(startDateCal);
	    	
	    	Calendar endDateCal = Calendar.getInstance();
	    	endDateCal.setTime(startDateCal.getTime());
	    	
	    	endDateCal.add(GregorianCalendar.DAY_OF_YEAR, 18*7); 
	    	semester.setEnddate(endDateCal); // 18 weeks later
	    	semester.setRegisterenddate(endDateCal);
	    	semester.setPayenddate(endDateCal);
	    	
	    	Calendar registerPayStartCal = Calendar.getInstance();
	    	registerPayStartCal.setTime(startDateCal.getTime());
	    	
	    	registerPayStartCal.add(GregorianCalendar.DAY_OF_YEAR, -5*7); 
	    	semester.setRegisterstartdate(registerPayStartCal); // 5 weeks earlier
	    	semester.setPaystartdate(registerPayStartCal);
	    	
	    	// end  of the current year is the discountDate
	    	Calendar cal = Calendar.getInstance();
	    	cal.set(Calendar.YEAR, yearToday);
	    	cal.set(Calendar.MONTH, 11); // 11 = december
	    	cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
	    	semester.setDiscountdate(cal);
	    	
	    }
	    else {
	    	//start August current year
	    	startDateCal = Calendar.getInstance();
	    	startDateCal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
	    	startDateCal.set(Calendar.MONTH, Calendar.AUGUST);
	    	startDateCal.set(Calendar.WEEK_OF_MONTH, 3);
	    	startDateCal.set(Calendar.YEAR, yearToday);
	    	semester.setStartdate(startDateCal);
	    	
	    	Calendar endDateCal = Calendar.getInstance();
	    	endDateCal.setTime(startDateCal.getTime());
	    	
	    	endDateCal.add(GregorianCalendar.DAY_OF_YEAR, 18*7); 
	    	semester.setEnddate(endDateCal); // 18 weeks later
	    	semester.setRegisterenddate(endDateCal);
	    	semester.setPayenddate(endDateCal);
	    	
	    	Calendar registerPayStartCal = Calendar.getInstance();
	    	registerPayStartCal.setTime(startDateCal.getTime());
	    	
	    	registerPayStartCal.add(GregorianCalendar.DAY_OF_YEAR, -15*7); 
	    	semester.setRegisterstartdate(registerPayStartCal); // 15 weeks earlier
	    	semester.setPaystartdate(registerPayStartCal);
	    	
	    	// end  of July of this year is the discountDate
	    	Calendar cal = Calendar.getInstance();
	    	cal.set(Calendar.YEAR, yearToday);
	    	cal.set(Calendar.MONTH, 6); // 6 = July
	    	cal.set(Calendar.DAY_OF_MONTH, 31);
	    	semester.setDiscountdate(cal);
	    }
	    
	    //semesterDropdown = semesterDropdown = myCustomSchoolService.queryTop2SemesterItems();
		
        createDialogVisible = true;
        return "/pages/admin/semester";
    }

	public String persist() {
        String message = "";
        String loginUsername = Utils.retrieveLoginUsername();
        Calendar calendar = GregorianCalendar.getInstance();
		
        Calendar originalStartDate = Calendar.getInstance();
    	originalStartDate.setTime(semester.getStartdate().getTime());
    	
    	//some default dates
    	semester.setPaystartdate(semester.getRegisterstartdate());
		semester.setPayenddate(semester.getEnddate());
		semester.setRegisterenddate(semester.getEnddate());
		
        if (semester.getId() != null) {
        	//Check whether already existed
    		List<Semester> result = semesterRepository.findByDescriptionIgnoreCase(semester.getDescription().trim());
    		if (result.size() > 1) {
    			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
    					"More than one Semester with the provided description has existed!", "");
    			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    			return null;
    		}
        	
    		//If startdate changed before registration start, re-adjust weeks/pods
    		/*GregorianCalendar todayCalendar= new GregorianCalendar();
    		if(semester.getRegisterstartdate().compareTo(todayCalendar) > 0) { //before Registerstartdate
    			//query and delete old weeks/pods
    			List<Semesterweek> oldweeks = 
    				semesterweekRepository.findBySemesteridOrderByDisplayweekidAsc(semester);
    			semesterweekRepository.delete(oldweeks);
    			
        		List<Semesterweek> srcSemesterweeks = new ArrayList<Semesterweek>();
        		srcSemesterweeks = generateFifteenSemesterWeeks(semester.getStartdate());
        		
        		saveSemesterWeeks(srcSemesterweeks);
    		}*/
    		
    		
    		semester.setStartdate(originalStartDate);
    		semester.setUpdatedby(loginUsername);
    		semester.setUpdatedtime(calendar);
    		semesterRepository.save(semester);
    		
            message = "message_successfully_updated";
        } else {
        	//Check whether already existed
    		List<Semester> result = semesterRepository.findByDescriptionIgnoreCase(semester.getDescription().trim());
    		if (result.size() > 0) {
    			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
    					"Semester with the provided description has existed!", "");
    			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    			return null;
    		}
        	//Semester srcSemester = null;
        	List<Semestercourse> srcSemestercourses = new ArrayList<Semestercourse>();
    		List<Semesterweek> srcSemesterweeks = new ArrayList<Semesterweek>();
        	
			Semester top1sem = semesterRepository.findFirstByOrderByRegisterstartdateDesc();
    		if(top1sem != null) {
    			srcSemestercourses = semestercourseRepository.findBySemesteridOrderBySemestercoursecodeAsc(top1sem);
    		}
    		// auto generate 15 weeks
    		srcSemesterweeks = generateFifteenSemesterWeeks(semester.getStartdate());
    		
    		semester.setStartdate(originalStartDate);
        	semester.setActive(true);
        	semester.setUpdatedby(loginUsername);
        	semester.setUpdatedtime(calendar);
        	semester = semesterRepository.save(semester);
	        
        	//save other parts
        	if(srcSemestercourses != null && srcSemestercourses.size() > 0)
        		saveSemesterClasses(srcSemestercourses);
        	if(srcSemesterweeks != null && srcSemesterweeks.size() > 0)
        		saveSemesterWeeks(srcSemesterweeks);
        	
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

	public void onWeekRowToggle(ToggleEvent event) {
		semesterweek = (Semesterweek) event.getData();
		
		selectedPods = semesterpodRepository.findBySemesterweekid(semesterweek);
		//selectedPods = semesterweek.getSemesterpods()
		
    }
	
	public void onDeactivateSemester() {
		Map<String,String> params =
			FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String semesterrowid = params.get("semesterrowid");
		semester = semesterRepository.findOne(Integer.valueOf(semesterrowid));
			
	}
	public void onManageSemesterweek() {
		Map<String,String> params =
			FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		String semesterrowid = params.get("semesterrowid");
		
		semester = semesterRepository.findOne(Integer.valueOf(semesterrowid));
		
		//semesterweeks = semesterweekRepository.queryAllSemesterweeks(semester);
		
		semesterweeks = semesterweekRepository.findBySemesteridOrderByDisplayweekidAsc(semester);
		
	}
	
	public void deleteSemesterweek() {
		List<Semesterpod> sempods = semesterpodRepository.findBySemesterweekid(semesterweek);
		if(sempods != null && sempods.size()>0) {
			semesterpodRepository.delete(sempods);
		}
		
		semesterweekRepository.delete(semesterweek);
		
		//semesterweeks.remove(semesterweek);
		semesterweeks = semesterweekRepository.findBySemesteridOrderByDisplayweekidAsc(semester);
		
		String username = Utils.retrieveLoginUsername();
		Calendar cal = GregorianCalendar.getInstance();
		
		utilBatchSemesterweekUpdate(semesterweeks, username, cal);
		
		String message = "message_successfully_updated";
		FacesMessage facesMessage = MessageFactory.getMessage(message, "Semester Week");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
	}
	
	public String batchSemesterweekUpdate() {
		String username = Utils.retrieveLoginUsername();
		Calendar cal = GregorianCalendar.getInstance();
		
		utilBatchSemesterweekUpdate(semesterweeks, username, cal);
		
		String message = "message_successfully_updated";
		FacesMessage facesMessage = MessageFactory.getMessage(message, "Semester Weeks");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
	}
	/*public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Week Edited");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     */
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        
        if(newValue != null && !newValue.equals(oldValue)) {
        	FacesContext context = FacesContext.getCurrentInstance();
        	Semesterweek semesterweek = context.getApplication().evaluateExpressionGet(context, "#{semesterweek}", Semesterweek.class);
            
        	semesterweekRepository.save(semesterweek);
        	
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onPodCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        
        if(newValue != null && !newValue.equals(oldValue)) {
        	FacesContext context = FacesContext.getCurrentInstance();
        	Semesterpod semesterpod = context.getApplication().evaluateExpressionGet(context, "#{pod}", Semesterpod.class);
            
        	semesterpodRepository.save(semesterpod);
        	
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void deleteSemesterpod() {
    	semesterpodRepository.delete(pod);
		semesterweeks = semesterweekRepository.findBySemesteridOrderByDisplayweekidAsc(semester);
		
		String message = "message_successfully_updated";
		FacesMessage facesMessage = MessageFactory.getMessage(message, "Semester Week");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
	}
    
    public void addSemesterweek() {
    	// check weekdate is between start and end and doesn't exist
    	if(semesterweek.getWeekdate().compareTo(semester.getStartdate()) > 0 &&
    		semesterweek.getWeekdate().compareTo(semester.getEnddate()) < 0) {
    		for(Semesterweek wk: semesterweeks) {
    			if(Utils.dateSameday(wk.getWeekdate(), semesterweek.getWeekdate())) {
    				FacesContext context = FacesContext.getCurrentInstance();
    				UIComponent component = context.getViewRoot().findComponent("addSemesterweekForm:weekdate"); 
    				context.addMessage(component.getClientId(), new FacesMessage("The week date already existed!"));
    		        return;
    			}
    		}
    	} 
    	else {
    		FacesContext context = FacesContext.getCurrentInstance();
			UIComponent component = context.getViewRoot().findComponent("addSemesterweekForm:weekdate"); 
			context.addMessage(component.getClientId(), new FacesMessage("The week date not valid for the selected semester."));
	        return;
    	}
    	
    	String loginUsername = Utils.retrieveLoginUsername();
        Calendar calendar = GregorianCalendar.getInstance();
        semesterweek.setUpdatedby(loginUsername);
        semesterweek.setUpdatedtime(calendar);
        semesterweekRepository.save(semesterweek);
		
        //create POD for semesterweek
    	List<Semesterpod> destSemesterpods = new ArrayList<Semesterpod>();
    	destSemesterpods.addAll(createSemesterpods(semesterweek, loginUsername, calendar));
    
    	semesterpodRepository.save(destSemesterpods);
    	
    	semesterweeks = semesterweekRepository.findBySemesteridOrderByDisplayweekidAsc(semester);
    	
    	utilBatchSemesterweekUpdate(semesterweeks, loginUsername, calendar);
    	
    	RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('addSemesterweekDialogWidget').hide()");
        
    	String message = "message_successfully_updated";
		FacesMessage facesMessage = MessageFactory.getMessage(message, "Semester Week");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
	}
    
    public void onAddSemesterweek() {
    	semesterweek = new Semesterweek();
    	semesterweek.setSemesterid(semester);;
    	semesterweek.setHasclass(true);
    	semesterweek.setWeekdate(Calendar.getInstance());
    	
    }
    
    public void addSemesterpod() {
    	String loginUsername = Utils.retrieveLoginUsername();
        Calendar calendar = GregorianCalendar.getInstance();
        pod.setUpdatedby(loginUsername);
        pod.setUpdatedtime(calendar);
		semesterpodRepository.save(pod);
		
		//selectedPods.add(pod);
		
    	semesterweeks = semesterweekRepository.findBySemesteridOrderByDisplayweekidAsc(semester);
		
    	String message = "message_successfully_updated";
		FacesMessage facesMessage = MessageFactory.getMessage(message, "Semester Week");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
	}
    
    public void onAddSemesterpod() {
    	pod = new Semesterpod();
    	pod.setCapacity(5);
    	pod.setFilled(0);
    	
    	Map<String,String> params =
    			FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    	String semesterweekrowid = params.get("semesterweekrowid");
    	semesterweek = semesterweekRepository.findOne(Integer.valueOf(semesterweekrowid));
    	pod.setSemesterweekid(semesterweek);
    	
    }
    
    public void onCheckSemesterpodmembers() {
    	Map<String,String> params =
    			FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    	String semesterweekrowid = params.get("semesterweekrowid");
    	semesterweek = semesterweekRepository.findOne(Integer.valueOf(semesterweekrowid));
    	
    	semesterfamilypods = semesterfamilypodRepository.findBySemesterweek(semesterweek);
    	
    }
    public void onDeleteSemesterweek() {
    	Map<String,String> params =
    			FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    	String semesterweekrowid = params.get("semesterweekrowid");
		semesterweek = semesterweekRepository.findOne(Integer.valueOf(semesterweekrowid));
    	
    }
    
    public void onDeleteSemesterpod() {
    	Map<String,String> params =
    			FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    	String semesterpodrowid = params.get("semesterpodrowid");
		pod = semesterpodRepository.findOne(Integer.valueOf(semesterpodrowid));
    	
		semesterweek = pod.getSemesterweekid();
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
	
	public List<SelectItem> getSemesterDropdown() {
		return semesterDropdown;
	}

	public void setSemesterDropdown(List<SelectItem> semesterDropdown) {
		this.semesterDropdown = semesterDropdown;
	}

	public Integer getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(Integer semesterId) {
		this.semesterId = semesterId;
	}
	
	private void saveSemesterClasses(List<Semestercourse> srcSemestercourses) {
		String loginUsername = Utils.retrieveLoginUsername();
        Calendar calendar = GregorianCalendar.getInstance();
        
        List<Semestercourse> destSemestercourses = new ArrayList<Semestercourse>();
		for(Semestercourse course: srcSemestercourses) {
    		Semestercourse acls = new Semestercourse();
    		try {
            	Field[] flds = Semestercourse.class.getDeclaredFields();
            	for(Field fld: flds)
                {
                    fld.setAccessible(true);
                    Object value = fld.get(course);
                    if(value != null)
                    	fld.set(acls, value);
                }
            } catch (IllegalArgumentException x) {
                x.printStackTrace();
            } catch (IllegalAccessException x) {
              x.printStackTrace();
            }
    		
    		acls.setId(null);
    		acls.setSemesterid(semester);
    		acls.setUpdatedby(loginUsername);
    		acls.setUpdatedtime(calendar);
    		acls.setWaitingcapacity(0);
    		acls.setCurrentcapacity(0);
    		acls.setMincapacity(0);
    		acls.setStatus(Utils.CLASS_STATUS_OPEN);
    		destSemestercourses.add(acls);
		}
    	semestercourseRepository.save(destSemestercourses);
	}
	
	private List<Semesterweek> generateFifteenSemesterWeeks(Calendar dateCal) {
		List<Semesterweek> fifteenWeeks = new ArrayList<Semesterweek>();
		//SimpleDateFormat dteFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		//Go back one week so the for loop starts from 1
		if(dateCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			dateCal.add(GregorianCalendar.DAY_OF_YEAR, (-1)*7);
		}
		
		for(int i=1; i<16; i++) {
			Semesterweek wk = new Semesterweek();
			Calendar dateCalWeek = GregorianCalendar.getInstance();
			dateCalWeek.setTime(dateCal.getTime());
			if(dateCalWeek.get(Calendar.DAY_OF_WEEK) > Calendar.SUNDAY) {
				dateCalWeek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			dateCalWeek.add(GregorianCalendar.DAY_OF_YEAR, i*7);
			
			wk.setDescription("Week " + i);
			wk.setDisplaynumber("" + i);
			wk.setDisplayweekid(i);
			wk.setHasclass(true);
			//String weekdate = dteFormat.format(dateCalWeek.getTime());
			wk.setWeekdate(dateCalWeek);
			
			fifteenWeeks.add(wk);
		}
    	
		return fifteenWeeks;
	}
	
	private void saveSemesterWeeks(List<Semesterweek> srcSemesterweeks) {
		String loginUsername = Utils.retrieveLoginUsername();
        Calendar calendar = GregorianCalendar.getInstance();
        
        List<Semesterweek> destSemesterweeks = new ArrayList<Semesterweek>();
		for(Semesterweek wk: srcSemesterweeks) {
			Semesterweek awk = new Semesterweek();
    		try {
            	Field[] flds = Semesterweek.class.getDeclaredFields();
            	for(Field fld: flds)
                {
                    fld.setAccessible(true);
                    Object value = fld.get(wk);
                    if(value != null)
                    	fld.set(awk, value);
                }
            } catch (IllegalArgumentException x) {
                x.printStackTrace();
            } catch (IllegalAccessException x) {
              x.printStackTrace();
            }
    		
    		awk.setId(null);
    		awk.setSemesterid(semester);
    		awk.setUpdatedby(loginUsername);
    		awk.setUpdatedtime(calendar);
    		awk.setHasclass(true);
    		awk.setSemesterpods(null);
    		destSemesterweeks.add(awk);
		}
    	semesterweekRepository.save(destSemesterweeks);
    	
    	//create POD for all weeks
    	List<Semesterpod> destSemesterpods = new ArrayList<Semesterpod>();
    	for(Semesterweek wk: destSemesterweeks) {
    		destSemesterpods.addAll(createSemesterpods(wk, loginUsername, calendar));
    	}
    	semesterpodRepository.save(destSemesterpods);
	}
	
	private List<Semesterpod> createSemesterpods(Semesterweek wk, String loginUsername, Calendar calendar) {
		List<Semesterpod> wkpods = new ArrayList<Semesterpod>();
		
		Semesterpod pod1 = new Semesterpod();
		pod1.setCapacity(5);
		pod1.setFilled(0);
		pod1.setPodhour(Utils.POD_PERIOD_ONE);
		pod1.setSemesterweekid(wk);
		pod1.setUpdatedby(loginUsername);
		pod1.setUpdatedtime(calendar);
		wkpods.add(pod1);
		
		Semesterpod pod2 = new Semesterpod();
		pod2.setCapacity(5);
		pod2.setFilled(0);
		pod2.setPodhour(Utils.POD_PERIOD_TWO);
		pod2.setSemesterweekid(wk);
		pod2.setUpdatedby(loginUsername);
		pod2.setUpdatedtime(calendar);
		wkpods.add(pod2);
		
		return wkpods;
	}
	
	public void batchSemesterpodPerformedUpdate() {
		if(!semesterfamilypods.isEmpty()) {
			semesterfamilypodRepository.save(semesterfamilypods);
		}
		
		String message = "message_successfully_updated";
		FacesMessage facesMessage = MessageFactory.getMessage(message, "Family POD");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	private void utilBatchSemesterweekUpdate(List<Semesterweek> wks, String username, Calendar cal) {
		Collections.sort(wks);
		int displayweekid = 1;
		int displaynumber = 1;
		
		for(int index=0; index<wks.size(); index++) {
			Semesterweek wk = wks.get(index);
			
			wk.setDisplayweekid(displayweekid);
			displayweekid++;
			if(wk.getHasclass()) {
				wk.setDisplaynumber("" +displaynumber);
				displaynumber++;
			}
			else {
				wk.setDisplaynumber("");
			}
			
			wk.setUpdatedby(username);
			wk.setUpdatedtime(cal);
		}
		
		if(!wks.isEmpty()) {
			semesterweekRepository.save(wks);
		}
	}
	
	public List<Semesterweek> getSemesterweeks() {
		return semesterweeks;
	}

	public void setSemesterweeks(List<Semesterweek> semesterweeks) {
		this.semesterweeks = semesterweeks;
	}

	public Semesterweek getSemesterweek() {
		return semesterweek;
	}

	public void setSemesterweek(Semesterweek semesterweek) {
		this.semesterweek = semesterweek;
	}

	public Semesterpod getPod() {
		return pod;
	}

	public void setPod(Semesterpod pod) {
		this.pod = pod;
	}

	public List<Semesterpod> getSelectedPods() {
		return selectedPods;
	}

	public void setSelectedPods(List<Semesterpod> selectedPods) {
		this.selectedPods = selectedPods;
	}

	public List<Semesterfamilypod> getSemesterfamilypods() {
		return semesterfamilypods;
	}

	public void setSemesterfamilypods(List<Semesterfamilypod> semesterfamilypods) {
		this.semesterfamilypods = semesterfamilypods;
	}

	public Semesterfamilypod getFamilypod() {
		return familypod;
	}

	public void setFamilypod(Semesterfamilypod familypod) {
		this.familypod = familypod;
	}

	private static final long serialVersionUID = 1L;
}

