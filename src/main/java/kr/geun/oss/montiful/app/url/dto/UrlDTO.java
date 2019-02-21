package kr.geun.oss.montiful.app.url.dto;

import kr.geun.oss.montiful.core.dto.CmnPageModule;
import lombok.*;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * URL DTO
 *
 * @author akageun
 */
public class UrlDTO {

	@Getter
	@Builder
	@AllArgsConstructor
	public static class PageReq  extends CmnPageModule {
		private String st; //SearchType
		private String sv; //SearchValue TODO : Create Search Custom Valid Annotation(Length...)
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

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class StatusCnt {

		private Long urlCnt;
		private String healthStatusCd;
	}
}
