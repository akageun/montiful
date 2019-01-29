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
	 * Get
	 *
	 * @param programIdx
	 * @return
	 */
	Optional<ProgramEntity> get(Long programIdx);

	/**
	 * Validation
	 *
	 * @param programName
	 * @return
	 */
	Res valid(String programName);

	/**
	 * Add Program
	 *
	 * @param param
	 * @return
	 */
	ProgramEntity add(ProgramEntity param);

	/**
	 * Update Program
	 *
	 * @param param
	 * @return
	 */
	ProgramEntity modify(ProgramEntity param);

	/**
	 * For Search API
	 *
	 * @param keyword
	 * @return
	 */
	List<ProgramEntity> search(String keyword);

}
