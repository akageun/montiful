package kr.geun.oss.montiful.app.alarm.common.cd;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Alarm Channel Code
 *
 * @author akageun
 */
@Getter
@AllArgsConstructor
public enum AlarmChannelCd {

	//@formatter:off
	LINE_NOTIFY("Line Notify","LineNotifyChannelService", "fab fa-line"),
	EMAIL_SMTP("Email SMTP","EmailSmtpChannelService", "fas fa-at"),
	SLACK_INCOMING("Slack Incoming","SlackIncomingChannelService", "fab fa-slack"),
	//@formatter:off
	;

	private String cdNm;
	private String beanName;
	private String faIconClasses;

}
