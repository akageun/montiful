package kr.geun.oss.montiful.core.constants;

/**
 *
 *
 * @author akageun
 */
public class Const {

	public static class Redis {
		public static final String REDIS_TEMPLATE_BEAN_NM = "redisTemplate";
		public static final String URL_CHECK_ID = "url:check:id";
		public static final String URL_HIST_PREFIX = "url:hist:";

	}

	public static class BeanNm {
		public static final String LINE_NOTIFY_BEAN_NAME = "LineNotifyChannelService";
		public static final String EMAIL_SMTP = "EmailSmtpChannelService";
		public static final String SLACK_INCOMING = "SlackIncomingChannelService";
	}
}