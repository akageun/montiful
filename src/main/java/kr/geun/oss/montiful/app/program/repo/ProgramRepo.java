package kr.geun.oss.montiful.app.program.repo;

import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Program Repository
 *
 * @author akageun
 */
public interface ProgramRepo extends JpaRepository<ProgramEntity, Long>, ProgramRepoSupt {

	/**
	 * Program Name exist check
	 *
	 * @param programName
	 * @return
	 */
	boolean existsByProgramName(String programName);

	/**
	 * Search
	 *
	 * @param programName
	 * @param memo
	 * @return
	 */
	List<ProgramEntity> findByProgramNameStartingWithOrMemoStartsWith(String programName, String memo);
}
