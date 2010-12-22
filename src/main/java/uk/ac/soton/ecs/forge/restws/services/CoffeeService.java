package uk.ac.soton.ecs.forge.restws.services;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

import uk.ac.soton.ecs.forge.restws.interfaces.ICoffee;
import uk.ac.soton.ecs.forge.restws.interfaces.ITray;
import uk.ac.soton.ecs.forge.restws.representations.Delivery;
import uk.ac.soton.ecs.forge.restws.representations.OrderInfo;
import uk.ac.soton.ecs.forge.restws.representations.Receipt;
import uk.ac.soton.ecs.forge.restws.utils.SecurityManager;

public class CoffeeService implements ICoffee {

	public Response makeCoffee(OrderInfo oi) throws Exception {
		Receipt receipt = oi.getReceipt();
		// prepare the coffee(s)
		Delivery d = new Delivery();
		d.setReceipt(receipt);
		d.getCoffee().clear();
		d.getCoffee().addAll(receipt.getCoffees());
		
		// put the coffee(s) on tray
		ITray trayClient = JAXRSClientFactory.create(SecurityManager.getBaseUrl(), ITray.class);
		SecurityManager.setClientAuthentication(trayClient);
		Response tcResp = trayClient.putDeliveriesOnTray(d);
		if (tcResp.getStatus() != 200) {
			return Response.serverError().build();
		}
		
		return Response.ok().build();
	}

}
