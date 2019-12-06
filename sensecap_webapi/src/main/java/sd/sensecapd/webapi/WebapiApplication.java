package sd.sensecapd.webapi;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("sd.sensecapd.webapi.dao")
public class WebapiApplication {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	* If you have any questions about this project code, please send an email to
	* weitong.zhu@seeed.cc
	* thank you
	* */
	public static void main(String[] args) {

		SpringApplication.run(WebapiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
