package kr.geun.oss.montiful.app.url.repo;

import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.url.cd.UrlManageSearchTypeCd;
import kr.geun.oss.montiful.app.url.dto.UrlDTO;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Url Repository Custom Interface
 *
 * @author akageun
 */
public interface UrlRepoSupt {

	List<AlarmEntity> findUrlAlarmList(Long urlIdx);

	void updateStatusCheckCdInUrlIdx(String healthStatusCd, List<Long> urlIdxs);

	List<MonitorDTO.CheckReq> findByCheckList();

	/**
	 * Health Status Code Count
	 *  - Group By
	 *
	 * @return
	 */
	List<UrlDTO.StatusCnt> findGroupByStatusCnt();

	Page<UrlEntity> findPage(Pageable pageable, UrlManageSearchTypeCd searchTypeCd, String searchValue);
}
