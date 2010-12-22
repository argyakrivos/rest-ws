package uk.ac.soton.ecs.forge.restws.services;

import java.util.Properties;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

import uk.ac.soton.ecs.forge.restws.interfaces.IBeverage;
import uk.ac.soton.ecs.forge.restws.interfaces.ICoffee;
import uk.ac.soton.ecs.forge.restws.interfaces.IOrder;
import uk.ac.soton.ecs.forge.restws.interfaces.ITray;
import uk.ac.soton.ecs.forge.restws.representations.Beverage;
import uk.ac.soton.ecs.forge.restws.representations.Coffee;
import uk.ac.soton.ecs.forge.restws.representations.Order;
import uk.ac.soton.ecs.forge.restws.representations.OrderInfo;
import uk.ac.soton.ecs.forge.restws.representations.OrderResponse;
import uk.ac.soton.ecs.forge.restws.representations.Receipt;
import uk.ac.soton.ecs.forge.restws.utils.PropertiesLoader;
import uk.ac.soton.ecs.forge.restws.utils.SecurityManager;

public class OrderService implements IOrder {

	private static int receiptGenID;
	private boolean hasOrdered = false;

	public Response createOrder(Order order) throws Exception {
		double cost = 0.0;
		Properties p = PropertiesLoader
				.getPropertiesFromFile("config.properties");
		OrderResponse or = new OrderResponse();

		if (!order.getCoffee().isEmpty()) {
			for (Coffee c : order.getCoffee()) {
				if (p.getProperty(c.getName()) != null) {
					try {
						Double price = new Double(p.getProperty(c.getName()));
						c.setPrice(price);
						cost += price;
					} catch (NumberFormatException e) {
						return Response.serverError().build();
					}
				} else {
					or.setStatusMessage("We don't serve " + c.getName()
							+ " coffee - we are not Starbucks!");
					return Response.ok(or, "application/xml").build();
				}
			}
			hasOrdered = true;
		}

		if (!order.getBeverage().isEmpty()) {
			for (Beverage b : order.getBeverage()) {
				if (p.getProperty(b.getName()) != null) {
					try {
						Double price = new Double(p.getProperty(b.getName()));
						b.setPrice(price);
						cost += price;
					} catch (NumberFormatException e) {
						return Response.serverError().build();
					}
				} else {
					or.setStatusMessage("We don't serve " + b.getName()
							+ " juice - we are not Starbucks!");
					return Response.ok(or, "application/xml").build();
				}
			}
			hasOrdered = true;
		}

		if (hasOrdered) {
			or.setChange(order.getCash() - cost);

			if (order.getCash() < cost) {
				or.setStatusMessage("There is no thing as a free drink.");
				return Response.ok(or, "application/xml").build();
			}

			// create the receipt
			receiptGenID++;
			Receipt receipt = new Receipt();
			receipt.setReceiptID(receiptGenID);
			receipt.getBeverages().clear();
			receipt.getBeverages().addAll(order.getBeverage());
			receipt.getCoffees().clear();
			receipt.getCoffees().addAll(order.getCoffee());
			receipt.setTotalPrice(cost);

			OrderInfo oi = new OrderInfo();
			oi.setReceipt(receipt);

			// create the tray
			ITray trayClient = JAXRSClientFactory.create(
					SecurityManager.getBaseUrl(), ITray.class);
			SecurityManager.setClientAuthentication(trayClient);
			Response tcResp = trayClient.createTray(oi.getReceipt()
					.getReceiptID());
			if (tcResp.getStatus() != 200) {
				or.setStatusMessage("Internal application error. The Universe has collapsed. Darn!");
				return Response.ok(or, "application/xml").build();
			}

			// pass the receipt to the coffee operator
			if (!oi.getReceipt().getCoffees().isEmpty()) {
				ICoffee coffeeClient = JAXRSClientFactory.create(
						SecurityManager.getBaseUrl(), ICoffee.class);
				SecurityManager.setClientAuthentication(coffeeClient);
				Response ccResp = coffeeClient.makeCoffee(oi);
				if (ccResp.getStatus() != 200) {
					or.setStatusMessage("Sorry but the coffee operator is very busy to process your request!");
					return Response.ok(or, "application/xml").build();
				}
			}

			// pass the receipt to the beverage operator
			if (!oi.getReceipt().getBeverages().isEmpty()) {
				IBeverage beverageClient = JAXRSClientFactory.create(
						SecurityManager.getBaseUrl(), IBeverage.class);
				SecurityManager.setClientAuthentication(beverageClient);
				Response bcResp = beverageClient.makeBeverage(oi);
				if (bcResp.getStatus() != 200) {
					or.setStatusMessage("Sorry but the beverage operator is very busy to process your request!");
					return Response.ok(or, "application/xml").build();
				}
			}

			or.setStatusMessage("Okay");
			or.setUrl("/tray/" + receipt.getReceiptID());
		} else {
			or.setStatusMessage("Next, please!");
		}

		return Response.ok(or, "application/xml").build();
	}

}