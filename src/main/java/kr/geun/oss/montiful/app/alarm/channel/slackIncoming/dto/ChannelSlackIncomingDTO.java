package kr.geun.oss.montiful.app.alarm.channel.slackIncoming.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author akageun
 */
public class ChannelSlackIncomingDTO {

	@Data
	@NoArgsConstructor
	public static class Add {
		private String alarmName;
		private String webHookUrl;
		private String channel;
		private String memo;
	}

	@Data
	@NoArgsConstructor
	public static class Modify {
		private Long alarmIdx;
		private String alarmName;
		private String webHookUrl;
		private String channel;
		private String memo;
	}

	@Data
	@NoArgsConstructor
	public static class AlarmValue {
		private String webHookUrl;
		private String channel;
	}
}
