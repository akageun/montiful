package kr.geun.oss.montiful.app.url.repo;

import kr.geun.oss.montiful.app.url.models.UrlAlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 *
 * @author 김형근
 */
public interface UrlAlarmRepo extends JpaRepository<UrlAlarmEntity, UrlAlarmEntity.CompositeKey> {
}
