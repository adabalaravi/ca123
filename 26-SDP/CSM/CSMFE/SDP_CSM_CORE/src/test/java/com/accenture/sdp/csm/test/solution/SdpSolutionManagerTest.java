package com.accenture.sdp.csm.test.solution;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.requests.SdpPartyGroupRequestDto;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.managers.SdpSolutionManager;
import com.accenture.sdp.csm.test.BaseTestCase;
import com.accenture.sdp.csm.test.utilities.TestConstants;
import com.accenture.sdp.csm.test.utilities.Utils;
import com.accenture.sdp.csm.utilities.Utilities;

public class SdpSolutionManagerTest extends BaseTestCase {

	SdpSolutionManager manager = SdpSolutionManager.getInstance();

	@Test
	public void testc_CreateSolution() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = c_createSolution();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse c_createSolution() throws PropertyNotFoundException {

		String solutionName = Utils.getRandomName(10);
		String solutionDescription = Utils.getRandomName(10);
		String externalId = null;
		String solutionProfile = null;

		CreateServiceResponse response = manager.createSolution(solutionName, solutionDescription, externalId, new Date(), new Date(), 1L, solutionProfile,
				getTenantName());
		return response;
	}

	@Test
	public void testCreateSolution() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			CreateServiceResponse resp = createSolution();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateServiceResponse createSolution() throws PropertyNotFoundException {

		String solutionName = Utils.getRandomName(10);
		String solutionDescription = Utils.getRandomName(10);
		String externalId = null;
		String solutionProfile = null;
		ArrayList<String> partyGroups = new ArrayList<String>();
		partyGroups.add("Business");

		CreateServiceResponse response = manager.createSolution(solutionName, solutionDescription, externalId, new Date(), new Date(), 1L, solutionProfile,
				partyGroups, getTenantName());

		return response;
	}

	@Test
	public void testModifySolution() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifySolution();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifySolution() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = c_createSolution();

		Long solutionId = createServiceResponse.getEntityId();

		String solutionName = Utils.getRandomName(10);
		String solutionDescription = Utils.getRandomName(10);
		String externalId = null;
		String solutionProfile = null;

		DataServiceResponse response = manager.modifySolution(solutionId, solutionName, 1L, solutionDescription, externalId, new Date(), new Date(),
				solutionProfile, getTenantName());

		return response;
	}

	@Test
	public void testModifySolutionPartyGroups() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = modifySolutionPartyGroups();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse modifySolutionPartyGroups() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = c_createSolution();

		Long solutionId = createServiceResponse.getEntityId();

		ArrayList<SdpPartyGroupRequestDto> partyGroups = new ArrayList<SdpPartyGroupRequestDto>();

		SdpPartyGroupRequestDto dto = new SdpPartyGroupRequestDto();
		dto.setOperation(com.accenture.sdp.csm.commons.LinkUpdateOperation.Operation.NEW.getValue());
		dto.setPartyGroupId(2L);
		partyGroups.add(dto);

		DataServiceResponse response = manager.modifySolutionPartyGroups(solutionId, partyGroups, getTenantName());
		return response;
	}

	@Test
	public void testDeleteSolution() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			DataServiceResponse resp = deleteSolution();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataServiceResponse deleteSolution() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createSolution();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long SolutionId = createServiceResponse.getEntityId();

		manager.solutionChangeStatus(SolutionId, "Suspended", getTenantName());
		manager.solutionChangeStatus(SolutionId, "Inactive", getTenantName());

		DataServiceResponse response2 = manager.deleteSolution(SolutionId, getTenantName());

		return response2;
	}

	@Test
	public void testSearchSolution() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchSolution();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchSolution() throws PropertyNotFoundException {

		CreateServiceResponse createServiceResponse = createSolution();

		assertTrue(createServiceResponse.getResultCode().equals(TestConstants.CODE_OK));

		Long SolutionId = createServiceResponse.getEntityId();

		SearchServiceResponse searchServiceResponse = manager.searchSolution(SolutionId, getTenantName());

		return searchServiceResponse;
	}

	public SearchServiceResponse searchSolution(Long solutionId) throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchSolution(solutionId, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchAllSolutions() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchAllSolutions();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchAllSolutions() throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchAllSolutions(0L, 0L, getTenantName());

		return searchServiceResponse;
	}

	@Test
	public void testSearchSolutionsByPartyGroup() {
		try {
			System.out.println(Utilities.getCurrentClassAndMethod());
			SearchServiceResponse resp = searchSolutionsByPartyGroup();
			printResponse(resp, Utilities.getCurrentClassAndMethod());
			assertTrue(resp.getResultCode().equals(TestConstants.CODE_OK));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SearchServiceResponse searchSolutionsByPartyGroup() throws PropertyNotFoundException {

		SearchServiceResponse searchServiceResponse = manager.searchSolutionsByPartyGroup("Business", 0L, 0L, getTenantName());

		return searchServiceResponse;
	}

}
