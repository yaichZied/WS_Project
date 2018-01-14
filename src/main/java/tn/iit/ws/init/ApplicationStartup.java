package tn.iit.ws.init;

import java.util.Set;

import javax.persistence.Entity;

import org.reflections.Reflections;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationStartingEvent> {

	@Override
	public void onApplicationEvent(final ApplicationStartingEvent event) {
		Reflections reflection = new Reflections("tn.iit.ws.init");
		Set<Class<?>> entities = reflection.getTypesAnnotatedWith(Entity.class);
		entities.forEach(entity -> {
			
		});
	}
}
