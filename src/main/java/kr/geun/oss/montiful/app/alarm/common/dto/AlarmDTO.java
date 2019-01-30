package kr.geun.oss.montiful.app.alarm.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 *
 *
 * @author akageun
 */
public class AlarmDTO {

	@Data
	@NoArgsConstructor
	public static class Page {
		@Min(0)
		private int pageNumber;

//		@NotBlank
//		private String sortName; //Enum을 만들어서 컨버팅해서 사용.
//
//		@NotBlank
//		private String sortType;
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
