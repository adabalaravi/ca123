package com.accenture.avs.ca.be.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.accenture.avs.be.db.bean.DiscountBean;
import com.accenture.avs.be.db.bean.PackagePriceView;
import com.accenture.avs.be.db.bean.SdpSolutionOfferExternalId;
import com.accenture.avs.be.db.bean.SolutionOfferDetailsBean;
import com.accenture.avs.ca.be.db.bean.ProductBeanCA;

/**
 * @author aditya.madhav.k
 * 
 */
public class ProductListUtilCA {

	private String baseSolutionIds = "";
	private String subSolutionIds = "";
	private Map<Long, ProductBeanCA> productMap = new HashMap<Long, ProductBeanCA>();

	// NEW CR as on 09-MAR-2015 - RESTRICTING DAY PASS FOR 365 SUBSCRIBED USERS
	// As part of CR ,Added productEligibleToDisplay, packageToBeShownList parameters to the method
	public void segregateSolutionIds(
			List<SolutionOfferDetailsBean> listOfSolutionOfferDetails,boolean productEligibleToDisplay,List<String> packageToBeShownList) {

		setBaseSolutionIds(listOfSolutionOfferDetails);
		setSubSolutionIds(listOfSolutionOfferDetails);
		// NEW CR as on 09-MAR-2015 - RESTRICTING DAY PASS FOR 365 SUBSCRIBED USERS
		//Passing the productEligibleToDisplay flag and packages to be shown list -  so that eligible field for each product will be processed either true or false
		// As part of CR ,Added productEligibleToDisplay,packageToBeShownList parameters to the method
		createProductMap(listOfSolutionOfferDetails,productEligibleToDisplay,packageToBeShownList);
	}


	public void createProductMap(
			List<SolutionOfferDetailsBean> listOfSolutionOfferDetails,boolean productEligibleToDisplay,List<String> packageToBeShownList) {

		Iterator<SolutionOfferDetailsBean> it = listOfSolutionOfferDetails
				.iterator();

		while (it.hasNext()) {
			SolutionOfferDetailsBean solutionOfferDetailsBean = it.next();
			ProductBeanCA ProductBeanCA = new ProductBeanCA();
			// NEW CR as on 09-MAR-2015 - RESTRICTING DAY PASS FOR 365 SUBSCRIBED USERS
			// As part of CR ,Added productEligibleToDisplay,packageToBeShownList parameters to the method
			loadProductBeanCA(solutionOfferDetailsBean,ProductBeanCA,productEligibleToDisplay,packageToBeShownList);

			productMap.put(solutionOfferDetailsBean.getSolutionOfferID(),
					ProductBeanCA);
		}
	}

	public void addSolutionOfferPriceToProductMap(
			List<PackagePriceView> listOfPackagePriceView) {
		Iterator<PackagePriceView> it = listOfPackagePriceView.iterator();
		while (it.hasNext()) {
			PackagePriceView packagePriceView = (PackagePriceView) it.next();
			ProductBeanCA ProductBeanCA = productMap.get(packagePriceView
					.getPackageView().getSolutionOfferId());

			if (ProductBeanCA.getItemPrice() != null) {
				ProductBeanCA.setItemPrice(ProductBeanCA.getItemPrice()
						+ packagePriceView.getNrcPrice());
			} else {
				ProductBeanCA.setItemPrice(packagePriceView.getNrcPrice());
			}
			ProductBeanCA.setItemRecurringPrice(packagePriceView.getRcPrice());
			ProductBeanCA.setCurrency(packagePriceView.getCurrency()
					.getDescription());
		}
	}

	
	
	public void addDeviceInfoToProductMap(
			List<SdpSolutionOfferExternalId> sdpSolutionOfferExternalIdList) {

		if (sdpSolutionOfferExternalIdList != null
				&& sdpSolutionOfferExternalIdList.size() > 0) {

			Iterator it = sdpSolutionOfferExternalIdList.iterator();

			while (it.hasNext()) {
				SdpSolutionOfferExternalId sdpSolutionOfferExternalId = (SdpSolutionOfferExternalId) it.next();
				ProductBeanCA productBeanCA = productMap.get(sdpSolutionOfferExternalId.getSolutionOfferID());
				if (sdpSolutionOfferExternalId.getExternalPlatformName().equalsIgnoreCase("PlayStore")) {
					productBeanCA.setPlayStoreId(sdpSolutionOfferExternalId.getExternalID());
				} else if (sdpSolutionOfferExternalId.getExternalPlatformName()
						.equalsIgnoreCase("AppleStore")) {
					productBeanCA.setAppleStoreId(sdpSolutionOfferExternalId.getExternalID());
				}
				productMap.put(productBeanCA.getItemId(), productBeanCA);
				// productBeanCA.getp.getExternalIdList().add(sdpSolutionOfferExternalIdBean);

			}
		}
	}
	
