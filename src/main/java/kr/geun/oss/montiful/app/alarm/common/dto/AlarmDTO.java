package kr.geun.oss.montiful.app.alarm.common.dto;

import kr.geun.oss.montiful.core.dto.CmnPageModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 *
 *
 * @author akageun
 */
public class AlarmDTO {

	@Getter
	@Builder
	@AllArgsConstructor
	public static class PageReq extends CmnPageModule {
		private String st; //SearchType
		private String sv; //SearchValue TODO : Create Search Custom Valid Annotation(Length...)
	}

	@Getter
	@AllArgsConstructor
	public static class Get {
		private Long alarmIdx;

	}

	@Getter
	@AllArgsConstructor
	public static class Search {
		private String keyword;
	}
}
