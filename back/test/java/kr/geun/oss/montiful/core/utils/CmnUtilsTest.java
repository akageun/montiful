package kr.geun.oss.montiful.core.utils;

import kr.geun.oss.montiful.app.monitor.cd.ManageProgramSearchTypeCd;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class CmnUtilsTest {

    @Test
    public void defaultEnumSettingTest() {

        ManageProgramSearchTypeCd result = CmnUtils.defaultEnumCode(null, null, ManageProgramSearchTypeCd.MM);
        ManageProgramSearchTypeCd result2 = CmnUtils.defaultEnumCode(ManageProgramSearchTypeCd.class, ManageProgramSearchTypeCd.U.name(), ManageProgramSearchTypeCd.MM);

        Assert.assertEquals(result, ManageProgramSearchTypeCd.MM);
        Assert.assertEquals(result2, ManageProgramSearchTypeCd.U);

    }

}
