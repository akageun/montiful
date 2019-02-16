package kr.geun.oss.montiful.routes.manage.alarm.api;

import kr.geun.oss.montiful.app.alarm.channel.lineNotify.dto.ChannelLineNotifyDTO;
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
@RequestMapping("/manage/alarm/api/v1/line/notify")
public class LineNotifyChannelApi extends AlarmCommonModule {

	@PostMapping(value = "")
	public ResponseEntity<Res> add(@Valid ChannelLineNotifyDTO.Add param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		try {
			AlarmEntity alarmEntity = initAlarm(param.getAlarmName(), AlarmChannelCd.LINE_NOTIFY,
				OM.writeValueAsString(CmnUtils.copyProperties(param, ChannelLineNotifyDTO.AlarmValue.class)), param.getMemo());

			AlarmEntity alarmEntity1 = alarmService.add(alarmEntity);

			return ResponseEntity.ok(Res.of(true, "SUCCESS", alarmEntity1));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Res.of(false, "SYSTEM_ERROR"));
		}
	}

	@PutMapping(value = "")
	public ResponseEntity<Res> modify(@RequestBody @Valid ChannelLineNotifyDTO.Modify param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		try {
			AlarmEntity AlarmEntity = initAlarm(param.getAlarmName(), AlarmChannelCd.LINE_NOTIFY,
				OM.writeValueAsString(CmnUtils.copyProperties(param, ChannelLineNotifyDTO.AlarmValue.class)), param.getMemo(), param.getAlarmIdx());

			AlarmEntity alarmEntity1 = alarmService.modify(AlarmEntity);

			return ResponseEntity.ok(Res.of(true, "SUCCESS", alarmEntity1));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Res.of(false, "SYSTEM_ERROR"));
		}
	}
}
