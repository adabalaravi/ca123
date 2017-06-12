package com.accenture.sdp.csmac.converters;

import java.util.ArrayList;
import java.util.List;

import com.accenture.sdp.csmac.beans.PackageBean;
import com.accenture.sdp.csmfe.webservices.clients.packages.PackageComplexInfoResp;

public abstract class PackageConverter {

	public static List<PackageBean> convertPackages(List<PackageComplexInfoResp> infos) {
		List<PackageBean> resultList = new ArrayList<PackageBean>();
		if (infos != null) {
			for (PackageComplexInfoResp info : infos) {
				resultList.add(convertPackage(info));
			}
		}
		return resultList;
	}

	public static PackageBean convertPackage(PackageComplexInfoResp info) {
		PackageBean bean = new PackageBean();
		bean.setPackageId(info.getPackageId());
		bean.setOfferId(info.getOfferId());
		bean.setNrcPrice(info.getNrcPrice());
		bean.setRcPrice(info.getRcPrice());
		bean.setDiscountId(info.getDiscountId());
		bean.setDiscountAbsNrc(info.getDiscountAbsNrc());
		bean.setDiscountAbsRc(info.getDiscountAbsRc());
		bean.setDiscountPercNrc(info.getDiscountPercNrc());
		bean.setDiscountPercRc(info.getDiscountPercRc());
		bean.setFrequencyName(info.getFrequencyPriceName());
		return bean;
	}

}
