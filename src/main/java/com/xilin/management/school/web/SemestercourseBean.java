package com.xilin.management.school.web;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.jsf.FacesContextUtils;

import com.xilin.management.school.model.Bookitem;
import com.xilin.management.school.model.BookitemRepository;
import com.xilin.management.school.model.Courseinformation;
import com.xilin.management.school.model.CourseinformationRepository;
import com.xilin.management.school.model.MyCustomSchoolService;
import com.xilin.management.school.model.Personnel;
import com.xilin.management.school.model.PersonnelRepository;
import com.xilin.management.school.model.Semester;
import com.xilin.management.school.model.SemesterRepository;
import com.xilin.management.school.model.Semestercourse;
import com.xilin.management.school.model.SemestercourseRepository;
import com.xilin.management.school.web.util.MessageFactory;
import com.xilin.management.school.web.util.Utils;

@ManagedBean(name = "semestercourseBean")
@SessionScoped
@Configurable
public class SemestercourseBean implements Serializable {

	@Autowired
    SemestercourseRepository semestercourseRepository;

	@Autowired
	private SemesterRepository semesterRepository;
	
	@Autowired
	private BookitemRepository bookitemRepository;
	
	@Autowired
	private PersonnelRepository personnelRepository;
	
	@Autowired
	private CourseinformationRepository courseinformationRepository;
	
	@Autowired
    MyCustomSchoolService myCustomSchoolService;
	
	private String name = "Semestercourses";

	private Semestercourse semestercourse;

	private List<Semestercourse> allSemestercourses;


	private boolean createDialogVisible = false;

	private List<Semestercourse> filteredSemestercourses;
	
	private List<SelectItem> semesterDropdown;
	private List<SelectItem> courseinfoDropdown;
	private List<SelectItem> teacherDropdown;
	private List<SelectItem> bookitemDropdown;
	private Integer semesterId;
	private Integer courseinfoId;
	private Integer teacherId;
	private Integer bookitemId;
	private Courseinformation courseinfo;
	private Personnel teacher;
	private Bookitem bookitem;
	private Semester latestSemester;
	private String semesterCourseAction;
	
	@PostConstruct
    public void init() {
		FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
		.getAutowireCapableBeanFactory().autowireBean(this);
		
		// populate all dropdowns
		semesterDropdown = myCustomSchoolService.queryTop2SemesterItems();
		courseinfoDropdown = myCustomSchoolService.queryAllCourseinformationItems();
		teacherDropdown = myCustomSchoolService.queryTeacherItems();
		bookitemDropdown = myCustomSchoolService.queryAllBookitemItems();
		
		latestSemester = semesterRepository.findFirstByOrderByRegisterstartdateDesc();
	
    }

	public String getName() {
        return name;
    }

	public void onChangeCourseinfoId(ValueChangeEvent e) {
		courseinfoId = (Integer)e.getNewValue();
		
		courseinfo = courseinformationRepository.findOne(courseinfoId);
	}

	public void onChangeTeacherId(ValueChangeEvent e) {
		teacherId = (Integer)e.getNewValue();
		
		teacher = personnelRepository.findOne(teacherId);
	}
	
	public void onChangeBookitemId(ValueChangeEvent e) {
		bookitemId = (Integer)e.getNewValue();
		
		bookitem = bookitemRepository.findOne(bookitemId);
	}
	
	public void onChangeSemesterSelection() {
		latestSemester = semesterRepository.findOne(semesterId);
		
		allSemestercourses = semestercourseRepository.findBySemesteridOrderBySemestercoursecodeAsc(latestSemester);
        
		//semestercourse = semestercourseRepository.findOne(semesterId)
	}
	
	public void onChangeSemesterCourseAction() {
		Map<String,String> params =
			FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		String semestercourserowid = params.get("semestercourserowid");
		
		semestercourse = semestercourseRepository.findOne(Integer.valueOf(semestercourserowid));
	}
	
	public List<Semestercourse> getAllSemestercourses() {
        return allSemestercourses;
    }

	public void setAllSemestercourses(List<Semestercourse> allSemestercourses) {
        this.allSemestercourses = allSemestercourses;
    }

	public String findAllSemestercourses() {
        allSemestercourses = semestercourseRepository.findBySemesteridOrderBySemestercoursecodeAsc(latestSemester);
        return "/pages/admin/semestercourse";
    }

	public Semestercourse getSemestercourse() {
        if (semestercourse == null) {
            semestercourse = new Semestercourse();
        }
        return semestercourse;
    }

	public void setSemestercourse(Semestercourse semestercourse) {
        this.semestercourse = semestercourse;
    }

	public String onEdit() {
		
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
        findAllSemestercourses();
        return "/pages/admin/semestercourse";
    }

	public String displayCreateDialog() {
        semestercourse = new Semestercourse();
        semestercourse.setStatus(Utils.CLASS_STATUS_OPEN);
        semestercourse.setMaxcapacity(30);
        semestercourse.setCurrentcapacity(0);
        semestercourse.setWaitingcapacity(0);
        semestercourse.setDiscountstatus(false);
        semestercourse.setDiscountamount(new BigDecimal(0));
        semestercourse.setSemesterid(latestSemester);
        
        createDialogVisible = true;
        return "/pages/admin/semestercourse";
    }

