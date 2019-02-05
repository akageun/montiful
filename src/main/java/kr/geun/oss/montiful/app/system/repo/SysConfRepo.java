package kr.geun.oss.montiful.app.system.repo;

import kr.geun.oss.montiful.app.system.models.SysConfEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 *
 * @author akageun
 */
public interface SysConfRepo extends JpaRepository<SysConfEntity, String> {
}
