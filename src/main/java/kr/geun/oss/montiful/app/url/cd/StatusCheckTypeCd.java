package kr.geun.oss.montiful.app.url.cd;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Status Check Type Code Enum
 *
 * @author akageun
 */
@Getter
@AllArgsConstructor
public enum StatusCheckTypeCd {//@formatter:off
	ONLY_200_CHK("", ""){

		@Override
		public MonitorDTO.CheckRes isOk(MonitorDTO.CheckRes checkRes, ResponseEntity<String> result, String checkValue) {
			if (checkValue != null) {
				throw  new IllegalArgumentException("해당 값은 Null 이여야합니다.");
			}

			if (HttpStatus.OK != result.getStatusCode()) {
				checkRes.setHealthStatusCd(HealthStatusCd.ERROR);
				checkRes.setResultMsg(String.format("%s(%s)로 상태값이 변경되었습니다. OK(200)로 변경이 필요합니다.", result.getStatusCode(), result.getStatusCodeValue()));
			}

    		return checkRes;
    	}
    },
	SUCCESS_2XX_CHK("", ""){

		@Override
		public MonitorDTO.CheckRes isOk(MonitorDTO.CheckRes checkRes, ResponseEntity<String> result, String checkValue) {
			if (checkValue != null) {
				throw  new IllegalArgumentException("해당 값은 Null 이여야합니다.");
			}

			if (result.getStatusCode().is2xxSuccessful() == false) {
				checkRes.setHealthStatusCd(HealthStatusCd.ERROR);
				checkRes.setResultMsg(String.format("%s(%s)로 상태값이 변경되었습니다. OK(2xx)로 변경이 필요합니다.", result.getStatusCode(), result.getStatusCodeValue()));
			}

			return checkRes;
		}
	},
	SAME_TEXT("", ""){

		@Override
		public MonitorDTO.CheckRes isOk(MonitorDTO.CheckRes checkRes, ResponseEntity<String> result, String checkValue) {
			if (checkValue == null) {
				throw  new IllegalArgumentException("해당 값은 Null 이여야합니다.");
			}

			if (StringUtils.equals(result.getBody(), checkValue)) {
				checkRes.setHealthStatusCd(HealthStatusCd.ERROR);
				checkRes.setResultMsg(String.format("문자열이 다릅니다. 응답결과 : %s", result.getBody()));
			}

			return checkRes;
		}
	},
	//@formatter:on
	;

	private String cdNm;
	private String description;

	public abstract MonitorDTO.CheckRes isOk(MonitorDTO.CheckRes checkRes, ResponseEntity<String> result, String checkValue);}
