package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the AVS_PC_Level database table.
 * 
 */
@Entity
@Table(name = "AVS_PC_Level")
@NamedQueries({
	@NamedQuery(name = AvsPcLevel.QUERY_RETRIEVE_ALL, query = "select o from AvsPcLevel o ORDER BY o.value")
})
public class AvsPcLevel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String QUERY_RETRIEVE_ALL = "selectAllAvsPcLevels";

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="creation_date")
	private Date creationDate;

	private String description;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="update_date")
	private Date updateDate;

    @Id
	private String value;

    public AvsPcLevel() {
    }

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}