package kr.geun.oss.montiful.app.url.repo;

import kr.geun.oss.montiful.app.url.models.UrlMonitorHistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 *
 * @author akageun
 */
public interface UrlMonitorHistRepo extends JpaRepository<UrlMonitorHistEntity, Long>, UrlMonitorHistSupt {
}
