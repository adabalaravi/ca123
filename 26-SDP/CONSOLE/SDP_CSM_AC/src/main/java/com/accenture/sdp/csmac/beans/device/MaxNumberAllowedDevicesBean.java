package com.accenture.sdp.csmac.beans.device;

import java.io.Serializable;

public class MaxNumberAllowedDevicesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String channel;
	private long number;
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
		return "MaxNumberAllowedDevices [channel=" + channel + ", number="
				+ number + "]";
	}
	
	
}
