package kr.geun.oss.montiful.app.alarm.channel.lineNotify.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.geun.oss.montiful.app.alarm.channel.lineNotify.dto.ChannelLineNotifyDTO;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.service.IAlarmChannelService;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
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
 *
 *
 * @author akageun
 */
@Slf4j
@Service("LineNotifyChannelService")
public class LineNotificationChannelServiceImplI implements IAlarmChannelService {

	private static final String LINE_NOTIFY_URL = "https://notify-api.line.me/api/notify";

	//@formatter:off
	protected static final ObjectMapper OM = new ObjectMapper()
		.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
		.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
	//@formatter:on

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

			log.info("tt : {} ", tt.getStatusCode());
			log.info("tt : {} ", tt.getBody());
			log.info("tt : {} ", tt.getBody().message);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@Data
	@NoArgsConstructor
	public static class TMP {
		private String accessToken;
	}

	@Data
	@NoArgsConstructor
	public static class LineNotifySendMsg {
		private int status;
		private String message;
	}
}
