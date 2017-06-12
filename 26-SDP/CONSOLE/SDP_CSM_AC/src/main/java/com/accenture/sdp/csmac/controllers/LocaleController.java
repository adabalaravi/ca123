package com.accenture.sdp.csmac.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

public class LocaleController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2267248000973041080L;

	private List<SelectItem> availableLocales;
	private Locale currentLocale;
	private String selectedLocale;

	public LocaleController() {
		super();
		this.currentLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		this.selectedLocale = currentLocale.getLanguage();
		generateAvailableLocales();
	}

	private SelectItem makeLocaleItem(Locale toWrap) {
		if (toWrap != null) {
			return new SelectItem(toWrap.getLanguage(), toWrap.getDisplayName());
		}
		return null;
	}

	private void generateAvailableLocales() {
		availableLocales = new ArrayList<SelectItem>();
		Locale current = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		availableLocales.add(makeLocaleItem(current));
		Locale defaultLocale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
		if (!defaultLocale.getLanguage().equals(current.getLanguage())) {
			availableLocales.add(makeLocaleItem(defaultLocale));
		}
		for (Iterator<Locale> iter = FacesContext.getCurrentInstance().getApplication().getSupportedLocales(); iter.hasNext();) {
			Locale localeTemp = iter.next();
			if (!localeTemp.getLanguage().equals(current.getLanguage())) {
				availableLocales.add(makeLocaleItem(localeTemp));
			}
		}
	}

	public void applyLocale(Locale toApply) {
		setCurrentLocale(toApply);
		setSelectedLocale(toApply.getLanguage());
		FacesContext.getCurrentInstance().getViewRoot().setLocale(toApply);
	}

	public void localeChanged(ValueChangeEvent event) {
		if (event.getNewValue() != null) {
			applyLocale(new Locale(event.getNewValue().toString()));
		}
	}

	public Locale getCurrentLocale() {
		return currentLocale;
	}

	public void setCurrentLocale(Locale currentLocale) {
		this.currentLocale = currentLocale;
	}

	public String getSelectedLocale() {
		return selectedLocale;
	}

	public void setSelectedLocale(String selectedLocale) {
		this.selectedLocale = selectedLocale;
	}

	public List<SelectItem> getAvailableLocales() {
		if (availableLocales == null) {
			generateAvailableLocales();
		}
		return availableLocales;
	}

	public void setAvailableLocales(List<SelectItem> availableLocales) {
		this.availableLocales = availableLocales;
	}
}
