package kr.geun.oss.montiful.app.program.service.impl;

import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.app.program.models.ProgramUrlEntity;
import kr.geun.oss.montiful.app.program.repo.ProgramRepo;
import kr.geun.oss.montiful.app.program.repo.ProgramUrlRepo;
import kr.geun.oss.montiful.app.program.service.ProgramService;
import kr.geun.oss.montiful.core.response.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ProgramServiceImpl implements ProgramService {

	@Autowired
	private ProgramRepo programRepo;

	@Autowired
	private ProgramUrlRepo programUrlRepo;

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
	 * GetReq
	 *
	 * @param programIdx
	 * @return
	 */
	@Override
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
	@Override
	public Res valid(String programName, Long programIdx) {
		ProgramEntity existCheck = programRepo.findByProgramName(programName);
		if (existCheck != null) {

			if (programIdx == null || existCheck.getProgramIdx().equals(programIdx) == false) {
				return Res.of(false, "이미 존재하는 프로그램 이름입니다.");
			}
		}

		return Res.of(true, "SUCCESS");
	}

	/**
	 * Add Program
	 *
	 * @param param
	 * @return
	 */
	@Transactional
	@Override
	public ProgramEntity add(ProgramEntity param, List<Long> urlIdxs) {
		ProgramEntity programEntity = programRepo.save(param);

		if (urlIdxs.isEmpty() == false) {
			List<ProgramUrlEntity> programUrlEntities = urlIdxs.stream().map(idx ->
					//@formatter:off
					ProgramUrlEntity.builder()
						.programIdx(programEntity.getProgramIdx())
						.urlIdx(idx)
						.createdUserId(param.getCreatedUserId())
						.build()
					//@formatter:on
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
	@Override
	public ProgramEntity modify(ProgramEntity param, List<Long> urlIdxs) {
		ProgramEntity programEntity = programRepo.save(param);

		programUrlRepo.deleteByProgramIdx(param.getProgramIdx());

		if (urlIdxs.isEmpty() == false) {
			List<ProgramUrlEntity> programUrlEntities = urlIdxs.stream().map(idx ->
					//@formatter:off
					ProgramUrlEntity.builder()
						.programIdx(param.getProgramIdx())
						.urlIdx(idx)
						.createdUserId(param.getUpdatedUserId())
						.build()
					//@formatter:on
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
	@Override
	public List<ProgramEntity> search(String keyword) {
		return programRepo.findByProgramNameStartingWithOrMemoStartsWith(keyword, keyword);
	}
}
