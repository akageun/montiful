package kr.geun.oss.montiful.app.url.repo;

import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;

import java.util.List;

/**
 *
 *
 * @author 김형근
 */
public interface UrlRepoSupt {

	List<AlarmEntity> findUrlAlarmList(Long urlIdx);
}
