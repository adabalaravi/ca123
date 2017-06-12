package com.accenture.ams.db.bean;
import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import commontypes.accountmgmt.avs.accenture.FlagTypeMF;

/**
 * The persistent class for the CRM_ACCOUNT database table.
 * 
 * @author BEA Workshop
 */
public class CrmAccount  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long username;
	private String articleId;
	private java.sql.Timestamp birthDate;
	private String cap;
	private String consensoPctvottv;
//	private java.sql.Timestamp creationDate;
	private String crmaccountid;
	private String email;
	private String mobilePhone;
	private String name;
	private String registrazioneWeb;
	private String sesso;
	private String smartcardNumber;
	private String smartcardPaymentType;
	private String smartcardStatus;
	private String statoMorosita;
	private String surname;
//	private java.sql.Timestamp updateDate;
	private String vip;
	private AccountUser accountUser;

    public CrmAccount() {
    }

	public Long getUsername() {
		return this.username;
	}
	public void setUsername(Long username) {
		this.username = username;
	}

	public String getArticleId() {
		return this.articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public java.sql.Timestamp getBirthDate() {
		return this.birthDate;
	}
	public void setBirthDate(java.sql.Timestamp birthDate) {
		this.birthDate = birthDate;
	}

	public String getCap() {
		return this.cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getConsensoPctvottv() {
		return this.consensoPctvottv;
	}
	public void setConsensoPctvottv(String consensoPctvottv) {
		this.consensoPctvottv = consensoPctvottv;
	}

//	public java.sql.Timestamp getCreationDate() {
//		return this.creationDate;
//	}
//	public void setCreationDate(java.sql.Timestamp creationDate) {
//		this.creationDate = creationDate;
//	}

	public String getCrmaccountid() {
		return this.crmaccountid;
	}
	public void setCrmaccountid(String crmaccountid) {
		this.crmaccountid = crmaccountid;
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getRegistrazioneWeb() {
		return this.registrazioneWeb;
	}
	public void setRegistrazioneWeb(String registrazioneWeb) {
		this.registrazioneWeb = registrazioneWeb;
	}

	public String getSesso() {
		return this.sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getSmartcardNumber() {
		return this.smartcardNumber;
	}
	public void setSmartcardNumber(String smartcardNumber) {
		this.smartcardNumber = smartcardNumber;
	}

	public String getSmartcardPaymentType() {
		return this.smartcardPaymentType;
	}
	public void setSmartcardPaymentType(String smartcardPaymentType) {
		this.smartcardPaymentType = smartcardPaymentType;
	}

	public String getSmartcardStatus() {
		return this.smartcardStatus;
	}
	public void setSmartcardStatus(String smartcardStatus) {
		this.smartcardStatus = smartcardStatus;
	}

	public String getStatoMorosita() {
		return this.statoMorosita;
	}
	public void setStatoMorosita(String statoMorosita) {
		this.statoMorosita = statoMorosita;
	}

	public String getSurname() {
		return this.surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	public String getVip() {
		return this.vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}

	//bi-directional one-to-one association to AccountUser
	public AccountUser getAccountUser() {
		return this.accountUser;
	}
	public void setAccountUser(AccountUser accountUser) {
		this.accountUser = accountUser;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CrmAccount)) {
			return false;
		}
		CrmAccount castOther = (CrmAccount)other;
		return new EqualsBuilder()
			.append(this.getUsername(), castOther.getUsername())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getUsername())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("username", getUsername())
			.toString();
	}
}