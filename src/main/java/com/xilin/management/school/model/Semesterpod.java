package com.xilin.management.school.model;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema = "public",name = "semesterpod")
public class Semesterpod {

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
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("semesterfamilypods", "semesterweekid").toString();
    }

	@OneToMany(mappedBy = "semesterpodid")
    private Set<Semesterfamilypod> semesterfamilypods;

	@ManyToOne
    @JoinColumn(name = "semesterweekid", referencedColumnName = "id")
    private Semesterweek semesterweekid;

	@Column(name = "capacity")
    private Integer capacity;

	@Column(name = "filled")
    private Integer filled;

	@Column(name = "podhour", length = 40)
    private String podhour;

	@Column(name = "updatedtime")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar updatedtime;

	@Column(name = "updatedby", length = 40)
    private String updatedby;

	@Transient
	private boolean selected;
	
	public Set<Semesterfamilypod> getSemesterfamilypods() {
        return semesterfamilypods;
    }

	public void setSemesterfamilypods(Set<Semesterfamilypod> semesterfamilypods) {
        this.semesterfamilypods = semesterfamilypods;
    }

	public Semesterweek getSemesterweekid() {
        return semesterweekid;
    }

	public void setSemesterweekid(Semesterweek semesterweekid) {
        this.semesterweekid = semesterweekid;
    }

	public Integer getCapacity() {
        return capacity;
    }

	public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

	public Integer getFilled() {
        return filled;
    }

	public void setFilled(Integer filled) {
        this.filled = filled;
    }

	public String getPodhour() {
        return podhour;
    }

	public void setPodhour(String podhour) {
        this.podhour = podhour;
    }

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
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