	// NEW CR as on 09-MAR-2015 - RESTRICTING DAY PASS FOR 365 SUBSCRIBED USERS
	// As part of CR ,Added productEligibleToDisplay,packageToBeShownList parameters to the method
	private void loadProductBeanCA(
			SolutionOfferDetailsBean solutionOfferDetailsBean,
			ProductBeanCA ProductBeanCA,boolean productEligibleToDisplay,List<String> packageToBeShownList) {

		ProductBeanCA.setItemId(solutionOfferDetailsBean.getSolutionOfferID());
		ProductBeanCA
				.setItemName(solutionOfferDetailsBean.getSolutionOfferName());
		ProductBeanCA.setItemDescription(solutionOfferDetailsBean
				.getSolutionOfferDescription());
		if (solutionOfferDetailsBean.getSolutionOfferProfile() != null) {
			StringTokenizer profileTokenizer = new StringTokenizer(
					solutionOfferDetailsBean.getSolutionOfferProfile(), ";");
			while (profileTokenizer.hasMoreTokens()) {
				StringTokenizer subTokenizer = new StringTokenizer(
						profileTokenizer.nextToken(), "=");
				while (subTokenizer.hasMoreTokens()) {
					if ("productType"
							.equalsIgnoreCase(subTokenizer.nextToken())) {
						ProductBeanCA.setItemType(subTokenizer.nextToken());
					}
				}
			}
		}
		ProductBeanCA.setStatus(solutionOfferDetailsBean.getStatusType()
				.getStatusName());
		if (solutionOfferDetailsBean.getStartDate() != null)
			ProductBeanCA.setStartDate(solutionOfferDetailsBean.getStartDate()
					.getTime() / 1000);
		if (solutionOfferDetailsBean.getEndDate() != null)
			ProductBeanCA.setEndDate(solutionOfferDetailsBean.getEndDate()
					.getTime() / 1000);

		ProductBeanCA.setParentItemId(solutionOfferDetailsBean
				.getParentSolutionOfferID());
		// NEW CR as on 09-MAR-2015 - RESTRICTING DAY PASS FOR 365 SUBSCRIBED USERS
		//Comparing packages whether packages available in packageToBeShownList.if package available then setting productEligibleToDisplay=true for corresponding package
		       if(!productEligibleToDisplay)
		       {
					if(packageToBeShownList!=null && packageToBeShownList.size() > 0 ){ 
						if( packageToBeShownList.contains(String.valueOf(solutionOfferDetailsBean.getSolutionOfferID()))) {
						    productEligibleToDisplay=true;
					   }
					}
		       }	
				//setting eligible field response based on productEligibleToDisplay flag condition
				ProductBeanCA.setEligible(String.valueOf(productEligibleToDisplay));
				
	}

	protected void setBaseSolutionIds(
			List<SolutionOfferDetailsBean> listOfSolutionOfferIds) {

		Iterator<SolutionOfferDetailsBean> it = listOfSolutionOfferIds
				.iterator();
		baseSolutionIds = "";
		while (it.hasNext()) {

			SolutionOfferDetailsBean solutionOfferDetailsBean = it.next();

			if (solutionOfferDetailsBean.getParentSolutionOfferID() == null) {
				baseSolutionIds = baseSolutionIds
						+ solutionOfferDetailsBean.getSolutionOfferID();
				if (it.hasNext()) {
					baseSolutionIds = baseSolutionIds + ",";
				}
			}
		}
	}

	private void setSubSolutionIds(
			List<SolutionOfferDetailsBean> listOfSolutionOfferIds) {
		Iterator<SolutionOfferDetailsBean> it = listOfSolutionOfferIds
				.iterator();
		subSolutionIds = "";

		while (it.hasNext()) {

			SolutionOfferDetailsBean solutionOfferDetailsBean = it.next();

			if (solutionOfferDetailsBean.getParentSolutionOfferID() != null) {
				subSolutionIds = subSolutionIds
						+ solutionOfferDetailsBean.getSolutionOfferID();
				if (it.hasNext()) {
					subSolutionIds = subSolutionIds + ",";
				}
			}
		}
	}

	public Map<Long, ProductBeanCA> getProductMap() {
		return productMap;
	}

	public String getAllBaseSolutionIds() {
		return baseSolutionIds;
	}

	public String getAllSubSolutionIds() {
		return subSolutionIds;
	}

