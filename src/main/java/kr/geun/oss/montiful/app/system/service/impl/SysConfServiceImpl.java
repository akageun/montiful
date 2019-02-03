package kr.geun.oss.montiful.app.system.service.impl;

import kr.geun.oss.montiful.app.system.models.SysConfEntity;
import kr.geun.oss.montiful.app.system.service.SysConfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * System Configuration Service
 *
 * @author akageun
 */
@Slf4j
@Service
public class SysConfServiceImpl implements SysConfService {

    /**
     * Page
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<SysConfEntity> page(Pageable pageable) {
        return null;
    }
}
