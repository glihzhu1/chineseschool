package com.xilin.management.school.model;
import java.math.BigDecimal;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema = "public",name = "bookitem")
public class Bookitem {

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
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("familytransactions", "semestercourses").toString();
    }

	@OneToMany(mappedBy = "bookitemid")
    private Set<Familytransaction> familytransactions;

	@OneToMany(mappedBy = "bookitemid")
    private Set<Semestercourse> semestercourses;

	@Column(name = "bookcode", length = 100)
    @NotNull
    private String bookcode;

	@Column(name = "bookname", length = 100)
    @NotNull
    private String bookname;

	@Column(name = "bookchinesename", length = 100)
    private String bookchinesename;

	@Column(name = "bookdescription", length = 100)
    private String bookdescription;

	@Column(name = "active")
    private Boolean active;

	@Column(name = "bookprice", precision = 131089)
    private BigDecimal bookprice;

	@Column(name = "bookdiscountprice", precision = 131089)
    private BigDecimal bookdiscountprice;

	@Column(name = "amountsold")
    private Integer amountsold;

	@Column(name = "amountinstock")
    private Integer amountinstock;
	
	@Column(name = "updatedtime")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar updatedtime;

	@Column(name = "updatedby", length = 40)
    private String updatedby;

	@Transient
	boolean selected;
	
	public Set<Familytransaction> getFamilytransactions() {
		return familytransactions;
	}

	public void setFamilytransactions(Set<Familytransaction> familytransactions) {
		this.familytransactions = familytransactions;
	}

	public Set<Semestercourse> getSemestercourses() {
        return semestercourses;
    }

	public void setSemestercourses(Set<Semestercourse> semestercourses) {
        this.semestercourses = semestercourses;
    }

	public String getBookcode() {
        return bookcode;
    }

	public void setBookcode(String bookcode) {
        this.bookcode = bookcode;
    }

	public String getBookname() {
        return bookname;
    }

	public void setBookname(String bookname) {
        this.bookname = bookname;
    }

	public String getBookchinesename() {
        return bookchinesename;
    }

	public void setBookchinesename(String bookchinesename) {
        this.bookchinesename = bookchinesename;
    }

	public String getBookdescription() {
        return bookdescription;
    }

	public void setBookdescription(String bookdescription) {
        this.bookdescription = bookdescription;
    }

	public Boolean getActive() {
        return active;
    }

	public void setActive(Boolean active) {
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

	public BigDecimal getBookprice() {
		return bookprice;
	}

	public void setBookprice(BigDecimal bookprice) {
		this.bookprice = bookprice;
	}

	public BigDecimal getBookdiscountprice() {
		return bookdiscountprice;
	}

	public void setBookdiscountprice(BigDecimal bookdiscountprice) {
		this.bookdiscountprice = bookdiscountprice;
	}

	public Integer getAmountsold() {
		return amountsold;
	}

	public void setAmountsold(Integer amountsold) {
		this.amountsold = amountsold;
	}

	public Integer getAmountinstock() {
		return amountinstock;
	}

	public void setAmountinstock(Integer amountinstock) {
		this.amountinstock = amountinstock;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
