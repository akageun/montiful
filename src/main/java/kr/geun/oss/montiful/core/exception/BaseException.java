package kr.geun.oss.montiful.core.exception;

import kr.geun.oss.montiful.core.cd.ErrorCd;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author akageun
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseException extends RuntimeException {

    private ErrorCd errorCd;
    private String msg;

    public BaseException(ErrorCd errorCd) {
        super(errorCd.getMsg());
        this.errorCd = errorCd;
    }

    public BaseException(ErrorCd errorCd, String msg) {
        super(msg);
        this.errorCd = errorCd;
        this.msg = msg;
    }

    public ErrorCd getErrorCd() {
        return errorCd;
    }

    public String getMsg() {
        if (StringUtils.isBlank(this.msg)) {
            return this.getErrorCd().getMsg();
        }
        return msg;
    }
}