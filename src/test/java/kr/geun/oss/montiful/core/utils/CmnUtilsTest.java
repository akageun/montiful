package kr.geun.oss.montiful.core.utils;

import kr.geun.oss.montiful.app.program.cd.ProgramManageSearchTypeCd;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class CmnUtilsTest {

    @Test
    public void defaultEnumSettingTest() {

        ProgramManageSearchTypeCd result = CmnUtils.defaultEnumCode(null, null, ProgramManageSearchTypeCd.MM);
        ProgramManageSearchTypeCd result2 = CmnUtils.defaultEnumCode(ProgramManageSearchTypeCd.class, ProgramManageSearchTypeCd.U.name(), ProgramManageSearchTypeCd.MM);

        Assert.assertEquals(result, ProgramManageSearchTypeCd.MM);
        Assert.assertEquals(result2, ProgramManageSearchTypeCd.U);

    }

}