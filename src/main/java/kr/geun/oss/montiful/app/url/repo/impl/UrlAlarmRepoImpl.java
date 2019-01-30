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
 *
 *
 * @author akageun
 */
public class UrlAlarmRepoImpl implements UrlAlarmRepoSupt {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<AlarmEntity> findUrlNotificationListByUrlIdx(Long urlIdx) {
        QAlarmEntity qAlarmEntity = QAlarmEntity.alarmEntity;
        QUrlAlarmEntity qUrlNotificationEntity = QUrlAlarmEntity.urlAlarmEntity;

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        return jpaQueryFactory.select(qAlarmEntity).from(qUrlNotificationEntity).leftJoin(qAlarmEntity).on(
            qUrlNotificationEntity.alarmIdx.eq(qAlarmEntity.alarmIdx)).where(qUrlNotificationEntity.urlIdx.eq(urlIdx)).fetch();
    }
}
