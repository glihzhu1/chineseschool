package com.xilin.management.school.model;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-08-22T14:58:41.415-0500")
@StaticMetamodel(Familybilling.class)
public class Familybilling_ {
	public static volatile SingularAttribute<Familybilling, Integer> id;
	public static volatile SingularAttribute<Familybilling, Family> familyid;
	public static volatile SingularAttribute<Familybilling, Semester> semesterid;
	public static volatile SingularAttribute<Familybilling, BigDecimal> amount;
	public static volatile SingularAttribute<Familybilling, String> checknum;
	public static volatile SingularAttribute<Familybilling, String> billingtype;
	public static volatile SingularAttribute<Familybilling, String> description;
	public static volatile SingularAttribute<Familybilling, Calendar> processtime;
	public static volatile SingularAttribute<Familybilling, String> status;
	public static volatile SingularAttribute<Familybilling, Calendar> updatedtime;
	public static volatile SingularAttribute<Familybilling, String> updatedby;
}
