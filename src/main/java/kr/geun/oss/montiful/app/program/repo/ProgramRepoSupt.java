package kr.geun.oss.montiful.app.program.repo;

import kr.geun.oss.montiful.app.program.cd.ProgramManageSearchTypeCd;
import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Program Repository Support
 *
 * @author akageun
 */
public interface ProgramRepoSupt {

	/**
	 *
	 * @param pageable
	 * @param searchType : ProgramManageSearchTypeCd
	 * @param searchValue
	 * @return
	 */
	Page<ProgramEntity> findPage(Pageable pageable, ProgramManageSearchTypeCd searchType, String searchValue);
}
