package kr.geun.oss.montiful.app.url.repo.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.models.QAlarmEntity;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.url.cd.UrlManageSearchTypeCd;
import kr.geun.oss.montiful.app.url.dto.UrlDTO;
import kr.geun.oss.montiful.app.url.models.QUrlAlarmEntity;
import kr.geun.oss.montiful.app.url.models.QUrlEntity;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import kr.geun.oss.montiful.app.url.repo.UrlRepoSupt;
import kr.geun.oss.montiful.core.cd.LikeSearchTypeCd;
import kr.geun.oss.montiful.core.repo.CmnRepoModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Url Repository Custom Implements
 *
 * @author akageun
 */
public class UrlRepoImpl extends CmnRepoModule implements UrlRepoSupt {

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

	/**
	 * Health Status Code Count
	 *  - Group By
	 *
	 * @return
	 */
	@Override
	public List<UrlDTO.StatusCnt> findGroupByStatusCnt() {
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

		QUrlEntity qUrlEntity = QUrlEntity.urlEntity;

		NumberPath<Long> aliasQuantity = Expressions.numberPath(Long.class, "urlCnt");

		//@formatter:off
        return jpaQueryFactory
            .select(
            	Projections.fields(UrlDTO.StatusCnt.class,
					qUrlEntity.healthStatusCd,
					qUrlEntity.urlIdx.count().as(aliasQuantity)
				)
			)
            .from(qUrlEntity)
			.groupBy(qUrlEntity.healthStatusCd)
			.orderBy(aliasQuantity.desc())
            .fetch();
        //@formatter:on
	}

	@Override
	public Page<UrlEntity> findPage(Pageable pageable, UrlManageSearchTypeCd searchTypeCd, String searchValue) {
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

		QUrlEntity qUrlEntity = QUrlEntity.urlEntity;

		//@formatter:off
		JPAQuery<UrlEntity> jpaQuery = jpaQueryFactory
			.select(qUrlEntity)
			.from(qUrlEntity)
			.where(booleanLikeSearch(qUrlEntity, searchTypeCd, searchValue, LikeSearchTypeCd.BOTH))
			.orderBy(getOrderBy(qUrlEntity, pageable.getSort()))
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset())
			;
		//@formatter:on

		return new PageImpl<>(jpaQuery.fetch(), pageable, jpaQuery.fetchCount());
	}
}
