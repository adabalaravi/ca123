package com.accenture.sdp.csmac.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.Holder;

import com.accenture.sdp.csmac.common.LoggerWrapper;
import com.accenture.sdp.csmac.common.PropertyManager;
import com.accenture.sdp.csmac.common.constants.ApplicationConstants;
import com.accenture.sdp.csmac.common.exception.ServiceErrorException;
import com.accenture.sdp.csmac.common.utils.Utilities;
import com.accenture.sdp.csmfe.webservices.clients.party.BaseResp;
import com.accenture.sdp.csmfe.webservices.clients.party.CModifyParentParty;
import com.accenture.sdp.csmfe.webservices.clients.party.ModifyChildParty;
import com.accenture.sdp.csmfe.webservices.clients.party.ModifyChildPartyRequest;
import com.accenture.sdp.csmfe.webservices.clients.party.ModifyParentPartyRequest;
import com.accenture.sdp.csmfe.webservices.clients.party.ModifyPartyCluster;
import com.accenture.sdp.csmfe.webservices.clients.party.ModifyPartyClusterRequest;
import com.accenture.sdp.csmfe.webservices.clients.party.ParameterInfoResp;
import com.accenture.sdp.csmfe.webservices.clients.party.PartyGroupOperationInfoRequest;
import com.accenture.sdp.csmfe.webservices.clients.party.PartyGroupOperationListRequest;
import com.accenture.sdp.csmfe.webservices.clients.party.SdpPartyService;
import com.accenture.sdp.csmfe.webservices.clients.party.SdpPartyService_Service;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchChildParty;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchChildPartyByName;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchChildPartyByNameRequest;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchChildPartyRequest;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchChildPartyResp;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchChildPartyRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchParentParty;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchParentPartyRequest;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchParentPartyRespPaginated;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchPartiesByAccount;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchPartiesByAccountRequest;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchPartiesByCredential;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchPartiesByCredentialRequest;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchParty;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchPartyRequest;
import com.accenture.sdp.csmfe.webservices.clients.party.SearchPartyResp;

@ManagedBean(name = ApplicationConstants.PARTY_SERVICE_BEAN)
@ApplicationScoped
public class PartyService {

	private static LoggerWrapper log = new LoggerWrapper(PartyService.class);
	private SdpPartyService port;

	public PartyService() {
		URL url = null;
		PropertyManager propertyManager = PropertyManager.getManager(ApplicationConstants.APPLICATION_FILE_NAME);
		String urlString = propertyManager.getProperty(ApplicationConstants.ROOT_ENDPOINT_URL)
				+ propertyManager.getProperty(ApplicationConstants.PARTY_WSDL_URL);
		try {
			url = new URL(urlString);
			SdpPartyService_Service service = new SdpPartyService_Service(url);
			port = service.getSdpPartyServicePort();
			log.logDebug("SdpPartyService instantiated");
		} catch (MalformedURLException e) {
			log.logError(e);
		}

	}

	@Deprecated
	public SearchParentPartyRespPaginated searchPartiesByOrganizzationName(String organizzationName, Long partyGroupId, long startRow, long numElementToFind,
			Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchParentPartyRequest req = new SearchParentPartyRequest();
		SearchParentParty wrapper = new SearchParentParty();
		wrapper.setSearchParentPartyRequest(req);
		req.setPartyName(organizzationName);
		req.setPartyGroupId(partyGroupId);
		req.setStartPosition(startRow);
		req.setMaxRecordsNumber(numElementToFind);
		SearchParentPartyRespPaginated resp = port.searchParentParty(wrapper, tenantName).getSearchParentPartyResponse();
		log.logDebug("searchPartiesByOrganizzationName " + resp.getResultCode() + ", " + resp.getDescription() + " - result:" + resp.getTotalResult());
		parseResponse(resp);
		return resp;
	}

