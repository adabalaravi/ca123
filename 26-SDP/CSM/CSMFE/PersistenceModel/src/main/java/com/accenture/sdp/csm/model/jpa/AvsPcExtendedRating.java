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
 * The persistent class for the AVS_PC_Extended_Rating database table.
 * 
 */
@Entity
@Table(name = "AVS_PC_Extended_Rating")
@NamedQueries({
	@NamedQuery(name = AvsPcExtendedRating.QUERY_RETRIEVE_ALL, query = "select o from AvsPcExtendedRating o ORDER BY o.pcId")
})
public class AvsPcExtendedRating implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String QUERY_RETRIEVE_ALL = "selectAllAvsPcExtendedRatings";

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="pc_description")
	private String pcDescription;

	@Id
	@Column(name="pc_id")
	private int pcId;

	@Column(name="pc_value")
	private String pcValue;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="update_date")
	private Date updateDate;

    public AvsPcExtendedRating() {
    }

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPcDescription() {
		return this.pcDescription;
	}

	public void setPcDescription(String pcDescription) {
		this.pcDescription = pcDescription;
	}

	public int getPcId() {
		return this.pcId;
	}

	public void setPcId(int pcId) {
		this.pcId = pcId;
	}

	public String getPcValue() {
		return this.pcValue;
	}

	public void setPcValue(String pcValue) {
		this.pcValue = pcValue;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}