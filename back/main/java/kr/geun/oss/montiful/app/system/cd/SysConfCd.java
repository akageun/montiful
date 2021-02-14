package kr.geun.oss.montiful.app.system.cd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.List;

/**
 * System Configuration Code
 *
 * @author akageun
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum SysConfCd {//@formatter:off
	URL_HEALTH_CHECK_RUN_THREAD("URL 상태 체크 Thread 수", "", String.valueOf(3)) {
		private final List<Integer> allowList = Arrays.asList(1, 2, 3, 4, 5);

		@Override
		public boolean valid(String value) {
			if (NumberUtils.isCreatable(value) == false) {
				return false;
			}

			if (allowList.contains(Integer.parseInt(value)) == false) {
				return false;
			}

			return true;
		}
    },
	URL_HEALTH_CHECK_RUN_YN("URL 상태체크 실행여부", "", "Y") {

		@Override
		public boolean valid(String value) {
			return this.strValid(value);
		}
	},
	GLOBAL_ALARM_YN("전체 알람 사용 여부", "Montiful 내에서 발생하는 모든 알림시스템 사용여부를 결정 합니다. 전체알람을 끄고싶으실 경우 N으로 설정해주시기 바랍니다.", "Y") {

		@Override
		public boolean valid(String value) {
			return this.strValid(value);
		}
	},
	MONITOR_HIST_TTL_MINUTE("모니터링 이력 데이터 유지 시간(분)", "Redis 내에 모니터링 이력을 저장합니다."
		+ "<br> 단, Redis Key에 TTL을 최소 10분, 최대 1,440분(1 Day)으로 지정하여 사용합니다.", String.valueOf(60)){

		@Override
		public boolean valid(String value) {
			int current = Integer.parseInt(value);

			int min = 10;
			int max = 1440;

			if (min <= current && current >= max) {
				return true;
			}

			return false;
		}
	}
	//@formatter:on
	;
	private String cdNm;
	private String description;
	private String defaultValue;

	public abstract boolean valid(String value);

	private final List<String> ynAllowList = Arrays.asList("Y", "N");

	protected boolean strValid(String value) {
		if (StringUtils.isBlank(value)) {
			return false;
		}

		if (ynAllowList.contains(value) == false) {
			return false;
		}

		return true;
	}
}

