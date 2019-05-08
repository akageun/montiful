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

    protected static final ObjectMapper OM = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

    /**
     * init Alarm Entity
     *
     * @param alarmName
     * @param alarmChannelCd
     * @param alarmValue
     * @param memo
     * @return
     */
    protected final AlarmEntity initAlarm(String alarmName, AlarmChannelCd alarmChannelCd, String alarmValue, String memo) {
        String userId = SecUtils.userId();

        AlarmEntity.AlarmEntityBuilder initAlarmBuilder = initAlarmBuilder(alarmName, alarmChannelCd, alarmValue, memo, userId);

        return initAlarmBuilder
                .createdUserId(userId)
                .build();
    }

    /**
     * init Alarm Entity
     *
     * @param alarmName
     * @param alarmChannelCd
     * @param alarmValue
     * @param memo
     * @param alarmIdx
     * @return
     */
    protected final AlarmEntity initAlarm(String alarmName, AlarmChannelCd alarmChannelCd, String alarmValue, String memo, Long alarmIdx) {
        String userId = SecUtils.userId();

        AlarmEntity.AlarmEntityBuilder initAlarmBuilder = initAlarmBuilder(alarmName, alarmChannelCd, alarmValue, memo, userId);

        return initAlarmBuilder.alarmIdx(alarmIdx).build();
    }

    /**
     * init Alarm
     *
     * @param alarmName
     * @param alarmChannelCd
     * @param alarmValue
     * @param memo
     * @param userId
     * @return
     */
    private AlarmEntity.AlarmEntityBuilder initAlarmBuilder(String alarmName, AlarmChannelCd alarmChannelCd, String alarmValue, String memo, String userId) {
        return AlarmEntity.builder()
                .alarmName(alarmName)
                .alarmChannel(alarmChannelCd.name())
                .alarmValue(alarmValue)
                .memo(memo)
                .updatedUserId(userId);
    }

}
