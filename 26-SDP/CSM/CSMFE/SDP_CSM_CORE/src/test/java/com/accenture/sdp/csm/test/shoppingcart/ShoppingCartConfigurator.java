package com.accenture.sdp.csm.test.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.accenture.sdp.csm.database.NamedQueryHelper;
import com.accenture.sdp.csm.database.PersistenceManager;
import com.accenture.sdp.csm.exceptions.PropertyNotFoundException;
import com.accenture.sdp.csm.helpers.SdpPartyHelper;
import com.accenture.sdp.csm.helpers.SdpShoppingCartHelper;
import com.accenture.sdp.csm.model.jpa.RefItemType;
import com.accenture.sdp.csm.model.jpa.SdpParty;
import com.accenture.sdp.csm.model.jpa.SdpShoppingCart;

public class ShoppingCartConfigurator {

	private ShoppingCartConfigurator() {
	}

	public static List<String> getShoppingCartItemTypes(String tenantName) throws PropertyNotFoundException {
		EntityManager em = null;
		List<String> types = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			List<RefItemType> beans = NamedQueryHelper.executeQueryString(RefItemType.class, "select r from RefItemType r", null, em);
			types = new ArrayList<String>();
			for (RefItemType bean : beans) {
				types.add(bean.getItemTypeName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
		return types;
	}

	public static void resetPartyShoppingCarts(Long partyId, String tenantName) {
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = PersistenceManager.getEntityManager(tenantName);
			tx = em.getTransaction();
			tx.begin();
			SdpParty party = SdpPartyHelper.getInstance().searchPartyById(partyId, em);
			for (SdpShoppingCart cart : party.getSdpShoppingCarts()) {
				SdpShoppingCartHelper.getInstance().deleteShoppingCart(cart, em);
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("unable to reset party device");
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}
}
