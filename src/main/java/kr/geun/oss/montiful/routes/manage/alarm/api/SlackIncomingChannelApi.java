package kr.geun.oss.montiful.routes.manage.alarm.api;

import kr.geun.oss.montiful.app.alarm.channel.slackIncoming.dto.ChannelSlackIncomingDTO;
import kr.geun.oss.montiful.app.alarm.common.cd.AlarmChannelCd;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.core.cd.ErrorCd;
import kr.geun.oss.montiful.core.exception.BaseException;
import kr.geun.oss.montiful.core.response.Res;
import kr.geun.oss.montiful.core.utils.CmnUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Alarm Manage
 * - slack incoming
 *
 * @author akageun
 */
@Slf4j
@RestController
@RequestMapping("/manage/alarm/api/v1/slack/incoming")
public class SlackIncomingChannelApi extends AlarmCommonModule {

    /**
     * 추가.
     *
     * @param param
     * @param result
     * @return
     */
    @PostMapping(value = "")
    public ResponseEntity<Res> add(
            @Valid ChannelSlackIncomingDTO.Add param,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new BaseException(ErrorCd.INVALID_PARAMETER);
        }

        try {
            String alarmValue = OM.writeValueAsString(CmnUtils.copyProperties(param, ChannelSlackIncomingDTO.AlarmValue.class));

            AlarmEntity notificationEntity = initAlarm(param.getAlarmName(), AlarmChannelCd.SLACK_INCOMING, alarmValue, param.getMemo());

            alarmService.add(notificationEntity);

            return ResponseEntity.ok(Res.ok());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Res.of(false, "SYSTEM_ERROR"));
        }
    }

    /**
     * 수정
     *
     * @param param
     * @param result
     * @return
     */
    @PutMapping(value = "")
    public ResponseEntity<Res> modify(
            @RequestBody @Valid ChannelSlackIncomingDTO.Modify param,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new BaseException(ErrorCd.INVALID_PARAMETER);
        }

        try {
            String alarmValue = OM.writeValueAsString(CmnUtils.copyProperties(param, ChannelSlackIncomingDTO.AlarmValue.class));

            AlarmEntity notificationEntity = initAlarm(param.getAlarmName(), AlarmChannelCd.SLACK_INCOMING, alarmValue, param.getMemo(), param.getAlarmIdx());
            alarmService.modify(notificationEntity);

            return ResponseEntity.ok(Res.ok());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Res.of(false, "SYSTEM_ERROR"));
        }
    }

}
