package uk.ac.soton.ecs.forge.restws;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import uk.ac.soton.ecs.forge.restws.services.BeverageService;
import uk.ac.soton.ecs.forge.restws.services.CoffeeService;
import uk.ac.soton.ecs.forge.restws.services.OrderService;
import uk.ac.soton.ecs.forge.restws.services.TrayService;

public class Alice extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		set.add(OrderService.class);
		set.add(TrayService.class);
		set.add(CoffeeService.class);
		set.add(BeverageService.class);
		return set;
	}
	
}
