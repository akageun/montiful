package kr.geun.oss.montiful.app.user.repo;

import kr.geun.oss.montiful.app.user.cd.UserManageSearchTypeCd;
import kr.geun.oss.montiful.app.user.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 *
 * @author akageun
 */
public interface UserRepoSupt {

    Page<UserEntity> findPage(Pageable pageable, UserManageSearchTypeCd searchType, String searchValue, boolean isSearchMode);
}
