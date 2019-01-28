package kr.geun.oss.montiful.app.program.repo;

import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Program Repository
 *
 * @author akageun
 */
public interface ProgramRepo extends JpaRepository<ProgramEntity, Long>, ProgramRepoSupt {
}