	public SearchChildPartyRespPaginated searchPartiesByFirstAndLastName(String firstName, String lastName, long startRow, long numElementToFind,
			Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchChildPartyByNameRequest req = new SearchChildPartyByNameRequest();
		SearchChildPartyByName wrapper = new SearchChildPartyByName();
		wrapper.setSearchChildPartyByNameRequest(req);
		req.setFirstName(firstName);
		req.setLastName(lastName);
		req.setStartPosition(startRow);
		req.setMaxRecordsNumber(numElementToFind);
		SearchChildPartyRespPaginated resp = port.searchChildPartyByName(wrapper, tenantName).getSearchChildPartyByNameResponse();
		log.logDebug("searchPartiesByFirstAndLastName " + resp.getResultCode() + ", " + resp.getDescription() + " - result:" + resp.getTotalResult());
		parseResponse(resp);
		return resp;
	}

	@Deprecated
	public SearchChildPartyResp searchPartiesByCredential(String username, Long partyGroupId, Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchPartiesByCredentialRequest req = new SearchPartiesByCredentialRequest();
		SearchPartiesByCredential wrapper = new SearchPartiesByCredential();
		wrapper.setSearchPartiesByCredentialRequest(req);
		req.setUsername(username);
		req.setPartyGroupId(partyGroupId);
		SearchChildPartyResp resp = port.searchPartiesByCredential(wrapper, tenantName).getSearchPartiesByCredentialResponse();
		log.logDebug("searchPartiesByCredential " + resp.getResultCode() + ", " + resp.getDescription());
		parseResponse(resp);
		return resp;
	}

	@Deprecated
	public SearchChildPartyResp searchPartiesByAccount(String accountName, Long partyGroupId, Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchPartiesByAccountRequest req = new SearchPartiesByAccountRequest();
		SearchPartiesByAccount wrapper = new SearchPartiesByAccount();
		wrapper.setSearchPartiesByAccountRequest(req);
		req.setAccountName(accountName);
		req.setPartyGroupId(partyGroupId);
		SearchChildPartyResp resp = port.searchPartiesByAccount(wrapper, tenantName).getSearchPartiesByAccountResponse();
		log.logDebug("searchPartiesByAccount " + resp.getResultCode() + ", " + resp.getDescription());
		parseResponse(resp);
		return resp;
	}

	public SearchChildPartyRespPaginated searchChildPartiesByPartyName(String partyName, long parentPartyId, long startRow, long numElementToFind,
			Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchChildPartyRequest req = new SearchChildPartyRequest();
		SearchChildParty wrapper = new SearchChildParty();
		wrapper.setSearchChildPartyRequest(req);
		req.setPartyName(partyName);
		req.setParentPartyId(parentPartyId);
		req.setStartPosition(startRow);
		req.setMaxRecordsNumber(numElementToFind);
		SearchChildPartyRespPaginated resp = port.searchChildParty(wrapper, tenantName).getSearchChildPartyResponse();
		log.logDebug("searchPartiesByAccount " + resp.getResultCode() + ", " + resp.getDescription());
		parseResponse(resp);
		return resp;
	}
	
	public Long countAllParties(Holder<String> tenantName) throws ServiceErrorException{
		log.logDebug(Utilities.getCurrentClassAndMethod());
		return searchChildPartiesByPartyName("%", 1L, 0L, 1L, tenantName).getTotalResult() ;
	}
	

