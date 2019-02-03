package kr.geun.oss.montiful.app.program.repo;

import kr.geun.oss.montiful.app.program.models.ProgramUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 *
 * @author akageun
 */
public interface ProgramUrlRepo extends JpaRepository<ProgramUrlEntity, ProgramUrlEntity.CompositeKey>, ProgramUrlRepoSupt {

	void deleteByProgramIdx(Long programIdx);
}
