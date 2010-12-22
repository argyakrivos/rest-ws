package uk.ac.soton.ecs.forge.restws.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import uk.ac.soton.ecs.forge.restws.representations.OrderInfo;

@Path("/beverage")
public interface IBeverage {
	@POST
	@Consumes("application/xml")
	public Response makeBeverage(OrderInfo oi) throws Exception;
}
