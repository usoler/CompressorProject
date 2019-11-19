package domain.exception;

public enum CompressorErrorCode {
    // JPEG error codes
    // ******************************************
    // PPM Component
    // ******************************************
    // Failure to read ppm file
    READ_PPM_FAILURE("1000"),
    // Failure to write ppm file
    WRITE_PPM_FAILURE("1001"),
    // ******************************************
    // Conversor YCbCr Component
    // ******************************************
    // Failure to convert RGB matrix to YCbCr matrix
    CONVERT_RGB_TO_YCBCR_FAILURE("1002"),
    // Failure to convert YCbCr matrix to RGB matrix
    CONVERT_YCBCR_TO_RGB_FAILURE("1003"),
    // ******************************************
    // Sampling Component
    // ******************************************
    // Failure to downsampling YCbCr pixel matrix
    DOWNSAMPLING_FAILURE("1004"),
    // Failure to upsampling YCbCr macro block
    UPSAMPLING_FAILURE("1005"),
    // ******************************************
    // DCT Component
    // ******************************************
    // Failure to apply DCT
    APPLY_DCT_FAILURE("1006"),
    // Failure to undo DCT
    UNDO_DCT_FAILURE("1007"),
    // ******************************************
    // Quantization Component
    // ******************************************
    // Failure to quantize matrix
    QUANTIZE_FAILURE("1008"),
    // Failure to dequantize matrix
    DEQUANTIZE_FAILURE("1009"),
    // ******************************************
    // Zig Zag Component
    // ******************************************
    // Failure to make zig zag
    MAKE_ZIGZAG_FAILURE("1010"),
    // Failure to undo zig zag
    UNDO_ZIGZAG_FAILURE("1011"),
    // ******************************************
    // Huffman Component
    // ******************************************
    // Failure to encode coefficient
    ENCODE_COEFFICIENT_FAILURE("1012"),
    // Failure to decode coefficient
    DECODE_COEFFICIENT_FAILURE("1013");

    // LZW error codes (Start from 2000)
    // LZ78 error codes (Start from 3000)

    private String code;

    CompressorErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
