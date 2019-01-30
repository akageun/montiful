package kr.geun.oss.montiful.app.url.repo.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.models.QAlarmEntity;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.url.models.QUrlAlarmEntity;
import kr.geun.oss.montiful.app.url.models.QUrlEntity;
import kr.geun.oss.montiful.app.url.repo.UrlRepoSupt;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 *
 *
 * @author akageun
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

	@Transactional
	@Override
	public void updateStatusCheckCdInUrlIdx(String healthStatusCd, List<Long> urlIdxs) {
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
		QUrlEntity qUrlEntity = QUrlEntity.urlEntity;

		//@formatter:off
		jpaQueryFactory.update(qUrlEntity)
			.set(qUrlEntity.healthStatusCd, healthStatusCd)
			.where(qUrlEntity.urlIdx.in(urlIdxs)).execute();
		//@formatter:on
	}

	@Override
	public List<MonitorDTO.CheckReq> findByCheckList() {
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

		QUrlEntity qUrlEntity = QUrlEntity.urlEntity;

		//@formatter:off
        return jpaQueryFactory
            .select(
            	Projections.fields(MonitorDTO.CheckReq.class,
					qUrlEntity.urlIdx,
					qUrlEntity.url,
					qUrlEntity.urlName,
					qUrlEntity.connectionTimeout,
					qUrlEntity.readTimeout,
					qUrlEntity.healthStatusCd,
					qUrlEntity.method,
					qUrlEntity.statusCheckTypeCd,
					qUrlEntity.statusCheckValue
				)
			)
            .from(qUrlEntity)
            .fetch();
        //@formatter:on
	}
}
