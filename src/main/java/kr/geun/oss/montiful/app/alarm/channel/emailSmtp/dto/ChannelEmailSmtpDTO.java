package kr.geun.oss.montiful.app.alarm.channel.emailSmtp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author akageun
 */
public class ChannelEmailSmtpDTO {

	/**
	 * ADD
	 */
	@Data
	@NoArgsConstructor
	public static class Add {
		private String alarmName;
		private String memo;

		private String hostname;
		private int smtpPort;
		private boolean ssl;
		private String authUserName;
		private String authPassword;
		private String fromEmail;
		private String fromName;
		private String toEmail;
	}

	@Data
	@NoArgsConstructor
	public static class Modify {
		private Long alarmIdx;
		private String alarmName;
		private String memo;

		private String hostname;
		private int smtpPort;
		private boolean ssl;
		private String authUserName;
		private String authPassword;
		private String fromEmail;
		private String fromName;
		private String toEmail;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AlarmValue {
		private String hostname;
		private int smtpPort;
		private boolean ssl;
		private String authUserName;
		private String authPassword;
		private String fromEmail;
		private String fromName;
		private String toEmail;
	}
}
