package kr.geun.oss.montiful.core.exception.handler;

import kr.geun.oss.montiful.core.exception.BaseException;
import kr.geun.oss.montiful.core.response.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author akageun
 */
@Slf4j
@RestControllerAdvice
public class RestGlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Res> baseException(BaseException e) {
        return ResponseEntity.status(e.getErrorCd().getHttpStatus()).body(Res.of(false, e.getErrorCd().getMsg()));
    }
}
