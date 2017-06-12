package com.accenture.sdp.csmac.beans.party;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class PartyBean implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String firstName;
	private String lastName;
	private List<PartyGroupBean> partyGroups;

	public PartyBean() {
		this.partyGroups = new ArrayList<PartyGroupBean>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<PartyGroupBean> getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(List<PartyGroupBean> partyGroups) {
		this.partyGroups = partyGroups;
	}

	public Object clone() throws CloneNotSupportedException {
		PartyBean clone = (PartyBean) super.clone();
		if (partyGroups != null) {
			clone.setPartyGroups(new ArrayList<PartyGroupBean>(partyGroups));
		}
		return clone;
	}
}
