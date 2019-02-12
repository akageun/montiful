package kr.geun.oss.montiful.app.system.service;

import kr.geun.oss.montiful.app.system.cd.SysConfCd;
import kr.geun.oss.montiful.app.system.models.SysConfEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * System Configuration Service
 *
 * @author akageun
 */
public interface SysConfService {

	/**
	 * Page
	 *
	 * @param pageable
	 * @return
	 */
	Page<SysConfEntity> page(Pageable pageable);

	Optional<SysConfEntity> get(String confCd);

	SysConfEntity add(SysConfEntity param);

	SysConfEntity modify(SysConfEntity param);

	/**
	 * Get System Config Value
	 *
	 * @param cd
	 * @return
	 */
	String getValue(SysConfCd cd);
}
