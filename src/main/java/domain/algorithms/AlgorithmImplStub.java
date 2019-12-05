package domain.algorithms;

import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlgorithmImplStub implements AlgorithmInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmImplStub.class);

    @Override
    public byte[] encode(byte[] file) throws CompressorException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            String message = "The thread was interrupted";
            LOGGER.error(message);
            throw new CompressorException(message, e, CompressorErrorCode.THREAD_INTERRUPTED_FAILURE);
        }
        return new byte[]{0, 1, 2, 3};
    }

    @Override
    public byte[] decode(byte[] file) throws CompressorException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            String message = "The thread was interrupted";
            LOGGER.error(message);
            throw new CompressorException(message, e, CompressorErrorCode.THREAD_INTERRUPTED_FAILURE);
        }
        return new byte[]{3, 2, 1, 0};
    }
}
