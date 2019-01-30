package kr.geun.oss.montiful.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.geun.oss.montiful.app.alarm.channel.lineNotify.dto.ChannelLineNotifyDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

/**
 *
 *
 * @author akageun
 */
@Slf4j
public class JsonParsing {

    //@formatter:off
	protected static final ObjectMapper OM = new ObjectMapper()
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
		;
	//@formatter:on

    @Test
    public void jsonParse() throws IOException {

        ChannelLineNotifyDTO.AlarmValue value = OM.readValue("{\"accessToken\":\"1234\"}",
            ChannelLineNotifyDTO.AlarmValue.class);

        log.info("t2 : {}", value.getAccessToken());

    }
}
