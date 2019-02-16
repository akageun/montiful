package kr.geun.oss.montiful.app.url.repo.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.url.models.QUrlEntity;
import kr.geun.oss.montiful.app.url.models.QUrlMonitorHistEntity;
import kr.geun.oss.montiful.app.url.repo.UrlMonitorHistSupt;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 *
 * @author akageun
 */
public class UrlMonitorHistRepoImpl implements UrlMonitorHistSupt {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<MonitorDTO.MonitorHistRes> findUrlMonitorHistEntities(Long limit) {

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

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
