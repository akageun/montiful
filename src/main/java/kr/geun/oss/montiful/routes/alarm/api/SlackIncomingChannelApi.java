package kr.geun.oss.montiful.routes.alarm.api;

import kr.geun.oss.montiful.app.alarm.channel.slackIncoming.dto.ChannelSlackIncomingDTO;
import kr.geun.oss.montiful.app.alarm.common.cd.AlarmChannelCd;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.core.response.Res;
import kr.geun.oss.montiful.core.utils.CmnUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/alarm/slack/incoming")
public class SlackIncomingChannelApi extends AlarmCommonModule {

	@PostMapping(value = "")
	public ResponseEntity<Res> add(@Valid ChannelSlackIncomingDTO.Add param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		try {
			AlarmEntity notificationEntity = initNotification(param.getAlarmName(), AlarmChannelCd.SLACK_INCOMING,
				OM.writeValueAsString(CmnUtils.copyProperties(param, ChannelSlackIncomingDTO.AlarmValue.class)), param.getMemo());

			AlarmEntity add = alarmService.add(notificationEntity);

			return ResponseEntity.ok(Res.of(true, "SUCCESS", add));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Res.of(false, "SYSTEM_ERROR"));
		}
	}

	@PutMapping(value = "")
	public ResponseEntity<Res> modify(@RequestBody @Valid ChannelSlackIncomingDTO.Modify param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		try {
			AlarmEntity notificationEntity = initNotification(param.getAlarmName(), AlarmChannelCd.SLACK_INCOMING,
				OM.writeValueAsString(CmnUtils.copyProperties(param, ChannelSlackIncomingDTO.AlarmValue.class)), param.getMemo(),
				param.getAlarmIdx());

			AlarmEntity modify = alarmService.modify(notificationEntity);

			return ResponseEntity.ok(Res.of(true, "SUCCESS", modify));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Res.of(false, "SYSTEM_ERROR"));
		}
	}

}
