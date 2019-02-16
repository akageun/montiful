package kr.geun.oss.montiful.core.utils;

import kr.geun.oss.montiful.app.program.cd.ProgramManageSearchTypeCd;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class CmnUtilsTest {

	@Test
	public void defaultEnumSettingTest() {

		ProgramManageSearchTypeCd result = CmnUtils.defaultEnumCode(null, ProgramManageSearchTypeCd.MEMO);
		ProgramManageSearchTypeCd result2 = CmnUtils.defaultEnumCode(ProgramManageSearchTypeCd.UPDATED_ID, ProgramManageSearchTypeCd.MEMO);

		Assert.assertEquals(ProgramManageSearchTypeCd.MEMO, result);
		Assert.assertEquals(ProgramManageSearchTypeCd.UPDATED_ID, result2);

	}

}