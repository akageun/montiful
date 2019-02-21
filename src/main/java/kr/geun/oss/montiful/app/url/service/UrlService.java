package kr.geun.oss.montiful.app.url.service;

import com.google.common.base.Stopwatch;
import kr.geun.oss.montiful.app.alarm.common.models.AlarmEntity;
import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import kr.geun.oss.montiful.app.program.repo.ProgramUrlRepo;
import kr.geun.oss.montiful.app.url.cd.HealthStatusCd;
import kr.geun.oss.montiful.app.url.cd.StatusCheckTypeCd;
import kr.geun.oss.montiful.app.url.cd.UrlManageSearchTypeCd;
import kr.geun.oss.montiful.app.url.dto.UrlDTO;
import kr.geun.oss.montiful.app.url.models.UrlAlarmEntity;
import kr.geun.oss.montiful.app.url.models.UrlEntity;
import kr.geun.oss.montiful.app.url.models.UrlMonitorHistEntity;
import kr.geun.oss.montiful.app.url.repo.UrlAlarmRepo;
import kr.geun.oss.montiful.app.url.repo.UrlMonitorHistRepo;
import kr.geun.oss.montiful.app.url.repo.UrlRepo;
import kr.geun.oss.montiful.core.utils.CmnUtils;
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
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * URL Service Implements
 *
 * @author akageun
 */
@Slf4j
@Service
public class UrlService {

	@Autowired
	private UrlRepo urlRepo;

	@Autowired
	private ProgramUrlRepo programUrlRepo;

	@Autowired
	private UrlAlarmRepo urlAlarmRepo;

	@Autowired
	private UrlMonitorHistRepo urlMonitorHistRepo;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Page
	 *
	 * @param pageable
	 * @return
	 */
	public Page<UrlEntity> page(Pageable pageable, String searchType, String searchValue) {

		UrlManageSearchTypeCd searchTypeCd = EnumUtils.getEnum(UrlManageSearchTypeCd.class, searchType);

		if (CmnUtils.isSearchable(searchTypeCd, searchValue)) {
			return urlRepo.findPage(pageable, searchTypeCd, searchValue);
		}

		return urlRepo.findAll(pageable);
	}

	/**
	 * GetReq
	 *
	 * @param urlIdx
	 * @return
	 */
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
	public UrlEntity add(UrlEntity param, List<Long> alarmIdxs) {

		UrlEntity urlEntity = urlRepo.save(param);

		saveAllAlarmIdxs(param.getUrlIdx(), param.getUpdatedUserId(), alarmIdxs);

		return urlEntity;
	}

	/**
	 * modify
	 *
	 * @param param
	 * @param alarmIdxs
	 */
	@Transactional
	public UrlEntity modify(UrlEntity param, List<Long> alarmIdxs) {

		UrlEntity urlEntity = urlRepo.save(param);

		urlAlarmRepo.deleteByUrlIdx(param.getUrlIdx());

		saveAllAlarmIdxs(param.getUrlIdx(), param.getUpdatedUserId(), alarmIdxs);

		return urlEntity;
	}

	private void saveAllAlarmIdxs(Long urlIdx, String updatedUserId, List<Long> alarmIdxs) {
		if (alarmIdxs != null && alarmIdxs.isEmpty() == false) {
			List<UrlAlarmEntity> programUrlEntities = alarmIdxs.stream().map(idx ->
					//@formatter:off
					UrlAlarmEntity.builder()
						.alarmIdx(idx)
						.urlIdx(urlIdx)
						.createdUserId(updatedUserId)
						.build()
					//@formatter:on
			).collect(Collectors.toList());

			urlAlarmRepo.saveAll(programUrlEntities);
		}
	}

	/**
	 * Url Name Search
	 * TODO : Cache
	 *
	 * @param keyword
	 * @return
	 */
	public List<UrlEntity> urlNameSearch(String keyword) {
		return urlRepo.findByUrlNameStartingWith(keyword);
	}

	/**
	 * Url Program
	 *
	 * @param programIdx
	 * @return
	 */
	public List<UrlEntity> urlProgramList(Long programIdx) {
		return programUrlRepo.findByProgramUrlList(programIdx);
	}

	/**
	 * Url Mapping Alarm List
	 *
	 * @param urlIdx
	 * @return
	 */
	public List<AlarmEntity> urlAlarmList(Long urlIdx) {
		return urlRepo.findUrlAlarmList(urlIdx);
	}

