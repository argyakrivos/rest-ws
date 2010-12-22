package uk.ac.soton.ecs.forge.restws.services;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

import uk.ac.soton.ecs.forge.restws.interfaces.IBeverage;
import uk.ac.soton.ecs.forge.restws.interfaces.ITray;
import uk.ac.soton.ecs.forge.restws.representations.Delivery;
import uk.ac.soton.ecs.forge.restws.representations.OrderInfo;
import uk.ac.soton.ecs.forge.restws.representations.Receipt;
import uk.ac.soton.ecs.forge.restws.utils.SecurityManager;

public class BeverageService implements IBeverage {

	public Response makeBeverage(OrderInfo oi) throws Exception {
		Receipt receipt = oi.getReceipt();
		// prepare the beverage(s)
		Delivery d = new Delivery();
		d.setReceipt(receipt);
		d.getBeverage().clear();
		d.getBeverage().addAll(receipt.getBeverages());
		
		// put the beverage(s) on tray
		ITray trayClient = JAXRSClientFactory.create(SecurityManager.getBaseUrl(), ITray.class);
		SecurityManager.setClientAuthentication(trayClient);
		Response tcResp = trayClient.putDeliveriesOnTray(d);
		if (tcResp.getStatus() != 200) {
			return Response.serverError().build();
		}
		
		return Response.ok().build();
	}

}
