package com.xilin.management.school.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-11-01T08:56:21.361-0500")
@StaticMetamodel(Semesterweek.class)
public class Semesterweek_ {
	public static volatile SingularAttribute<Semesterweek, Integer> id;
	public static volatile SetAttribute<Semesterweek, Semesterpod> semesterpods;
	public static volatile SingularAttribute<Semesterweek, Semester> semesterid;
	public static volatile SingularAttribute<Semesterweek, Integer> displayweekid;
	public static volatile SingularAttribute<Semesterweek, String> displaynumber;
	public static volatile SingularAttribute<Semesterweek, Calendar> weekdate;
	public static volatile SingularAttribute<Semesterweek, String> description;
	public static volatile SingularAttribute<Semesterweek, Boolean> hasclass;
	public static volatile SingularAttribute<Semesterweek, Calendar> updatedtime;
	public static volatile SingularAttribute<Semesterweek, String> updatedby;
}
