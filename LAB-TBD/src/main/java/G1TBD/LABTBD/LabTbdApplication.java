package G1TBD.LABTBD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "G1TBD.LABTBD.mongo")
public class LabTbdApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabTbdApplication.class, args);
	}

}