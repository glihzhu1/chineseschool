package com.xilin.management.school.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-08-09T10:09:40.473-0500")
@StaticMetamodel(Family.class)
public class Family_ {
	public static volatile SingularAttribute<Family, Integer> id;
	public static volatile SetAttribute<Family, Registration> registrations;
	public static volatile SetAttribute<Family, Student> students;
	public static volatile SingularAttribute<Family, Character> type;
	public static volatile SingularAttribute<Family, String> fatherlastname;
	public static volatile SingularAttribute<Family, String> fatherfirstname;
	public static volatile SingularAttribute<Family, String> fathermiddlename;
	public static volatile SingularAttribute<Family, String> fatherchinesename;
	public static volatile SingularAttribute<Family, String> motherlastname;
	public static volatile SingularAttribute<Family, String> motherfirstname;
	public static volatile SingularAttribute<Family, String> mothermiddlename;
	public static volatile SingularAttribute<Family, String> motherchinesename;
	public static volatile SingularAttribute<Family, String> address1;
	public static volatile SingularAttribute<Family, String> address2;
	public static volatile SingularAttribute<Family, String> city;
	public static volatile SingularAttribute<Family, String> state;
	public static volatile SingularAttribute<Family, String> zip;
	public static volatile SingularAttribute<Family, String> homephone;
	public static volatile SingularAttribute<Family, String> cellphone;
	public static volatile SingularAttribute<Family, String> email;
	public static volatile SingularAttribute<Family, String> xilinemail;
	public static volatile SingularAttribute<Family, Boolean> active;
	public static volatile SingularAttribute<Family, Calendar> updatedtime;
	public static volatile SingularAttribute<Family, Integer> externaluserid;
	public static volatile SingularAttribute<Family, String> loginId;
	public static volatile SingularAttribute<Family, String> updatedBy;
}
