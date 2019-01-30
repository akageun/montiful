package kr.geun.oss.montiful.app.url.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.List;

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

	@Data
	@NoArgsConstructor
	public static class Add {
		private String urlName;
		private String url;
		private String memo;
		private int connectionTimeout;
		private int readTimeout;
		private String method;
		private String statusCheckTypeCd;
		private String statusCheckValue;
		//private boolean notify;

		private List<Long> alarmIdxs;
	}

	@Data
	@NoArgsConstructor
	public static class Modify {

		@Min(0)
		private Long urlIdx;
		private String urlName;
		private String url;
		private String memo;
		private int connectionTimeout;
		private int readTimeout;
		private String healthStatusCd;
		private String method;
		private String statusCheckTypeCd;
		private String statusCheckValue;

		private List<Long> alarmIdxs;
	}
}
