package Backend.noteApp.factory;

import Backend.noteApp.external.impl.ResponseParsing;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonToPojoFactory {


    private final ObjectMapper objectMapper;

    public JsonToPojoFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public ResponseParsing createResponseParsing() {
        return new ResponseParsing(objectMapper);
    }


}
