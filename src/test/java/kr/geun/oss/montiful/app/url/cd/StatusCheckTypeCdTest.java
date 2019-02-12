package kr.geun.oss.montiful.app.url.cd;

import kr.geun.oss.montiful.app.monitor.dto.MonitorDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class StatusCheckTypeCdTest {

	private static MonitorDTO.CheckRes CHECK_RES;

	@BeforeClass
	public static void initClass() {
		CHECK_RES = MonitorDTO.CheckRes.builder().healthStatusCd(HealthStatusCd.HEALTH).resultMsg("SUCCESS").build();
	}

	@Test
	public void isOkTest_2xx_success() {
		MonitorDTO.CheckRes result = StatusCheckTypeCd.SUCCESS_2XX_CHECK.isOk(CHECK_RES, new ResponseEntity<>(HttpStatus.CREATED), null);

		Assert.assertNotNull(result);
		Assert.assertEquals(result.getHealthStatusCd(), CHECK_RES.getHealthStatusCd());
	}

	@Test
	public void isOkTest_2xx_not_success() {
		MonitorDTO.CheckRes result = StatusCheckTypeCd.SUCCESS_2XX_CHECK.isOk(CHECK_RES, new ResponseEntity<>(HttpStatus.BAD_REQUEST), null);

		Assert.assertNotNull(result);
		Assert.assertNotEquals(HealthStatusCd.ERROR.name(), result.getHealthStatusCd());
		Assert.assertNotNull(result.getResultMsg());
	}

	@Test(expected = IllegalArgumentException.class)
	public void isOkTest_2xx_fail_param() {
		StatusCheckTypeCd.SUCCESS_2XX_CHECK.isOk(CHECK_RES, new ResponseEntity<>(HttpStatus.CREATED), "ERROR Check Value");
	}

}
