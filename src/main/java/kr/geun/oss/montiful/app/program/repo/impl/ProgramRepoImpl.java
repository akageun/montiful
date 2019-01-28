package kr.geun.oss.montiful.app.program.repo.impl;

import kr.geun.oss.montiful.app.program.repo.ProgramRepoSupt;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Program Repository Implements
 *
 * @author akageun
 */
@Slf4j
public class ProgramRepoImpl implements ProgramRepoSupt {

	@PersistenceContext
	private EntityManager em;

}
