package com.xilin.management.school.model;

import java.util.List;

import javax.faces.model.SelectItem;

public interface MyCustomSchoolService {
	public List<SelectItem> queryTeacherItems();
	
	public List<SelectItem> queryTop2SemesterItems();
	
	public List<SelectItem> queryAllBookitemItems();
	
	public List<SelectItem> queryAllCourseinformationItems();
	
	public List<MyClassViewObject> queryAllClasses();
	
	public void saveStudentRegisteredFamilytransactions(List<Familytransaction> registeredFamilytransactions);
	public void deleteRegisteredCls(Familytransaction familytransaction);
	
	//public void saveBoughtBooksFamilytransactions(List<Familytransaction> newAddedBooks, List<Familytransaction> removebookFamilytransactions);
	public void saveBoughtBooksFamilytransactions(List<Familytransaction> newAddedBooks);
	public void deleteBoughtBook(Familytransaction bookFamilytransaction);
	public void processBoughtBook(Familytransaction bookFamilytransaction);
	public void returnBoughtBook(Familytransaction bookFamilytransaction);
	
}
