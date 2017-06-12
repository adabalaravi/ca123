package com.accenture.sdp.csmfe.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.accenture.sdp.csm.utilities.ConstantsHandler;
import com.accenture.sdp.csmfe.webservices.utils.Utilities;

public class ConstantsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String RESULT_STRING = "Refresh %s constants : %s";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConstantsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tenantName = Utilities.trim(request.getParameter("tenant"));
		String result;
		try {
			if (tenantName == null) {
				tenantName = "CONFIG";
				ConstantsHandler.refresh();
			} else {
				ConstantsHandler.refresh(tenantName);
			}
			result = "OK";
		} catch (Exception e) {
			result = "ERROR " + e.getMessage();
		}
		PrintWriter out = response.getWriter();
		out.format(RESULT_STRING, tenantName, result);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
