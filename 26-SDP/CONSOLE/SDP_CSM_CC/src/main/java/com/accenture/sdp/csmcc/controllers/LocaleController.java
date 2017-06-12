package com.accenture.sdp.csmcc.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "pageBean")
@SessionScoped
public class LocaleController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SelectItem> availableLocales;
	private Locale currentLocale;
	private String dropdownItem;
	private String localeMenuLabel;

	public LocaleController() {
		currentLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		this.localeMenuLabel = currentLocale.getDisplayName(currentLocale);
		dropdownItem = currentLocale.getLanguage();
	}

	public String getLocaleMenuLabel() {
		return localeMenuLabel;
	}

	public void setLocaleMenuLabel(String localeMenuLabel) {
		this.localeMenuLabel = localeMenuLabel;

	}
	
	public Locale getCurrentLocale() {
		return currentLocale;
	}

	public void setCurrentLocale(Locale currentLocale) {
		this.currentLocale = currentLocale;
	}

	public String getDropdownItem() {
		return dropdownItem;
	}

	public void setDropdownItem(String dropdownItem) {
		this.dropdownItem = dropdownItem;
	}
	
	private SelectItem makeLocaleItem(Locale toWrap) {
		if (toWrap != null) {
			return new SelectItem(toWrap.getLanguage(), toWrap.getDisplayName(toWrap));
		}
		return null;
	}
	private void generateAvailableLocales() {
		availableLocales = new ArrayList<SelectItem>(0);
		currentLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		availableLocales.add(makeLocaleItem(currentLocale));
		Locale defaultLocale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
		if (!defaultLocale.getLanguage().equals(currentLocale.getLanguage())){
			availableLocales.add(makeLocaleItem(defaultLocale));	
		}
		for (Iterator<Locale> iter = FacesContext.getCurrentInstance().getApplication().getSupportedLocales(); iter.hasNext();) {
			Locale localeTemp = iter.next();
			if (!localeTemp.getLanguage().equals(currentLocale.getLanguage())){
				availableLocales.add(makeLocaleItem(localeTemp));
			}
		}
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


	public void applyLocale(Locale toApply) {
		setCurrentLocale(toApply);
		setLocaleMenuLabel(toApply.getDisplayName(toApply));
		FacesContext.getCurrentInstance().getViewRoot().setLocale(toApply);
	}

	public void localeChanged(ValueChangeEvent event) {
		if (event.getNewValue() != null) {
			applyLocale(new Locale(event.getNewValue().toString()));
		}
	}
}
