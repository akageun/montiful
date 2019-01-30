package kr.geun.oss.montiful.app.url.service;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;

import java.util.List;

/**
 *
 *
 * @author akageun
 */
public interface UrlHistService {
    void urlAppendHealthCheckHist(Long runTime, List<MonitorDTO.CheckRes> allList);
}
