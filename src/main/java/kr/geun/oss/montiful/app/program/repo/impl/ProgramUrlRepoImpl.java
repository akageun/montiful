package kr.geun.oss.montiful.app.program.repo.impl;

import kr.geun.oss.montiful.app.program.models.QProgramUrlEntity;
import kr.geun.oss.montiful.app.program.repo.ProgramUrlRepoSupt;
import kr.geun.oss.montiful.app.url.models.QUrlEntity;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import kr.geun.oss.montiful.core.repo.CmnRepoModule;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Program-Url Repository Custom Implements
 *
 * @author akageun
 */
@Slf4j
public class ProgramUrlRepoImpl extends CmnRepoModule implements ProgramUrlRepoSupt {

	/**
	 * Program에 맵핑된 Url 리스트 가져오기
	 *
	 * @param programIdx
	 * @return
	 */
	@Override
	public List<UrlEntity> findByProgramUrlList(Long programIdx) {
		QProgramUrlEntity qProgramUrlEntity = QProgramUrlEntity.programUrlEntity;
		QUrlEntity qUrlEntity = QUrlEntity.urlEntity;

		//@formatter:off
        return jpaQueryFactory
            .select(qUrlEntity)
            .from(qProgramUrlEntity)
            .leftJoin(qUrlEntity)
            .on(qProgramUrlEntity.urlIdx.eq(qUrlEntity.urlIdx))
            .where(qProgramUrlEntity.programIdx.eq(programIdx))
				.orderBy(qProgramUrlEntity.createdAt.desc())
			.fetch();
        //@formatter:on
	}
}
