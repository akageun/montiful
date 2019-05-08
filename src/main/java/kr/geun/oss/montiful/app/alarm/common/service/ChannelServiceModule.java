package kr.geun.oss.montiful.app.alarm.common.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Channel Common Module
 *
 * @author akageun
 */
public abstract class ChannelServiceModule {

    private final ObjectMapper OM = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

    /**
     * convert alarm
     *
     * @param alarmValue
     * @param clz
     * @param <T>
     * @return
     * @throws IOException
     */
    protected <T> T convertAlarmValue(String alarmValue, Class<T> clz) throws IOException {
        return OM.readValue(alarmValue, clz);
    }
}
