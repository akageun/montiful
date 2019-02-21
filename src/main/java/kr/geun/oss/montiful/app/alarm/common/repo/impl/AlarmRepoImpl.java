package kr.geun.oss.montiful.app.alarm.common.repo.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.geun.oss.montiful.app.alarm.common.cd.AlarmManageSearchTypeCd;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.models.QAlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.repo.AlarmRepoSupt;
import kr.geun.oss.montiful.core.cd.LikeSearchTypeCd;
import kr.geun.oss.montiful.core.repo.CmnRepoModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 *
 * @author akageun
 */
public class AlarmRepoImpl extends CmnRepoModule implements AlarmRepoSupt {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<AlarmEntity> findPage(Pageable pageable, AlarmManageSearchTypeCd searchType, String searchValue) {
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

		QAlarmEntity qAlarmEntity = QAlarmEntity.alarmEntity;

		//@formatter:off
		JPAQuery<AlarmEntity> jpaQuery = jpaQueryFactory
			.select(qAlarmEntity)
			.from(qAlarmEntity)
			.where(booleanLikeSearch(qAlarmEntity, searchType, searchValue, LikeSearchTypeCd.BOTH))
			.orderBy(getOrderBy(qAlarmEntity, pageable.getSort()))
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset())
			;
		//@formatter:on

		return new PageImpl<>(jpaQuery.fetch(), pageable, jpaQuery.fetchCount());
	}
}
