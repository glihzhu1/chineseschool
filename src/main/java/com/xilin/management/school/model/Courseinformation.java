package com.xilin.management.school.model;
import java.util.Calendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema = "public",name = "courseinformation")
public class Courseinformation {

	@OneToMany(mappedBy = "courseinfoid")
    private Set<Semestercourse> semestercourses;

	/*@ManyToOne
    @JoinColumn(name = "coursecategoryid", referencedColumnName = "id")
    private Coursecategory coursecategoryid;
*/
	@Column(name = "coursecode", length = 50)
    private String coursecode;

	@Column(name = "coursegrade", length = 50)
    private String coursegrade;

	@Column(name = "coursename", length = 50)
    private String coursename;

	@Column(name = "chinesecoursename", length = 50)
    private String chinesecoursename;

	@Column(name = "coursetype", length = 50)
    private String coursetype;
	
	@Column(name = "updatedtime")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar updatedtime;

	@Column(name = "updatedby", length = 40)
    private String updatedby;

	public Set<Semestercourse> getSemestercourses() {
        return semestercourses;
    }

	public void setSemestercourses(Set<Semestercourse> semestercourses) {
        this.semestercourses = semestercourses;
    }

	public String getCoursecode() {
        return coursecode;
    }

	public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

	public String getCoursegrade() {
        return coursegrade;
    }

	public void setCoursegrade(String coursegrade) {
        this.coursegrade = coursegrade;
    }

	public String getCoursename() {
        return coursename;
    }

	public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

	public String getChinesecoursename() {
        return chinesecoursename;
    }

	public void setChinesecoursename(String chinesecoursename) {
        this.chinesecoursename = chinesecoursename;
    }

	public String getCoursetype() {
		return coursetype;
	}

	public void setCoursetype(String coursetype) {
		this.coursetype = coursetype;
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

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("semestercourses").toString();
    }
}
