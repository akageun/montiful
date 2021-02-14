package kr.geun.oss.montiful;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * Entry Class
 *
 * @author akageun
 */
@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		Environment env = app.run(args).getEnvironment();

		// @formatter:off
		String protocol = "http";

		log.info("\n----------------------------------------------------------\n\t" +
			"Access URLs\n\t" +
			"Local: \t\t{}://localhost:{}\n\t" +
			"----------------------------------------------------------",
			protocol,
			env.getRequiredProperty("server.port")
		);

		// @formatter:on
	}

}

