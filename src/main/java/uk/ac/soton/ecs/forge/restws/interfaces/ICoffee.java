package uk.ac.soton.ecs.forge.restws.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import uk.ac.soton.ecs.forge.restws.representations.OrderInfo;

@Path("/coffee")
public interface ICoffee {
	@POST
	@Consumes("application/xml")
	public Response makeCoffee(OrderInfo oi) throws Exception;
}
