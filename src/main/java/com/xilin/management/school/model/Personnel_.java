package com.xilin.management.school.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-08-25T21:52:48.210-0500")
@StaticMetamodel(Personnel.class)
public class Personnel_ {
	public static volatile SingularAttribute<Personnel, Integer> id;
	public static volatile SetAttribute<Personnel, Semestercourse> semestercourse;
	public static volatile SingularAttribute<Personnel, Character> type;
	public static volatile SingularAttribute<Personnel, String> jobtitle;
	public static volatile SingularAttribute<Personnel, String> firstname;
	public static volatile SingularAttribute<Personnel, String> lastname;
	public static volatile SingularAttribute<Personnel, String> middlename;
	public static volatile SingularAttribute<Personnel, String> chinesename;
	public static volatile SingularAttribute<Personnel, Calendar> hiredate;
	public static volatile SingularAttribute<Personnel, Character> gender;
	public static volatile SingularAttribute<Personnel, String> address1;
	public static volatile SingularAttribute<Personnel, String> address2;
	public static volatile SingularAttribute<Personnel, String> city;
	public static volatile SingularAttribute<Personnel, String> state;
	public static volatile SingularAttribute<Personnel, String> zip;
	public static volatile SingularAttribute<Personnel, String> homephone;
	public static volatile SingularAttribute<Personnel, String> cellphone;
	public static volatile SingularAttribute<Personnel, String> email;
	public static volatile SingularAttribute<Personnel, String> xilinemail;
	public static volatile SingularAttribute<Personnel, String> resume;
	public static volatile SingularAttribute<Personnel, Boolean> active;
	public static volatile SingularAttribute<Personnel, Calendar> updatedtime;
	public static volatile SingularAttribute<Personnel, Integer> externaluserid;
	public static volatile SingularAttribute<Personnel, String> updatedBy;
	public static volatile SingularAttribute<Personnel, String> loginId;
}
