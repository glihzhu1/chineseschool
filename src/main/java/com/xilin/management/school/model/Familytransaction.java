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
@Table(schema = "public",name = "familytransaction")
public class Familytransaction {

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
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("bookitemid", "familyid", "semesterid", "studentid", "semestercourseid").toString();
    }

	@ManyToOne
    @JoinColumn(name = "bookitemid", referencedColumnName = "id")
    private Bookitem bookitemid;

	@ManyToOne
    @JoinColumn(name = "familyid", referencedColumnName = "id")
    private Family familyid;

	@ManyToOne
    @JoinColumn(name = "semesterid", referencedColumnName = "id")
    private Semester semesterid;

	@ManyToOne
    @JoinColumn(name = "studentid", referencedColumnName = "id")
    private Student studentid;

	@Column(name = "registereddate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar registereddate;

	@Column(name = "paiddate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar paiddate;

	@Column(name = "status", length = 40)
    private String status;

	@Column(name = "active")
    private Boolean active;

	@Column(name = "updatedby", length = 40)
    private String updatedby;

	@Column(name = "updatedtime")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar updatedtime;

	@ManyToOne
    @JoinColumn(name = "semestercourseid", referencedColumnName = "id")
    private Semestercourse semestercourseid;
	
	@Column(name = "transactiontype", length = 20)
    private String transactiontype;
	
	public Bookitem getBookitemid() {
        return bookitemid;
    }

	public void setBookitemid(Bookitem bookitemid) {
        this.bookitemid = bookitemid;
    }

	public Semestercourse getSemestercourseid() {
		return semestercourseid;
	}

	public void setSemestercourseid(Semestercourse semestercourseid) {
		this.semestercourseid = semestercourseid;
	}

	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

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

	public String getStatus() {
        return status;
    }

	public void setStatus(String status) {
        this.status = status;
    }

	public Boolean getActive() {
        return active;
    }

	public void setActive(Boolean active) {
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
}
