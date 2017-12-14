package com.xilin.management.school.model;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-09-07T21:28:26.327-0500")
@StaticMetamodel(Semester.class)
public class Semester_ {
	public static volatile SetAttribute<Semester, Familytransaction> familytransactions;
	public static volatile SetAttribute<Semester, Familybilling> familybillings;
	public static volatile SetAttribute<Semester, Registration> registrations;
	public static volatile SetAttribute<Semester, Semestercourse> semestercourses;
	public static volatile SetAttribute<Semester, Semesterweek> semesterweeks;
	public static volatile SetAttribute<Semester, Semestercourse> semestercourse;
	public static volatile SingularAttribute<Semester, String> description;
	public static volatile SingularAttribute<Semester, Calendar> startdate;
	public static volatile SingularAttribute<Semester, Calendar> enddate;
	public static volatile SingularAttribute<Semester, Boolean> active;
	public static volatile SingularAttribute<Semester, Calendar> updatedtime;
	public static volatile SingularAttribute<Semester, String> updatedby;
	public static volatile SingularAttribute<Semester, Calendar> registerstartdate;
	public static volatile SingularAttribute<Semester, Calendar> registerenddate;
	public static volatile SingularAttribute<Semester, Calendar> paystartdate;
	public static volatile SingularAttribute<Semester, Calendar> payenddate;
	public static volatile SingularAttribute<Semester, BigDecimal> registrationfee;
	public static volatile SingularAttribute<Semester, Calendar> discountdate;
	public static volatile SingularAttribute<Semester, BigDecimal> discountamount;
	public static volatile SingularAttribute<Semester, BigDecimal> podfee;
	public static volatile SingularAttribute<Semester, BigDecimal> podrefundamount;
	public static volatile SingularAttribute<Semester, BigDecimal> returnedcheckfee;
	public static volatile SingularAttribute<Semester, Integer> id;
}
