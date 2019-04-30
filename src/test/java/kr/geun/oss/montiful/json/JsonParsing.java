package kr.geun.oss.montiful.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.geun.oss.montiful.app.alarm.channel.lineNotify.dto.ChannelLineNotifyDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author akageun
 */
@Slf4j
public class JsonParsing {

    @Test
    public void jsonParse() throws IOException {

        final String accessToken = "12345";

        ObjectMapper OM = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ChannelLineNotifyDTO.AlarmValue value = OM.readValue(String.format("{\"accessToken\":\"%s\"}", accessToken),
                ChannelLineNotifyDTO.AlarmValue.class);

        Assert.assertEquals(value.getAccessToken(), accessToken);

    }
}
