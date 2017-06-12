package com.accenture.sdp.csm.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the SDP_OPERATORS database table.
 * 
 */
@Entity
@Table(name = "SDP_OPERATOR")
@NamedQueries({
		@NamedQuery(name = SdpOperator.QUERY_RETRIEVE_ALL, query = "select o from SdpOperator o"),
		@NamedQuery(name = SdpOperator.QUERY_COUNT_ALL, query = "select count(o.username) from SdpOperator o"),
		@NamedQuery(name = SdpOperator.QUERY_RETRIEVE_BY_LASTNAME, query = "select o from SdpOperator o where o.lastName=:lastName"),
		@NamedQuery(name = SdpOperator.QUERY_RETRIEVE_BY_LASTNAME_AND_FIRSTNAME, query = "select o from SdpOperator o where o.lastName=:lastName and o.firstName=:firstName"),
		@NamedQuery(name = SdpOperator.QUERY_COUNT_BY_LASTNAME, query = "select count(o.username) from SdpOperator o where o.lastName=:lastName"),
		@NamedQuery(name = SdpOperator.QUERY_COUNT_BY_LASTNAME_AND_FIRSTNAME, query = "select count(o.username) from SdpOperator o where o.lastName=:lastName and o.firstName=:firstName"),
		@NamedQuery(name = SdpOperator.QUERY_RETRIEVE_BY_TENANTNAME, query = "select o from SdpOperator o, LnkTenantOperator lnk where o.username=lnk.id.username and lnk.id.tenantName=:tenantName"),
		@NamedQuery(name = SdpOperator.QUERY_RETRIEVE_BY_USERNAME_AND_TENANTNAME, query = "select o from SdpOperator o, LnkTenantOperator lnk where o.username=:username and o.username=lnk.id.username and lnk.id.tenantName=:tenantName") })
public class SdpOperator implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_RETRIEVE_ALL = "selectAllOperators";
	public static final String QUERY_COUNT_ALL = "countAllOperators";
	public static final String QUERY_RETRIEVE_BY_LASTNAME = "selectOperatorsByLastName";
	public static final String QUERY_RETRIEVE_BY_LASTNAME_AND_FIRSTNAME = "selectOperatorsByLastNameAndFirstName";
	public static final String QUERY_COUNT_BY_LASTNAME = "countOperatorsByLastName";
	public static final String QUERY_COUNT_BY_LASTNAME_AND_FIRSTNAME = "countOperatorsByLastNameAndFirstName";
	public static final String QUERY_RETRIEVE_BY_TENANTNAME = "selectOperatorsByTenantName";
	public static final String QUERY_RETRIEVE_BY_USERNAME_AND_TENANTNAME = "selectOperatorByUsernameAndTenantName";

	public static final String PARAM_USERNAME = "username";
	public static final String PARAM_TENANT_NAME = "tenantName";
	public static final String PARAM_LAST_NAME = "lastName";
	public static final String PARAM_FIRST_NAME = "firstName";

	@Id
	private String username;

	@Column(name = "CHG_STATUS_BY_ID")
	private String chgStatusById;

	@Temporal(TemporalType.DATE)
	@Column(name = "CHG_STATUS_DATE")
	private Date changeStatusDate;

	@Column(name = "CREATED_BY_ID")
	private String createdById;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	private String password;

	private String email;

	@Column(name = "STATUS_ID")
	private Long statusId;

	@Column(name = "UPDATED_BY_ID")
	private String updatedById;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	// bi-directional many-to-many association to RefTenant
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "LNK_TENANT_OPERATOR", joinColumns = @JoinColumn(name = "USERNAME"), inverseJoinColumns = @JoinColumn(name = "TENANT_NAME"))
	private List<RefTenant> tenants;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "LNK_OPERATOR_OPERATOR_RIGHT", joinColumns = @JoinColumn(name = "USERNAME"), inverseJoinColumns = @JoinColumn(name = "OPERATOR_RIGHT_ID"))
	private List<SdpOperatorRight> operatorRights;

	public SdpOperator() {
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getChangeStatusById() {
		return this.chgStatusById;
	}

	public void setChangeStatusById(String chgStatusById) {
		this.chgStatusById = chgStatusById;
	}

	public Date getChangeStatusDate() {
		return changeStatusDate;
	}

	public void setChangeStatusDate(Date changeStatusDate) {
		this.changeStatusDate = changeStatusDate;
	}

	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getUpdatedById() {
		return this.updatedById;
	}

	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<RefTenant> getTenants() {
		return tenants;
	}

	public void setTenants(List<RefTenant> tenants) {
		this.tenants = tenants;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SdpOperator other = (SdpOperator) obj;
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

	public List<SdpOperatorRight> getOperatorRights() {
		return operatorRights;
	}

	public void setOperatorRights(List<SdpOperatorRight> operatorRights) {
		this.operatorRights = operatorRights;
	}

}