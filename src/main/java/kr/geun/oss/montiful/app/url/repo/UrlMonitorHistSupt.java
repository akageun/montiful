package kr.geun.oss.montiful.app.url.repo;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;

import java.util.List;

/**
 *
 *
 * @author akageun
 */
public interface UrlMonitorHistSupt {

	List<MonitorDTO.MonitorHistRes> findUrlMonitorHistEntities(Long limit);
}
