package kr.geun.oss.montiful.core.cd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author akageun
 */
@Getter
@AllArgsConstructor
public enum ErrorCd {
    INVALID_PARAMETER(
        HttpStatus.NOT_FOUND,
        "필수 파라미터가 없습니다."
    );

    private HttpStatus httpStatus;
    private String msg;
}
