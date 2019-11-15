package domain.components;

import domain.dataObjects.CoefficientEnum;
import javafx.util.Pair;

public class HuffmanComponent {

    public StringBuffer encodeAC(int ac, int numOfPreZeros, CoefficientEnum typeOfCoefficient, StringBuffer buffer) throws Exception {
        Pair<String, Integer> huffmanCodes = getDCValueEncoded(ac);
        int tableRow = getTableRow(huffmanCodes.getKey());
        String binaryTableColumn = Integer.toBinaryString(huffmanCodes.getValue());

        if (binaryTableColumn.length() < tableRow) {
            binaryTableColumn = bitExtension(binaryTableColumn, tableRow);
        }

        String huffmanEncoded;
        if (typeOfCoefficient.equals(CoefficientEnum.LUMINANCE)) {
            huffmanEncoded = getLuminanceACEncoded(tableRow, numOfPreZeros);
        } else {
            huffmanEncoded = getChrominanceACEncoded(tableRow, numOfPreZeros);
        }

        buffer.append(huffmanEncoded);
        buffer.append(binaryTableColumn);

        return buffer;
    }

    public StringBuffer encodeDC(int dc, StringBuffer buffer) throws Exception {
        Pair<String, Integer> huffmanCodes = getDCValueEncoded(dc);
        String huffmanEncoded = huffmanCodes.getKey();
        String binaryTableColumn = Integer.toBinaryString(huffmanCodes.getValue());

        buffer.append(huffmanEncoded);
        buffer.append(binaryTableColumn);

        return buffer;
    }

    public int getNumOfBitsOfColumn(String huffmanValue) {
        switch (huffmanValue) {
            case "0":
                return 0;
            case "10":
                return 1;
            case "110":
                return 2;
            case "1110":
                return 3;
            case "11110":
                return 4;
            case "111110":
                return 5;
            case "1111110":
                return 6;
            case "11111110":
                return 7;
            case "111111110":
                return 8;
            case "1111111110":
                return 9;
            case "11111111110":
                return 10;
            case "111111111110":
                return 11;
            case "1111111111110":
                return 12;
            case "11111111111110":
                return 13;
            case "111111111111110":
                return 14;
            case "1111111111111110":
                return 15;
            case "11111111111111110":
                return 16;
            default:
                return -1;
        }
    }

    public int decodeDC(int row, int column) throws Exception {
        switch (row) {
            case 0:
                return 0;
            case 1:
                if (column >= 1) {
                    return 1;
                }
                return -1;
            case 2:
                if (column >= 2) {
                    return column;
                }
                return column - 3;
            case 3:
                if (column >= 4) {
                    return column;
                }
                return column - 7;
            case 4:
                if (column >= 8) {
                    return column;
                }
                return column - 15;
            case 5:
                if (column >= 16) {
                    return column;
                }
                return column - 31;
            case 6:
                if (column >= 32) {
                    return column;
                }
                return column - 63;
            case 7:
                if (column >= 64) {
                    return column;
                }
                return column - 127;
            case 8:
                if (column >= 128) {
                    return column;
                }
                return column - 255;
            case 9:
                if (column >= 256) {
                    return column;
                }
                return column - 511;
            case 10:
                if (column >= 512) {
                    return column;
                }
                return column - 1023;
            case 11:
                if (column >= 1024) {
                    return column;
                }
                return column - 2047;
            case 12:
                if (column >= 2048) {
                    return column;
                }
                return column - 4095;
            case 13:
                if (column >= 4096) {
                    return column;
                }
                return column - 8191;
            case 14:
                if (column >= 8192) {
                    return column;
                }
                return column - 16383;
            case 15:
                if (column >= 16384) {
                    return column;
                }
                return column - 32767;
            case 16:
                return 32768;
            default:
                throw new Exception();
        }
    }

    private String bitExtension(String bits, int size) {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < size - bits.length(); ++i) {
            buffer.append(0);
        }

        buffer.append(bits);

