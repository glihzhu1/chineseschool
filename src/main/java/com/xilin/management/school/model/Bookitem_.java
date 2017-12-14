package com.xilin.management.school.model;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-09-07T21:28:26.219-0500")
@StaticMetamodel(Bookitem.class)
public class Bookitem_ {
	public static volatile SingularAttribute<Bookitem, Integer> id;
	public static volatile SetAttribute<Bookitem, Familytransaction> familytransactions;
	public static volatile SetAttribute<Bookitem, Semestercourse> semestercourses;
	public static volatile SingularAttribute<Bookitem, String> bookcode;
	public static volatile SingularAttribute<Bookitem, String> bookname;
	public static volatile SingularAttribute<Bookitem, String> bookchinesename;
	public static volatile SingularAttribute<Bookitem, String> bookdescription;
	public static volatile SingularAttribute<Bookitem, Boolean> active;
	public static volatile SingularAttribute<Bookitem, BigDecimal> bookprice;
	public static volatile SingularAttribute<Bookitem, BigDecimal> bookdiscountprice;
	public static volatile SingularAttribute<Bookitem, Integer> amountsold;
	public static volatile SingularAttribute<Bookitem, Integer> amountinstock;
	public static volatile SingularAttribute<Bookitem, Calendar> updatedtime;
	public static volatile SingularAttribute<Bookitem, String> updatedby;
}
