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
public enum SysConfCd {
	//@formatter:off
	HEALTH_CHECK_RUN_THREAD("","") {
		private final List<Integer> allowList = Arrays.asList(1,2,3,4,5);

		@Override
		public boolean valid(String value) {
			if (NumberUtils.isCreatable(value) == false) {
				return false;
			}

			if(allowList.contains(Integer.parseInt(value)) == false){
				return false;
			}

			return true;
		}
    },
	HEALTH_CHECK_RUN_YN("","") {

		@Override
		public boolean valid(String value) {
			return this.strValid(value);
		}
	},
	GLOBAL_NOTIFY("","") {

		@Override
		public boolean valid(String value) {
			return this.strValid(value);
		}
	},
	//@formatter:on
	;
	private String cdNm;
	private String description;

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

