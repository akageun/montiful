package kr.geun.oss.montiful.app.alarm.channel.slackIncoming.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import kr.geun.oss.montiful.app.alarm.channel.slackIncoming.dto.ChannelSlackIncomingDTO;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.service.AlarmChannelService;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 *
 *
 * @author 김형근
 */
@Slf4j
@Service("SlackIncomingChannelService")
public class SlackIncomingChannelServiceImpl implements AlarmChannelService {

	//@formatter:off
	protected static final ObjectMapper OM = new ObjectMapper()
		.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
		.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
	//@formatter:on

	@Override
	public void send(MonitorDTO.CheckRes checkRes, AlarmEntity param) {
		try {
			ChannelSlackIncomingDTO.AlarmValue value = OM.readValue(param.getAlarmValue(), ChannelSlackIncomingDTO.AlarmValue.class);

			SlackMessageAttachement dd = SlackMessageAttachement.builder().text("안녕하세요.").build();

			SlackMessage slackMessage = SlackMessage.builder().channel(value.getChannel()).attachments(Lists.newArrayList(dd)).build();
			new RestTemplate().postForEntity(value.getWebHookUrl(), slackMessage, String.class);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public enum SlackTarget { // TODO webHookUrl 은 자신의 슬랙 IncomingWebHookAPI로 변경하세요.
		CH_INCOMING("", "random");
		String webHookUrl;
		String channel;

		SlackTarget(String webHookUrl, String channel) {
			this.webHookUrl = webHookUrl;
			this.channel = channel;
		}
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class SlackMessageAttachement {
		private String color;
		private String pretext;
		private String title;
		private String title_link;
		private String text;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class SlackMessage {
		private String text;
		private String channel;
		private List<SlackMessageAttachement> attachments;

		void addAttachment(SlackMessageAttachement attachement) {
			if (this.attachments == null) {
				this.attachments = Lists.newArrayList();
			}
			this.attachments.add(attachement);
		}
	}

}
