package kr.geun.oss.montiful.app.user.security.service;

import kr.geun.oss.montiful.app.user.models.UserAuthorityEntity;
import kr.geun.oss.montiful.app.user.models.UserEntity;
import kr.geun.oss.montiful.app.user.repo.UserAuthorityRepo;
import kr.geun.oss.montiful.app.user.repo.UserRepo;
import kr.geun.oss.montiful.core.utils.SecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Spring Security Detail Service
 *
 * @author akageun
 */
public class SimpleDetailSecurityService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserAuthorityRepo userAuthorityRepo;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Optional<UserEntity> optUserInfo = userRepo.findById(userId);
		if (optUserInfo.isPresent() == false) {
			throw new UsernameNotFoundException("Not Found User");
		}

		List<UserAuthorityEntity> authList = userAuthorityRepo.findByUserId(userId);
		if (authList.isEmpty()) {
			throw new UsernameNotFoundException("계정 내 권한이 없습니다.");
		}

		UserEntity userEntity = optUserInfo.get();
		//@formatter:off
		List<GrantedAuthority> grantedAuthorities = SecUtils.mapToGrantedAuthorities(
			authList
				.stream()
				.map(UserAuthorityEntity::getAuthorityCd)
				.collect(Collectors.toList())
		);
		//@formatter:on

		return new User(userEntity.getUserId(), userEntity.getPassWd(), grantedAuthorities);
	}
}