package kr.geun.oss.montiful.app.program.service.impl;

import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.app.program.repo.ProgramRepo;
import kr.geun.oss.montiful.app.program.service.ProgramService;
import kr.geun.oss.montiful.core.response.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
	 * @return
	 */
	@Override
	public Res valid(String programName) {
		boolean existCheck = programRepo.existsByProgramName(programName);
		if (existCheck) {
			return Res.of(false, "이미 존재하는 프로그램 이름입니다.");
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
	public ProgramEntity add(ProgramEntity param) {
		return programRepo.save(param);
	}

	/**
	 * Update Program
	 *
	 * @param param
	 * @return
	 */
	@Transactional
	@Override
	public ProgramEntity modify(ProgramEntity param) {
		return programRepo.save(param);
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
