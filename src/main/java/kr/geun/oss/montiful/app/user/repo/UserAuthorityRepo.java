package kr.geun.oss.montiful.app.user.repo;

import kr.geun.oss.montiful.app.user.models.UserAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 *
 * @author 김형근
 */
public interface UserAuthorityRepo extends JpaRepository<UserAuthorityEntity, UserAuthorityEntity.CompositeKey> {

	List<UserAuthorityEntity> findByUserId(String userId);
}