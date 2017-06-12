package com.accenture.sdp.csmfe.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpTokenDto;
import com.accenture.sdp.csm.managers.SdpTokenManager;
import com.accenture.sdp.csm.utilities.Constants;
import com.accenture.sdp.csm.utilities.logging.LoggerWrapper;

/**
 * Servlet implementation class SdpTokenManagerServlet
 */
public class SdpTokenManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static LoggerWrapper log = new LoggerWrapper(SdpTokenManagerServlet.class);

	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String PLATFORM = "platform";
	private static final String TOKEN = "token";
	private static final String CONTENT_TYPE = "text/xml";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SdpTokenManagerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.logDebug("SdpTokenManagerServlet GET request received");
		String responseTemplate = "<SdpGenerateTokenResponse><resultCode>%s</resultCode><description>%s</description><token>%s</token></SdpGenerateTokenResponse>";
		String responseXml = String.format(responseTemplate, Constants.CODE_GENERIC_ERROR, "Generic Error", "");
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = null;
		String token = "";
		try {
			String username = request.getParameter(USERNAME);
			log.logDebug("Request username: %s", username);
			String password = request.getParameter(PASSWORD);
			String platformName = request.getParameter(PLATFORM);
			log.logDebug("Request platformName: %s", platformName);

			ComplexServiceResponse serviceResponse = SdpTokenManager.getInstance().generateToken(username, password, platformName);
			if (serviceResponse != null) {

				if (serviceResponse.getComplexObject() != null) {
					token = serviceResponse.getComplexObject().toString();
				}
				log.logDebug("Generated token: %s", token);
				responseXml = String.format(responseTemplate, serviceResponse.getResultCode(), serviceResponse.getDescription(), token);
				out = response.getWriter();

				out.println(responseXml);

			}

		} catch (Exception e) {
			e.printStackTrace();
			log.logDebug(e.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.logDebug("SdpTokenManagerServlet POST request received");
		String responseTemplate = "<SdpValidateTokenResponse><resultCode>%s</resultCode><description>%s</description><username>%s</username><password>%s</password><platform>%s</platform></SdpValidateTokenResponse>";
		String responseXml = String.format(responseTemplate, Constants.CODE_GENERIC_ERROR, "Generic Error", "", "", "");
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = null;
		String username = "";
		String password = "";
		String platform = "";
		try {
			String token = request.getParameter(TOKEN);
			log.logDebug("Request token: %s", token);
			String platformName = request.getParameter(PLATFORM);
			log.logDebug("Request platformName: %s", platformName);

			SearchServiceResponse serviceResponse = SdpTokenManager.getInstance().validateToken(token, platformName);
			if (serviceResponse != null) {
				if (serviceResponse.getSearchResult() != null && serviceResponse.getSearchResult().size() > 0) {
					SdpTokenDto dto = (SdpTokenDto) serviceResponse.getSearchResult().get(0);
					username = dto.getUsername();
					password = dto.getPassword();
					platform = dto.getPlatform();
				}
				responseXml = String.format(responseTemplate, serviceResponse.getResultCode(), serviceResponse.getDescription(), username, password, platform);
				out = response.getWriter();

				out.println(responseXml);

			}

		} catch (Exception e) {
			e.printStackTrace();
			log.logDebug(e.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
