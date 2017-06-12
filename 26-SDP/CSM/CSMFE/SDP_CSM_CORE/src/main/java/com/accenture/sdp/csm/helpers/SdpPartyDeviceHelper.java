package com.accenture.sdp.csm.helpers;

import java.util.Date;

import javax.persistence.EntityManager;

import com.accenture.sdp.csm.commons.BooleanConverter;
import com.accenture.sdp.csm.model.jpa.RefDeviceChannel;
import com.accenture.sdp.csm.model.jpa.SdpDeviceCounterConfig;
import com.accenture.sdp.csm.model.jpa.SdpDeviceCounterConfigPK;
import com.accenture.sdp.csm.model.jpa.SdpDevicePolicy;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpPartyDeviceExt;
import com.accenture.sdp.csm.utilities.Utilities;

public final class SdpPartyDeviceHelper extends SdpBaseHelper {

	private static SdpPartyDeviceHelper instance;

	private SdpPartyDeviceHelper() {
		super();
	}

	public static SdpPartyDeviceHelper getInstance() {
		if (instance == null) {
			instance = new SdpPartyDeviceHelper();
		}
		return instance;
	}

	public SdpPartyDeviceExt createPartyDeviceExt(SdpParty party, SdpDevicePolicy policy, String createdById, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpPartyDeviceExt ext = new SdpPartyDeviceExt();
		ext.setPartyId(party.getPartyId());
		ext.setSdpParty(party);
		ext.setSdpDevicePolicy(policy);
		ext.setIsBl(BooleanConverter.getByteValue(false));
		ext.setIsWl(BooleanConverter.getByteValue(false));
		ext.setRegistrationsDone(0L);
		ext.setCreatedById(createdById);
		ext.setCreatedDate(new Date());
		party.setSdpPartyDeviceExt(ext);
		em.persist(ext);
		return ext;
	}

	/**
	 * Checks if the user is in safety period or safety period has elapsed ( current date > safety period end date). In this case the counter of associations
	 * and the safety period are reset
	 * 
	 * @param party
	 * @param updatedById
	 * @return whether or not user is in safety period
	 */
	public boolean checkIsInSafetyPeriod(SdpPartyDeviceExt ext, String updatedById) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (ext.getSafetyPeriodExpiration() != null) {
			if (ext.getSafetyPeriodExpiration().before(new Date())) {
				resetSafetyPeriod(ext, updatedById);
				return false;
			}
			return true;
		}
		// bonifica
		if (ext.getRegistrationsDone() >= ext.getSdpDevicePolicy().getNumberOfAssociations()) {
			startSafetyPeriod(ext, updatedById);
			return true;
		}
		return false;
	}

	public void startSafetyPeriod(SdpPartyDeviceExt ext, String updatedById) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		final long millisInDay = 3600 * 24 * 1000;
		Date now = new Date();
		Date expiration = new Date(now.getTime() + millisInDay * ext.getSdpDevicePolicy().getSafetyPeriodDuration());
		ext.setSafetyPeriodExpiration(expiration);
		ext.setUpdatedById(updatedById);
		ext.setUpdatedDate(now);
	}

	public void resetSafetyPeriod(SdpPartyDeviceExt ext, String updatedById) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		ext.setSafetyPeriodExpiration(null);
		ext.setRegistrationsDone(0L);
		ext.setUpdatedById(updatedById);
		ext.setUpdatedDate(new Date());
	}

	public SdpDeviceCounterConfig searchDeviceCounterConfigById(Long partyId, Long channelId, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		if (partyId == null || channelId == null) {
			return null;
		}
		SdpDeviceCounterConfigPK id = new SdpDeviceCounterConfigPK();
		id.setPartyId(partyId);
		id.setDeviceChannelId(channelId);
		return em.find(SdpDeviceCounterConfig.class, id);
	}

	public SdpDeviceCounterConfig createDeviceCounterConfig(SdpPartyDeviceExt ext, RefDeviceChannel channel, EntityManager em) {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SdpDeviceCounterConfig counter = new SdpDeviceCounterConfig();
		SdpDeviceCounterConfigPK id = new SdpDeviceCounterConfigPK();
		id.setDeviceChannelId(channel.getDeviceChannelId());
		id.setPartyId(ext.getPartyId());
		counter.setId(id);
		// model valorization
		counter.setRefDeviceChannel(channel);
		counter.setSdpPartyDeviceExt(ext);
		counter.setRegisteredDevices(0L);
		ext.getSdpDeviceCounterConfigs().add(counter);
		em.persist(counter);
		return counter;
	}

	public void updateDeviceCounterConfig(SdpDeviceCounterConfig counter, long delta) {
		long registered = counter.getRegisteredDevices() == null ? 0L : counter.getRegisteredDevices().longValue();
		counter.setRegisteredDevices(registered + delta);
	}

	public void increaseRegistrationsDone(SdpPartyDeviceExt partyExt, String updatedBy) {
		long done = partyExt.getRegistrationsDone() == null ? 0L : partyExt.getRegistrationsDone().longValue();
		partyExt.setRegistrationsDone(done + 1);
		partyExt.setUpdatedById(updatedBy);
		partyExt.setUpdatedDate(new Date());
		if (partyExt.getRegistrationsDone() >= partyExt.getSdpDevicePolicy().getNumberOfAssociations()) {
			startSafetyPeriod(partyExt, updatedBy);
		}
	}
}