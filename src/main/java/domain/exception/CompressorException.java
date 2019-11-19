package domain.exception;

public class CompressorException extends Exception {
    private String errorCode;

    public CompressorException(String errorMessage) {
        super(errorMessage);
    }

    public CompressorException(String errorMessage, CompressorErrorCode errorCode) {
        super(errorMessage);
        this.errorCode = errorCode.getCode();
    }

    public CompressorException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public CompressorException(String errorMessage, Throwable cause, CompressorErrorCode errorCode) {
        super(errorMessage, cause);
        this.errorCode = errorCode.getCode();
    }

    public String getErrorCode() {
        return errorCode;
    }
}
