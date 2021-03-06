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
	SUCCESS_2XX_CHECK("", "Success Status Code Check"){

		@Override
		public MonitorDTO.CheckRes isOk(MonitorDTO.CheckRes checkRes, ResponseEntity<String> result, String checkValue) {
			if (checkValue != null) {
				throw  new IllegalArgumentException("해당 값은 Null 이여야합니다.");
			}

			if (result.getStatusCode().is2xxSuccessful() == false) {
				checkRes.setHealthAndMsg(HealthStatusCd.ERROR, String.format("%s(%s)로 상태값이 변경되었습니다. OK(2xx)로 변경이 필요합니다.", result.getStatusCode(), result.getStatusCodeValue()));
			}

			return checkRes;
		}
	},
	ONLY_200_CHECK("", "Only 200 Status Code Check"){

		@Override
		public MonitorDTO.CheckRes isOk(MonitorDTO.CheckRes checkRes, ResponseEntity<String> result, String checkValue) {
			if (checkValue != null) {
				throw  new IllegalArgumentException("해당 값은 Null 이여야합니다.");
			}

			if (HttpStatus.OK != result.getStatusCode()) {
				checkRes.setHealthAndMsg(HealthStatusCd.ERROR, String.format("%s(%s)로 상태값이 변경되었습니다. OK(200)로 변경이 필요합니다.", result.getStatusCode(), result.getStatusCodeValue()));
			}

    		return checkRes;
    	}
    },
	SAME_STRING("", "200 Status Check and Same String Check"){

		@Override
		public MonitorDTO.CheckRes isOk(MonitorDTO.CheckRes checkRes, ResponseEntity<String> result, String checkValue) {
			if (checkValue == null) {
				throw new IllegalArgumentException("해당 값은 Null 이 아니여야 합니다.");
			}

			if (StringUtils.equals(result.getBody(), checkValue)) {
				checkRes.setHealthAndMsg(HealthStatusCd.ERROR, String.format("문자열이 다릅니다. 응답결과 : %s", result.getBody()));
			}

			return checkRes;
		}
	},
	//@formatter:on
    ;

    private String cdNm;
    private String description;

    public abstract MonitorDTO.CheckRes isOk(MonitorDTO.CheckRes checkRes, ResponseEntity<String> result, String checkValue);
}
