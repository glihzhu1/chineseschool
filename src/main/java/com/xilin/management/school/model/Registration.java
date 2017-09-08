package com.xilin.management.school.model;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema = "public",name = "registration")
public class Registration {

	@ManyToOne
    @JoinColumn(name = "familyid", referencedColumnName = "id")
    private Family familyid;

	@ManyToOne
    @JoinColumn(name = "semesterid", referencedColumnName = "id")
    private Semester semesterid;

	@ManyToOne
    @JoinColumn(name = "studentid", referencedColumnName = "id")
    private Student studentid;

	@Column(name = "registrationsemesterids", length = 100)
    private String registrationsemesterids;

	@Column(name = "coursecode", length = 20)
    @NotNull
    private String coursecode;

	@Column(name = "ordercode")
    private Integer ordercode;

	@Column(name = "registereddate")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar registereddate;

	@Column(name = "paiddate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar paiddate;

	@Column(name = "status")
    @NotNull
    private Integer status;

	@Column(name = "active")
    @NotNull
    private boolean active;

	@Column(name = "updatedby", length = 40)
    @NotNull
    private String updatedby;

	@Column(name = "updatedtime")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar updatedtime;

	public Family getFamilyid() {
        return familyid;
    }

	public void setFamilyid(Family familyid) {
        this.familyid = familyid;
    }

	public Semester getSemesterid() {
        return semesterid;
    }

	public void setSemesterid(Semester semesterid) {
        this.semesterid = semesterid;
    }

	public Student getStudentid() {
        return studentid;
    }

	public void setStudentid(Student studentid) {
        this.studentid = studentid;
    }

	public String getRegistrationsemesterids() {
        return registrationsemesterids;
    }

	public void setRegistrationsemesterids(String registrationsemesterids) {
        this.registrationsemesterids = registrationsemesterids;
    }

	public String getCoursecode() {
        return coursecode;
    }

	public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

	public Integer getOrdercode() {
        return ordercode;
    }

	public void setOrdercode(Integer ordercode) {
        this.ordercode = ordercode;
    }

	public Calendar getRegistereddate() {
        return registereddate;
    }

	public void setRegistereddate(Calendar registereddate) {
        this.registereddate = registereddate;
    }

	public Calendar getPaiddate() {
        return paiddate;
    }

	public void setPaiddate(Calendar paiddate) {
        this.paiddate = paiddate;
    }

	public Integer getStatus() {
        return status;
    }

	public void setStatus(Integer status) {
        this.status = status;
    }

	public boolean isActive() {
        return active;
    }

	public void setActive(boolean active) {
        this.active = active;
    }

	public String getUpdatedby() {
        return updatedby;
    }

	public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

	public Calendar getUpdatedtime() {
        return updatedtime;
    }

	public void setUpdatedtime(Calendar updatedtime) {
        this.updatedtime = updatedtime;
    }

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("familyid", "semesterid", "studentid").toString();
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
}
