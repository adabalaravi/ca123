package com.accenture.sdp.csmcc.popups;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.xml.bind.JAXBException;

import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;

@ManagedBean(name = ApplicationConstants.METHOD_POPUP)
@SessionScoped
public class MethodPopupBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2424638077939870540L;

	private boolean visible = false;

	private String info;
	private String title;
	

	public MethodPopupBean(){
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	
	public void openPopup(ActionEvent event) throws JAXBException {
		String  titleValue = ((String)event.getComponent().getAttributes().get("titleValue"));
		String  profileValue = ((String)event.getComponent().getAttributes().get("infoValue"));
		title = titleValue;
		this.info = profileValue;
		visible = true;

	}

	public void closePopup() {
		this.visible = false;
	}
	
}
