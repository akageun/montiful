package kr.geun.oss.montiful.app.program.service.impl;

import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.app.program.repo.ProgramRepo;
import kr.geun.oss.montiful.app.program.service.ProgramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Program Service Implements
 *
 * @author akageun
 */
@Slf4j
@Service
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private ProgramRepo programRepo;

    /**
     * Page
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<ProgramEntity> page(Pageable pageable) {
        return programRepo.findAll(pageable);
    }

    /**
     * Get
     *
     * @param programIdx
     * @return
     */
    @Override
    public Optional<ProgramEntity> get(Long programIdx) {
        return programRepo.findById(programIdx);
    }
}
