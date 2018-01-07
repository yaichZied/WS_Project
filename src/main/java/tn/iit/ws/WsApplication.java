package tn.iit.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WsApplication {

	public static ConfigurableApplicationContext appConext;

	public static void main(String[] args) {
		appConext = SpringApplication.run(WsApplication.class, args);
	}
}
