package kr.geun.oss.montiful.app.system.service;

import kr.geun.oss.montiful.app.system.models.SysConfEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
}
