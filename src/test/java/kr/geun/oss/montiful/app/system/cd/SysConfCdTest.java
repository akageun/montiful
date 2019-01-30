package kr.geun.oss.montiful.app.system.cd;

import org.junit.Assert;
import org.junit.Test;

public class SysConfCdTest {

	@Test
	public void sysConfigValidTest() {
		Assert.assertTrue(SysConfCd.HEALTH_CHECK_RUN_THREAD.valid("3"));
		Assert.assertFalse(SysConfCd.HEALTH_CHECK_RUN_THREAD.valid("7"));

		Assert.assertTrue(SysConfCd.HEALTH_CHECK_RUN_YN.valid("Y"));
		Assert.assertFalse(SysConfCd.HEALTH_CHECK_RUN_YN.valid("F"));
	}
}
