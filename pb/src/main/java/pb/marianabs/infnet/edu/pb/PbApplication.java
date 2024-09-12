package pb.marianabs.infnet.edu.pb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class PbApplication {
	public static void main(String[] args) {
		SpringApplication.run(PbApplication.class, args);
	}
}
