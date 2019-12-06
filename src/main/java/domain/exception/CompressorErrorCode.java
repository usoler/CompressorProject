package domain.exception;

public enum CompressorErrorCode {
    // JPEG error codes
    // Failure to read jpeg file
    READ_JPEG_FAILURE("1016"),
    // Extension compatibility failure with Jpeg algorithm
    JPEG_EXTENSION_COMPATIBILITY_FAILURE("1017"),
    // ******************************************
    // PPM Component
    // ******************************************
    // Failure to read ppm file
    READ_PPM_FAILURE("1000"),
    // Failure to write ppm file
    WRITE_PPM_FAILURE("1001"),
    // Compatibility magic number failure
    PPM_COMPATIBILITY_FAILURE("1014"),
    // Failure to parse width or height from ppm file
    PPM_PARSE_FAILURE("1015"),
    // Failure to parse pixel from P3 ppm file
    PPM_P3_PARSE_FAILURE("1020"),
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
    DECODE_COEFFICIENT_FAILURE("1013"),

    // LZW error codes (Start from 2000)
    // Extension compatibility failure with Lzw algorithm
    LZW_EXTENSION_COMPATIBILITY_FAILURE("1018"),
    // LZ78 error codes (Start from 3000)
    // Extension compatibility failure with Lz78 algorithm
    LZ78_EXTENSION_COMPATIBILITY_FAILURE("1019"),
    // Others (Start from 4000)
    // ******************************************
    // File Writer Impl
    // ******************************************
    // Failure closing file output stream
    CLOSE_FILE_OUTPUT_STREAM_FAILURE("4000"),
    // Failure writting into file output stream
    WRITE_FILE_OUTPUT_STREAM_FAILURE("4001"),
    // Failure initializing file output stream
    INIT_FILE_OUTPUT_STREAM_FAILURE("4002"),
    // Failure initializing file writer
    INIT_FILE_WRITER_FAILURE("4003"),
    // ******************************************
    // Algorithm
    // ******************************************
    // Param file cannot be null
    ILLEGAL_NULL_FILE_FAILURE("4004"),
    // ******************************************
    // FileReader
    // ******************************************
    // Failure to read the input stream
    READ_INPUT_STREAM_FAILURE("4005"),
    // Failure to close the input stream
    CLOSE_INPUT_STREAM_FAILURE("4006"),
    // ******************************************
    // Stubs
    // ******************************************
    // Failure with interrupted thread
    THREAD_INTERRUPTED_FAILURE("4007"),
    // ******************************************
    // DomainController
    // ******************************************
    // Failure to read all bytes from file
    READ_FILE_BYTES_FAILURE("4008"),
    // ******************************************
    // DataController
    // ******************************************
    // Failure to read all paths from history file
    READ_HISTORY_PATHS_FAILURE("4010"),
    // Failure to write in history file
    WRITE_HISTORY_PATHS_FAILURE("4011"),
    // Failure to rewrite in history file
    REWRITE_HISTORY_PATHS_FAILURE("4013"),
    // ******************************************
    // MainViewSwing
    // ******************************************
    // Failure to choose a file
    CHOOSE_FILE_FAILURE("4009"),
    // Failure to parse the file date
    PARSE_DATA_FAILURE("4012");

    private String code;

    CompressorErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
