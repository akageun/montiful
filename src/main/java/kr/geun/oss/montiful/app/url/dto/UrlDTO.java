package kr.geun.oss.montiful.app.url.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

/**
 *
 *
 * @author akageun
 */
public class UrlDTO {

	@Data
	@NoArgsConstructor
	public static class PageReq {
		@Min(0)
		private int pageNumber;
	}

	@Data
	@NoArgsConstructor
	public static class GetReq {

		@Min(0)
		private Long urlIdx;

	}

	@Data
	@NoArgsConstructor
	public static class ModifyPage {
		@Min(0)
		private Long urlIdx;
	}
}
