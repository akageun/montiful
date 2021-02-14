package kr.geun.oss.montiful.core.db.repo.monitor;

import kr.geun.oss.montiful.BaseTestCase;
import kr.geun.oss.montiful.core.db.entity.monitor.ProgramEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Slf4j
@DataJpaTest
class ProgramRepoTest extends BaseTestCase {

    @Autowired
    private ProgramRepo programRepo;

    @Disabled
    @Test
    public void createProgramTest() {
        //GIVEN
        ProgramEntity programEntity = ProgramEntity.builder()
            .name("Test Program")
            .memo("Test Program Memo")
            .build();

        //WHEN

        //THEN
    }

    @Disabled
    @Test
    public void findOneTest() {

    }
}
