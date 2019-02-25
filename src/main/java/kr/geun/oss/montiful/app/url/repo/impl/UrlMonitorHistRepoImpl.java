package kr.geun.oss.montiful.app.url.repo.impl;

import com.querydsl.core.types.Projections;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.url.models.QUrlEntity;
import kr.geun.oss.montiful.app.url.models.QUrlMonitorHistEntity;
import kr.geun.oss.montiful.app.url.repo.UrlMonitorHistSupt;
import kr.geun.oss.montiful.core.repo.CmnRepoModule;

import java.util.List;

/**
 *
 *
 * @author akageun
 */
public class UrlMonitorHistRepoImpl extends CmnRepoModule implements UrlMonitorHistSupt {

	@Override
	public List<MonitorDTO.MonitorHistRes> findUrlMonitorHistEntities(Long limit) {

		QUrlMonitorHistEntity qUrlMonitorHistEntity = QUrlMonitorHistEntity.urlMonitorHistEntity;
		QUrlEntity qUrlEntity = QUrlEntity.urlEntity;

		//@formatter:off
        return jpaQueryFactory.select(Projections.fields(MonitorDTO.MonitorHistRes.class,
            qUrlMonitorHistEntity.monitorHistIdx,
            qUrlEntity.urlIdx,
            qUrlEntity.urlName,
            qUrlMonitorHistEntity.preHealthStatusCheckCd,
            qUrlMonitorHistEntity.healthStatusCd,
            qUrlMonitorHistEntity.createdAt
        )).from(qUrlMonitorHistEntity)
            .leftJoin(qUrlEntity)
            .on(qUrlMonitorHistEntity.urlIdx.eq(qUrlEntity.urlIdx))
            .orderBy(qUrlMonitorHistEntity.createdAt.desc())
            .limit(limit)
        .fetch();
        //@formatter:on
	}
}
