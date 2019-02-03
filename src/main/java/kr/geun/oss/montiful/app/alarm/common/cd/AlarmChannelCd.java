package kr.geun.oss.montiful.app.alarm.common.cd;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 *
 * @author akageun
 */
@Getter
@AllArgsConstructor
public enum AlarmChannelCd {

	//@formatter:off
	LINE_NOTIFY("Line Notify","LineNotifyChannelService"),
	EMAIL_SMTP("Email SMTP","EmailSmtpChannelService"),
	SLACK_INCOMING("Slack Incoming","SlackIncomingChannelService"),
	//@formatter:off
	;

	private String cdNm;
	private String beanName;

}
