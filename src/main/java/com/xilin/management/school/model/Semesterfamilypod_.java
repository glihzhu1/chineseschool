package com.xilin.management.school.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-08-22T14:58:41.524-0500")
@StaticMetamodel(Semesterfamilypod.class)
public class Semesterfamilypod_ {
	public static volatile SingularAttribute<Semesterfamilypod, Family> familyid;
	public static volatile SingularAttribute<Semesterfamilypod, Semesterpod> semesterpodid;
	public static volatile SingularAttribute<Semesterfamilypod, Boolean> performed;
	public static volatile SingularAttribute<Semesterfamilypod, Calendar> updatedtime;
	public static volatile SingularAttribute<Semesterfamilypod, String> updatedby;
	public static volatile SingularAttribute<Semesterfamilypod, Integer> id;
}
