package kr.geun.oss.montiful.app.url.service.impl;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.url.service.UrlHistService;
import kr.geun.oss.montiful.core.constants.Const;
import kr.geun.oss.montiful.core.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 *
 * @author akageun
 */
@Slf4j
@Service
public class UrlHistServiceImpl implements UrlHistService {

}
