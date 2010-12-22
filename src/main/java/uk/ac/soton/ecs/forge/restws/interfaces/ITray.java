package uk.ac.soton.ecs.forge.restws.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import uk.ac.soton.ecs.forge.restws.representations.Delivery;

@Path("/tray")
public interface ITray {
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public Response getTray(@PathParam("id") int id);
	
	@PUT
	@Path("{id}")
	public Response createTray(@PathParam("id") int id);

	@POST
	@Consumes("application/xml")	
	public Response putDeliveriesOnTray(Delivery delivery);
}
