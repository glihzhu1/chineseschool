package com.xilin.management.school.model;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema = "public",name = "semester")
public class Semester {
//a comment
	@OneToMany(mappedBy = "semesterid")
    private Set<Familytransaction> familytransactions;

	@OneToMany(mappedBy = "semesterid")
    private Set<Familybilling> familybillings;

	@OneToMany(mappedBy = "semesterid")
    private Set<Registration> registrations;

	@OneToMany(mappedBy = "semesterid")
    private Set<Semestercourse> semestercourses;

	@OneToMany(mappedBy = "semesterid")
    private Set<Semesterweek> semesterweeks;

	@OneToMany(mappedBy = "semesterid")
    private Set<Semestercourse> semestercourse;

	@Column(name = "description", length = 50)
    private String description;

	@Column(name = "startdate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar startdate;

	@Column(name = "enddate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar enddate;

	@Column(name = "active")
    @NotNull
    private boolean active;

	@Column(name = "updatedtime")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar updatedtime;

	@Column(name = "updatedby", length = 40)
    private String updatedby;

	@Column(name = "registerstartdate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar registerstartdate;

	@Column(name = "registerenddate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar registerenddate;

	@Column(name = "paystartdate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar paystartdate;

	@Column(name = "payenddate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar payenddate;

	@Column(name = "registrationfee", precision = 131089)
    private BigDecimal registrationfee;

	@Column(name = "discountdate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar discountdate;

	@Column(name = "discountamount", precision = 131089)
    private BigDecimal discountamount;

	@Column(name = "podfee", precision = 131089)
    private BigDecimal podfee;

	@Column(name = "podrefundamount", precision = 131089)
    private BigDecimal podrefundamount;

	@Column(name = "returnedcheckfee", precision = 131089)
    private BigDecimal returnedcheckfee;

	@Transient
	private boolean passed;

	public Set<Familytransaction> getFamilytransactions() {
		return familytransactions;
	}

	public void setFamilytransactions(Set<Familytransaction> familytransactions) {
		this.familytransactions = familytransactions;
	}

	public Set<Familybilling> getFamilybillings() {
        return familybillings;
    }

	public void setFamilybillings(Set<Familybilling> familybillings) {
        this.familybillings = familybillings;
    }

	public Set<Registration> getRegistrations() {
        return registrations;
    }

	public void setRegistrations(Set<Registration> registrations) {
        this.registrations = registrations;
    }

	public Set<Semestercourse> getSemestercourses() {
        return semestercourses;
    }

	public void setSemestercourses(Set<Semestercourse> semestercourses) {
        this.semestercourses = semestercourses;
    }

	public Set<Semesterweek> getSemesterweeks() {
        return semesterweeks;
    }

	public void setSemesterweeks(Set<Semesterweek> semesterweeks) {
        this.semesterweeks = semesterweeks;
    }

	
	public Set<Semestercourse> getSemestercourse() {
		return semestercourse;
	}

	public void setSemestercourse(Set<Semestercourse> semestercourse) {
		this.semestercourse = semestercourse;
	}

	public String getDescription() {
        return description;
    }

	public void setDescription(String description) {
        this.description = description;
    }

	public Calendar getStartdate() {
        return startdate;
    }

	public void setStartdate(Calendar startdate) {
        this.startdate = startdate;
    }

	public Calendar getEnddate() {
        return enddate;
    }

	public void setEnddate(Calendar enddate) {
        this.enddate = enddate;
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

	public String getUpdatedby() {
        return updatedby;
    }

	public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

	public Calendar getRegisterstartdate() {
        return registerstartdate;
    }

	public void setRegisterstartdate(Calendar registerstartdate) {
        this.registerstartdate = registerstartdate;
    }

	public Calendar getRegisterenddate() {
        return registerenddate;
    }

	public void setRegisterenddate(Calendar registerenddate) {
        this.registerenddate = registerenddate;
    }

	public Calendar getPaystartdate() {
        return paystartdate;
    }

	public void setPaystartdate(Calendar paystartdate) {
        this.paystartdate = paystartdate;
    }

	public Calendar getPayenddate() {
        return payenddate;
    }

	public void setPayenddate(Calendar payenddate) {
        this.payenddate = payenddate;
    }

	public BigDecimal getRegistrationfee() {
        return registrationfee;
    }

	public void setRegistrationfee(BigDecimal registrationfee) {
        this.registrationfee = registrationfee;
    }

	public Calendar getDiscountdate() {
        return discountdate;
    }

	public void setDiscountdate(Calendar discountdate) {
        this.discountdate = discountdate;
    }

	public BigDecimal getDiscountamount() {
        return discountamount;
    }

	public void setDiscountamount(BigDecimal discountamount) {
        this.discountamount = discountamount;
    }

	public BigDecimal getPodfee() {
        return podfee;
    }

	public void setPodfee(BigDecimal podfee) {
        this.podfee = podfee;
    }

	public BigDecimal getPodrefundamount() {
        return podrefundamount;
    }

	public void setPodrefundamount(BigDecimal podrefundamount) {
        this.podrefundamount = podrefundamount;
    }

	public BigDecimal getReturnedcheckfee() {
        return returnedcheckfee;
    }

	public void setReturnedcheckfee(BigDecimal returnedcheckfee) {
        this.returnedcheckfee = returnedcheckfee;
    }

	public boolean isPassed() {
		Calendar today = GregorianCalendar.getInstance();
		return today.compareTo(this.getEnddate()) > 0 ? true : false;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	
	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("familytransactions", "familybillings", "registrations", "semestercourses", "semesterweeks").toString();
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
