package kr.geun.oss.montiful.app.monitor.service;

import kr.geun.oss.montiful.app.monitor.model.Url;
import kr.geun.oss.montiful.core.db.entity.monitor.UrlEntity;
import kr.geun.oss.montiful.core.db.repo.monitor.UrlRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UrlService
 *
 * @author akageun
 * @since 2021-02-14
 */
@Slf4j
@Service
public class UrlService {

    @Autowired
    private UrlRepo urlRepo;

//    public Url retrieveById(Long id) {
//        UrlEntity entity = urlRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found Exception"));
//
//        return
//    }
}
