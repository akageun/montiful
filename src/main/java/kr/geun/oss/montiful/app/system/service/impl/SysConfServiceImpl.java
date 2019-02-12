package kr.geun.oss.montiful.app.system.service.impl;

import kr.geun.oss.montiful.app.system.cd.SysConfCd;
import kr.geun.oss.montiful.app.system.models.SysConfEntity;
import kr.geun.oss.montiful.app.system.repo.SysConfRepo;
import kr.geun.oss.montiful.app.system.service.SysConfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		//Pageable 제거하고 enum Values로 가져오고, list를 가지고 와서 merge하는 형태로 동작

		return sysConfRepo.findAll(pageable);
	}

	@Override
	public Optional<SysConfEntity> get(String confCd) {
		return sysConfRepo.findById(confCd);
	}

	@Transactional
	@Override
	public SysConfEntity add(SysConfEntity param) {
		return sysConfRepo.save(param);
	}

	@Transactional
	@Override
	public SysConfEntity modify(SysConfEntity param) {
		return sysConfRepo.save(param);
	}

	/**
	 * Get System Config Value
	 * TODO : Cache 필요, nocache 메소드도 필요함
	 *
	 * @param cd
	 * @return
	 */
	@Override
	public String getValue(SysConfCd cd) {
		Optional<SysConfEntity> optSysConfEntity = get(cd.name());
		if (optSysConfEntity.isPresent()) {
			return optSysConfEntity.get().getConfValue();
		}

		return cd.getDefaultValue();
	}
}
