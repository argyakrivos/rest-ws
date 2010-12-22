package uk.ac.soton.ecs.forge.restws.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import uk.ac.soton.ecs.forge.restws.representations.Order;

@Path("/order")
public interface IOrder {
	@POST
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response createOrder(Order order) throws WebApplicationException, Exception;
}
