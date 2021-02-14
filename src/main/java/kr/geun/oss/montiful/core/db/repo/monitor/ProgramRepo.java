package kr.geun.oss.montiful.core.db.repo.monitor;

import kr.geun.oss.montiful.core.db.entity.monitor.ProgramEntity;
import kr.geun.oss.montiful.core.db.repo.monitor.supt.ProgramSupt;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ProgramRepo
 *
 * @author akageun
 * @since 2021-02-14
 */
public interface ProgramRepo extends JpaRepository<ProgramEntity, Long>, ProgramSupt {
}
