package kr.geun.oss.montiful.app.program.repo;

import kr.geun.oss.montiful.app.url.models.UrlEntity;

import java.util.List;

/**
 *
 *
 * @author akageun
 */
public interface ProgramUrlRepoSupt {

	List<UrlEntity> findByProgramUrlList(Long programIdx);
}
