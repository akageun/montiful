package kr.geun.oss.montiful.routes.manage.alarm.api;

import kr.geun.oss.montiful.app.alarm.common.dto.AlarmDTO;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.core.cd.ErrorCd;
import kr.geun.oss.montiful.core.exception.BaseException;
import kr.geun.oss.montiful.core.response.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Alarm Manage Api Controller
 *
 * @author akageun
 */
@Slf4j
@RestController
@RequestMapping("/manage/alarm/api/v1")
public class ManageAlarmV1Api extends AlarmCommonModule {

    /**
     * 단건조회
     *
     * @param alarmIdx
     * @return
     */
    @GetMapping("/{alarmIdx}")
    public ResponseEntity<Res> get(
            @PathVariable Long alarmIdx
    ) {

        if (alarmIdx == null) {
            throw new BaseException(ErrorCd.INVALID_PARAMETER);
        }

        Optional<AlarmEntity> alarmEntity = alarmService.get(alarmIdx);
        if (alarmEntity.isPresent() == false) {
            log.error("잘못된 조회 입니다. Alarm Idx 가 없습니다. alarmIdx : {}", alarmIdx);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Res.of(false, "없는 Alarm Idx 입니다."));
        }

        return ResponseEntity.ok().body(Res.of(true, "SUCCESS", alarmEntity.get()));
    }

    /**
     * 알람 관리 검색
     *
     * @param param
     * @param result
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<Res> search(
            @Valid AlarmDTO.Search param,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new BaseException(ErrorCd.INVALID_PARAMETER);
        }

        List<AlarmEntity> notifyEntity = alarmService.search(param.getKeyword());

        return ResponseEntity.ok().body(Res.of(true, "SUCCESS", notifyEntity));
    }
}
