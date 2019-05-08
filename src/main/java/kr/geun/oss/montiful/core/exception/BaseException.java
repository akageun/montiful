package kr.geun.oss.montiful.core.exception;

import kr.geun.oss.montiful.core.cd.ErrorCd;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author akageun
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseException extends RuntimeException {

    private ErrorCd errorCd;

    public BaseException(ErrorCd errorCd) {
        super(errorCd.getMsg());
        this.errorCd = errorCd;
    }

    public ErrorCd getErrorCd() {
        return errorCd;
    }
}