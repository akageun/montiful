package kr.geun.oss.montiful.core.constants;

/**
 * Constants
 *
 * @author akageun
 */
public class Const {

	/**
	 * Redis Constants
	 *
	 */
	public static class Redis {
		public static final String URL_CHECK_ID = "url:check:id";
		public static final String URL_HIST_PREFIX = "url:hist:";

		public static final int MAX_URL_CHECK_CNT = 10;
	}

	/**
	 * Spring bean Name
	 */
	public static class BeanNm {
		public static final String LINE_NOTIFY_BEAN_NAME = "LineNotifyChannelService";
		public static final String EMAIL_SMTP = "EmailSmtpChannelService";
		public static final String SLACK_INCOMING = "SlackIncomingChannelService";
	}
}
