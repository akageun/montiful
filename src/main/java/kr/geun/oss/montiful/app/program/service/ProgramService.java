package kr.geun.oss.montiful.app.program.service;

import kr.geun.oss.montiful.app.program.cd.ManageProgramSearchTypeCd;
import kr.geun.oss.montiful.app.program.dto.ProgramDTO;
import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.app.program.models.ProgramUrlEntity;
import kr.geun.oss.montiful.app.program.repo.ProgramRepo;
import kr.geun.oss.montiful.app.program.repo.ProgramUrlRepo;
import kr.geun.oss.montiful.core.cd.ErrorCd;
import kr.geun.oss.montiful.core.exception.BaseException;
import kr.geun.oss.montiful.core.utils.CmnUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Program Service Implements
 *
 * @author akageun
 */
@Slf4j
@Service
public class ProgramService {

    @Autowired
    private ProgramRepo programRepo;

    @Autowired
    private ProgramUrlRepo programUrlRepo;

    public Page<ProgramDTO.PageRes> page(Pageable pageable) {
        return page(pageable, null, null);
    }

    /**
     * Page
     *
     * @param pageable
     * @return
     */
    public Page<ProgramDTO.PageRes> page(Pageable pageable, String searchType, String searchValue) {
        ManageProgramSearchTypeCd searchTypeCd = EnumUtils.getEnum(ManageProgramSearchTypeCd.class, searchType);

        boolean isSearchMode = false;
        if (CmnUtils.isSearchable(searchTypeCd, searchValue)) {
            isSearchMode = true;

        }

        return programRepo.findPage(pageable, searchTypeCd, searchValue, isSearchMode);
    }

    /**
     * Get Req
     *
     * @param programIdx
     * @return
     */
    public Optional<ProgramEntity> get(Long programIdx) {
        return programRepo.findById(programIdx);
    }

    /**
     * Validation
     *
     * @param programName
     * @param programIdx
     * @return
     */
    public void valid(String programName, Long programIdx) throws BaseException {
        ProgramEntity existCheck = programRepo.findByProgramName(programName);
        if (existCheck != null) {

            //TODO : TC 작성 필요
            if (programIdx == null || existCheck.getProgramIdx().equals(programIdx) == false) {
                throw new BaseException(ErrorCd.NOT_VALID_REQUEST, "이미 존재하는 프로그램 이름입니다.");
            }
        }

    }

    /**
     * Add Program
     *
     * @param param
     * @return
     */
    @Transactional
    public ProgramEntity add(ProgramEntity param, List<String> urlIdxs) {
        ProgramEntity programEntity = programRepo.save(param);


        if (CollectionUtils.isEmpty(urlIdxs) == false) {
            List<ProgramUrlEntity> programUrlEntities = urlIdxs.stream()
                    .map(idx ->
                            ProgramUrlEntity.builder()
                                    .programIdx(programEntity.getProgramIdx())
                                    .urlIdx(Long.parseLong(idx))
                                    .createdUserId(param.getCreatedUserId())
                                    .build()
                    ).collect(Collectors.toList());

            programUrlRepo.saveAll(programUrlEntities);
        }

        return programEntity;
    }

    /**
     * Update Program
     *
     * @param param
     * @return
     */
    @Transactional
    public ProgramEntity modify(ProgramEntity param, List<String> urlIdxs) {
        ProgramEntity programEntity = programRepo.save(param);

        programUrlRepo.deleteByProgramIdx(param.getProgramIdx());

        if (CollectionUtils.isEmpty(urlIdxs) == false) {
            List<ProgramUrlEntity> programUrlEntities = urlIdxs.stream()
                    .map(idx ->
                            ProgramUrlEntity.builder()
                                    .programIdx(param.getProgramIdx())
                                    .urlIdx(Long.parseLong(idx))
                                    .createdUserId(param.getUpdatedUserId())
                                    .build()
                    ).collect(Collectors.toList());

            programUrlRepo.saveAll(programUrlEntities);
        }

        return programEntity;
    }

    /**
     * For Search API
     * TODO : Cache 추가해야함.
     *
     * @param keyword
     * @return
     */
    public List<ProgramEntity> search(String keyword) {
        List<ProgramEntity> programEntities = programRepo.findByProgramNameStartingWithOrMemoStartsWith(keyword, keyword);
        if (CollectionUtils.isEmpty(programEntities)) {
            return Collections.emptyList();
        }

        return programEntities;
    }
}
