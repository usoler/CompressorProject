package domain.dataObjects;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ByteUtils {

    public static final long unsignedIntToLong(byte[] b) {
        long l = 0;
        l |= b[0] & 0xFF;
        l <<= 8;
        l |= b[1] & 0xFF;
        l <<= 8;
        l |= b[2] & 0xFF;
        l <<= 8;
        l |= b[3] & 0xFF;
        return l;
    }

    public static final short unsignedByteToShort(byte[] b) {
        short l = 0;
        l |= b[0] & 0xFF;
        return l;
    }

    public static final int unsignedShortToInt(byte[] b) {
        int l = 0;
        l |= b[0] & 0xFF;
        l <<= 8;
        l |= b[1] & 0xFF;
        return l;
    }

    public static final long unsigned3bytesTo6bytesLong(byte[] b) {
        long l = 0;
        l |= b[0] & 0xFF;
        l <<= 8;
        l |= b[1] & 0xFF;
        l <<= 8;
        l |= b[2] & 0xFF;
        return l;
    }

    public static byte[] ArrayListByteToByteArray(ArrayList<Byte> listOfBytes) {
        byte[] byteArray = new byte[listOfBytes.size()];
        for (int j = 0; j < listOfBytes.size(); j++) {
            byteArray[j] = listOfBytes.get(j).byteValue();
        }
        return byteArray;
    }

    public static byte[] transformIntToByteArray(int index, int extraBytesNeeded) {
        byte[] bytes = ByteBuffer.allocate(4).putInt(index).array();
        switch (extraBytesNeeded) {
            case 0:
                byte b1[] = {bytes[3]};
                return b1;
            case 1:
                byte b2[] = {bytes[2], bytes[3]};
                return b2;
            case 2:
                byte b3[] = {bytes[1], bytes[2], bytes[3]};
                return b3;
            default:
                return bytes;
        }
    }

    private static String bytesToBinary(byte[] bytes) {
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int k = 0; k < 8; k++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        return binary.toString();
    }



}
