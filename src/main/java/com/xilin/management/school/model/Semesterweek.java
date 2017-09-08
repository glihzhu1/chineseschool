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
@Table(schema = "public",name = "semesterweek")
public class Semesterweek {

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

	@Column(name = "displaynumber")
    private Integer displaynumber;

	@Column(name = "weekdate", length = 40)
    private String weekdate;

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

	public Integer getDisplaynumber() {
        return displaynumber;
    }

	public void setDisplaynumber(Integer displaynumber) {
        this.displaynumber = displaynumber;
    }

	public String getWeekdate() {
        return weekdate;
    }

	public void setWeekdate(String weekdate) {
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
}
