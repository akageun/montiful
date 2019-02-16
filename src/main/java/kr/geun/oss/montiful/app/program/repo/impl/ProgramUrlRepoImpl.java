package kr.geun.oss.montiful.app.program.repo.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.geun.oss.montiful.app.program.models.QProgramUrlEntity;
import kr.geun.oss.montiful.app.program.repo.ProgramUrlRepoSupt;
import kr.geun.oss.montiful.app.url.models.QUrlEntity;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Program-Url Repository Custom Implements
 *
 * @author akageun
 */
@Slf4j
public class ProgramUrlRepoImpl implements ProgramUrlRepoSupt {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Program에 맵핑된 Url 리스트 가져오기
	 *
	 * @param programIdx
	 * @return
	 */
	@Override
	public List<UrlEntity> findByProgramUrlList(Long programIdx) {
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

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
