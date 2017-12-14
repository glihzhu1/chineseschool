package com.xilin.management.school.model;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema = "public",name = "semestercourse")
public class Semestercourse {

	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "bookitemid", referencedColumnName = "id")
    private Bookitem bookitemid;

	@ManyToOne
    @JoinColumn(name = "courseinfoid", referencedColumnName = "id")
    private Courseinformation courseinfoid;

	@ManyToOne
    @JoinColumn(name = "semesterid", referencedColumnName = "id")
    private Semester semesterid;

	@ManyToOne
    @JoinColumn(name = "teacherid", referencedColumnName = "id")
    private Personnel teacherid;

	@Column(name = "semestercoursecode", length = 50)
    @NotNull
    private String semestercoursecode;

	@Column(name = "starttime", length = 20)
    @NotNull
    private String starttime;

	@Column(name = "endtime", length = 20)
    @NotNull
    private String endtime;

	@Column(name = "room", length = 50)
    @NotNull
    private String room;

	@Column(name = "maxcapacity")
    private Integer maxcapacity;

	@Column(name = "mincapacity")
    private Integer mincapacity;

	@Column(name = "currentcapacity")
    private Integer currentcapacity;

	@Column(name = "waitingcapacity")
    private Integer waitingcapacity;

	@Column(name = "price", precision = 131089)
    @NotNull
    private BigDecimal price;

	@Column(name = "discountstatus")
    private Boolean discountstatus;

	@Column(name = "discountamount", precision = 131089)
    private BigDecimal discountamount;

	@Column(name = "status", length = 10)
    @NotNull
    private String status;

	@Column(name = "note", length = 5000)
    private String note;

	@Column(name = "updatedtime")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar updatedtime;

	@Column(name = "updatedby", length = 40)
    private String updatedby;

	@Transient
	boolean selected;
	
	@Transient
	boolean selectedbook;
	
	public Bookitem getBookitemid() {
        return bookitemid;
    }

	public void setBookitemid(Bookitem bookitemid) {
        this.bookitemid = bookitemid;
    }

	public Courseinformation getCourseinfoid() {
        return courseinfoid;
    }

	public void setCourseinfoid(Courseinformation courseinfoid) {
        this.courseinfoid = courseinfoid;
    }

	public Semester getSemesterid() {
        return semesterid;
    }

	public void setSemesterid(Semester semesterid) {
        this.semesterid = semesterid;
    }

	public Personnel getTeacherid() {
        return teacherid;
    }

	public void setTeacherid(Personnel teacherid) {
        this.teacherid = teacherid;
    }

	public String getSemestercoursecode() {
        return semestercoursecode;
    }

	public void setSemestercoursecode(String semestercoursecode) {
        this.semestercoursecode = semestercoursecode;
    }


	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getRoom() {
        return room;
    }

	public void setRoom(String room) {
		this.room = room;
    }

	public Integer getMaxcapacity() {
        return maxcapacity;
    }

	public void setMaxcapacity(Integer maxcapacity) {
        this.maxcapacity = maxcapacity;
    }

	public Integer getMincapacity() {
        return mincapacity;
    }

	public void setMincapacity(Integer mincapacity) {
        this.mincapacity = mincapacity;
    }

	public Integer getCurrentcapacity() {
        return currentcapacity;
    }

	public void setCurrentcapacity(Integer currentcapacity) {
        this.currentcapacity = currentcapacity;
    }

	public Integer getWaitingcapacity() {
        return waitingcapacity;
    }

	public void setWaitingcapacity(Integer waitingcapacity) {
        this.waitingcapacity = waitingcapacity;
    }

	public BigDecimal getPrice() {
        return price;
    }

	public void setPrice(BigDecimal price) {
        this.price = price;
    }

	public Boolean getDiscountstatus() {
        return discountstatus;
    }

	public void setDiscountstatus(Boolean discountstatus) {
        this.discountstatus = discountstatus;
    }

	public BigDecimal getDiscountamount() {
        return discountamount;
    }

	public void setDiscountamount(BigDecimal discountamount) {
        this.discountamount = discountamount;
    }

	public String getStatus() {
        return status;
    }

	public void setStatus(String status) {
        this.status = status;
    }

	public String getNote() {
        return note;
    }

	public void setNote(String note) {
        this.note = note;
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
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("bookitemid", "courseinfoid", "semesterid", "teacherid").toString();
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelectedbook() {
		return selectedbook;
	}

	public void setSelectedbook(boolean selectedbook) {
		this.selectedbook = selectedbook;
	}
	
}
