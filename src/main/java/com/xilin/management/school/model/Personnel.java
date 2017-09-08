package com.xilin.management.school.model;
import java.util.Calendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema = "public",name = "personnel")
public class Personnel {

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("semestercourse").toString();
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

	@OneToMany(mappedBy = "teacherid")
    private Set<Semestercourse> semestercourse;

	@Column(name = "type")
    @NotNull
    private Character type;

	@Column(name = "jobtitle", length = 50)
    private String jobtitle;

	@Column(name = "firstname", length = 50)
    @NotNull
    private String firstname;

	@Column(name = "lastname", length = 50)
    @NotNull
    private String lastname;

	@Column(name = "middlename", length = 50)
    private String middlename;

	@Column(name = "chinesename", length = 50)
    private String chinesename;

	@Column(name = "hiredate")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar hiredate;

	@Column(name = "gender")
    private Character gender;

	@Column(name = "address1", length = 200)
    private String address1;

	@Column(name = "address2", length = 200)
    private String address2;

	@Column(name = "city", length = 200)
    private String city;

	@Column(name = "state", length = 2)
    @NotNull
    private String state;

	@Column(name = "zip", length = 5)
    private String zip;

	@Column(name = "homephone", length = 10)
    private String homephone;

	@Column(name = "cellphone", length = 10)
    private String cellphone;

	@Column(name = "email", length = 100)
    private String email;

	@Column(name = "xilinemail", length = 100)
    private String xilinemail;

	@Column(name = "resume", length = 5000)
    private String resume;

	@Column(name = "active")
    @NotNull
    private boolean active;

	@Column(name = "updatedtime")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar updatedtime;

	@Column(name = "externaluserid")
    @NotNull
    private Integer externaluserid;

	@Column(name = "updatedBy", length = 40)
    private String updatedBy;

	@Column(name = "loginid", length = 200)
    @NotNull
    private String loginId;

	public Set<Semestercourse> getSemestercourse() {
		return semestercourse;
	}

	public void setSemestercourse(Set<Semestercourse> semestercourse) {
		this.semestercourse = semestercourse;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Character getType() {
        return type;
    }

	public void setType(Character type) {
        this.type = type;
    }

	public String getJobtitle() {
        return jobtitle;
    }

	public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

	public String getFirstname() {
        return firstname;
    }

	public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

	public String getLastname() {
        return lastname;
    }

	public void setLastname(String lastname) {
        this.lastname = lastname;
    }

	public String getMiddlename() {
        return middlename;
    }

	public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

	public String getChinesename() {
        return chinesename;
    }

	public void setChinesename(String chinesename) {
        this.chinesename = chinesename;
    }

	public Calendar getHiredate() {
        return hiredate;
    }

	public void setHiredate(Calendar hiredate) {
        this.hiredate = hiredate;
    }

	public Character getGender() {
        return gender;
    }

	public void setGender(Character gender) {
        this.gender = gender;
    }

	public String getAddress1() {
        return address1;
    }

	public void setAddress1(String address1) {
        this.address1 = address1;
    }

	public String getAddress2() {
        return address2;
    }

	public void setAddress2(String address2) {
        this.address2 = address2;
    }

	public String getCity() {
        return city;
    }

	public void setCity(String city) {
        this.city = city;
    }

	public String getState() {
        return state;
    }

	public void setState(String state) {
        this.state = state;
    }

	public String getZip() {
        return zip;
    }

	public void setZip(String zip) {
        this.zip = zip;
    }

	public String getHomephone() {
        return homephone;
    }

	public void setHomephone(String homephone) {
        this.homephone = homephone;
    }

	public String getCellphone() {
        return cellphone;
    }

	public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

	public String getEmail() {
        return email;
    }

	public void setEmail(String email) {
        this.email = email;
    }

	public String getXilinemail() {
        return xilinemail;
    }

	public void setXilinemail(String xilinemail) {
        this.xilinemail = xilinemail;
    }

	public String getResume() {
        return resume;
    }

	public void setResume(String resume) {
        this.resume = resume;
    }

	public boolean isActive() {
        return active;
    }

	public void setActive(boolean active) {
        this.active = active;
    }

	public Calendar getUpdatedtime() {
        return updatedtime;
    }

	public void setUpdatedtime(Calendar updatedtime) {
        this.updatedtime = updatedtime;
    }

	public Integer getExternaluserid() {
        return externaluserid;
    }

	public void setExternaluserid(Integer externaluserid) {
        this.externaluserid = externaluserid;
    }

	public String getUpdatedBy() {
        return updatedBy;
    }

	public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
