package kr.geun.oss.montiful.app.program.repo;

import kr.geun.oss.montiful.app.url.models.UrlEntity;

import java.util.List;

/**
 * Program-Url Repository Custom Interface
 *
 * @author akageun
 */
public interface ProgramUrlRepoSupt {

	/**
	 * Program에 맵핑된 Url 리스트 가져오기
	 *
	 * @param programIdx
	 * @return
	 */
	List<UrlEntity> findByProgramUrlList(Long programIdx);
}
