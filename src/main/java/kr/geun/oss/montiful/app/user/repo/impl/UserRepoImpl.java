package kr.geun.oss.montiful.app.user.repo.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.geun.oss.montiful.app.user.cd.UserManageSearchTypeCd;
import kr.geun.oss.montiful.app.user.models.QUserEntity;
import kr.geun.oss.montiful.app.user.models.UserEntity;
import kr.geun.oss.montiful.app.user.repo.UserRepoSupt;
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
public class UserRepoImpl extends CmnRepoModule implements UserRepoSupt {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<UserEntity> findPage(Pageable pageable, UserManageSearchTypeCd searchType, String searchValue, boolean isSearchMode) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        QUserEntity qUserEntity = QUserEntity.userEntity;

        //@formatter:off
		JPAQuery<UserEntity> jpaQuery = jpaQueryFactory.select(qUserEntity).from(qUserEntity);

		if (isSearchMode) {
			jpaQuery.where(booleanLikeSearch(qUserEntity, searchType, searchValue, LikeSearchTypeCd.BOTH));
		}

		jpaQuery
            .orderBy(getOrderBy(qUserEntity, pageable.getSort()))
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset());
		//@formatter:on

        return new PageImpl<>(jpaQuery.fetch(), pageable, jpaQuery.fetchCount());
    }
}
