package com.xilin.management.school.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-10-30T15:46:01.825-0500")
@StaticMetamodel(Semesterpod.class)
public class Semesterpod_ {
	public static volatile SingularAttribute<Semesterpod, Integer> id;
	public static volatile SetAttribute<Semesterpod, Semesterfamilypod> semesterfamilypods;
	public static volatile SingularAttribute<Semesterpod, Semesterweek> semesterweekid;
	public static volatile SingularAttribute<Semesterpod, Integer> capacity;
	public static volatile SingularAttribute<Semesterpod, Integer> filled;
	public static volatile SingularAttribute<Semesterpod, String> podhour;
	public static volatile SingularAttribute<Semesterpod, Calendar> updatedtime;
	public static volatile SingularAttribute<Semesterpod, String> updatedby;
}
