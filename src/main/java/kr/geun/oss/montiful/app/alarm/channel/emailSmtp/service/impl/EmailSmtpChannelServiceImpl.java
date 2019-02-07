package kr.geun.oss.montiful.app.alarm.channel.emailSmtp.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.geun.oss.montiful.app.alarm.channel.emailSmtp.dto.ChannelEmailSmtpDTO;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.service.AlarmChannelService;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@Service("EmailSmtpChannelService")
public class EmailSmtpChannelServiceImpl implements AlarmChannelService {

	//@formatter:off
	protected static final ObjectMapper OM = new ObjectMapper()
		.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
		.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
	//@formatter:on

	@Override
	public void send(MonitorDTO.CheckRes checkRes, AlarmEntity param) {

		try {
			ChannelEmailSmtpDTO.AlarmValue value = OM.readValue(param.getAlarmValue(), ChannelEmailSmtpDTO.AlarmValue.class);

			Email email = new SimpleEmail();
			email.setSmtpPort(value.getSmtpPort());
			email.setAuthenticator(new DefaultAuthenticator(value.getAuthUserName(), value.getAuthPassword()));
			email.setSSLOnConnect(value.isSsl());
			email.setDebug(false);
			email.setHostName(value.getHostname());
			email.setFrom(value.getFromEmail(), value.getFromName());

			email.setSubject("Hi");
			email.setMsg("This is a test mail ... :-)");
			email.addTo(value.getToEmail());
			email.send();
			log.info("Mail sent!");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
}
