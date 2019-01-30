package kr.geun.oss.montiful.app.url.repo.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.models.QAlarmEntity;
import kr.geun.oss.montiful.app.program.models.QProgramUrlEntity;
import kr.geun.oss.montiful.app.url.models.QUrlAlarmEntity;
import kr.geun.oss.montiful.app.url.models.QUrlEntity;
import kr.geun.oss.montiful.app.url.repo.UrlRepoSupt;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 *
 * @author 김형근
 */
public class UrlRepoImpl implements UrlRepoSupt {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<AlarmEntity> findUrlAlarmList(Long urlIdx) {
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

		QAlarmEntity qAlarmEntity = QAlarmEntity.alarmEntity;
		QUrlAlarmEntity qUrlAlarmEntity = QUrlAlarmEntity.urlAlarmEntity;

		//@formatter:off
        return jpaQueryFactory
            .select(qAlarmEntity)
            .from(qUrlAlarmEntity)
            .leftJoin(qAlarmEntity)
            .on(qUrlAlarmEntity.alarmIdx.eq(qAlarmEntity.alarmIdx))
            .where(qUrlAlarmEntity.urlIdx.eq(urlIdx)).fetch();
        //@formatter:on
	}
}
