package com.accenture.sdp.csm.dto.responses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SdpPartyDeviceResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long registrationsDone;
	private Date safetyPeriodExpirationDate;
	private List<SdpDeviceCounterResponseDto> counters;

	public Long getRegistrationsDone() {
		return registrationsDone;
	}

	public void setRegistrationsDone(Long registrationsDone) {
		this.registrationsDone = registrationsDone;
	}

	public Date getSafetyPeriodExpirationDate() {
		return safetyPeriodExpirationDate;
	}

	public void setSafetyPeriodExpirationDate(Date safetyPeriodExpirationDate) {
		this.safetyPeriodExpirationDate = safetyPeriodExpirationDate;
	}

	public List<SdpDeviceCounterResponseDto> getCounters() {
		return counters;
	}

	public void setCounters(List<SdpDeviceCounterResponseDto> counters) {
		this.counters = counters;
	}

}