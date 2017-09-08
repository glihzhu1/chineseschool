package com.xilin.management.school.model;

import java.util.List;

import javax.faces.model.SelectItem;

public interface MyCustomSchoolService {
	public List<SelectItem> queryTeacherItems();
	
	public List<SelectItem> queryTop2SemesterItems();
	
	public List<SelectItem> queryAllBookitemItems();
	
	public List<SelectItem> queryAllCourseinformationItems();
	
	public List<MyClassViewObject> queryAllClasses();
}
