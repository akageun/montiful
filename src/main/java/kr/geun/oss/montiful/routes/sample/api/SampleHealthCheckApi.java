package kr.geun.oss.montiful.routes.sample.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@RestController
@RequestMapping("/sample/health/check")
public class SampleHealthCheckApi {

	@GetMapping("/api")
	public ResponseEntity<String> getHealthCheck() {
		return ResponseEntity.ok("OK");
	}

	@PostMapping("/api")
	public ResponseEntity<String> postHealthCheck() {

		if (RandomUtils.nextInt(0, 2) == 1) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR");
		}

		//return ResponseEntity.ok("OK");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR");
	}

	@GetMapping("/api2")
	public ResponseEntity<String> getHealthCheck2() {
		try {
			Thread.sleep(RandomUtils.nextInt(10, 500));
		} catch (InterruptedException e) {
			log.info(e.getMessage(), e);
		}

		return ResponseEntity.ok("OK");
	}

	@PostMapping("/api2")
	public ResponseEntity<String> postHealthCheck2() {
		return ResponseEntity.ok("OK");
	}

	@GetMapping("/api3")
	public ResponseEntity<String> getHealthCheck3() {
		try {
			Thread.sleep(RandomUtils.nextInt(10, 1000));
		} catch (InterruptedException e) {
			log.info(e.getMessage(), e);
		}

		return ResponseEntity.ok("OK");
	}

	@PostMapping("/api3")
	public ResponseEntity<String> postHealthCheck3() {
		return ResponseEntity.ok("OK");
	}
}
