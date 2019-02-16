package kr.geun.oss.montiful.app.url.cd;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * @author akageun
 */
@Getter
@AllArgsConstructor
public enum HealthStatusCd {
	//@formatter:off
	HEALTH,
	WARNING,
	ERROR
	//@formatter:on
	;

	public static List<String> getNameList() {
		return Arrays.stream(HealthStatusCd.values()).map(String::valueOf).collect(Collectors.toList());
	}
}
