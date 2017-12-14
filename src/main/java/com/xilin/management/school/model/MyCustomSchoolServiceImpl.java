package com.xilin.management.school.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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

	@Autowired
	private SemestercourseRepository semestercourseRepository;
	
	@Autowired
	private FamilytransactionRepository familytransactionRepository;
	
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
		
		List<Semester> allLst = semesterRepository.findTop2ByOrderByStartdateDesc();
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
	
	public void saveStudentRegisteredFamilytransactions(List<Familytransaction> registeredFamilytransactions) {
		String loginUser = Utils.retrieveLoginUsername();
		Calendar currentTime = GregorianCalendar.getInstance();
		
		for(Familytransaction rgs: registeredFamilytransactions) {
			Semestercourse semestercourse = rgs.getSemestercourseid();
			
			semestercourse.setUpdatedby(loginUser);
			semestercourse.setUpdatedtime(currentTime);
			
			if(rgs.getId() == null) {
				// new enroll, adjust class registration number
				int rgsNum = semestercourse.getCurrentcapacity();
				if(rgsNum >= semestercourse.getMaxcapacity()) {
					semestercourse.setStatus(Utils.CLASS_STATUS_FULL);
					int waitingNum = semestercourse.getWaitingcapacity();
					semestercourse.setWaitingcapacity(waitingNum+1);
				}
				else {
					semestercourse.setCurrentcapacity(rgsNum+1);
				}
			}
			semestercourseRepository.save(semestercourse);
			
			/*if(rgs.getBookitemid() != null) {
				Bookitem item = rgs.getBookitemid();
				item.setUpdatedby(loginUser);
				item.setUpdatedtime(currentTime);
				int stockNum = item.getAmountinstock();
				int amountsold = item.getAmountsold();
				item.setAmountinstock(stockNum-1);
				item.setAmountsold(amountsold+1);
				
				bookitemRepository.save(item);
			}*/
		}
		
		familytransactionRepository.save(registeredFamilytransactions);
	}
	
	public void deleteRegisteredCls(Familytransaction familytransaction) {
		String loginUser = Utils.retrieveLoginUsername();
		Calendar currentTime = GregorianCalendar.getInstance();
		
		/*if(familytransaction.getBookitemid() != null) {
			Bookitem item = familytransaction.getBookitemid();
			item.setUpdatedby(loginUser);
			item.setUpdatedtime(currentTime);
			int stockNum = item.getAmountinstock();
			int amountsold = item.getAmountsold();
			item.setAmountinstock(stockNum+1);
			item.setAmountsold(amountsold-1);
			
			bookitemRepository.save(item);
		}*/
		
		if(familytransaction.getId() != null) {
			Semestercourse semestercourse = familytransaction.getSemestercourseid();
			
			int rgsNum = semestercourse.getCurrentcapacity();
			if(rgsNum >= semestercourse.getMaxcapacity()) {
				int waitingNum = semestercourse.getWaitingcapacity();
				if(waitingNum > 0) {
					semestercourse.setWaitingcapacity(waitingNum-1);
				}
				else {
					semestercourse.setStatus(Utils.CLASS_STATUS_OPEN);
					semestercourse.setCurrentcapacity(rgsNum-1);
				}
			}
			else {
				semestercourse.setCurrentcapacity(rgsNum-1);
			}
			
			semestercourseRepository.save(semestercourse);
			familytransactionRepository.delete(familytransaction);
		}
		
	}
	
	public void saveBoughtBooksFamilytransactions(List<Familytransaction> newAddedBooks) {
		/*String loginUser = Utils.retrieveLoginUsername();
		Calendar currentTime = GregorianCalendar.getInstance();
		
		for(Familytransaction rgs: newAddedBooks) {
			Bookitem item = rgs.getBookitemid();
			item.setUpdatedby(loginUser);
			item.setUpdatedtime(currentTime);
			
			int stockNum = item.getAmountinstock();
			int amountsold = item.getAmountsold();
			item.setAmountinstock(stockNum-1);
			item.setAmountsold(amountsold+1);
			
			bookitemRepository.save(item);
		}*/
		
		familytransactionRepository.save(newAddedBooks);
	}
	
	public void deleteBoughtBook(Familytransaction bookFamilytransaction) {
		String loginUser = Utils.retrieveLoginUsername();
		Calendar currentTime = GregorianCalendar.getInstance();
		
		//Bookitem stock updated when serviced
		/*Bookitem item = bookFamilytransaction.getBookitemid();
		item.setUpdatedby(loginUser);
		item.setUpdatedtime(currentTime);
		
		if(bookFamilytransaction.getId() != null) {  //this book from a class registration
			int stockNum = item.getAmountinstock();
			int amountsold = item.getAmountsold();
			item.setAmountinstock(stockNum+1);
			item.setAmountsold(amountsold-1);
		}
		bookitemRepository.save(item);*/
		
		if(bookFamilytransaction.getTransactiontype().equalsIgnoreCase(Utils.FAMILY_TRANSACTION_REGISTER)) {
			bookFamilytransaction.setUpdatedby(loginUser);
			bookFamilytransaction.setUpdatedtime(currentTime);
			bookFamilytransaction.setBookitemid(null);
			bookFamilytransaction.setStatus("");
			familytransactionRepository.save(bookFamilytransaction);
		}
		else {
			familytransactionRepository.delete(bookFamilytransaction);
		}
	}
	
	public void processBoughtBook(Familytransaction bookFamilytransaction) {
		String loginUser = Utils.retrieveLoginUsername();
		Calendar currentTime = GregorianCalendar.getInstance();
		
		//Bookitem stock updated here
		Bookitem item = bookFamilytransaction.getBookitemid();
		item.setUpdatedby(loginUser);
		item.setUpdatedtime(currentTime);
		
		if(bookFamilytransaction.getId() != null) {
			int stockNum = item.getAmountinstock();
			int amountsold = item.getAmountsold();
			item.setAmountinstock(stockNum-1);
			item.setAmountsold(amountsold+1);
		}
		bookitemRepository.save(item);
		
		bookFamilytransaction.setUpdatedby(loginUser);
		bookFamilytransaction.setUpdatedtime(currentTime);
		bookFamilytransaction.setStatus(Utils.BOOK_TRANSACTION_PROCESSED);
		familytransactionRepository.save(bookFamilytransaction);
		
	}
	
	public void returnBoughtBook(Familytransaction bookFamilytransaction) {
		String loginUser = Utils.retrieveLoginUsername();
		Calendar currentTime = GregorianCalendar.getInstance();
		
		//Bookitem stock updated here too
		Bookitem item = bookFamilytransaction.getBookitemid();
		item.setUpdatedby(loginUser);
		item.setUpdatedtime(currentTime);
		
		if(bookFamilytransaction.getId() != null) {
			int stockNum = item.getAmountinstock();
			int amountsold = item.getAmountsold();
			item.setAmountinstock(stockNum+1);
			item.setAmountsold(amountsold-1);
		}
		bookitemRepository.save(item);
		
		bookFamilytransaction.setUpdatedby(loginUser);
		bookFamilytransaction.setUpdatedtime(currentTime);
		bookFamilytransaction.setStatus(Utils.BOOK_TRANSACTION_RETURNED);
		familytransactionRepository.save(bookFamilytransaction);
		
	}
}
