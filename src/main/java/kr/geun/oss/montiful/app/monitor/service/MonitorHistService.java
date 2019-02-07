package kr.geun.oss.montiful.app.monitor.service;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author akageun
 */
public interface MonitorHistService {

    void saveMonitorAllHist(Long runTime, List<MonitorDTO.CheckRes> allList);

    List<Object> getUrlHistList();

    List<MonitorDTO.CheckRes> getList();
}
