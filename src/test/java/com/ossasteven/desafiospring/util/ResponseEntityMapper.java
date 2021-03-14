package com.ossasteven.desafiospring.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

public class ResponseEntityMapper {

    public static ObjectMapper mapper = new ObjectMapper();


    public static String serializeEntity(ResponseEntity<Object> entity) {

        mapper.addMixIn(ResponseEntity.class,ResponseEntityMixin1 .class);
        mapper.addMixIn(HttpStatus.class,HttpStatusMixIn1 .class);

        try {
            return mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static ResponseEntity<Object> deserializeEntity(String json) {

        mapper.addMixIn(ResponseEntity.class,ResponseEntityMixin1 .class);
        mapper.addMixIn(HttpStatus.class,HttpStatusMixIn1 .class);

        TypeReference<ResponseEntity<Object>> ref = new TypeReference<ResponseEntity<Object>>() {
        };

        try {
            return mapper.readValue(json, ref);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}


@JsonIgnoreProperties(ignoreUnknown = true)
class ResponseEntityMixin1 {
    @JsonCreator
    public ResponseEntityMixin1(@JsonProperty("body") Object body,
                                @JsonDeserialize(as = LinkedMultiValueMap.class) @JsonProperty("headers") MultiValueMap<String, String> headers,
                                @JsonProperty("statusCodeValue") HttpStatus status) {
    }
}

class HttpStatusMixIn1 {

    @JsonCreator
    public static HttpStatus resolve(int statusCode) {
        return HttpStatus.NO_CONTENT;
    }
}



