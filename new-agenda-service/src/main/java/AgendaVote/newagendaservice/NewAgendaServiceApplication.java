package AgendaVote.newagendaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class NewAgendaServiceApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(NewAgendaServiceApplication.class, args);
	}

}
