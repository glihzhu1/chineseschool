package com.xilin.management.school.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-08-22T14:58:41.418-0500")
@StaticMetamodel(Registration.class)
public class Registration_ {
	public static volatile SingularAttribute<Registration, Family> familyid;
	public static volatile SingularAttribute<Registration, Semester> semesterid;
	public static volatile SingularAttribute<Registration, Student> studentid;
	public static volatile SingularAttribute<Registration, String> registrationsemesterids;
	public static volatile SingularAttribute<Registration, String> coursecode;
	public static volatile SingularAttribute<Registration, Integer> ordercode;
	public static volatile SingularAttribute<Registration, Calendar> registereddate;
	public static volatile SingularAttribute<Registration, Calendar> paiddate;
	public static volatile SingularAttribute<Registration, Integer> status;
	public static volatile SingularAttribute<Registration, Boolean> active;
	public static volatile SingularAttribute<Registration, String> updatedby;
	public static volatile SingularAttribute<Registration, Calendar> updatedtime;
	public static volatile SingularAttribute<Registration, Integer> id;
}
