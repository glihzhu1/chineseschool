package com.xilin.management.school.model;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-09-12T08:58:36.603-0500")
@StaticMetamodel(Semestercourse.class)
public class Semestercourse_ {
	public static volatile SingularAttribute<Semestercourse, Bookitem> bookitemid;
	public static volatile SingularAttribute<Semestercourse, Courseinformation> courseinfoid;
	public static volatile SingularAttribute<Semestercourse, Semester> semesterid;
	public static volatile SingularAttribute<Semestercourse, Personnel> teacherid;
	public static volatile SingularAttribute<Semestercourse, String> semestercoursecode;
	public static volatile SingularAttribute<Semestercourse, String> starttime;
	public static volatile SingularAttribute<Semestercourse, String> endtime;
	public static volatile SingularAttribute<Semestercourse, String> room;
	public static volatile SingularAttribute<Semestercourse, Integer> maxcapacity;
	public static volatile SingularAttribute<Semestercourse, Integer> mincapacity;
	public static volatile SingularAttribute<Semestercourse, Integer> currentcapacity;
	public static volatile SingularAttribute<Semestercourse, Integer> waitingcapacity;
	public static volatile SingularAttribute<Semestercourse, BigDecimal> price;
	public static volatile SingularAttribute<Semestercourse, Boolean> discountstatus;
	public static volatile SingularAttribute<Semestercourse, BigDecimal> discountamount;
	public static volatile SingularAttribute<Semestercourse, String> status;
	public static volatile SingularAttribute<Semestercourse, String> note;
	public static volatile SingularAttribute<Semestercourse, Calendar> updatedtime;
	public static volatile SingularAttribute<Semestercourse, String> updatedby;
	public static volatile SingularAttribute<Semestercourse, Integer> id;
}
