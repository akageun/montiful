package kr.geun.oss.montiful.app.alarm.common.repo.impl;

import com.querydsl.jpa.impl.JPAQuery;
import kr.geun.oss.montiful.app.alarm.common.cd.AlarmManageSearchTypeCd;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.models.QAlarmEntity;
import kr.geun.oss.montiful.app.alarm.common.repo.AlarmRepoSupt;
import kr.geun.oss.montiful.core.cd.LikeSearchTypeCd;
import kr.geun.oss.montiful.core.repo.CmnRepoModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * Alarm Repository Implements
 *
 * @author akageun
 */
public class AlarmRepoImpl extends CmnRepoModule implements AlarmRepoSupt {

    @Override
    public Page<AlarmEntity> findPage(Pageable pageable, AlarmManageSearchTypeCd searchType, String searchValue) {
        QAlarmEntity qAlarmEntity = QAlarmEntity.alarmEntity;

        JPAQuery<AlarmEntity> jpaQuery = getJpaQueryFactory()
                .select(qAlarmEntity)
                .from(qAlarmEntity)
                .where(booleanLikeSearch(qAlarmEntity, searchType, searchValue, LikeSearchTypeCd.BOTH))
                .orderBy(getOrderBy(qAlarmEntity, pageable.getSort()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        return new PageImpl<>(jpaQuery.fetch(), pageable, jpaQuery.fetchCount());
    }
}
