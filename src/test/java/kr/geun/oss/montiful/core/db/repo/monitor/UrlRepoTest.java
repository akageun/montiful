package kr.geun.oss.montiful.core.db.repo.monitor;

import kr.geun.oss.montiful.BaseTestCase;
import kr.geun.oss.montiful.app.monitor.type.UrlCheckType;
import kr.geun.oss.montiful.core.db.entity.monitor.UrlEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@Slf4j
@DataJpaTest
class UrlRepoTest extends BaseTestCase {

    @Autowired
    private UrlRepo urlRepo;

    @Test
    public void createUrlTest() {
        //GIVEN
        LocalDateTime ldt = LocalDateTime.now();
        Long userId = 1L;

        UrlEntity urlEntityParam = UrlEntity.builder()
            .url("https://github.com/")
            .memo("github url Memo")
            .checkType(UrlCheckType.STATUS_CHECK)
            .checkJson("{'test':'value'}")
            .use(true)
            .notify(true)
            .createdAt(ldt)
            .createdUserId(userId)
            .updatedAt(ldt)
            .updatedUserId(userId)
            .build();

        //WHEN
        UrlEntity urlEntityResult = urlRepo.save(urlEntityParam);

        //THEN
        Assertions.assertAll(
            () -> Assertions.assertNotNull(urlEntityResult.getId())
            , () -> Assertions.assertEquals(urlEntityParam.getUrl(), urlEntityResult.getUrl())
            , () -> Assertions.assertEquals(urlEntityParam.getMemo(), urlEntityResult.getMemo())
            , () -> Assertions.assertEquals(urlEntityParam.getCheckType(), urlEntityResult.getCheckType())
            , () -> Assertions.assertEquals(urlEntityParam.getCheckJson(), urlEntityResult.getCheckJson())
            , () -> Assertions.assertEquals(urlEntityParam.isUse(), urlEntityResult.isUse())
            , () -> Assertions.assertEquals(urlEntityParam.isNotify(), urlEntityResult.isNotify())
            , () -> Assertions.assertEquals(urlEntityParam.getCreatedAt(), urlEntityResult.getCreatedAt())
            , () -> Assertions.assertEquals(urlEntityParam.getCreatedUserId(), urlEntityResult.getCreatedUserId())
            , () -> Assertions.assertEquals(urlEntityParam.getUpdatedAt(), urlEntityResult.getUpdatedAt())
            , () -> Assertions.assertEquals(urlEntityParam.getUpdatedUserId(), urlEntityResult.getUpdatedUserId())
        );
    }

    @Test
    public void updateUrlTest() {
        //GIVEN
        LocalDateTime ldt = LocalDateTime.now();
        Long userId = 1L;

        UrlEntity urlEntityParam = UrlEntity.builder()
            .url("https://github.com/")
            .memo("github url Memo")
            .checkType(UrlCheckType.STATUS_CHECK)
            .checkJson("{'test':'value'}")
            .createdAt(ldt)
            .createdUserId(userId)
            .updatedAt(ldt)
            .updatedUserId(userId)
            .build();

        //WHEN
        UrlEntity urlEntityResult = urlRepo.save(urlEntityParam);

        UrlEntity urlEntityUpdateParam = UrlEntity.builder()
            .id(urlEntityResult.getId())
            .memo("github url Memo Update")
            .notify(false)
            .updatedAt(LocalDateTime.now())
            .updatedUserId(2L)
            .build();

        UrlEntity urlEntityUpdateResult = urlRepo.save(urlEntityUpdateParam);

        //THEN
        Assertions.assertAll(
            () -> Assertions.assertNotNull(urlEntityUpdateResult.getId())
            , () -> Assertions.assertEquals(urlEntityParam.getUrl(), urlEntityUpdateResult.getUrl())
            , () -> Assertions.assertEquals(urlEntityUpdateParam.getMemo(), urlEntityUpdateResult.getMemo())
            , () -> Assertions.assertEquals(urlEntityParam.getCheckType(), urlEntityUpdateResult.getCheckType())
            , () -> Assertions.assertEquals(urlEntityParam.getCheckJson(), urlEntityUpdateResult.getCheckJson())
            , () -> Assertions.assertEquals(urlEntityParam.isUse(), urlEntityResult.isUse())
            , () -> Assertions.assertEquals(urlEntityParam.isNotify(), urlEntityUpdateResult.isNotify())
            , () -> Assertions.assertEquals(urlEntityParam.getCreatedAt(), urlEntityUpdateResult.getCreatedAt())
            , () -> Assertions.assertEquals(urlEntityParam.getCreatedUserId(), urlEntityUpdateResult.getCreatedUserId())
            , () -> Assertions.assertEquals(urlEntityUpdateParam.getUpdatedAt(), urlEntityUpdateResult.getUpdatedAt())
            , () -> Assertions.assertEquals(urlEntityUpdateParam.getUpdatedUserId(), urlEntityUpdateResult.getUpdatedUserId())
        );
    }
}
