package dev.glory.demo.web.external.exception;

import dev.glory.demo.common.code.BaseResponseCode;
import dev.glory.demo.common.exception.BizRuntimeException;

public class ExternalException extends BizRuntimeException {

    public ExternalException(BaseResponseCode code) {
        super(code);
    }

    public ExternalException(BaseResponseCode code, Throwable cause) {
        super(code, cause);
    }
}
