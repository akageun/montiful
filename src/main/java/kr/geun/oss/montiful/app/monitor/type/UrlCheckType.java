package kr.geun.oss.montiful.app.monitor.type;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * UrlCheckType
 *
 * @author akageun
 * @since 2021-02-14
 */
@Getter
@NoArgsConstructor
public enum UrlCheckType {
    STATUS_CHECK("status 체크"),
    SELENIUM_CHECK("selenium 체크"),

    ;

    private String codeName;

    UrlCheckType(String codeName) {
        this.codeName = codeName;
    }

    public static class Deserializer extends JsonDeserializer<UrlCheckType> {

        @Override
        public UrlCheckType deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext
        ) throws IOException, JsonProcessingException {
            return null;
        }
    }

    public static class Serializer extends JsonSerializer<UrlCheckType> {

        @Override
        public void serialize(
            UrlCheckType urlCheckType,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
        ) throws IOException {

        }
    }
}
