package kr.geun.oss.montiful.app.alarm.common.dto;

import kr.geun.oss.montiful.core.dto.CmnPageModule;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 *
 *
 * @author akageun
 */
public class AlarmDTO {

	@Getter
	@Builder
	@AllArgsConstructor
	public static class PageReq  extends CmnPageModule {
		private String st; //SearchType
		private String sv; //SearchValue TODO : Create Search Custom Valid Annotation(Length...)
	}

	@Data
	@NoArgsConstructor
	public static class Get {
		private Long alarmIdx;

	}

	@Data
	@NoArgsConstructor
	public static class Search {
		private String keyword;
	}
}
