package kr.geun.oss.montiful.app.alarm.channel.lineNotify.service.impl;

import kr.geun.oss.montiful.app.alarm.channel.lineNotify.dto.ChannelLineNotifyDTO;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
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

	@Override
	public void send(MonitorDTO.CheckRes checkRes, AlarmEntity param) {
		try {
			ChannelLineNotifyDTO.AlarmValue value = OM.readValue(param.getAlarmValue(), ChannelLineNotifyDTO.AlarmValue.class);

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
			header.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE); //헤더셋팅
			header.set("Authorization", String.format("Bearer %s", value.getAccessToken())); //헤더셋팅

			MultiValueMap<String, Object> param2 = new LinkedMultiValueMap<>();
			param2.set("message", String.format("%s 소요, %s", checkRes.getResponseTime(), checkRes.getResultMsg()));

			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(param2, header);

			ResponseEntity<LineNotifySendMsg> tt = restTemplate.exchange(LINE_NOTIFY_URL, HttpMethod.POST, request, LineNotifySendMsg.class);

			log.debug("tt : {} ", tt.getStatusCode());
			log.debug("tt : {} ", tt.getBody());
			log.debug("tt : {} ", tt.getBody().message);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	private String getMessage(MonitorDTO.CheckRes checkRes){
		String msg = "URL 상태값이 변경되었습니다.";

		return msg;
	}

	@Builder
	@Getter
	@AllArgsConstructor
	public static class LineNotifySendMsg {
		private int status;
		private String message;
	}
}
