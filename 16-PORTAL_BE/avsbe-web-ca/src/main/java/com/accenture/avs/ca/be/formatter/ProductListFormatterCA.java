package com.accenture.avs.ca.be.formatter;

import java.util.Iterator;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import com.accenture.avs.be.configurator.TenantConfigurator;

import com.accenture.avs.be.db.framework.SystemMessages;
import com.accenture.avs.be.db.util.LogUtil;
import com.accenture.avs.be.formatter.ProductListFormatter;
import com.accenture.avs.be.json.bean.ProductList;
import com.accenture.avs.be.json.bean.ProductListResponse;
import com.accenture.avs.ca.be.dao.SolutionOfferinclDurationDAO;
import com.accenture.avs.ca.be.db.bean.ProductBeanCA;
import com.accenture.avs.ca.be.db.bean.SolutionOfferinclDurationDetailsBean;
import com.accenture.avs.ca.be.json.bean.ProductListCA;
import com.accenture.avs.ca.be.json.bean.ProductListResponseCA;


public class ProductListFormatterCA extends ProductListFormatter{

	public ProductListFormatterCA(TenantConfigurator tenantConfigurator) {
		super(tenantConfigurator);
		// TODO Auto-generated constructor stub
	}
	
	
	public static Object bean2JSON(List<ProductBeanCA> listOfProducts,TenantConfigurator tenantConfigurator) {
		JSON returnJSON = null;
		JsonConfig jsonConfig = new JsonConfig();
		ProductListResponseCA response = new ProductListResponseCA();
		SystemMessages systemMessages = tenantConfigurator.getSystemMessages();
		response.setResultCode("OK");
		try {
			if (listOfProducts != null && listOfProducts.size() > 0) {

				ProductListCA productList = new ProductListCA();
				ProductBeanCA[] arrayOfProducts = new ProductBeanCA[listOfProducts.size()];

				Iterator<ProductBeanCA> productIterator = listOfProducts
						.iterator();
				for (int i = 0; productIterator.hasNext(); i++) {
					arrayOfProducts[i] = productIterator.next();
					
					SolutionOfferinclDurationDetailsBean solutionOfferinclDetailsBean = new SolutionOfferinclDurationDetailsBean();
					SolutionOfferinclDurationDAO solutionOfferinclDurationDAO = new SolutionOfferinclDurationDAO();
					solutionOfferinclDetailsBean = solutionOfferinclDurationDAO.getDurationofSolutionOffer(arrayOfProducts[i].getItemId(), tenantConfigurator);
					
					arrayOfProducts[i].setProductType(solutionOfferinclDetailsBean.getSolutionOfferType());
				}

				productList.setProductDetails(arrayOfProducts);

				response.setResultObj(productList);
			}
			returnJSON = JSONSerializer.toJSON(response, jsonConfig);
			return returnJSON;
		} catch (Exception e) {
			e.printStackTrace();
			//LogUtil.writeErrorLog(ProductListFormatter.class,
				//	systemMessages.ERROR_BE_FORMATTER_3104_FORMAT_KO, e);
			return (null);
		}
	}
	
	


}
