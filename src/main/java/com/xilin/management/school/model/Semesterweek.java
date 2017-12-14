package com.xilin.management.school.model;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema = "public",name = "semesterweek")
public class Semesterweek implements Comparable<Semesterweek> {

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("semesterpods", "semesterid").toString();
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

	public Integer getId() {
        return this.id;
    }

	public void setId(Integer id) {
        this.id = id;
    }

	@OneToMany(mappedBy = "semesterweekid")
    private Set<Semesterpod> semesterpods;

	@ManyToOne
    @JoinColumn(name = "semesterid", referencedColumnName = "id")
    private Semester semesterid;

	@Column(name = "displayweekid")
    private Integer displayweekid;

	@Column(name = "displaynumber", length = 10)
    private String displaynumber;

	@Column(name = "weekdate")
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar weekdate;

	@Column(name = "description", length = 200)
    private String description;

	@Column(name = "hasclass")
    private Boolean hasclass;

	@Column(name = "updatedtime")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar updatedtime;

	@Column(name = "updatedby", length = 40)
    private String updatedby;

	
    //private Boolean editable;
	//@Transient
	//private List<Semesterpod> lstsemesterpods;
	
	@Transient
	private boolean passed;
	
	public Set<Semesterpod> getSemesterpods() {
        return semesterpods;
    }

	public void setSemesterpods(Set<Semesterpod> semesterpods) {
        this.semesterpods = semesterpods;
    }

	public Semester getSemesterid() {
        return semesterid;
    }

	public void setSemesterid(Semester semesterid) {
        this.semesterid = semesterid;
    }

	public Integer getDisplayweekid() {
        return displayweekid;
    }

	public void setDisplayweekid(Integer displayweekid) {
        this.displayweekid = displayweekid;
    }

	public String getDisplaynumber() {
        return displaynumber;
    }

	public void setDisplaynumber(String displaynumber) {
        this.displaynumber = displaynumber;
    }

	public Calendar getWeekdate() {
        return weekdate;
    }

	public void setWeekdate(Calendar weekdate) {
        this.weekdate = weekdate;
    }

	public String getDescription() {
        return description;
    }

	public void setDescription(String description) {
        this.description = description;
    }

	public Boolean getHasclass() {
        return hasclass;
    }

	public void setHasclass(Boolean hasclass) {
        this.hasclass = hasclass;
    }

	public Calendar getUpdatedtime() {
        return updatedtime;
    }

	public void setUpdatedtime(Calendar updatedtime) {
        this.updatedtime = updatedtime;
    }

	public String getUpdatedby() {
        return updatedby;
    }

	public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }


	public boolean isPassed() {
		Calendar today = GregorianCalendar.getInstance();
		return today.compareTo(this.getWeekdate()) > 0 ? true : false;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public int compareTo(Semesterweek compareSemesterweek) {

		Calendar compareCal = compareSemesterweek.getWeekdate();

		//ascending order
		return this.getWeekdate().compareTo(compareCal);

	}
	
	/*public static Comparator<Semesterweek> SemesterweekDateComparator
    	= new Comparator<Semesterweek>() {
		public int compare(Semesterweek week1, Semesterweek week2) {
			Calendar weekdate1 = week1.getWeekdate();
			Calendar weekdate2 = week2.getWeekdate();

			//ascending order
			return weekdate1.compareTo(weekdate2);

			//descending order
			//return weekdate2.compareTo(weekdate1);
		}
	};*/

}
