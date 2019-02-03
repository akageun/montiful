package kr.geun.oss.montiful.routes.manage.alarm.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.geun.oss.montiful.app.alarm.common.cd.AlarmChannelCd;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.service.AlarmService;
import kr.geun.oss.montiful.core.utils.SecUtils;
import kr.geun.oss.montiful.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Notification Common Module
 *
 * @author akageun
 */
public class AlarmCommonModule extends BaseController {

	@Autowired
	protected AlarmService alarmService;

	//@formatter:off
	protected static final ObjectMapper OM = new ObjectMapper()
		.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
		.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
	//@formatter:on

	/**
	 * init
	 *
	 * @return
	 */
	protected AlarmEntity initNotification(String alarmName, AlarmChannelCd alarmChannelCd, String alarmValue, String memo) {
		return initNotification(alarmName, alarmChannelCd, alarmValue, memo, null);
	}

	protected AlarmEntity initNotification(String alarmName, AlarmChannelCd alarmChannelCd, String alarmValue, String memo, Long alarmIdx) {
		String userId = SecUtils.userId();
		//@formatter:off
        AlarmEntity alarmEntity = AlarmEntity.builder()
                .alarmName(alarmName)
                .alarmChannel(alarmChannelCd.name())
                .alarmValue(alarmValue)
                .memo(memo)
                .updatedUserId(userId)
            .build();
        //@formatter:on

		if (alarmIdx != null) {
			alarmEntity.setAlarmIdx(alarmIdx);
		} else {
			alarmEntity.setCreatedUserId(userId);
		}

		return alarmEntity;
	}
}