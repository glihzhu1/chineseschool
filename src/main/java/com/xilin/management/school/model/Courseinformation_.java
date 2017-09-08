package com.xilin.management.school.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-08-29T14:14:45.193-0500")
@StaticMetamodel(Courseinformation.class)
public class Courseinformation_ {
	public static volatile SetAttribute<Courseinformation, Semestercourse> semestercourses;
	public static volatile SingularAttribute<Courseinformation, String> coursecode;
	public static volatile SingularAttribute<Courseinformation, String> coursegrade;
	public static volatile SingularAttribute<Courseinformation, String> coursename;
	public static volatile SingularAttribute<Courseinformation, String> chinesecoursename;
	public static volatile SingularAttribute<Courseinformation, String> coursetype;
	public static volatile SingularAttribute<Courseinformation, Calendar> updatedtime;
	public static volatile SingularAttribute<Courseinformation, String> updatedby;
	public static volatile SingularAttribute<Courseinformation, Integer> id;
}
