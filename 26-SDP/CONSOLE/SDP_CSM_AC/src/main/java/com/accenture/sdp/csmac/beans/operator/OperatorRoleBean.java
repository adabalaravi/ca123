package com.accenture.sdp.csmac.beans.operator;

import java.io.Serializable;

public class OperatorRoleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1775669132432933781L;
	
	private boolean operatorsRead;
	private boolean operatorsWrite;
	private boolean assuranceRead;
	private boolean assuranceWrite;
	private boolean catalogueRead;
	private boolean catalogueWrite;
	private boolean catchupRead;
	private boolean catchupWrite;
	private boolean multicameraRead;
	private boolean multicameraWrite;

	public boolean isOperatorsRead() {
		return operatorsRead;
	}
	public void setOperatorsRead(boolean operatorsRead) {
		this.operatorsRead = operatorsRead;
	}
	public boolean isOperatorsWrite() {
		return operatorsWrite;
	}
	public void setOperatorsWrite(boolean operatorsWrite) {
		this.operatorsWrite = operatorsWrite;
	}
	public boolean isAssuranceRead() {
		return assuranceRead;
	}
	public void setAssuranceRead(boolean assuranceRead) {
		this.assuranceRead = assuranceRead;
	}
	public boolean isAssuranceWrite() {
		return assuranceWrite;
	}
	public void setAssuranceWrite(boolean assuranceWrite) {
		this.assuranceWrite = assuranceWrite;
	}
	public boolean isCatalogueRead() {
		return catalogueRead;
	}
	public void setCatalogueRead(boolean catalogueRead) {
		this.catalogueRead = catalogueRead;
	}
	public boolean isCatalogueWrite() {
		return catalogueWrite;
	}
	public void setCatalogueWrite(boolean catalogueWrite) {
		this.catalogueWrite = catalogueWrite;
	}
	public boolean isCatchupRead() {
		return catchupRead;
	}
	public void setCatchupRead(boolean catchupRead) {
		this.catchupRead = catchupRead;
	}
	public boolean isCatchupWrite() {
		return catchupWrite;
	}
	public void setCatchupWrite(boolean catchupWrite) {
		this.catchupWrite = catchupWrite;
	}
	public boolean isMulticameraRead() {
		return multicameraRead;
	}
	public void setMulticameraRead(boolean multicameraRead) {
		this.multicameraRead = multicameraRead;
	}
	public boolean isMulticameraWrite() {
		return multicameraWrite;
	}
	public void setMulticameraWrite(boolean multicameraWrite) {
		this.multicameraWrite = multicameraWrite;
	}
}
