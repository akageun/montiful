package kr.geun.oss.montiful.core.db.repo.monitor;

import kr.geun.oss.montiful.core.db.entity.monitor.UrlEntity;
import kr.geun.oss.montiful.core.db.repo.monitor.supt.UrlSupt;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UrlRepo
 *
 * @author akageun
 * @since 2021-02-14
 */
public interface UrlRepo extends JpaRepository<UrlEntity, Long>, UrlSupt {
}
