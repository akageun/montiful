package kr.geun.oss.montiful.routes.monitor.program.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Slf4j
public class MonitorProgramWebTest {

	@Test
	public void average() {
		List<String> list = new ArrayList<>();

		list.add("Hello");
		list.add("Hello");
		list.add("Hello");
		list.add("World");
		list.add("World");
		list.add("World2");
		list.add("World3");
		list.add("World4");

		int totalCnt = list.size();

		Map<String, Long> counted = list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		counted.forEach((key, value) -> {
			double t = (double) value / (double) totalCnt;
			log.info("t : {},{}, {}, {}, {}", key, totalCnt, value, t, String.format("%.2f", t * 100.0));
		});

		log.info("counted : {}", counted);
	}

}
