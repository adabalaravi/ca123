package com.accenture.avs.console.common.exception;

import java.util.Iterator;

import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import com.accenture.avs.console.common.beans.ErrorBean;
import com.accenture.avs.console.common.constants.ApplicationConstants;
import com.accenture.avs.console.common.utils.Utilities;

public class ViewExpiredExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	public ViewExpiredExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() {
		Iterable<ExceptionQueuedEvent> events = this.wrapped.getUnhandledExceptionQueuedEvents();
		for (Iterator<ExceptionQueuedEvent> it = events.iterator(); it.hasNext();) {
			ExceptionQueuedEvent event = it.next();
			ExceptionQueuedEventContext eqec = event.getContext();

			if (eqec.getException() instanceof ViewExpiredException) {
				FacesContext context = eqec.getContext();
				NavigationHandler navHandler = context.getApplication().getNavigationHandler();

				try {
					// force logout
					context.getExternalContext().invalidateSession();
					// redirect opening popup
					navHandler.handleNavigation(context, null, "login?faces-redirect=true&expired=true");
				} finally {
					it.remove();
				}
			} else {
				FacesContext context = eqec.getContext();
				NavigationHandler navHandler = context.getApplication().getNavigationHandler();
				try {
					// force logout
					context.getExternalContext().invalidateSession();
					// redirect opening popup
					ErrorBean bean = Utilities.findBean(ApplicationConstants.ERROR_BEAN_NAME, ErrorBean.class);
					bean.loadError(eqec.getException());
					navHandler.handleNavigation(context, null, "errorPage?faces-redirect=true");
				} finally {
					it.remove();
				}
			}
		}

		this.wrapped.handle();
	}

}
