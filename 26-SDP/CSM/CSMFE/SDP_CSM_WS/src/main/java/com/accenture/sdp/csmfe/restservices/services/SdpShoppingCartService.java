/**
 * 
 */
package com.accenture.sdp.csmfe.restservices.services;



import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.accenture.sdp.csm.dto.ComplexServiceResponse;
import com.accenture.sdp.csm.dto.CreateServiceResponse;
import com.accenture.sdp.csm.dto.DataServiceResponse;
import com.accenture.sdp.csm.dto.SearchServiceResponse;
import com.accenture.sdp.csm.dto.responses.SdpShoppingCartResponseDto;
import com.accenture.sdp.csm.managers.SdpShoppingCartManager;
import com.accenture.sdp.csmfe.restservices.request.shoppingcart.AddItemsToShoppingCartRequest;
import com.accenture.sdp.csmfe.restservices.request.shoppingcart.RemoveItemsFromShoppingCartRequest;
import com.accenture.sdp.csmfe.restservices.request.shoppingcart.ShoppingCartChangeStatusRequest;
import com.accenture.sdp.csmfe.restservices.response.shoppingcart.AddItemsToShoppingCartResponse;
import com.accenture.sdp.csmfe.restservices.response.shoppingcart.GetShoppingCartByIdResponse;
import com.accenture.sdp.csmfe.restservices.response.shoppingcart.GetShoppingCartsByPartyIdResponse;
import com.accenture.sdp.csmfe.restservices.response.shoppingcart.RemoveItemsFromShoppingCartResponse;
import com.accenture.sdp.csmfe.restservices.response.shoppingcart.RemoveShoppingCartResponse;
import com.accenture.sdp.csmfe.restservices.response.shoppingcart.ShoppingCartChangeStatusResponse;
import com.accenture.sdp.csmfe.webservices.services.BaseWebService;
import com.accenture.sdp.csmfe.webservices.utils.ShoppingCartConverter;
 
@Path("/shoppingCart")
public class SdpShoppingCartService extends BaseWebService{
 
	
	@POST
	@Path("/addItemsToShoppingCart")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public AddItemsToShoppingCartResponse addItemsToShoppingCart(AddItemsToShoppingCartRequest req) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		AddItemsToShoppingCartResponse wsResp = new AddItemsToShoppingCartResponse();
		try {
			
			CreateServiceResponse resp = SdpShoppingCartManager.getInstance().addItemsToShoppingCart(req.getPartyId(), req.getShoppingCartId(), 
					ShoppingCartConverter.convertShoppingCartItems(req.getItems().getItem()), trim(req.getTenantName()));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setShoppingCartId(resp.getEntityId());
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@PUT
	@Path("/removeItemsFromShoppingCart")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public RemoveItemsFromShoppingCartResponse removeItemsFromShoppingCart(RemoveItemsFromShoppingCartRequest req) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		RemoveItemsFromShoppingCartResponse wsResp = new RemoveItemsFromShoppingCartResponse();
		try {
			
			DataServiceResponse resp = SdpShoppingCartManager.getInstance().removeItemsFromShoppingCart(req.getShoppingCartId(), 
					ShoppingCartConverter.convertRemoveShoppingCartItems(req.getItems().getItem()), trim(req.getTenantName()));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
		
	}
	
	@DELETE
	@Path("/removeShoppingCart")
	@Produces(MediaType.APPLICATION_XML)
	public RemoveShoppingCartResponse removeShoppingCart(@QueryParam("tenantName") String tenantName, @QueryParam("shoppingCartId") Long shoppingCartId) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		RemoveShoppingCartResponse wsResp = new RemoveShoppingCartResponse();
		try {
			
			DataServiceResponse resp = SdpShoppingCartManager.getInstance().removeShoppingCart(shoppingCartId, trim(tenantName));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	
	@GET
	@Path("getShoppingCartsByPartyId")
	@Produces(MediaType.APPLICATION_XML)
	public GetShoppingCartsByPartyIdResponse getShoppingCartsByPartyId(@QueryParam("tenantName") String tenantName, @QueryParam("partyId") Long partyId) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		GetShoppingCartsByPartyIdResponse wsResp = new GetShoppingCartsByPartyIdResponse();
		try {
			
			SearchServiceResponse resp = SdpShoppingCartManager.getInstance().getShoppingCartsByPartyId(partyId, tenantName);
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setShoppingCartList(ShoppingCartConverter.convertGetShoppingCartItems((List<SdpShoppingCartResponseDto>)resp.getSearchResult()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@GET
	@Path("getShoppingCartById")
	@Produces(MediaType.APPLICATION_XML)
	public GetShoppingCartByIdResponse getShoppingCartById(@QueryParam("tenantName") String tenantName, @QueryParam("shoppingCartId") Long shoppingCartId) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		GetShoppingCartByIdResponse wsResp = new GetShoppingCartByIdResponse();
		try {
			ComplexServiceResponse resp = SdpShoppingCartManager.getInstance().getShoppingCartById(shoppingCartId, tenantName);
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
				wsResp.setShoppingCart(ShoppingCartConverter.convertGetShoppingCart((SdpShoppingCartResponseDto) resp.getComplexObject()));
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	@PUT
	@Path("/shoppingCartChangeStatus")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public ShoppingCartChangeStatusResponse shoppingCartChangeStatus(ShoppingCartChangeStatusRequest req) {
		long startTime = System.currentTimeMillis();
		log.logStartMethod(startTime);

		ShoppingCartChangeStatusResponse wsResp = new ShoppingCartChangeStatusResponse();
		try {
			DataServiceResponse resp = SdpShoppingCartManager.getInstance().shoppingCartChangeStatus(req.getShoppingCartId(), trim(req.getStatus()), trim(req.getTenantName()));
			if (resp != null) {
				convertBaseResponse(resp, wsResp);
			}
		} catch (Exception e) {
			log.logError(e);
		}
		log.logEndMethod(startTime, System.currentTimeMillis());
		return wsResp;
	}
	
	
	

 
}
