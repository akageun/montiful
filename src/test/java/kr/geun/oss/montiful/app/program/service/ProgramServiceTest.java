package kr.geun.oss.montiful.app.program.service;

import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import kr.geun.oss.montiful.app.program.repo.ProgramRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProgramService.class})
public class ProgramServiceTest {

	@MockBean
	private ProgramRepo programRepo;

	@Autowired
	private ProgramService programService;

	//	@Test
	//	public void pageTest() {
	//
	//	}

	@Ignore
	@Test
	public void getTest() {
		final Long programIdx = 1L;
		ProgramEntity param;

		GIVEN:
		{
			param = ProgramEntity.builder().programName("Test").memo("Memo Text").build();
			given(programRepo.findById(programIdx)).willReturn(Optional.of(param));
		}

		Optional<ProgramEntity> optionalProgramEntity;

		WHEN:
		{
			optionalProgramEntity = programService.get(programIdx);
		}

		THEN:
		{
			Assert.assertTrue(optionalProgramEntity.isPresent());
			Assert.assertEquals(param.getProgramName(), optionalProgramEntity.get().getProgramName());
		}
	}
}
