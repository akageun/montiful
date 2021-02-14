package kr.geun.oss.montiful.app.alarm.common.service;

import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;

/**
 * @author akageun
 */
public interface IAlarmChannelService {

    void asyncSendMsg(MonitorDTO.CheckRes checkRes, String alarmValue);
}
