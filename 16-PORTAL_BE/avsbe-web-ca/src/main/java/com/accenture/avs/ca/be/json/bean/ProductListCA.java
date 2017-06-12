
package com.accenture.avs.ca.be.json.bean;

import com.accenture.avs.be.db.bean.ProductBean;
import com.accenture.avs.ca.be.db.bean.ProductBeanCA;

/**
 * @author aditya.madhav.k
 *
 */
public class ProductListCA {

	private ProductBeanCA[] ProductDetails;

	public ProductBeanCA[] getProductDetails() {
		return ProductDetails;
	}

	public void setProductDetails(ProductBeanCA[] productBeans) {
		this.ProductDetails = productBeans;
	}
	
}