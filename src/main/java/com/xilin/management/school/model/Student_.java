package com.xilin.management.school.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-08-03T15:39:52.586-0500")
@StaticMetamodel(Student.class)
public class Student_ {
	public static volatile SetAttribute<Student, Registration> registrations;
	public static volatile SingularAttribute<Student, Family> familyid;
	public static volatile SingularAttribute<Student, String> lastname;
	public static volatile SingularAttribute<Student, String> firstname;
	public static volatile SingularAttribute<Student, String> middlename;
	public static volatile SingularAttribute<Student, String> chinesename;
	public static volatile SingularAttribute<Student, Calendar> dob;
	public static volatile SingularAttribute<Student, Character> gender;
	public static volatile SingularAttribute<Student, Boolean> active;
	public static volatile SingularAttribute<Student, Calendar> updatedtime;
	public static volatile SingularAttribute<Student, String> updatedby;
	public static volatile SingularAttribute<Student, Integer> id;
}
