package kr.geun.oss.montiful.app.monitor.service;

/**
 *
 *
 * @author akageun
 */
public interface AsyncMonitorService {

    void asyncMonitorCheck(Long runTime, String redisKey);
}