        return buffer.toString();
    }

    private String getChrominanceACEncoded(int row, int numOfPreZeros) throws Exception {
        if (numOfPreZeros == 0) {
            if (row == 1) {
                return "01";
            } else if (row == 2) {
                return "100";
            } else if (row == 3) {
                return "1010";
            } else if (row == 4) {
                return "11000";
            } else if (row == 5) {
                return "11001";
            } else if (row == 6) {
                return "111000";
            } else if (row == 7) {
                return "1111000";
            } else if (row == 8) {
                return "111110100";
            } else if (row == 9) {
                return "1111110110";
            } else if (row == 10) {
                return "111111110100";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 1) {
            if (row == 1) {
                return "1011";
            } else if (row == 2) {
                return "111001";
            } else if (row == 3) {
                return "11110110";
            } else if (row == 4) {
                return "111110101";
            } else if (row == 5) {
                return "11111110110";
            } else if (row == 6) {
                return "111111110101";
            } else if (row == 7) {
                return "111111110001000";
            } else if (row == 8) {
                return "111111110001001";
            } else if (row == 9) {
                return "111111110001010";
            } else if (row == 10) {
                return "111111110001011";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 2) {
            if (row == 1) {
                return "11010";
            } else if (row == 2) {
                return "11110111";
            } else if (row == 3) {
                return "1111110111";
            } else if (row == 4) {
                return "111111110110";
            } else if (row == 5) {
                return "111111111000010";
            } else if (row == 6) {
                return "1111111110001100";
            } else if (row == 7) {
                return "1111111110001101";
            } else if (row == 8) {
                return "1111111110001110";
            } else if (row == 9) {
                return "1111111110001111";
            } else if (row == 10) {
                return "1111111110010000";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 3) {
            if (row == 1) {
                return "11011";
            } else if (row == 2) {
                return "11111000";
            } else if (row == 3) {
                return "1111111000";
            } else if (row == 4) {
                return "111111110111";
            } else if (row == 5) {
                return "1111111110010001";
            } else if (row == 6) {
                return "1111111110010010";
            } else if (row == 7) {
                return "1111111110010011";
            } else if (row == 8) {
                return "1111111110010100";
            } else if (row == 9) {
                return "1111111110010101";
            } else if (row == 10) {
                return "1111111110010110";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 4) {
            if (row == 1) {
                return "111010";
            } else if (row == 2) {
                return "111110110";
            } else if (row == 3) {
                return "1111111110010111";
            } else if (row == 4) {
                return "1111111110011000";
            } else if (row == 5) {
                return "1111111110011001";
            } else if (row == 6) {
                return "1111111110011010";
            } else if (row == 7) {
                return "1111111110011011";
            } else if (row == 8) {
                return "1111111110011100";
            } else if (row == 9) {
                return "1111111110011101";
            } else if (row == 10) {
                return "1111111110011110";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 5) {
            if (row == 1) {
                return "111011";
            } else if (row == 2) {
                return "1111111001";
            } else if (row == 3) {
                return "1111111110011111";
            } else if (row == 4) {
                return "1111111110100000";
            } else if (row == 5) {
                return "1111111110100001";
            } else if (row == 6) {
                return "1111111110100010";
            } else if (row == 7) {
                return "1111111110100011";
            } else if (row == 8) {
                return "1111111110100100";
            } else if (row == 9) {
                return "1111111110100101";
            } else if (row == 10) {
                return "1111111110100110";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 6) {
            if (row == 1) {
                return "1111001";
            } else if (row == 2) {
                return "11111110111";
            } else if (row == 3) {
                return "1111111110100111";
            } else if (row == 4) {
                return "1111111110101000";
            } else if (row == 5) {
                return "1111111110101001";
            } else if (row == 6) {
                return "1111111110101010";
            } else if (row == 7) {
                return "1111111110101011";
            } else if (row == 8) {
                return "1111111110101100";
            } else if (row == 9) {
                return "1111111110101101";
            } else if (row == 10) {
                return "1111111110101110";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 7) {
            if (row == 1) {
                return "1111010";
            } else if (row == 2) {
                return "11111111000";
            } else if (row == 3) {
                return "1111111110101111";
            } else if (row == 4) {
                return "1111111110110000";
            } else if (row == 5) {
                return "1111111110110001";
            } else if (row == 6) {
                return "1111111110110010";
            } else if (row == 7) {
                return "1111111110110011";
            } else if (row == 8) {
                return "1111111110110100";
            } else if (row == 9) {
                return "1111111110110101";
            } else if (row == 10) {
                return "1111111110110110";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 8) {
            if (row == 1) {
                return "11111001";
            } else if (row == 2) {
                return "1111111110110111";
            } else if (row == 3) {
                return "1111111110111000";
            } else if (row == 4) {
                return "1111111110111001";
            } else if (row == 5) {
                return "1111111110111010";
            } else if (row == 6) {
                return "1111111110111011";
            } else if (row == 7) {
                return "1111111110111100";
            } else if (row == 8) {
                return "1111111110111101";
            } else if (row == 9) {
                return "1111111110111110";
            } else if (row == 10) {
                return "1111111110111111";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 9) {
            if (row == 1) {
                return "111110111";
            } else if (row == 2) {
                return "1111111111000000";
            } else if (row == 3) {
                return "1111111111000001";
            } else if (row == 4) {
                return "1111111111000010";
            } else if (row == 5) {
                return "1111111111000011";
            } else if (row == 6) {
                return "1111111111000100";
            } else if (row == 7) {
                return "1111111111000101";
            } else if (row == 8) {
                return "1111111111000110";
            } else if (row == 9) {
                return "1111111111000111";
            } else if (row == 10) {
                return "1111111111001000";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 10) {
            if (row == 1) {
                return "111111000";
            } else if (row == 2) {
                return "1111111111001001";
            } else if (row == 3) {
                return "1111111111001010";
            } else if (row == 4) {
                return "1111111111001011";
            } else if (row == 5) {
                return "1111111111001100";
            } else if (row == 6) {
                return "1111111111001101";
            } else if (row == 7) {
                return "1111111111001110";
            } else if (row == 8) {
                return "1111111111001111";
            } else if (row == 9) {
                return "1111111111010000";
            } else if (row == 10) {
                return "1111111111010001";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 11) {
            if (row == 1) {
                return "111111001";
            } else if (row == 2) {
                return "1111111111010010";
            } else if (row == 3) {
                return "1111111111010011";
            } else if (row == 4) {
                return "1111111111010100";
            } else if (row == 5) {
                return "1111111111010101";
            } else if (row == 6) {
                return "1111111111010110";
            } else if (row == 7) {
                return "1111111111010111";
            } else if (row == 8) {
                return "1111111111011000";
            } else if (row == 9) {
                return "1111111111011001";
            } else if (row == 10) {
                return "1111111111011010";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 12) {
            if (row == 1) {
                return "111111010";
            } else if (row == 2) {
                return "1111111111011011";
            } else if (row == 3) {
                return "1111111111011100";
            } else if (row == 4) {
                return "1111111111011101";
            } else if (row == 5) {
                return "1111111111011110";
            } else if (row == 6) {
                return "1111111111011111";
            } else if (row == 7) {
                return "1111111111100000";
            } else if (row == 8) {
                return "1111111111100001";
            } else if (row == 9) {
                return "1111111111100010";
            } else if (row == 10) {
                return "1111111111100011";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 13) {
            if (row == 1) {
                return "11111111001";
            } else if (row == 2) {
                return "1111111111100100";
            } else if (row == 3) {
                return "1111111111100101";
            } else if (row == 4) {
                return "1111111111100110";
            } else if (row == 5) {
                return "1111111111100111";
            } else if (row == 6) {
                return "1111111111101000";
            } else if (row == 7) {
                return "1111111111101001";
            } else if (row == 8) {
                return "1111111111101010";
            } else if (row == 9) {
                return "1111111111101011";
            } else if (row == 10) {
                return "1111111111101100";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 14) {
            if (row == 1) {
                return "11111111100000";
            } else if (row == 2) {
                return "1111111111101101";
            } else if (row == 3) {
                return "1111111111101110";
            } else if (row == 4) {
                return "1111111111101111";
            } else if (row == 5) {
                return "1111111111110000";
            } else if (row == 6) {
                return "1111111111110001";
            } else if (row == 7) {
                return "1111111111110010";
            } else if (row == 8) {
                return "1111111111110011";
            } else if (row == 9) {
                return "1111111111110100";
            } else if (row == 10) {
                return "1111111111110101";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 15) {
            if (row == 1) {
                return "111111111000011";
            } else if (row == 2) {
                return "111111111010110";
            } else if (row == 3) {
                return "1111111111110111";
            } else if (row == 4) {
                return "1111111111111000";
            } else if (row == 5) {
                return "1111111111111001";
            } else if (row == 6) {
                return "1111111111111010";
            } else if (row == 7) {
                return "1111111111111011";
            } else if (row == 8) {
                return "1111111111111100";
            } else if (row == 9) {
                return "1111111111111101";
            } else if (row == 10) {
                return "1111111111111110";
            } else {
                throw new Exception();
            }
        } else {
            throw new Exception();
        }
    }

    private String getLuminanceACEncoded(int row, int numOfPreZeros) throws Exception { // TODO: Static table vs code ??
        if (numOfPreZeros == 0) {
            if (row == 1) {
                return "00";
            } else if (row == 2) {
                return "01";
            } else if (row == 3) {
                return "100";
            } else if (row == 4) {
                return "1011";
            } else if (row == 5) {
                return "11010";
            } else if (row == 6) {
                return "1111000";
            } else if (row == 7) {
                return "11111000";
            } else if (row == 8) {
                return "1111110110";
            } else if (row == 9) {
                return "1111111110000010";
            } else if (row == 10) {
                return "1111111110000011";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 1) {
            if (row == 1) {
                return "1100";
            } else if (row == 2) {
                return "11011";
            } else if (row == 3) {
                return "11110001";
            } else if (row == 4) {
                return "111110110";
            } else if (row == 5) {
                return "11111110110";
            } else if (row == 6) {
                return "1111111110000100";
            } else if (row == 7) {
                return "1111111110000101";
            } else if (row == 8) {
                return "1111111110000110";
            } else if (row == 9) {
                return "1111111110000111";
            } else if (row == 10) {
                return "1111111110001000";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 2) {
            if (row == 1) {
                return "11100";
            } else if (row == 2) {
                return "11111001";
            } else if (row == 3) {
                return "1111110111";
            } else if (row == 4) {
                return "111111110100";
            } else if (row == 5) {
                return "111111110001001";
            } else if (row == 6) {
                return "111111110001010";
            } else if (row == 7) {
                return "111111110001011";
            } else if (row == 8) {
                return "111111110001100";
            } else if (row == 9) {
                return "111111110001101";
            } else if (row == 10) {
                return "111111110001110";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 3) {
            if (row == 1) {
                return "111010";
            } else if (row == 2) {
                return "111110111";
            } else if (row == 3) {
                return "111111110101";
            } else if (row == 4) {
                return "1111111110001111";
            } else if (row == 5) {
                return "1111111110010000";
            } else if (row == 6) {
                return "1111111110010001";
            } else if (row == 7) {
                return "1111111110010010";
            } else if (row == 8) {
                return "1111111110010011";
            } else if (row == 9) {
                return "1111111110010100";
            } else if (row == 10) {
                return "1111111110010101";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 4) {
            if (row == 1) {
                return "111011";
            } else if (row == 2) {
                return "1111111000";
            } else if (row == 3) {
                return "1111111110010110";
            } else if (row == 4) {
                return "1111111110010111";
            } else if (row == 5) {
                return "1111111110011000";
            } else if (row == 6) {
                return "1111111110011001";
            } else if (row == 7) {
                return "1111111110011010";
            } else if (row == 8) {
                return "1111111110011011";
            } else if (row == 9) {
                return "1111111110011100";
            } else if (row == 10) {
                return "1111111110011101";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 5) {
            if (row == 1) {
                return "1111010";
            } else if (row == 2) {
                return "11111110111";
            } else if (row == 3) {
                return "1111111110011110";
            } else if (row == 4) {
                return "1111111110011111";
            } else if (row == 5) {
                return "1111111110100000";
            } else if (row == 6) {
                return "1111111110100001";
            } else if (row == 7) {
                return "1111111110100010";
            } else if (row == 8) {
                return "1111111110100011";
            } else if (row == 9) {
                return "1111111110100100";
            } else if (row == 10) {
                return "1111111110100101";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 6) {
            if (row == 1) {
                return "1111011";
            } else if (row == 2) {
                return "111111110110";
            } else if (row == 3) {
                return "1111111110100110";
            } else if (row == 4) {
                return "1111111110100111";
            } else if (row == 5) {
                return "1111111110101000";
            } else if (row == 6) {
                return "1111111110101001";
            } else if (row == 7) {
                return "1111111110101010";
            } else if (row == 8) {
                return "1111111110101011";
            } else if (row == 9) {
                return "1111111110101100";
            } else if (row == 10) {
                return "1111111110101101";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 7) {
            if (row == 1) {
                return "11111010";
            } else if (row == 2) {
                return "111111110111";
            } else if (row == 3) {
                return "1111111110101110";
            } else if (row == 4) {
                return "1111111110101111";
            } else if (row == 5) {
                return "1111111110110000";
            } else if (row == 6) {
                return "1111111110110001";
            } else if (row == 7) {
                return "1111111110110010";
            } else if (row == 8) {
                return "1111111110110011";
            } else if (row == 9) {
                return "1111111110110100";
            } else if (row == 10) {
                return "1111111110110101";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 8) {
            if (row == 1) {
                return "111111000";
            } else if (row == 2) {
                return "111111111000000";
            } else if (row == 3) {
                return "1111111110110110";
            } else if (row == 4) {
                return "1111111110110111";
            } else if (row == 5) {
                return "1111111110111000";
            } else if (row == 6) {
                return "1111111110111001";
            } else if (row == 7) {
                return "1111111110111010";
            } else if (row == 8) {
                return "1111111110111011";
            } else if (row == 9) {
                return "1111111110111100";
            } else if (row == 10) {
                return "1111111110111101";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 9) {
            if (row == 1) {
                return "111111001";
            } else if (row == 2) {
                return "1111111110111110";
            } else if (row == 3) {
                return "1111111110111111";
            } else if (row == 4) {
                return "1111111111000000";
            } else if (row == 5) {
                return "1111111111000001";
            } else if (row == 6) {
                return "1111111111000010";
            } else if (row == 7) {
                return "1111111111000011";
            } else if (row == 8) {
                return "1111111111000100";
            } else if (row == 9) {
                return "1111111111000101";
            } else if (row == 10) {
                return "1111111111000110";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 10) {
            if (row == 1) {
                return "111111010";
            } else if (row == 2) {
                return "1111111111000111";
            } else if (row == 3) {
                return "1111111111001000";
            } else if (row == 4) {
                return "1111111111001001";
            } else if (row == 5) {
                return "1111111111001010";
            } else if (row == 6) {
                return "1111111111001011";
            } else if (row == 7) {
                return "1111111111001100";
            } else if (row == 8) {
                return "1111111111001101";
            } else if (row == 9) {
                return "1111111111001110";
            } else if (row == 10) {
                return "1111111111001111";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 11) {
            if (row == 1) {
                return "1111111001";
            } else if (row == 2) {
                return "1111111111010000";
            } else if (row == 3) {
                return "1111111111010001";
            } else if (row == 4) {
                return "1111111111010010";
            } else if (row == 5) {
                return "1111111111010011";
            } else if (row == 6) {
                return "1111111111010100";
            } else if (row == 7) {
                return "1111111111010101";
            } else if (row == 8) {
                return "1111111111010110";
            } else if (row == 9) {
                return "1111111111010111";
            } else if (row == 10) {
                return "1111111111011000";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 12) {
            if (row == 1) {
                return "1111111010";
            } else if (row == 2) {
                return "1111111111011001";
            } else if (row == 3) {
                return "1111111111011010";
            } else if (row == 4) {
                return "1111111111011011";
            } else if (row == 5) {
                return "1111111111011100";
            } else if (row == 6) {
                return "1111111111011101";
            } else if (row == 7) {
                return "1111111111011110";
            } else if (row == 8) {
                return "1111111111011111";
            } else if (row == 9) {
                return "1111111111100000";
            } else if (row == 10) {
                return "1111111111100001";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 13) {
            if (row == 1) {
                return "11111111000";
            } else if (row == 2) {
                return "1111111111100010";
            } else if (row == 3) {
                return "1111111111100011";
            } else if (row == 4) {
                return "1111111111100100";
            } else if (row == 5) {
                return "1111111111100101";
            } else if (row == 6) {
                return "1111111111100110";
            } else if (row == 7) {
                return "1111111111100111";
            } else if (row == 8) {
                return "1111111111101000";
            } else if (row == 9) {
                return "1111111111101001";
            } else if (row == 10) {
                return "1111111111101010";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 14) {
            if (row == 1) {
                return "1111111111101011";
            } else if (row == 2) {
                return "1111111111101100";
            } else if (row == 3) {
                return "1111111111101101";
            } else if (row == 4) {
                return "1111111111101110";
            } else if (row == 5) {
                return "1111111111101111";
            } else if (row == 6) {
                return "1111111111110000";
            } else if (row == 7) {
                return "1111111111110001";
            } else if (row == 8) {
                return "1111111111110010";
            } else if (row == 9) {
                return "1111111111110011";
            } else if (row == 10) {
                return "1111111111110100";
            } else {
                throw new Exception();
            }
        } else if (numOfPreZeros == 15) {
            if (row == 1) {
                return "1111111111110101";
            } else if (row == 2) {
                return "1111111111110110";
            } else if (row == 3) {
                return "1111111111110111";
            } else if (row == 4) {
                return "1111111111111000";
            } else if (row == 5) {
                return "1111111111111001";
            } else if (row == 6) {
                return "1111111111111010";
            } else if (row == 7) {
                return "1111111111111011";
            } else if (row == 8) {
                return "1111111111111100";
            } else if (row == 9) {
                return "1111111111111101";
            } else if (row == 10) {
                return "1111111111111110";
            } else {
                throw new Exception();
            }
        } else {
            throw new Exception();
        }
    }

    private int getTableRow(String code) {
        int tableRow = code.length() - 1;

        if (code.equals("32768")) {
            tableRow = 16;
        }

        return tableRow;
    }

    private Pair<String, Integer> getDCValueEncoded(int dc) { // TODO: Static table vs code ??
        if (dc == 0) {
            return new Pair<String, Integer>("0", 0);
        } else if (dc >= -1 && dc <= 1) {
            return new Pair<String, Integer>("10", getTableColumn(dc, -1));
        } else if (dc >= -3 && dc <= 3) {
            return new Pair<String, Integer>("110", getTableColumn(dc, -3));
        } else if (dc >= -7 && dc <= 7) {
            return new Pair<String, Integer>("1110", getTableColumn(dc, -7));
        } else if (dc >= -15 && dc <= 15) {
            return new Pair<String, Integer>("11110", getTableColumn(dc, -15));
        } else if (dc >= -31 && dc <= 31) {
            return new Pair<String, Integer>("111110", getTableColumn(dc, -31));
        } else if (dc >= -63 && dc <= 63) {
            return new Pair<String, Integer>("1111110", getTableColumn(dc, -63));
        } else if (dc >= -127 && dc <= 127) {
            return new Pair<String, Integer>("11111110", getTableColumn(dc, -127));
        } else if (dc >= -255 && dc <= 255) {
            return new Pair<String, Integer>("111111110", getTableColumn(dc, -255));
        } else if (dc >= -511 && dc <= 511) {
            return new Pair<String, Integer>("1111111110", getTableColumn(dc, -511));
        } else if (dc >= -1023 && dc <= 1023) {
            return new Pair<String, Integer>("11111111110", getTableColumn(dc, -1023));
        } else if (dc >= -2047 && dc <= 2047) {
            return new Pair<String, Integer>("111111111110", getTableColumn(dc, -2047));
        } else if (dc >= -4095 && dc <= 4095) {
            return new Pair<String, Integer>("1111111111110", getTableColumn(dc, -4095));
        } else if (dc >= -8191 && dc <= 8191) {
            return new Pair<String, Integer>("11111111111110", getTableColumn(dc, -8191));
        } else if (dc >= -16383 && dc <= 16383) {
            return new Pair<String, Integer>("111111111111110", getTableColumn(dc, -16383));
        } else if (dc >= -32767 && dc <= 32767) {
            return new Pair<String, Integer>("1111111111111110", getTableColumn(dc, -32767));
        } else {
            return new Pair<String, Integer>("1111111111111111", getTableColumn(dc, 0));
        }
    }

    private int getTableColumn(int dc, int min_value) {
        if (dc < 0) {
            return (dc + Math.abs(min_value));
        } else {
            return dc;
        }
    }
}
