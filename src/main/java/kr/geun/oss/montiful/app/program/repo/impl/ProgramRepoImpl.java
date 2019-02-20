package kr.geun.oss.montiful.app.program.repo.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.geun.oss.montiful.app.program.cd.ProgramManageSearchTypeCd;
import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.app.program.models.QProgramEntity;
import kr.geun.oss.montiful.app.program.repo.ProgramRepoSupt;
import kr.geun.oss.montiful.core.repo.CmnRepoModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Program Repository Implements
 *
 * @author akageun
 */
@Slf4j
public class ProgramRepoImpl extends CmnRepoModule implements ProgramRepoSupt {

	@PersistenceContext
	private EntityManager em;

	/**
	 *
	 * @param pageable
	 * @param searchType
	 * @param searchValue
	 * @return
	 */
	@Override
	public Page<ProgramEntity> findPage(Pageable pageable, ProgramManageSearchTypeCd searchType, String searchValue) {
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

		QProgramEntity qProgramEntity = QProgramEntity.programEntity;

		//@formatter:off
		JPAQuery<ProgramEntity> jpaQuery = jpaQueryFactory
			.select(qProgramEntity)
			.from(qProgramEntity)
			.where(booleanLikeSearch(qProgramEntity, searchType, searchValue))
			.orderBy(getOrderBy(qProgramEntity, pageable.getSort()))
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset())
			;
		//@formatter:on
		return new PageImpl<>(jpaQuery.fetch(), pageable, jpaQuery.fetchCount());
	}

}
