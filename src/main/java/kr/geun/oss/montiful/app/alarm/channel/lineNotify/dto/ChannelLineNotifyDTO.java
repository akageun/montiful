package kr.geun.oss.montiful.app.alarm.channel.lineNotify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author akageun
 */
public class ChannelLineNotifyDTO {

	@Data
	@NoArgsConstructor
	public static class Add {
		private String alarmName;
		private String accessToken;
		private String memo;
	}

	@Data
	@NoArgsConstructor
	public static class Modify {
		private Long alarmIdx;
		private String alarmName;
		private String accessToken;
		private String memo;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AlarmValue {
		private String accessToken;
	}
}
