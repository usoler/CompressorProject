package domain.exception;

public class CompressorException extends Exception {

    private CompressorErrorCode errorCode;

    /**
     * Constructs a new {@link CompressorException} with a given error message
     *
     * @param errorMessage the exception error message
     */
    public CompressorException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * Constructs a new {@link CompressorException} with a given error message and error code
     *
     * @param errorMessage the exception error message
     * @param errorCode    the exception error code
     */
    public CompressorException(String errorMessage, CompressorErrorCode errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    /**
     * Constructs a new {@link CompressorException} with a given error message and cause
     *
     * @param errorMessage the exception error message
     * @param cause        the exception cause
     */
    public CompressorException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    /**
     * Constructs a new {@link CompressorException} with a given error message, a cause and a error code
     *
     * @param errorMessage the exception error message
     * @param cause        the exception cause
     * @param errorCode    the exception error code
     */
    public CompressorException(String errorMessage, Throwable cause, CompressorErrorCode errorCode) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
    }

    /**
     * Gets the exception error code
     *
     * @return the exception error code
     */
    public CompressorErrorCode getErrorCode() {
        return errorCode;
    }
}
