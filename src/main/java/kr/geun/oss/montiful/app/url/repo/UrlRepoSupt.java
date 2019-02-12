package kr.geun.oss.montiful.app.url.repo;

import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.url.dto.UrlDTO;

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
}
