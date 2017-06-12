package com.accenture.ams.db.bean;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the ACCOUNT_USER database table.
 * 
 * @author BEA Workshop
 */
public class AccountUserBean  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long username;
	private String bloccoAcquisti;
	private int consumption;
	private int consumptionThreshold;
//	private java.sql.Timestamp creationDate;
	private String crmaccountid;
	private java.sql.Timestamp dataPrimoAccesso;
	private String flagInvioSmsAlerting;
	private String flagInvioSmsBlocking;
	private String name;
	private String partyDescription;
	private double purchaseAlerting;
	private double purchaseThresholdBlocking;
	private double purchaseValue;
	private String smartcardNumber;
	private String smsAlerting;
//	private java.sql.Timestamp updateDate;
//	private java.util.Set<AccountAttribute> accountAttributes;
//	private java.util.Set<AccountProduct> accountProducts;
//	private java.util.Set<AccountPromo> accountPromos;
//	private java.util.Set<AccountTechnicalPkg> accountTechnicalPkgs;
	private Long statusId;
//	private CrmAccount crmAccount;
//	private java.util.Set<AccountDevice> device;

    public AccountUserBean() {
    }

/*
	public java.util.Set<AccountDevice> getDevice() {
		return device;
	}
	public void setDevice(java.util.Set<AccountDevice> device) {
		this.device = device;
	}
*/
    public void setUsername(Long username){
    	this.username = username;
    }
	public Long getUsername() {
		return this.username;
	}

	public String getBloccoAcquisti() {
		return this.bloccoAcquisti;
	}
	public void setBloccoAcquisti(String bloccoAcquisti) {
		this.bloccoAcquisti = bloccoAcquisti;
	}

	public int getConsumption() {
		return this.consumption;
	}
	public void setConsumption(int consumption) {
		this.consumption = consumption;
	}

	public int getConsumptionThreshold() {
		return this.consumptionThreshold;
	}
	public void setConsumptionThreshold(int consumptionThreshold) {
		this.consumptionThreshold = consumptionThreshold;
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

	public java.sql.Timestamp getDataPrimoAccesso() {
		return this.dataPrimoAccesso;
	}
	public void setDataPrimoAccesso(java.sql.Timestamp dataPrimoAccesso) {
		this.dataPrimoAccesso = dataPrimoAccesso;
	}

	public String getFlagInvioSmsAlerting() {
		return this.flagInvioSmsAlerting;
	}
	public void setFlagInvioSmsAlerting(String flagInvioSmsAlerting) {
		this.flagInvioSmsAlerting = flagInvioSmsAlerting;
	}

	public String getFlagInvioSmsBlocking() {
		return this.flagInvioSmsBlocking;
	}
	public void setFlagInvioSmsBlocking(String flagInvioSmsBlocking) {
		this.flagInvioSmsBlocking = flagInvioSmsBlocking;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPartyDescription() {
		return this.partyDescription;
	}
	public void setPartyDescription(String partyDescription) {
		this.partyDescription = partyDescription;
	}

	public double getPurchaseAlerting() {
		return this.purchaseAlerting;
	}
	public void setPurchaseAlerting(double purchaseAlerting) {
		this.purchaseAlerting = purchaseAlerting;
	}

	public double getPurchaseThresholdBlocking() {
		return this.purchaseThresholdBlocking;
	}
	public void setPurchaseThresholdBlocking(double purchaseThresholdBlocking) {
		this.purchaseThresholdBlocking = purchaseThresholdBlocking;
	}

	public double getPurchaseValue() {
		return this.purchaseValue;
	}
	public void setPurchaseValue(double purchaseValue) {
		this.purchaseValue = purchaseValue;
	}

	public String getSmartcardNumber() {
		return this.smartcardNumber;
	}
	public void setSmartcardNumber(String smartcardNumber) {
		this.smartcardNumber = smartcardNumber;
	}

	public String getSmsAlerting() {
		return this.smsAlerting;
	}
	public void setSmsAlerting(String smsAlerting) {
		this.smsAlerting = smsAlerting;
	}

//	public java.sql.Timestamp getUpdateDate() {
//		return this.updateDate;
//	}
//	public void setUpdateDate(java.sql.Timestamp updateDate) {
//		this.updateDate = updateDate;
//	}

	//bi-directional many-to-one association to AccountAttribute
	/*
	public java.util.Set<AccountAttribute> getAccountAttributes() {
		return this.accountAttributes;
	}
	public void setAccountAttributes(java.util.Set<AccountAttribute> accountAttributes) {
		this.accountAttributes = accountAttributes;
	}
	 */
	//bi-directional many-to-one association to AccountProduct
	/*
	public java.util.Set<AccountProduct> getAccountProducts() {
		return this.accountProducts;
	}
	public void setAccountProducts(java.util.Set<AccountProduct> accountProducts) {
		this.accountProducts = accountProducts;
	}
	*/
	//bi-directional many-to-one association to AccountPromo
	/*
	public java.util.Set<AccountPromo> getAccountPromos() {
		return this.accountPromos;
	}
	public void setAccountPromos(java.util.Set<AccountPromo> accountPromos) {
		this.accountPromos = accountPromos;
	}
	*/
	//bi-directional many-to-one association to AccountTechnicalPkg
	/*
	public java.util.Set<AccountTechnicalPkg> getAccountTechnicalPkgs() {
		return this.accountTechnicalPkgs;
	}
	public void setAccountTechnicalPkgs(java.util.Set<AccountTechnicalPkg> accountTechnicalPkgs) {
		this.accountTechnicalPkgs = accountTechnicalPkgs;
	}
	*/
	//bi-directional many-to-one association to StatusType
	public Long getStatusId() {
		return this.statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	//bi-directional one-to-one association to CrmAccount
	/*
	public CrmAccount getCrmAccount() {
		return this.crmAccount;
	}
	public void setCrmAccount(CrmAccount crmAccount) {
		this.crmAccount = crmAccount;
	}
	*/
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccountUser)) {
			return false;
		}
		AccountUser castOther = (AccountUser)other;
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