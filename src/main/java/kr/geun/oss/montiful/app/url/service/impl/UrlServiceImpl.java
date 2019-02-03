package kr.geun.oss.montiful.app.url.service.impl;

import com.google.common.base.Stopwatch;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.program.repo.ProgramUrlRepo;
import kr.geun.oss.montiful.app.url.cd.HealthStatusCd;
import kr.geun.oss.montiful.app.url.cd.StatusCheckTypeCd;
import kr.geun.oss.montiful.app.url.dto.UrlDTO;
import kr.geun.oss.montiful.app.url.models.UrlAlarmEntity;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import kr.geun.oss.montiful.app.url.repo.UrlAlarmRepo;
import kr.geun.oss.montiful.app.url.repo.UrlRepo;
import kr.geun.oss.montiful.app.url.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * URL Service Implements
 *
 * @author akageun
 */
@Slf4j
@Service
public class UrlServiceImpl implements UrlService {

	@Autowired
	private UrlRepo urlRepo;

	@Autowired
	private ProgramUrlRepo programUrlRepo;

	@Autowired
	private UrlAlarmRepo urlAlarmRepo;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Page
	 *
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<UrlEntity> page(Pageable pageable) {
		return urlRepo.findAll(pageable);
	}

	/**
	 * GetReq
	 *
	 * @param urlIdx
	 * @return
	 */
	@Override
	public Optional<UrlEntity> get(Long urlIdx) {
		return urlRepo.findById(urlIdx);
	}

	/**
	 * Add
	 *
	 * @param param
	 * @param alarmIdxs
	 */
	@Transactional
	@Override
	public UrlEntity add(UrlEntity param, List<Long> alarmIdxs) {

		UrlEntity urlEntity = urlRepo.save(param);
		if (alarmIdxs != null && alarmIdxs.isEmpty() == false) {
			List<UrlAlarmEntity> programUrlEntities = alarmIdxs.stream().map(idx ->
					//@formatter:off
					UrlAlarmEntity.builder()
						.alarmIdx(idx)
						.urlIdx(param.getUrlIdx())
						.createdUserId(param.getUpdatedUserId())
						.build()
					//@formatter:on
			).collect(Collectors.toList());

			urlAlarmRepo.saveAll(programUrlEntities);
		}

		return urlEntity;
	}

	/**
	 * modify
	 *
	 * @param param
	 * @param alarmIdxs
	 */
	@Transactional
	@Override
	public UrlEntity modify(UrlEntity param, List<Long> alarmIdxs) {

		UrlEntity urlEntity = urlRepo.save(param);

		urlAlarmRepo.deleteByUrlIdx(param.getUrlIdx());

		if (alarmIdxs != null && alarmIdxs.isEmpty() == false) {
			List<UrlAlarmEntity> programUrlEntities = alarmIdxs.stream().map(idx ->
					//@formatter:off
					UrlAlarmEntity.builder()
						.alarmIdx(idx)
						.urlIdx(param.getUrlIdx())
						.createdUserId(param.getUpdatedUserId())
						.build()
					//@formatter:on
			).collect(Collectors.toList());

			urlAlarmRepo.saveAll(programUrlEntities);
		}

		return urlEntity;
	}

	/**
	 * Url Name Search
	 * TODO : Cache
	 *
	 * @param keyword
	 * @return
	 */
	@Override
	public List<UrlEntity> urlNameSearch(String keyword) {
		return urlRepo.findByUrlNameStartingWith(keyword);
	}

	/**
	 * Url Program
	 *
	 * @param programIdx
	 * @return
	 */
	@Override
	public List<UrlEntity> urlProgramList(Long programIdx) {
		return programUrlRepo.findByProgramUrlList(programIdx);
	}

	/**
	 * Url Mapping Alarm List
	 *
	 * @param urlIdx
	 * @return
	 */
	@Override
	public List<AlarmEntity> urlAlarmList(Long urlIdx) {
		return urlRepo.findUrlAlarmList(urlIdx);
	}

