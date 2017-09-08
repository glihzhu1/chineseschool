package com.xilin.management.school.model;

import java.math.BigDecimal;
import java.util.Calendar;

//not used....
public class MyClassViewObject {
	private Integer id;
	
	private Integer bookitemid;
	
	private String bookname;

	private Integer courseinfoid;
	
	private String coursecode;

	private Integer semesterid;
	
	private String semesterdescription;
	
    private Integer teacherid;
	
	private String teachername;

    private String semestercoursecode;

    private String starttime;

    private String endtime;

    private String room;

    private Integer maxcapacity;

    private Integer mincapacity;

    private Integer currentcapacity;

    private Integer waitingcapacity;

    private BigDecimal price;

    private Boolean discountstatus;

    private BigDecimal discountamount;

    private String status;

    private String note;

    private Calendar updatedtime;

    private String updatedby;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBookitemid() {
		return bookitemid;
	}

	public void setBookitemid(Integer bookitemid) {
		this.bookitemid = bookitemid;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public Integer getCourseinfoid() {
		return courseinfoid;
	}

	public void setCourseinfoid(Integer courseinfoid) {
		this.courseinfoid = courseinfoid;
	}

	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public Integer getSemesterid() {
		return semesterid;
	}

	public void setSemesterid(Integer semesterid) {
		this.semesterid = semesterid;
	}

	public String getSemesterdescription() {
		return semesterdescription;
	}

	public void setSemesterdescription(String semesterdescription) {
		this.semesterdescription = semesterdescription;
	}

	public Integer getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(Integer teacherid) {
		this.teacherid = teacherid;
	}

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
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

	@Override
	public String toString() {
		return "MyClassViewObject [id=" + id + ", bookitemid=" + bookitemid
				+ ", bookname=" + bookname + ", courseinfoid=" + courseinfoid
				+ ", coursecode=" + coursecode + ", semesterid=" + semesterid
				+ ", semesterdescription=" + semesterdescription
				+ ", teacherid=" + teacherid + ", teachername=" + teachername
				+ ", semestercoursecode=" + semestercoursecode + ", starttime="
				+ starttime + ", endtime=" + endtime + ", room=" + room
				+ ", maxcapacity=" + maxcapacity + ", mincapacity="
				+ mincapacity + ", currentcapacity=" + currentcapacity
				+ ", waitingcapacity=" + waitingcapacity + ", price=" + price
				+ ", discountstatus=" + discountstatus + ", discountamount="
				+ discountamount + ", status=" + status + ", note=" + note
				+ ", updatedtime=" + updatedtime + ", updatedby=" + updatedby
				+ "]";
	}

	public MyClassViewObject(Integer id, Integer bookitemid, String bookname,
			Integer courseinfoid, String coursecode, Integer semesterid,
			String semesterdescription, Integer teacherid, String teachername,
			String semestercoursecode, String starttime, String endtime,
			String room, Integer maxcapacity, Integer mincapacity,
			Integer currentcapacity, Integer waitingcapacity, BigDecimal price,
			Boolean discountstatus, BigDecimal discountamount, String status) {
		super();
		this.id = id;
		this.bookitemid = bookitemid;
		this.bookname = bookname;
		this.courseinfoid = courseinfoid;
		this.coursecode = coursecode;
		this.semesterid = semesterid;
		this.semesterdescription = semesterdescription;
		this.teacherid = teacherid;
		this.teachername = teachername;
		this.semestercoursecode = semestercoursecode;
		this.starttime = starttime;
		this.endtime = endtime;
		this.room = room;
		this.maxcapacity = maxcapacity;
		this.mincapacity = mincapacity;
		this.currentcapacity = currentcapacity;
		this.waitingcapacity = waitingcapacity;
		this.price = price;
		this.discountstatus = discountstatus;
		this.discountamount = discountamount;
		this.status = status;
	}
	
    
}
