package kr.geun.oss.montiful.app.url.repo.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.models.QAlarmEntity;
import kr.geun.oss.montiful.app.url.models.QUrlAlarmEntity;
import kr.geun.oss.montiful.app.url.repo.UrlAlarmRepoSupt;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Url Alarm Custom Repository
 *
 * @author akageun
 */
public class UrlAlarmRepoImpl implements UrlAlarmRepoSupt {

	@PersistenceContext
	private EntityManager em;

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

		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
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
