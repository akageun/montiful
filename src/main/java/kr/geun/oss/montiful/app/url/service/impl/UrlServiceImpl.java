package kr.geun.oss.montiful.app.url.service.impl;

import kr.geun.oss.montiful.app.program.repo.ProgramUrlRepo;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import kr.geun.oss.montiful.app.url.repo.UrlRepo;
import kr.geun.oss.montiful.app.url.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * URL Service Implements
 *
 * @author akageun
 */
@Slf4j
@Service
public class UrlServiceImpl implements UrlService {

	@Autowired
	private UrlRepo urlRepo;

	@Autowired
	private ProgramUrlRepo programUrlRepo;

	/**
	 * Page
	 *
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<UrlEntity> page(Pageable pageable) {
		return urlRepo.findAll(pageable);
	}

	/**
	 * Get
	 *
	 * @param urlIdx
	 * @return
	 */
	@Override
	public Optional<UrlEntity> get(Long urlIdx) {
		return urlRepo.findById(urlIdx);
	}

	/**
	 * Add
	 *
	 * @param param
	 * @param alarmIdxs
	 */
	@Transactional
	@Override
	public UrlEntity add(UrlEntity param, List<Long> alarmIdxs) {

		return null;
	}

	/**
	 * modify
	 *
	 * @param param
	 * @param alarmIdxs
	 */
	@Transactional
	@Override
	public UrlEntity modify(UrlEntity param, List<Long> alarmIdxs) {

		return null;
	}

	/**
	 * Url Name Search
	 * TODO : Cache
	 *
	 * @param keyword
	 * @return
	 */
	@Override
	public List<UrlEntity> urlNameSearch(String keyword) {
		return urlRepo.findByUrlNameStartingWith(keyword);
	}

	/**
	 * Program Mapping URL List
	 *
	 * @param programIdx
	 * @return
	 */
	@Override
	public List<UrlEntity> programUrlList(Long programIdx) {
		return programUrlRepo.findByProgramUrlList(programIdx);
	}
}
