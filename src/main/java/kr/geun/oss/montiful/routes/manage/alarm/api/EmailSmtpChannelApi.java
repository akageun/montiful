package kr.geun.oss.montiful.routes.manage.alarm.api;

import kr.geun.oss.montiful.app.alarm.channel.emailSmtp.dto.ChannelEmailSmtpDTO;
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
@RequestMapping("/manage/alarm/api/v1/email/smtp")
public class EmailSmtpChannelApi extends AlarmCommonModule {

	@PostMapping(value = "")
	public ResponseEntity<Res> add(@Valid ChannelEmailSmtpDTO.Add param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		try {
			AlarmEntity alarmEntity = initNotification(param.getAlarmName(), AlarmChannelCd.EMAIL_SMTP,
				OM.writeValueAsString(CmnUtils.copyProperties(param, ChannelEmailSmtpDTO.AlarmValue.class)), param.getMemo());

			AlarmEntity alarmEntity1 = alarmService.add(alarmEntity);

			return ResponseEntity.ok(Res.of(true, "SUCCESS", alarmEntity1));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Res.of(false, "SYSTEM_ERROR"));
		}

	}

	@PutMapping(value = "")
	public ResponseEntity<Res> modify(@RequestBody @Valid ChannelEmailSmtpDTO.Modify param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		try {
			AlarmEntity AlarmEntity = initNotification(param.getAlarmName(), AlarmChannelCd.EMAIL_SMTP,
				OM.writeValueAsString(CmnUtils.copyProperties(param, ChannelEmailSmtpDTO.AlarmValue.class)), param.getMemo(), param.getAlarmIdx());

			AlarmEntity alarmEntity1 = alarmService.modify(AlarmEntity);

			return ResponseEntity.ok(Res.of(true, "SUCCESS", alarmEntity1));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Res.of(false, "SYSTEM_ERROR"));
		}
	}

}
