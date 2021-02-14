package kr.geun.oss.montiful.app.alarm.channel.slackIncoming.service.impl;

import com.google.common.collect.Lists;
import kr.geun.oss.montiful.app.alarm.channel.slackIncoming.dto.ChannelSlackIncomingDTO;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.service.ChannelServiceModule;
import kr.geun.oss.montiful.app.alarm.common.service.IAlarmChannelService;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.core.constants.Const;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author akageun
 */
@Slf4j
@Service(Const.BeanNm.SLACK_INCOMING)
public class SlackIncomingChannelServiceImpl extends ChannelServiceModule implements IAlarmChannelService {

    @Async
    @Override
    public void asyncSendMsg(MonitorDTO.CheckRes checkRes, String alarmValue) {
        try {
            ChannelSlackIncomingDTO.AlarmValue value = convertAlarmValue(alarmValue, ChannelSlackIncomingDTO.AlarmValue.class);

            SlackMessageAttachement dd = SlackMessageAttachement.builder()
                    .text("안녕하세요.")
                    .build();

            SlackMessage slackMessage = SlackMessage.builder()
                    .channel(value.getChannel())
                    .attachments(Lists.newArrayList(dd))
                    .build();

            //TODO : pool 등 옵션 적용 필요
            new RestTemplate().postForEntity(value.getWebHookUrl(), slackMessage, String.class);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SlackMessageAttachement {
        private String color;
        private String pretext;
        private String title;
        private String title_link;
        private String text;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SlackMessage {
        private String text;
        private String channel;
        private List<SlackMessageAttachement> attachments;

        void addAttachment(SlackMessageAttachement attachement) {
            if (this.attachments == null) {
                this.attachments = Lists.newArrayList();
            }
            this.attachments.add(attachement);
        }
    }

}
