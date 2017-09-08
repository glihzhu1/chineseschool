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
@Table(schema = "public",name = "semesterfamilypod")
public class Semesterfamilypod {

	@ManyToOne
    @JoinColumn(name = "familyid", referencedColumnName = "id")
    private Family familyid;

	@ManyToOne
    @JoinColumn(name = "semesterpodid", referencedColumnName = "id")
    private Semesterpod semesterpodid;

	@Column(name = "performed")
    private Boolean performed;

	@Column(name = "updatedtime")
    @NotNull
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

	public Semesterpod getSemesterpodid() {
        return semesterpodid;
    }

	public void setSemesterpodid(Semesterpod semesterpodid) {
        this.semesterpodid = semesterpodid;
    }

	public Boolean getPerformed() {
        return performed;
    }

	public void setPerformed(Boolean performed) {
        this.performed = performed;
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

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("familyid", "semesterpodid").toString();
    }
}
