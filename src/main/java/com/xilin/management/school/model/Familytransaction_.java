package com.xilin.management.school.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-09-06T15:39:57.551-0500")
@StaticMetamodel(Familytransaction.class)
public class Familytransaction_ {
	public static volatile SingularAttribute<Familytransaction, Integer> id;
	public static volatile SingularAttribute<Familytransaction, Bookitem> bookitemid;
	public static volatile SingularAttribute<Familytransaction, Family> familyid;
	public static volatile SingularAttribute<Familytransaction, Semester> semesterid;
	public static volatile SingularAttribute<Familytransaction, Student> studentid;
	public static volatile SingularAttribute<Familytransaction, Calendar> registereddate;
	public static volatile SingularAttribute<Familytransaction, Calendar> paiddate;
	public static volatile SingularAttribute<Familytransaction, String> status;
	public static volatile SingularAttribute<Familytransaction, Boolean> active;
	public static volatile SingularAttribute<Familytransaction, String> updatedby;
	public static volatile SingularAttribute<Familytransaction, Calendar> updatedtime;
	public static volatile SingularAttribute<Familytransaction, Semestercourse> semestercourseid;
	public static volatile SingularAttribute<Familytransaction, String> transactiontype;
}
