package kr.geun.oss.montiful.app.url.repo;

import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;

import java.util.List;

/**
 *
 *
 * @author akageun
 */
public interface UrlRepoSupt {

	List<AlarmEntity> findUrlAlarmList(Long urlIdx);

	void updateStatusCheckCdInUrlIdx(String healthStatusCd, List<Long> urlIdxs);

	List<MonitorDTO.CheckReq> findByCheckList();
}
