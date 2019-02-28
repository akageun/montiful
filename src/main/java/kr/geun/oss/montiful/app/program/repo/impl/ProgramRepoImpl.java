package kr.geun.oss.montiful.app.program.repo.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import kr.geun.oss.montiful.app.program.cd.ProgramManageSearchTypeCd;
import kr.geun.oss.montiful.app.program.dto.ProgramDTO;
import kr.geun.oss.montiful.app.program.models.QProgramEntity;
import kr.geun.oss.montiful.app.program.repo.ProgramRepoSupt;
import kr.geun.oss.montiful.core.cd.LikeSearchTypeCd;
import kr.geun.oss.montiful.core.repo.CmnRepoModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * Program Repository Implements
 *
 * @author akageun
 */
@Slf4j
public class ProgramRepoImpl extends CmnRepoModule implements ProgramRepoSupt {

	/**
	 *
	 * @param pageable
	 * @param searchType
	 * @param searchValue
	 * @param isSearchMode
	 * @return
	 */
	@Override
	public Page<ProgramDTO.PageRes> findPage(Pageable pageable, ProgramManageSearchTypeCd searchType, String searchValue, boolean isSearchMode) {
		QProgramEntity qProgramEntity = QProgramEntity.programEntity;

		//@formatter:off
		JPAQuery<ProgramDTO.PageRes> jpaQuery = getJpaQueryFactory()
			.select(
				Projections.fields(ProgramDTO.PageRes.class,
					qProgramEntity.programIdx,
					qProgramEntity.programName,
					qProgramEntity.memo,
					qProgramEntity.createdUserId,
					qProgramEntity.createdAt,
					qProgramEntity.updatedUserId,
					qProgramEntity.updatedAt
				)
			)
			.from(qProgramEntity);

		if (isSearchMode) {
			jpaQuery.where(booleanLikeSearch(qProgramEntity, searchType, searchValue, LikeSearchTypeCd.BOTH));
		}

		jpaQuery.orderBy(getOrderBy(qProgramEntity, pageable.getSort()))
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset());
		//@formatter:on

		return new PageImpl<>(jpaQuery.fetch(), pageable, jpaQuery.fetchCount());
	}

}
