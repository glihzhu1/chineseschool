package com.xilin.management.school.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xilin.management.school.web.util.Utils;

@Service
@Transactional
public class MyCustomSchoolServiceImpl implements MyCustomSchoolService {
	
	@Autowired
	private SemesterRepository semesterRepository;
	
	@Autowired
	private BookitemRepository bookitemRepository;
	
	@Autowired
	private PersonnelRepository personnelRepository;
	
	@Autowired
	private CourseinformationRepository courseinformationRepository;
	
	@Override
	public List<SelectItem> queryTeacherItems() {
		List<SelectItem> allItems = new ArrayList<SelectItem>();
		
		List<Personnel> allLst = personnelRepository.findAllByTypeOrderByLastnameAsc(Utils.PERSONNEL_TEACHER);
		for(Personnel itm : allLst) {
			SelectItem item = new SelectItem(itm.getId(), 
					itm.getLastname() + ", " + itm.getFirstname());
			allItems.add(item);
		}
		
		return allItems;
	}
	
	@Override
	public List<SelectItem> queryTop2SemesterItems() {
		List<SelectItem> allItems = new ArrayList<SelectItem>();
		
		List<Semester> allLst = semesterRepository.findTop2ByOrderByUpdatedtimeDesc();
		for(Semester itm : allLst) {
			SelectItem item = new SelectItem(itm.getId(), 
					itm.getDescription());
			allItems.add(item);
		}
		
		return allItems;
	}
	
	@Override
	public List<SelectItem> queryAllBookitemItems() {
		List<SelectItem> allItems = new ArrayList<SelectItem>();
		
		List<Bookitem> allLst = bookitemRepository.findAllByOrderByBookcodeAsc();
		for(Bookitem itm : allLst) {
			SelectItem item = new SelectItem(itm.getId(), 
					itm.getBookcode());
			allItems.add(item);
		}
		
		return allItems;
	}
	
	@Override
	public List<SelectItem> queryAllCourseinformationItems() {
		List<SelectItem> allItems = new ArrayList<SelectItem>();
		
		List<Courseinformation> allLst = courseinformationRepository.findAllByOrderByCoursecodeAsc();
		for(Courseinformation itm : allLst) {
			SelectItem item = new SelectItem(itm.getId(), 
					itm.getCoursecode());
			allItems.add(item);
		}
		
		return allItems;
	}
	
	// Not used...
	@Query("select NEW com.xilin.management.school.model.MyClassViewObject(sc.id, u.strManuContactName, " +
			" u.strManuEmail, u.strManuPhone) " +
			"from Semestercourse sc left join sc.teacherid t ")
			//"where m = :pkCanId")
	public List<MyClassViewObject> queryAllClasses() {
		List<MyClassViewObject> allItems = new ArrayList<MyClassViewObject>();
		
		return allItems;
	}
}
