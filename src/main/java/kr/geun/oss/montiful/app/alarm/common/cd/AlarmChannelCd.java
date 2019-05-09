package kr.geun.oss.montiful.app.alarm.common.cd;

import kr.geun.oss.montiful.core.constants.Const;
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

    LINE_NOTIFY("Line Notify", Const.BeanNm.LINE_NOTIFY, "fab fa-line"),
    EMAIL_SMTP("Email SMTP", Const.BeanNm.EMAIL_SMTP, "fas fa-at"),
    SLACK_INCOMING("Slack Incoming", Const.BeanNm.SLACK_INCOMING, "fab fa-slack"),
    ;

    private String cdNm;
    private String beanName;
    private String faIconClasses;

}
