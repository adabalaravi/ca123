package com.accenture.sdp.csm.test;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.responses.ParameterDto;
import com.accenture.sdp.csm.test.utilities.AvsAdapter;
import com.accenture.sdp.csm.test.utilities.TestConfigurator;

//@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTestCase extends TestCase {

	protected String tenantName;

	public BaseTestCase() {
		try {
			this.tenantName = TestConfigurator.getInstance().getTenantName();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setUp() {
		System.out.println("Start Test");
	}

	@After
	public void tearDown() {
		System.out.println("End Test");
	}

	public String getTenantName() {
		return tenantName;
	}

	public void printResponse(DataServiceResponse response, String methodName) {
		System.out.println("Method: " + methodName + " Result code: " + response.getResultCode() + " Result description:" + response.getDescription());

		if (response.getParameters() != null) {
			for (ParameterDto parameterDto : response.getParameters()) {
				StringBuffer bf = new StringBuffer(parameterDto.getName());
				if (parameterDto.getValue() != null) {
					bf.append("\t=\t");
					bf.append(parameterDto.getValue());
				}
				System.out.println(bf.toString());
			}
		}
	}

	@Test
	public void testGetRandomPartyId() {
		assertNotNull(getRandomPartyId());
	}
	
	public Long getRandomPartyId() {
		return AvsAdapter.retrieveRandomAvsPartyId();
	}

}
