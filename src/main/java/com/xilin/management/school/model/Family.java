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
@Table(schema = "public",name = "family")
public class Family {

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

	@OneToMany(mappedBy = "familyid")
    private Set<Registration> registrations;

	@OneToMany(mappedBy = "familyid")
    private Set<Student> students;

	@Column(name = "type")
    @NotNull
    private Character type;

	@Column(name = "fatherlastname", length = 50)
    private String fatherlastname;

	@Column(name = "fatherfirstname", length = 50)
    private String fatherfirstname;

	@Column(name = "fathermiddlename", length = 50)
    private String fathermiddlename;

	@Column(name = "fatherchinesename", length = 50)
    private String fatherchinesename;

	@Column(name = "motherlastname", length = 50)
    private String motherlastname;

	@Column(name = "motherfirstname", length = 50)
    private String motherfirstname;

	@Column(name = "mothermiddlename", length = 50)
    private String mothermiddlename;

	@Column(name = "motherchinesename", length = 50)
    private String motherchinesename;

	@Column(name = "address1", length = 200)
    private String address1;

	@Column(name = "address2", length = 200)
    private String address2;

	@Column(name = "city", length = 200)
    private String city;

	@Column(name = "state", length = 2)
    @NotNull
    private String state;

	@Column(name = "zip", length = 20)
    @NotNull
    private String zip;

	@Column(name = "homephone", length = 20)
    private String homephone;

	@Column(name = "cellphone", length = 20)
    private String cellphone;

	@Column(name = "email", length = 100)
    private String email;

	@Column(name = "xilinemail", length = 100)
    private String xilinemail;

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

	@Column(name = "loginid", length = 200)
    @NotNull
    private String loginId;
	
	@Column(name = "updatedBy", length = 40)
    private String updatedBy;

	public Set<Registration> getRegistrations() {
        return registrations;
    }

	public void setRegistrations(Set<Registration> registrations) {
        this.registrations = registrations;
    }

	public Set<Student> getStudents() {
        return students;
    }

	public void setStudents(Set<Student> students) {
        this.students = students;
    }

	public Character getType() {
        return type;
    }

	public void setType(Character type) {
        this.type = type;
    }

	public String getFatherlastname() {
        return fatherlastname;
    }

	public void setFatherlastname(String fatherlastname) {
        this.fatherlastname = fatherlastname;
    }

	public String getFatherfirstname() {
        return fatherfirstname;
    }

	public void setFatherfirstname(String fatherfirstname) {
        this.fatherfirstname = fatherfirstname;
    }

	public String getFathermiddlename() {
        return fathermiddlename;
    }

	public void setFathermiddlename(String fathermiddlename) {
        this.fathermiddlename = fathermiddlename;
    }

	public String getFatherchinesename() {
        return fatherchinesename;
    }

	public void setFatherchinesename(String fatherchinesename) {
        this.fatherchinesename = fatherchinesename;
    }

	public String getMotherlastname() {
        return motherlastname;
    }

	public void setMotherlastname(String motherlastname) {
        this.motherlastname = motherlastname;
    }

	public String getMotherfirstname() {
        return motherfirstname;
    }

	public void setMotherfirstname(String motherfirstname) {
        this.motherfirstname = motherfirstname;
    }

	public String getMothermiddlename() {
        return mothermiddlename;
    }

	public void setMothermiddlename(String mothermiddlename) {
        this.mothermiddlename = mothermiddlename;
    }

	public String getMotherchinesename() {
        return motherchinesename;
    }

	public void setMotherchinesename(String motherchinesename) {
        this.motherchinesename = motherchinesename;
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

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("registrations", "students").toString();
    }
}
