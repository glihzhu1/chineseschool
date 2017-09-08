package com.xilin.management.school.model;
import java.math.BigDecimal;
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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema = "public",name = "familybilling")
public class Familybilling {

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

	@ManyToOne
    @JoinColumn(name = "familyid", referencedColumnName = "id")
    private Family familyid;

	@ManyToOne
    @JoinColumn(name = "semesterid", referencedColumnName = "id")
    private Semester semesterid;

	@Column(name = "amount", precision = 131089)
    private BigDecimal amount;

	@Column(name = "checknum", length = 20)
    private String checknum;

	@Column(name = "billingtype", length = 20)
    private String billingtype;

	@Column(name = "description", length = 100)
    private String description;

	@Column(name = "processtime")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar processtime;

	@Column(name = "status", length = 20)
    private String status;

	@Column(name = "updatedtime")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar updatedtime;

	@Column(name = "updatedby", length = 40)
    private String updatedby;

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

	public BigDecimal getAmount() {
        return amount;
    }

	public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

	public String getChecknum() {
        return checknum;
    }

	public void setChecknum(String checknum) {
        this.checknum = checknum;
    }

	public String getBillingtype() {
        return billingtype;
    }

	public void setBillingtype(String billingtype) {
        this.billingtype = billingtype;
    }

	public String getDescription() {
        return description;
    }

	public void setDescription(String description) {
        this.description = description;
    }

	public Calendar getProcesstime() {
        return processtime;
    }

	public void setProcesstime(Calendar processtime) {
        this.processtime = processtime;
    }

	public String getStatus() {
        return status;
    }

	public void setStatus(String status) {
        this.status = status;
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

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("familyid", "semesterid").toString();
    }
}
