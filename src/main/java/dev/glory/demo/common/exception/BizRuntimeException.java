package dev.glory.demo.common.exception;

import lombok.Getter;

import dev.glory.demo.common.code.BaseResponseCode;


@Getter
public class BizRuntimeException extends RuntimeException {

    private final BaseResponseCode code;
    private final String           exceptionMessage;
    private final boolean          saveDb;


    public BizRuntimeException(BaseResponseCode code) {
        super(codeMessage(code));
        this.code = code;
        this.exceptionMessage = code.getMessage();
        this.saveDb = false;
    }

    public BizRuntimeException(BaseResponseCode code, Throwable cause) {
        super(codeMessage(code), cause);
        this.code = code;
        this.exceptionMessage = code.getMessage();
        this.saveDb = false;
    }

    public BizRuntimeException(BaseResponseCode code, String exceptionMessage) {
        super(codeMessage(code, exceptionMessage));
        this.code = code;
        this.exceptionMessage = exceptionMessage;
        this.saveDb = false;
    }

    public BizRuntimeException(BaseResponseCode code, String exceptionMessage, Throwable cause) {
        super(codeMessage(code, exceptionMessage), cause);
        this.code = code;
        this.exceptionMessage = exceptionMessage;
        this.saveDb = false;
    }

    public BizRuntimeException(BaseResponseCode code, String exceptionMessage, boolean saveDb) {
        super(codeMessage(code, exceptionMessage));
        this.code = code;
        this.exceptionMessage = exceptionMessage;
        this.saveDb = saveDb;
    }

    public BizRuntimeException(BaseResponseCode code, String exceptionMessage, boolean saveDb, Throwable cause) {
        super(codeMessage(code, exceptionMessage), cause);
        this.code = code;
        this.exceptionMessage = exceptionMessage;
        this.saveDb = saveDb;
    }

    private static String codeMessage(BaseResponseCode code) {
        return codeMessage(code, code.getMessage());
    }

    private static String codeMessage(BaseResponseCode code, String exceptionMessage) {
        return "[" + code.getCode() + "] " + exceptionMessage;
    }
}
