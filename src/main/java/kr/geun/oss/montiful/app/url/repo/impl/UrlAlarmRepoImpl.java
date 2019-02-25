package kr.geun.oss.montiful.app.url.repo.impl;

import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.models.QAlarmEntity;
import kr.geun.oss.montiful.app.url.models.QUrlAlarmEntity;
import kr.geun.oss.montiful.app.url.repo.UrlAlarmRepoSupt;
import kr.geun.oss.montiful.core.repo.CmnRepoModule;

import java.util.List;

/**
 * Url Alarm Custom Repository
 *
 * @author akageun
 */
public class UrlAlarmRepoImpl extends CmnRepoModule implements UrlAlarmRepoSupt {

	/**
	 * URL Alarm List
	 *  - UrlIdx
	 *
	 * @param urlIdx
	 * @return
	 */
	@Override
	public List<AlarmEntity> findUrlAlarmListByUrlIdx(Long urlIdx) {
		QAlarmEntity qAlarmEntity = QAlarmEntity.alarmEntity;
		QUrlAlarmEntity qUrlNotificationEntity = QUrlAlarmEntity.urlAlarmEntity;

		//@formatter:off
        return jpaQueryFactory
            .select(qAlarmEntity)
            .from(qUrlNotificationEntity)
            .leftJoin(qAlarmEntity)
            .on(qUrlNotificationEntity.alarmIdx.eq(qAlarmEntity.alarmIdx))
            .where(qUrlNotificationEntity.urlIdx.eq(urlIdx))
            .fetch();
        //@formatter:on
	}
}
