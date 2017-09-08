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
@Table(schema = "public",name = "student")
public class Student {

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("registrations", "familyid").toString();
    }

	@OneToMany(mappedBy = "studentid")
    private Set<Registration> registrations;

	@ManyToOne
    @JoinColumn(name = "familyid", referencedColumnName = "id")
    private Family familyid;

	@Column(name = "lastname", length = 50)
    @NotNull
    private String lastname;

	@Column(name = "firstname", length = 50)
    @NotNull
    private String firstname;

	@Column(name = "middlename", length = 50)
    private String middlename;

	@Column(name = "chinesename", length = 50)
    private String chinesename;

	@Column(name = "dob")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar dob;

	@Column(name = "gender")
    @NotNull
    private Character gender;

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

	public Set<Registration> getRegistrations() {
        return registrations;
    }

	public void setRegistrations(Set<Registration> registrations) {
        this.registrations = registrations;
    }

	public Family getFamilyid() {
        return familyid;
    }

	public void setFamilyid(Family familyid) {
        this.familyid = familyid;
    }

	public String getLastname() {
        return lastname;
    }

	public void setLastname(String lastname) {
        this.lastname = lastname;
    }

	public String getFirstname() {
        return firstname;
    }

	public void setFirstname(String firstname) {
        this.firstname = firstname;
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

	public Calendar getDob() {
        return dob;
    }

	public void setDob(Calendar dob) {
        this.dob = dob;
    }

	public Character getGender() {
        return gender;
    }

	public void setGender(Character gender) {
        this.gender = gender;
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
