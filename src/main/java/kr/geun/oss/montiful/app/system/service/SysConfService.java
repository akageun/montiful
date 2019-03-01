package kr.geun.oss.montiful.app.system.service;

import kr.geun.oss.montiful.app.system.cd.SysConfCd;
import kr.geun.oss.montiful.app.system.models.SysConfEntity;
import kr.geun.oss.montiful.app.system.repo.SysConfRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class SysConfService {

	@Autowired
	private SysConfRepo sysConfRepo;

	/**
	 * PageReq
	 *
	 * @param pageable
	 * @return
	 */
	public Page<SysConfEntity> page(Pageable pageable) {
		//Pageable 제거하고 enum Values로 가져오고, list를 가지고 와서 merge하는 형태로 동작

		return sysConfRepo.findAll(pageable);
	}

	public Optional<SysConfEntity> get(String confCd) {
		return sysConfRepo.findById(confCd);
	}

	@Transactional
	public SysConfEntity add(SysConfEntity param) {
		return sysConfRepo.save(param);
	}

	@Transactional
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

	public String getValue(SysConfCd cd) {
		Optional<SysConfEntity> optSysConfEntity = get(cd.name());
		if (optSysConfEntity.isPresent()) {
			return optSysConfEntity.get().getConfValue();
		}

		return cd.getDefaultValue();
	}
}
