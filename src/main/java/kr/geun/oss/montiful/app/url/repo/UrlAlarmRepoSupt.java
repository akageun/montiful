package kr.geun.oss.montiful.app.url.repo;

import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;

import java.util.List;

/**
 *
 *
 * @author akageun
 */
public interface UrlAlarmRepoSupt {

    List<AlarmEntity> findUrlNotificationListByUrlIdx(Long urlIdx);
}