	public BaseResp modifyParentParty(Long partyId, String partyName, String partyDescription, String externalId, String partyProfile, Holder<String> tenantName)
			throws DatatypeConfigurationException, ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		// Request
		ModifyParentPartyRequest r = new ModifyParentPartyRequest();
		CModifyParentParty wrapper = new CModifyParentParty();
		r.setPartyName(partyName);
		r.setPartyDescription(partyDescription);
		r.setExternalId(externalId);
		r.setPartyProfile(partyProfile);
		r.setPartyId(partyId);
		// webmethod
		BaseResp resp = port.cModifyParentParty(wrapper, tenantName).getModifyParentPartyResponse();
		parseResponse(resp);
		return resp;
	}

	public BaseResp modifyChildParty(Long partyId, String partyName, String partyDescription, Long userDefaultSiteId, String firstName, String lastName,
			String gender, Date birthDate, String birthProvince, String birthCountry, String birthCity, String phoneNumber, String faxNumber, String email,
			String note, String externalId, String partyProfile, Holder<String> tenantName) throws DatatypeConfigurationException, ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		ModifyChildPartyRequest request = new ModifyChildPartyRequest();
		ModifyChildParty wrapper = new ModifyChildParty();
		wrapper.setModifyChildPartyRequest(request);
		request.setPartyId(partyId);
		request.setPartyName(partyName);
		request.setPartyDescription(partyDescription);
		request.setExternalId(externalId);
		request.setPartyProfile(partyProfile);
		request.setUserDefaultSiteId(userDefaultSiteId);
		request.setFirstName(firstName);
		request.setLastName(lastName);
		request.setGender(gender);
		request.setExternalId(externalId);
		request.setBirthDate(Utilities.getXMLGregorianCalendar(birthDate));
		request.setBirthProvince(birthProvince);
		request.setBirthCountry(birthCountry);
		request.setBirthCity(birthCity);
		request.setPhoneNumber(phoneNumber);
		request.setFaxNumber(faxNumber);
		request.setEmail(email);
		request.setNote(note);
		BaseResp response = port.modifyChildParty(wrapper, tenantName).getModifyChildPartyResponse();
		parseResponse(response);
		return response;
	}

	public SearchPartyResp searchPartyById(Long id, Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		SearchPartyRequest req = new SearchPartyRequest();
		SearchParty wrapper = new SearchParty();
		wrapper.setSearchPartyRequest(req);
		req.setPartyId(id);
		SearchPartyResp resp = port.searchParty(wrapper, tenantName).getSearchPartyResponse();
		parseResponse(resp);
		return resp;
	}

	private void parseResponse(BaseResp resp) throws ServiceErrorException {
		if (!ApplicationConstants.CODE_OK.equals(resp.getResultCode())) {
			ArrayList<String[]> parameters = new ArrayList<String[]>();
			if (resp.getParameters() != null) {
				for (ParameterInfoResp param : resp.getParameters().getParameter()) {
					parameters.add(new String[] { param.getName(), param.getValue() });
				}
			}
			throw new ServiceErrorException(resp.getResultCode(), Utilities.parseErrorParameter(resp.getResultCode(), parameters));
		}
	}

	public BaseResp modifyPartyCluster(Long partyId, List<Long> removeGroups, List<Long> addGroups, Holder<String> tenantName) throws ServiceErrorException {
		log.logDebug(Utilities.getCurrentClassAndMethod());
		log.logDebug("Removing from partyId " + partyId + " partyGroups with Ids :" + removeGroups);
		log.logDebug("Adding to partyId " + partyId + " partyGroups with Ids :" + addGroups);
		ModifyPartyClusterRequest req = new ModifyPartyClusterRequest();
		ModifyPartyCluster wrapper = new ModifyPartyCluster();
		wrapper.setModifyPartyClusterRequest(req);
		req.setPartyId(partyId);
		PartyGroupOperationListRequest operations = new PartyGroupOperationListRequest();
		if (removeGroups != null) {
			for (Long g : removeGroups) {
				PartyGroupOperationInfoRequest o = new PartyGroupOperationInfoRequest();
				o.setPartyGroupId(g);
				o.setOperation("Delete");
				operations.getPartyGroup().add(o);
			}
		}
		if (addGroups != null) {
			for (Long g : addGroups) {
				PartyGroupOperationInfoRequest o = new PartyGroupOperationInfoRequest();
				o.setPartyGroupId(g);
				o.setOperation("New");
				operations.getPartyGroup().add(o);
			}
		}
		req.setPartyGroups(operations);
		BaseResp resp = port.modifyPartyCluster(wrapper, tenantName).getModifyPartyClusterResponse();
		parseResponse(resp);
		return resp;
	}
}