	public String persist() {
        String message = "";
        String loginUsername = Utils.retrieveLoginUsername();
        //Calendar calendar = GregorianCalendar.getInstance();
        //calendar.setTime(startDate);

        
        if (semestercourse.getId() != null) {
        	//Check whether the semestercourse possibly already existing??
			/*List<Semestercourse> result = semestercourseRepository.findByEmailIgnoreCase(semestercourse.getEmail());
			if (result.size() > 1) {
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, 
    					"More than one semestercourse with the provided email has existed!", "");
				FacesContext.getCurrentInstance().addMessage("editForm:semestercourseemailedit", facesMessage);
    			return null;
			}*/
        	handleClassRelatedData();
			semestercourse.setUpdatedby(loginUsername);
    		semestercourse.setUpdatedtime(GregorianCalendar.getInstance());
    		semestercourseRepository.save(semestercourse);
    		
            message = "message_successfully_updated";
        } else {
        	handleClassRelatedData();
        	semestercourse.setUpdatedby(Utils.retrieveLoginUsername());
        	semestercourse.setUpdatedtime(GregorianCalendar.getInstance());
        	semestercourse = semestercourseRepository.save(semestercourse);
	        
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('createDialogWidget').hide()");
        context.execute("PF('editDialogWidget').hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Semestercourse");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllSemestercourses();
    }

	public String deactivateSemestercourse() {
		semestercourse.setStatus(Utils.CLASS_STATUS_CLOSED);
		semestercourse.setUpdatedby(Utils.retrieveLoginUsername());
		semestercourse.setUpdatedtime(GregorianCalendar.getInstance());
		semestercourseRepository.save(semestercourse);
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_updated", "Semestercourse");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllSemestercourses();
    }
	
	public String delete() {
		semestercourseRepository.delete(semestercourse);
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Semestercourse");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllSemestercourses();
    }

	public void reset() {
        semestercourse = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	private void handleClassRelatedData() {
		if(courseinfoId != null) {
    		Courseinformation classCourseinformation = courseinformationRepository.findOne(courseinfoId);
        	if(classCourseinformation != null) {
        		semestercourse.setCourseinfoid(classCourseinformation);
        	}
    	}
    	if(teacherId != null) {
        	Personnel classTeacher = personnelRepository.findOne(teacherId);
        	if(classTeacher != null) {
        		semestercourse.setTeacherid(classTeacher);
        	}
    	}
    	if(bookitemId != null) {
    		Bookitem classBookitem = bookitemRepository.findOne(bookitemId);
        	if(classBookitem != null) {
        		semestercourse.setBookitemid(classBookitem);
        	}
    	}
	}
	public List<Semestercourse> getFilteredSemestercourses() {
		return filteredSemestercourses;
	}

	public void setFilteredSemestercourses(List<Semestercourse> filteredSemestercourses) {
		this.filteredSemestercourses = filteredSemestercourses;
	}

	public List<SelectItem> getTeacherDropdown() {
		return teacherDropdown;
	}

	public void setTeacherDropdown(List<SelectItem> teacherDropdown) {
		this.teacherDropdown = teacherDropdown;
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

	public PersonnelRepository getPersonnelRepository() {
		return personnelRepository;
	}

	public void setPersonnelRepository(PersonnelRepository personnelRepository) {
		this.personnelRepository = personnelRepository;
	}

	public CourseinformationRepository getCourseinformationRepository() {
		return courseinformationRepository;
	}

	public void setCourseinformationRepository(
			CourseinformationRepository courseinformationRepository) {
		this.courseinformationRepository = courseinformationRepository;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}


	public List<SelectItem> getSemesterDropdown() {
		return semesterDropdown;
	}

	public void setSemesterDropdown(List<SelectItem> semesterDropdown) {
		this.semesterDropdown = semesterDropdown;
	}

	public List<SelectItem> getCourseinfoDropdown() {
		return courseinfoDropdown;
	}

	public void setCourseinfoDropdown(List<SelectItem> courseinfoDropdown) {
		this.courseinfoDropdown = courseinfoDropdown;
	}

	public List<SelectItem> getBookitemDropdown() {
		return bookitemDropdown;
	}

	public void setBookitemDropdown(List<SelectItem> bookitemDropdown) {
		this.bookitemDropdown = bookitemDropdown;
	}

	public Integer getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(Integer semesterId) {
		this.semesterId = semesterId;
	}

	public Integer getCourseinfoId() {
		return courseinfoId;
	}

	public void setCourseinfoId(Integer courseinfoId) {
		this.courseinfoId = courseinfoId;
	}

	public Integer getBookitemId() {
		return bookitemId;
	}

	public void setBookitemId(Integer bookitemId) {
		this.bookitemId = bookitemId;
	}


	public Courseinformation getCourseinfo() {
		return courseinfo;
	}

	public void setCourseinfo(Courseinformation courseinfo) {
		this.courseinfo = courseinfo;
	}


	public Personnel getTeacher() {
		return teacher;
	}

	public void setTeacher(Personnel teacher) {
		this.teacher = teacher;
	}

	public Bookitem getBookitem() {
		return bookitem;
	}

	public void setBookitem(Bookitem bookitem) {
		this.bookitem = bookitem;
	}


	public Semester getLatestSemester() {
		return latestSemester;
	}

	public void setLatestSemester(Semester latestSemester) {
		this.latestSemester = latestSemester;
	}


	public String getSemesterCourseAction() {
		return semesterCourseAction;
	}

	public void setSemesterCourseAction(String semesterCourseAction) {
		this.semesterCourseAction = semesterCourseAction;
	}


	private static final long serialVersionUID = 1L;
}