	/**
	 * Health Check
	 *
	 * @param param
	 * @return
	 */
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
			checkRes.setHealthAndMsg(HealthStatusCd.WARNING, String.format("설정값이 잘못되었습니다. %s", e1.getMessage()));

		} catch (HttpClientErrorException e2) {
			checkRes.setHealthAndMsg(HealthStatusCd.WARNING, String.format("페이지가 잘못되었습니다. (%s) %s", e2.getStatusCode(), e2.getMessage()));

		} catch (HttpServerErrorException ee) {
			if (ee.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				checkRes.setHealthAndMsg(HealthStatusCd.ERROR, String.format("500 에러가 발생했습니다. %s", ee.getMessage()));

			} else {
				checkRes.setHealthAndMsg(HealthStatusCd.ERROR, String.format("500이 아닌 에러가 발생했습니다. %s", ee.getMessage()));

				log.error("error : {}, {}", ee.getMessage(), ee);
			}

		} catch (RestClientException e) {
			checkRes.setHealthStatusCd(HealthStatusCd.ERROR);

			if (e.getRootCause() instanceof SocketTimeoutException) {
				checkRes.setHealthAndMsg(HealthStatusCd.ERROR, String.format("SocketTimeout 이 발생했습니다. %s", e.getMessage()));

			} else if (e.getRootCause() instanceof ConnectTimeoutException) {
				checkRes.setHealthAndMsg(HealthStatusCd.ERROR, String.format("ConnectTimeout 이 발생했습니다. %s", e.getMessage()));

			} else {
				checkRes.setHealthAndMsg(HealthStatusCd.ERROR, String.format("알수없는 에러가 발생했습니다. %s", e.getMessage()));
			}

		} catch (Exception e) {
			checkRes.setHealthAndMsg(HealthStatusCd.ERROR, String.format("System Error 가 발생했습니다. : %s", e.getMessage()));

		} finally {
			checkRes.setResponseTime(stopwatch.elapsed(TimeUnit.MILLISECONDS));

		}

		return Optional.ofNullable(checkRes);
	}

	public List<MonitorDTO.CheckReq> getHealthCheckTargetList() {
		return urlRepo.findByCheckList();
	}

	/**
	 * Status Value Modify
	 *
	 * @param list
	 */
	@Transactional
	public void modifyHealthStatusCheck(List<MonitorDTO.CheckRes> list) {
		if (list.isEmpty()) {
			return;
		}

		//@formatter:off
		list.stream()
			.collect(Collectors.groupingBy(MonitorDTO.CheckRes::getHealthStatusCd))
			.forEach((key, values) ->
				urlRepo.updateStatusCheckCdInUrlIdx(key.name(), values.stream().map(MonitorDTO.CheckRes::getUrlIdx).collect(Collectors.toList()))
			);

		urlMonitorHistRepo.saveAll(
			list.stream()
				.map(info ->
					UrlMonitorHistEntity.builder()
						.urlIdx(info.getUrlIdx())
						.healthStatusCd(info.getHealthStatusCd().name())
						.preHealthStatusCheckCd(info.getPreHealthStatusCheckCd().name())
					.build()
				).collect(Collectors.toList())
		);
		//@formatter:on

	}

	public List<UrlDTO.StatusCnt> getStatusCntForDashboard() {
		List<UrlDTO.StatusCnt> rtnList = new ArrayList<>();
		List<UrlDTO.StatusCnt> ttt = urlRepo.findGroupByStatusCnt();

		List<String> codeList = HealthStatusCd.getNameList();

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

	public Map<String, Object> getUrlInfoListByProgramIdx(Long programIdx) {
		Map<String, Object> rtnMap = new HashMap<>();

		List<UrlEntity> urlList = programUrlRepo.findByProgramUrlList(programIdx);

		final int urlListTotalCnt = urlList.size();

		Map<String, String> urlHealthCount = urlList.stream().collect(
			Collectors.groupingBy(UrlEntity::getHealthStatusCd, Collectors.collectingAndThen(Collectors.counting(), value -> {
				double t = (double)value / (double)urlListTotalCnt;
				return String.format("%.2f", t * 100.0);  //%.2f" 는 소수점 이하 2자리까지 출력
			})));

		rtnMap.put("urlHealthCount", urlHealthCount);
		rtnMap.put("urlList", urlList);

		return rtnMap;
	}

}
