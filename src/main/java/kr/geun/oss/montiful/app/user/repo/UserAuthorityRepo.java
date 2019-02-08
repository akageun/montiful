package kr.geun.oss.montiful.app.user.repo;

import kr.geun.oss.montiful.app.user.models.UserAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User Authority Repository
 *
 * @author akageun
 */
public interface UserAuthorityRepo extends JpaRepository<UserAuthorityEntity, UserAuthorityEntity.CompositeKey> {

	/**
	 * 유저 아이디로 권한 리스트 받아오기
	 *
	 * @param userId
	 * @return
	 */
	List<UserAuthorityEntity> findByUserId(String userId);
}
