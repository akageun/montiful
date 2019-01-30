package kr.geun.oss.montiful.app.url.repo;

import kr.geun.oss.montiful.app.url.models.UrlAlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 *
 * @author akageun
 */
public interface UrlAlarmRepo extends JpaRepository<UrlAlarmEntity, UrlAlarmEntity.CompositeKey> {
}
