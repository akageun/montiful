package kr.geun.oss.montiful.app.program.service;

import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.core.response.Res;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Program Service
 *
 * @author akageun
 */
public interface ProgramService {

	/**
	 * Page
	 *
	 * @param pageable
	 * @return
	 */
	Page<ProgramEntity> page(Pageable pageable);

	/**
	 * GetReq
	 *
	 * @param programIdx
	 * @return
	 */
	Optional<ProgramEntity> get(Long programIdx);

	/**
	 * Validation
	 *
	 * @param programName
	 * @param programIdx
	 * @return
	 */
	Res valid(String programName, Long programIdx);

	/**
	 * Add Program
	 *
	 * @param param
	 * @param urlIdxs
	 * @return
	 */
	ProgramEntity add(ProgramEntity param, List<Long> urlIdxs);

	/**
	 * Update Program
	 *
	 * @param param
	 * @param urlIdxs
	 * @return
	 */
	ProgramEntity modify(ProgramEntity param, List<Long> urlIdxs);

	/**
	 * For Search API
	 *
	 * @param keyword
	 * @return
	 */
	List<ProgramEntity> search(String keyword);

}
