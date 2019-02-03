package kr.geun.oss.montiful.routes.manage.alarm.api;

import kr.geun.oss.montiful.app.alarm.common.dto.AlarmDTO;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.core.response.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@RestController
@RequestMapping("/manage/alarm/api/v1")
public class AlarmManageApi extends AlarmCommonModule {

	@GetMapping("/{alarmIdx}")
	public ResponseEntity<Res> get(@Valid AlarmDTO.Get param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		Optional<AlarmEntity> notifyEntity = alarmService.get(param.getAlarmIdx());
		//TODO : 방어로직 추가해야함.

		return ResponseEntity.ok().body(Res.of(true, "SUCCESS", notifyEntity.get()));
	}

	@GetMapping("/search")
	public ResponseEntity<Res> get(@Valid AlarmDTO.Search param, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "필수 파라미터가 없습니다."));
		}

		List<AlarmEntity> notifyEntity = alarmService.search(param.getKeyword());
		//TODO : 방어로직 추가해야함.

		return ResponseEntity.ok().body(Res.of(true, "SUCCESS", Optional.ofNullable(notifyEntity).orElseGet(Collections::emptyList)));
	}
}