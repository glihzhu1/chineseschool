package com.xilin.management.school.model;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema = "public",name = "schoolannouncement")
public class Schoolannouncement {

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
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Column(name = "newstitle", length = 50)
    @NotNull
    private String newstitle;

	@Column(name = "newscontent", length = 50)
    private String newscontent;

	@Column(name = "infofilename", length = 50)
    private String infofilename;

	@Column(name = "imagefilename", length = 50)
    private String imagefilename;

	@Column(name = "active")
    private Boolean active;

	@Column(name = "updatedtime")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar updatedtime;

	@Column(name = "updatedby", length = 40)
    private String updatedby;

	@Column(name = "announcetype", length = 20)
    private String announcetype;

	@Column(name = "creatoremail", length = 50)
    private String creatoremail;

	@Column(name = "createtime")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar createtime;

	@Column(name = "creatorname", length = 100)
    private String creatorname;

	public String getNewstitle() {
        return newstitle;
    }

	public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

	public String getNewscontent() {
        return newscontent;
    }

	public void setNewscontent(String newscontent) {
        this.newscontent = newscontent;
    }

	public String getInfofilename() {
        return infofilename;
    }

	public void setInfofilename(String infofilename) {
        this.infofilename = infofilename;
    }

	public String getImagefilename() {
        return imagefilename;
    }

	public void setImagefilename(String imagefilename) {
        this.imagefilename = imagefilename;
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

	public String getAnnouncetype() {
        return announcetype;
    }

	public void setAnnouncetype(String announcetype) {
        this.announcetype = announcetype;
    }

	public String getCreatoremail() {
        return creatoremail;
    }

	public void setCreatoremail(String creatoremail) {
        this.creatoremail = creatoremail;
    }

	public Calendar getCreatetime() {
        return createtime;
    }

	public void setCreatetime(Calendar createtime) {
        this.createtime = createtime;
    }

	public String getCreatorname() {
        return creatorname;
    }

	public void setCreatorname(String creatorname) {
        this.creatorname = creatorname;
    }
}
