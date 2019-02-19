package kr.geun.oss.montiful.app.alarm.channel.emailSmtp.service.impl;

import kr.geun.oss.montiful.app.alarm.channel.emailSmtp.dto.ChannelEmailSmtpDTO;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.service.ChannelServiceModule;
import kr.geun.oss.montiful.app.alarm.common.service.IAlarmChannelService;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.core.constants.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;

/**
 * Email Smtp Channel Service Implements
 *
 * @author akageun
 */
@Slf4j
@Service(Const.BeanNm.EMAIL_SMTP)
public class EmailSmtpChannelServiceImpl extends ChannelServiceModule implements IAlarmChannelService {

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
