package com.accenture.sdp.csmfe.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.metering.MeteringClientFactory;
import com.accenture.sdp.csm.utilities.CodeManager;
import com.accenture.sdp.csm.utilities.logging.LoggerWrapper;

/**
 * Servlet implementation class StartupServlet
 */
public class StartupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StartupServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public void init() {
		LoggerWrapper log = null;
		EntityManager em = null;
		try {
			System.out.println("Initializing LoggerWrapper...");
			LoggerWrapper.initialize();
			log = new LoggerWrapper(this.getClass());
			log.logInfo("LoggerWrapper initialized");
			
			CodeManager.init();
			log.logInfo(CodeManager.class.getSimpleName()+" initialized");
			em = PersistenceManager.getEntityManager();
			log.logInfo(PersistenceManager.class.getSimpleName()+" initialized");
			MeteringClientFactory.getClient().init();
			log.logInfo(MeteringClientFactory.class.getSimpleName()+" initialized");
			
			log.logInfo(getServletName() + ": initialized");
		} catch (Exception e) {
			e.printStackTrace();
			if (log != null) {
				log.logError("Error initializing CSM FE. Exception %s", e.getMessage());
			}
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}

	@Override
	public void destroy() {

		try {
			System.out.println("Start destroying EntityManagerFactory");
			PersistenceManager.getInstance().destroyEntityManager();
			System.out.println("EntityManagerFactory destroyed");
		} catch (Exception e) {
			System.err.println("Unable to destroy EntityManagerFactory");
			e.printStackTrace();
		}
		super.destroy();
	}
}
