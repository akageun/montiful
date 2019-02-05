package kr.geun.oss.montiful.app.system.service.impl;

import kr.geun.oss.montiful.app.system.models.SysConfEntity;
import kr.geun.oss.montiful.app.system.repo.SysConfRepo;
import kr.geun.oss.montiful.app.system.service.SysConfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * System Configuration Service
 *
 * @author akageun
 */
@Slf4j
@Service
public class SysConfServiceImpl implements SysConfService {

	@Autowired
	private SysConfRepo sysConfRepo;

	/**
	 * Page
	 *
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<SysConfEntity> page(Pageable pageable) {
		return sysConfRepo.findAll(pageable);
	}

	@Override
	public Optional<SysConfEntity> get(String confCd) {
		return sysConfRepo.findById(confCd);
	}

	@Override
	public SysConfEntity modify(SysConfEntity param) {
		return sysConfRepo.save(param);
	}
}
