package kr.geun.oss.montiful.app.program.service;

import kr.geun.oss.montiful.app.program.cd.ProgramManageSearchTypeCd;
import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.app.program.models.ProgramUrlEntity;
import kr.geun.oss.montiful.app.program.repo.ProgramRepo;
import kr.geun.oss.montiful.app.program.repo.ProgramUrlRepo;
import kr.geun.oss.montiful.core.response.Res;
import kr.geun.oss.montiful.core.utils.CmnUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	/**
	 * Page
	 *
	 * @param pageable
	 * @return
	 */
	public Page<ProgramEntity> page(Pageable pageable, String searchType, String searchValue) {
		ProgramManageSearchTypeCd searchTypeCd = EnumUtils.getEnum(ProgramManageSearchTypeCd.class, searchType);

		if (CmnUtils.isSearchable(searchTypeCd, searchValue)) {
			return programRepo.findPage(pageable, searchTypeCd, searchValue);
		}

		return programRepo.findAll(pageable);
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
	public Res valid(String programName, Long programIdx) {
		ProgramEntity existCheck = programRepo.findByProgramName(programName);
		if (existCheck != null) {

			if (programIdx == null || existCheck.getProgramIdx().equals(programIdx) == false) {
				return Res.of(false, "이미 존재하는 프로그램 이름입니다.");
			}
		}

		return Res.ok();
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

		if (urlIdxs != null && urlIdxs.isEmpty() == false) {
			List<ProgramUrlEntity> programUrlEntities = urlIdxs.stream().map(idx ->
					//@formatter:off
					ProgramUrlEntity.builder()
						.programIdx(programEntity.getProgramIdx())
						.urlIdx(Long.parseLong(idx))
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
	public ProgramEntity modify(ProgramEntity param, List<String> urlIdxs) {
		ProgramEntity programEntity = programRepo.save(param);

		programUrlRepo.deleteByProgramIdx(param.getProgramIdx());

		if (urlIdxs != null && urlIdxs.isEmpty() == false) {
			List<ProgramUrlEntity> programUrlEntities = urlIdxs.stream().map(idx ->
					//@formatter:off
					ProgramUrlEntity.builder()
						.programIdx(param.getProgramIdx())
						.urlIdx(Long.parseLong(idx))
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
	public List<ProgramEntity> search(String keyword) {
		return programRepo.findByProgramNameStartingWithOrMemoStartsWith(keyword, keyword);
	}
}
