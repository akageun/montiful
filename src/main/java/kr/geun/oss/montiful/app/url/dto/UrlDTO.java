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
}
