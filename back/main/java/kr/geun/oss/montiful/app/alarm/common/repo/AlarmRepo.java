package kr.geun.oss.montiful.app.alarm.common.repo;

import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 *
 * @author akageun
 */
public interface AlarmRepo extends JpaRepository<AlarmEntity, Long>, AlarmRepoSupt {

	/**
	 * TODO : Query DSL 로 변경해서 limit 걸어야함.
	 *
	 * @param keyword
	 * @return
	 */
	List<AlarmEntity> findByAlarmNameStartingWith(String keyword);

}
