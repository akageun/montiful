package kr.geun.oss.montiful.app.program.service;

import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Program Service
 *
 * @author akageun
 */
public interface ProgramService {

    /**
     * Page
     *
     * @param pageable
     * @return
     */
    Page<ProgramEntity> page(Pageable pageable);

    /**
     * Get
     *
     * @param programIdx
     * @return
     */
    Optional<ProgramEntity> get(Long programIdx);
}
