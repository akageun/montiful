package kr.geun.oss.montiful.app.program.repo;

import kr.geun.oss.montiful.app.program.models.ProgramEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProgramRepoTest {

	@Autowired
	private ProgramRepo programRepo;

	@Test
	public void addTest() {
		//@formatter:off
		ProgramEntity dbParam = ProgramEntity.builder()
				.programName("Test Program Name")
				.memo("Test Memo")
				.createdUserId("akageun")
				.updatedUserId("akageun")
			.build();
		//@formatter:on

		ProgramEntity dbInfo = programRepo.save(dbParam);

		Assert.assertNotNull(dbInfo);
		Assert.assertEquals(dbInfo.getProgramName(), dbParam.getProgramName());
	}

}
