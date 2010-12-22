package uk.ac.soton.ecs.forge.restws.services;

import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import uk.ac.soton.ecs.forge.restws.interfaces.ITray;
import uk.ac.soton.ecs.forge.restws.representations.Delivery;
import uk.ac.soton.ecs.forge.restws.representations.TrayResponse;

public class TrayService implements ITray {
	private static ConcurrentHashMap<Integer, TrayResponse> trays = new ConcurrentHashMap<Integer, TrayResponse>();

	@Override
	public Response getTray(int id) throws WebApplicationException {
		if (trays.get(id) != null) {
			Response response = Response.ok(trays.get(id), "application/xml").build();
			trays.remove(id);
			return response;
		}
		return Response.status(404).build();
	}

	@Override
	public Response putDeliveriesOnTray(Delivery delivery) {
		Integer receiptID = delivery.getReceipt().getReceiptID();

		TrayResponse tray = trays.get(receiptID);
		if (tray == null) {
			return Response.serverError().build();
		}

		if (!delivery.getBeverage().isEmpty())
			tray.getBeverage().addAll(delivery.getBeverage());
		if (!delivery.getCoffee().isEmpty())
			tray.getCoffee().addAll(delivery.getCoffee());
		tray.setReceipt(delivery.getReceipt());
		trays.put(receiptID, tray);
		return Response.ok().build();
	}

	@Override
	public Response createTray(int id) {
		TrayResponse tr = new TrayResponse();
		trays.put(id, tr);
		return Response.ok().build();
	}

}