	/**
	 * Health Check
	 *
	 * @param param
	 * @return
	 */
	@Override
	public Optional<MonitorDTO.CheckRes> healthCheck(MonitorDTO.CheckReq param) {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(param.getConnectionTimeout()); //Connect timeout
		clientHttpRequestFactory.setReadTimeout(param.getReadTimeout()); //Read timeout

		restTemplate.setRequestFactory(clientHttpRequestFactory);

		Stopwatch stopwatch = Stopwatch.createStarted();
		MonitorDTO.CheckRes checkRes = MonitorDTO.CheckRes.builder().healthStatusCd(HealthStatusCd.HEALTH).resultMsg("SUCCESS").build();

		try {
			StatusCheckTypeCd statusCheckTypeCd = EnumUtils.getEnum(StatusCheckTypeCd.class, param.getStatusCheckTypeCd());
			if (statusCheckTypeCd == null) {
				throw new IllegalArgumentException("Not Found StatusCheck Type");
			}

			HttpMethod method = HttpMethod.resolve(param.getMethod());
			if (method == null) {
				throw new IllegalArgumentException("Not Supported Method!!");
			}

			ResponseEntity<String> result = restTemplate.exchange(param.getUrl(), method, new HttpEntity<>(new LinkedMultiValueMap<>()),
				String.class);

			checkRes = statusCheckTypeCd.isOk(checkRes, result, param.getStatusCheckValue());

		} catch (IllegalArgumentException e1) { //잘못 등록된 값
			checkRes.setHealthStatusCd(HealthStatusCd.WARNING);
			checkRes.setResultMsg(String.format("설정값이 잘못되었습니다. %s", e1.getMessage()));
		} catch (HttpClientErrorException e2) {
			checkRes.setHealthStatusCd(HealthStatusCd.WARNING);
			checkRes.setResultMsg(String.format("페이지가 잘못되었습니다. (%s) %s", e2.getStatusCode(), e2.getMessage()));

		} catch (HttpServerErrorException ee) {
			if (ee.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				checkRes.setHealthStatusCd(HealthStatusCd.ERROR);
				checkRes.setResultMsg(String.format("500 에러가 발생했습니다. %s", ee.getMessage()));

			} else {
				log.error("error : {}, {}", ee.getMessage(), ee);
			}
		} catch (RestClientException e) {
			checkRes.setHealthStatusCd(HealthStatusCd.ERROR);

			if (e.getRootCause() instanceof SocketTimeoutException) {
				checkRes.setResultMsg(String.format("SocketTimeout 이 발생했습니다. %s", e.getMessage()));

			} else if (e.getRootCause() instanceof ConnectTimeoutException) {
				checkRes.setResultMsg(String.format("ConnectTimeout 이 발생했습니다. %s", e.getMessage()));

			} else {
				log.error("error : {}, {}", e.getMessage(), e);
			}

		} catch (Exception e) {
			checkRes.setHealthStatusCd(HealthStatusCd.ERROR);
			checkRes.setResultMsg(String.format("System Error가 발생했습니다. : %s", e.getMessage()));
		} finally {
			checkRes.setResponseTime(stopwatch.elapsed(TimeUnit.MILLISECONDS));
		}

		return Optional.ofNullable(checkRes);
	}

	@Override
	public List<MonitorDTO.CheckReq> getHealthCheckTargetList() {
		return urlRepo.findByCheckList();
	}

	/**
	 * Status Value Modify
	 *
	 * @param list
	 */
	@Override
	public void modifyHealthStatusCheck(List<MonitorDTO.CheckRes> list) {
		if (list.isEmpty()) {
			return;
		}

		//Map<String, List<UrlEntity>> groupByResult = list.stream().collect(Collectors.groupingBy(UrlEntity::getHealthStatusCd));
		//@formatter:off
		list.stream()
			.collect(Collectors.groupingBy(MonitorDTO.CheckRes::getHealthStatusCd))
			.forEach((key, values) ->
				urlRepo.updateStatusCheckCdInUrlIdx(key.name(), values.stream().map(MonitorDTO.CheckRes::getUrlIdx).collect(Collectors.toList()))
			);
		//@formatter:on
	}

	@Override
	public List<UrlDTO.StatusCnt> getStatusCntForDashboard() {
		List<UrlDTO.StatusCnt> rtnList = new ArrayList<>();
		List<UrlDTO.StatusCnt> ttt = urlRepo.findGroupByStatusCntForDashboard();

		List<String> codeList = HealthStatusCd.getList();
		for (String code : codeList) {

			UrlDTO.StatusCnt tmpStatusCnt = null;
			for (UrlDTO.StatusCnt statusCnt : ttt) {
				if (StringUtils.equals(code, statusCnt.getHealthStatusCd())) {
					tmpStatusCnt = statusCnt;
				}
			}

			if (tmpStatusCnt == null) {
				tmpStatusCnt = UrlDTO.StatusCnt.builder().healthStatusCd(code).urlCnt(0L).build();
			}

			rtnList.add(tmpStatusCnt);

		}

		return rtnList;
	}

}
