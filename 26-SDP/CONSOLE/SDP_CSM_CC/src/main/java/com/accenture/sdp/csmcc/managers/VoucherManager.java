package com.accenture.sdp.csmcc.managers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.xml.datatype.DatatypeConfigurationException;

import com.accenture.sdp.csmcc.beans.SolutionOfferBean;
import com.accenture.sdp.csmcc.beans.VoucherInfo;
import com.accenture.sdp.csmcc.business.VoucherBusiness;
import com.accenture.sdp.csmcc.common.LoggerWrapper;
import com.accenture.sdp.csmcc.common.constants.ApplicationConstants;
import com.accenture.sdp.csmcc.common.exceptions.ServiceErrorException;
import com.accenture.sdp.csmcc.common.utils.Utilities;
import com.accenture.sdp.csmcc.common.utils.VoucherResponse;
import com.accenture.sdp.csmcc.popups.PopupBean;


@ManagedBean(name = ApplicationConstants.VOUCHER_MANAGER)
@SessionScoped
public class VoucherManager extends BaseManager  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VoucherInfo selectedBean;
	private SolutionOfferBean solutionOffer;
	private List<VoucherInfo> vouchers;
	private List<VoucherInfo> filteredList;
	private List<SelectItem> typeList;
	private Long voucherAvailableTotal;
	private Long voucherTotal;
	Map<String,VoucherInfo> voucherMap= new HashMap<String, VoucherInfo>();
	private LoggerWrapper log = new LoggerWrapper(VoucherManager.class);






	public List<SelectItem> getTypeList() {
		return typeList;
	}


	public void setTypeList(List<SelectItem> typeList) {
		this.typeList = typeList;
	}



	public VoucherInfo getSelectedBean() {
		return selectedBean;
	}

	public void setSelectedBean(VoucherInfo selectedBean) {
		this.selectedBean = selectedBean;
	}



	protected boolean isDefaultAscending(String sortColumn) {
		return false;
	}

	public void refreshTable() {
		voucherAvailableTotal = 0L;
		voucherTotal = 0L;
		if(vouchers != null){
			vouchers.clear();
		}
		try {
			VoucherResponse resp = VoucherBusiness.searchAvailableVouchersBySolutionOfferId(solutionOffer);
			voucherAvailableTotal = resp.getVoucherAvailableTotal();
			voucherTotal = resp.getVoucherTotal();
			vouchers =resp.getVouchers();
			if (filteredList!=null) {
				filteredList.clear();
				filteredList.addAll(vouchers);
			}
		} catch (ServiceErrorException e) {
			PopupBean msgBean = Utilities.findBean(ApplicationConstants.POPUP_BEAN, PopupBean.class);
			msgBean.openPopup(e.getMessage());
			LoggerWrapper log = new LoggerWrapper(VoucherManager.class);
			log.logEndFeature(e.getCode(),e.getMessage());
		}

		setAscending(false);
	}

	public String refreshTableAction()  {
		refreshTable();
		return null;
	}


	public void resetStartDate(){
		selectedBean.setStartDate(null);

	}
	public void resetEndDate(){
		selectedBean.setEndDate(null);

	}


	public void  loadVoucher(AjaxBehaviorEvent event) {
		//		selectedBean = (VoucherInfo) event.getComponent().getAttributes().get(ApplicationConstants.ITEM);
		VoucherInfo bean=voucherMap.get(selectedBean.getVoucherType());
		selectedBean.setValidityPeriod(bean.getValidityPeriod());	
		selectedBean.setStartDate(bean.getStartDate());
		selectedBean.setEndDate(bean.getEndDate());
		log.logDebug("selectedBean",selectedBean );
	}


	public void loadAvailableVoucherTypes(){
		typeList = new ArrayList<SelectItem>();
		typeList.add(new SelectItem("",""));
		voucherMap.clear();
		try {
			for (VoucherInfo voucherC : VoucherBusiness.searchAvailableVoucherTypes()) {

				typeList.add(new SelectItem(String.valueOf(voucherC.getVoucherType()), voucherC.getVoucherType()));
				voucherMap.put(voucherC.getVoucherType(), voucherC);

			}
		} catch (ServiceErrorException e) {
			log.logException(e.getMessage(), e);
		}
	}

	public void filterResult() {

	}


	@Override
	public void askChangeStatus(ActionEvent event) {

	}


	@Override
	public void changeStatusOfSelectedBean() {

	}


	public List<VoucherInfo> getVouchers() {
		return vouchers;
	}


	public void setVouchers(List<VoucherInfo> vouchers) {
		this.vouchers = vouchers;
	}


	public void addVoucherCompaign(ActionEvent event) {
		try {
			VoucherBusiness.addVoucherCompaign(solutionOffer, selectedBean);
		} catch (DatatypeConfigurationException e) {

		}
	}


	public SolutionOfferBean getSolutionOffer() {
		return solutionOffer;
	}


	public void setSolutionOffer(SolutionOfferBean solutionOffer) {
		this.solutionOffer = solutionOffer;
	}


	public Long getVoucherAvailableTotal() {
		return voucherAvailableTotal;
	}


	public void setVoucherAvailableTotal(Long voucherAvailableTotal) {
		this.voucherAvailableTotal = voucherAvailableTotal;
	}


	public Long getVoucherTotal() {
		return voucherTotal;
	}


	public void setVoucherTotal(Long voucherTotal) {
		this.voucherTotal = voucherTotal;
	}


	public List<VoucherInfo> getFilteredList() {
		return filteredList;
	}


	public void setFilteredList(List<VoucherInfo> filteredList) {
		this.filteredList = filteredList;
	}



}
