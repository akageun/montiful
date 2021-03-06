package kr.geun.oss.montiful.app.user.repo;

import kr.geun.oss.montiful.app.user.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User Repository
 *
 * @author akageun
 */
public interface UserRepo extends JpaRepository<UserEntity, String>, UserRepoSupt {

}
