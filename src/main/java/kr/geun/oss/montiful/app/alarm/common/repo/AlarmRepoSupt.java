package kr.geun.oss.montiful.app.alarm.common.repo;

import kr.geun.oss.montiful.app.alarm.common.cd.AlarmManageSearchTypeCd;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 *
 * @author akageun
 */
public interface AlarmRepoSupt {
	Page<AlarmEntity> findPage(Pageable pageable, AlarmManageSearchTypeCd searchTypeCd, String searchValue);
}