	public void calculateDiscountedPrice(
			List<PackagePriceView> listOfPackagePriceViewForsubSolutions,
			List<DiscountBean> listOfDiscountBeans) {

		Iterator<PackagePriceView> priceIterator = listOfPackagePriceViewForsubSolutions
				.iterator();
		while (priceIterator.hasNext()) {
			PackagePriceView packagePriceView = (PackagePriceView) priceIterator
					.next();

			for (int i = 0; i < listOfDiscountBeans.size(); i++) {

				DiscountBean discountBean = (DiscountBean) listOfDiscountBeans
						.get(i);
				boolean found = false;
				if (packagePriceView.getPackageView().getPackageId() == discountBean
						.getPackageId()) {

					if (discountBean.getNrcABSDiscount() == 0) {
						Double percentage = discountBean.getNrcPercDiscount();
						Double nrcPrice = packagePriceView.getNrcPrice();

						packagePriceView.setDiscountedNrcPrice(nrcPrice
								- ((nrcPrice * percentage) / 100));
					} else {
						packagePriceView.setDiscountedNrcPrice(discountBean
								.getNrcABSDiscount());
					}

					if (discountBean.getRcABSDiscount() == 0) {
						Double percentage = discountBean.getRcPercDiscount();
						Double rcPrice = packagePriceView.getRcPrice();

						packagePriceView.setDiscountedRcPrice(rcPrice
								- ((rcPrice * percentage) / 100));
					} else {
						packagePriceView.setDiscountedRcPrice(discountBean
								.getRcABSDiscount());
					}

					found = true;
				}

				if (found) {
					listOfDiscountBeans.remove(i);
					break;
				}
			}
		}
	}

	public void setDiscountedPriceToProductMap(
			List<PackagePriceView> listOfPackagePriceView) {
		Iterator<PackagePriceView> it = listOfPackagePriceView.iterator();
		while (it.hasNext()) {
			PackagePriceView packagePriceView = (PackagePriceView) it.next();
			ProductBeanCA ProductBeanCA = productMap.get(packagePriceView
					.getPackageView().getSolutionOfferId());

			if (ProductBeanCA.getItemPrice() != null) {
				ProductBeanCA.setItemPrice(ProductBeanCA.getItemPrice()
						+ packagePriceView.getNrcPrice());
			} else {
				ProductBeanCA.setItemPrice(packagePriceView.getNrcPrice());
			}
			ProductBeanCA.setItemRecurringPrice(packagePriceView.getRcPrice());
			ProductBeanCA.setCurrency(packagePriceView.getCurrency()
					.getDescription());
			ProductBeanCA.setDiscountedPrice(packagePriceView
					.getDiscountedNrcPrice());
			ProductBeanCA.setDiscountedRecurringPrice(packagePriceView
					.getDiscountedRcPrice());
		}

	}

	public void addRelatedPriceForSolutionOfferId(
			List<PackagePriceView> listOfPackagePriceView) {

		PackagePriceView packagePriceView1 = null;
		PackagePriceView packagePriceView2 = null;

		for (int i = 0; i < listOfPackagePriceView.size(); i++) {

			packagePriceView1 = (PackagePriceView) listOfPackagePriceView
					.get(i);

			for (int j = i + 1; j < listOfPackagePriceView.size(); j++) {
				packagePriceView2 = (PackagePriceView) listOfPackagePriceView
						.get(j);
				long l1 = packagePriceView1.getPackageView()
						.getSolutionOfferId();
				long l2 = packagePriceView2.getPackageView()
						.getSolutionOfferId();
				boolean added = false;

				if (l1 == l2) {
					packagePriceView1.setNrcPrice(packagePriceView1
							.getNrcPrice() + packagePriceView2.getNrcPrice());
					packagePriceView1.setRcPrice(packagePriceView1.getRcPrice()
							+ packagePriceView2.getRcPrice());
					packagePriceView1.setDiscountedNrcPrice(packagePriceView1
							.getDiscountedNrcPrice()
							+ packagePriceView2.getDiscountedNrcPrice());
					packagePriceView1.setDiscountedRcPrice(packagePriceView1
							.getDiscountedRcPrice()
							+ packagePriceView2.getDiscountedRcPrice());

					added = true;
				}
				if (added) {
					listOfPackagePriceView.remove(packagePriceView2);
					j--;
				}
			}
		}

	}
	// NEW CR as on 09-MAR-2015 - RESTRICTING DAY PASS FOR 365 SUBSCRIBED USERS
	//Removing the spaces in string array
	public static String[] trimListOfStrings(String[] values) {

		 for (int i = 0, length = values.length; i < length; i++) {
		  if (values[i] != null) {
		   values[i] = values[i].trim();                                
		  }
		 }
		 return values;

		}

}