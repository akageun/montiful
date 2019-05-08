package kr.geun.oss.montiful.app.alarm.channel.lineNotify.service.impl;

import kr.geun.oss.montiful.app.alarm.channel.lineNotify.dto.ChannelLineNotifyDTO;
import kr.geun.oss.montiful.app.alarm.common.service.ChannelServiceModule;
import kr.geun.oss.montiful.app.alarm.common.service.IAlarmChannelService;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.core.constants.Const;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Line Notify Channel
 * - https://notify-bot.line.me/en/
 *
 * @author akageun
 */
@Slf4j
@Service(Const.BeanNm.LINE_NOTIFY)
public class LineNotificationChannelServiceImpl extends ChannelServiceModule implements IAlarmChannelService {

    private static final String LINE_NOTIFY_URL = "https://notify-api.line.me/api/notify";

    @Async
    @Override
    public void asyncSendMsg(MonitorDTO.CheckRes checkRes, String alarmValue) {

        try {
            ChannelLineNotifyDTO.AlarmValue value = convertAlarmValue(alarmValue, ChannelLineNotifyDTO.AlarmValue.class);

            //TODO : Bean으로 만들거나, Pool, connectionTimeout등 설정해야함
            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
            header.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE); //헤더셋팅
            header.set("Authorization", String.format("Bearer %s", value.getAccessToken())); //헤더셋팅

            MultiValueMap<String, Object> sendParam = new LinkedMultiValueMap<>();
            sendParam.set("message", getMessage(checkRes));

            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(sendParam, header);

            ResponseEntity<LineNotifySendMsgRes> tt = restTemplate.exchange(LINE_NOTIFY_URL, HttpMethod.POST, request, LineNotifySendMsgRes.class);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    private String getMessage(MonitorDTO.CheckRes checkRes) {
        StringBuffer sb = new StringBuffer("URL 상태값이 변경되었습니다.");
        sb.append(String.format("(%s -> %s)", checkRes.getPreHealthStatusCheckCd().name(), checkRes.getHealthStatusCd().name()));
        sb.append(String.format("URL Name : %s", checkRes.getUrlName()));
        sb.append(String.format("URL  바로가기. "));
        sb.append(String.format("%s 소요, %s", checkRes.getResponseTime(), checkRes.getResultMsg()));

        return sb.toString();
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class LineNotifySendMsgRes {
        private int status;
        private String message;
    }
}
