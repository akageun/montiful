package kr.geun.oss.montiful.app.url.repo;

import kr.geun.oss.montiful.app.url.models.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Url Repository
 *
 * @author akageun
 */
public interface UrlRepo extends JpaRepository<UrlEntity, Long>, UrlRepoSupt {

    /**
     * urlName Like Search
     *
     * @param urlName
     * @return
     */
    List<UrlEntity> findByUrlNameStartingWith(String urlName);
}
