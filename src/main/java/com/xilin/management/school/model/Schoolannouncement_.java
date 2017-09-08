package com.xilin.management.school.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-08-22T14:58:41.484-0500")
@StaticMetamodel(Schoolannouncement.class)
public class Schoolannouncement_ {
	public static volatile SingularAttribute<Schoolannouncement, Integer> id;
	public static volatile SingularAttribute<Schoolannouncement, String> newstitle;
	public static volatile SingularAttribute<Schoolannouncement, String> newscontent;
	public static volatile SingularAttribute<Schoolannouncement, String> infofilename;
	public static volatile SingularAttribute<Schoolannouncement, String> imagefilename;
	public static volatile SingularAttribute<Schoolannouncement, Boolean> active;
	public static volatile SingularAttribute<Schoolannouncement, Calendar> updatedtime;
	public static volatile SingularAttribute<Schoolannouncement, String> updatedby;
	public static volatile SingularAttribute<Schoolannouncement, String> announcetype;
	public static volatile SingularAttribute<Schoolannouncement, String> creatoremail;
	public static volatile SingularAttribute<Schoolannouncement, Calendar> createtime;
	public static volatile SingularAttribute<Schoolannouncement, String> creatorname;
}
