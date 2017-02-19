package io.globomart.microservices.services.composite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Created by magnus on 08/03/15.
 *
 * TODO: Extract to a common util-lib
 */

@Component
public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);


    public <T> ResponseEntity<T> createOkResponse(T body) {
        return createResponse(body, HttpStatus.OK);
    }

    public <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
        return new ResponseEntity<>(body, httpStatus);
    }
}
