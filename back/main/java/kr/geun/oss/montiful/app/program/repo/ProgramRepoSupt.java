package kr.geun.oss.montiful.app.program.repo;

import kr.geun.oss.montiful.app.program.cd.ManageProgramSearchTypeCd;
import kr.geun.oss.montiful.app.program.dto.ProgramDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Program Repository Support
 *
 * @author akageun
 */
public interface ProgramRepoSupt {

    /**
     * @param pageable
     * @param searchType   : UserManageSearchTypeCd
     * @param searchValue
     * @param isSearchMode
     * @return
     */
    Page<ProgramDTO.PageRes> findPage(Pageable pageable, ManageProgramSearchTypeCd searchType, String searchValue, boolean isSearchMode);
}
