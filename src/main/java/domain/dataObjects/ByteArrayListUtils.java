package domain.dataObjects;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ByteArrayListUtils {

    // TODO: ??? javadoc
    public static void addIntToByteArrayList(int num, int bytesNeeded, ArrayList<Byte> arrayList) {
        byte[] number = transformIntToByteArray(num, bytesNeeded - 1);
        for (byte b : number) arrayList.add(b);
    }

    public static void addIntToByteArrayList(int num, int bytesNeeded, int index, ArrayList<Byte> arrayList) {
        byte[] number = transformIntToByteArray(num, bytesNeeded - 1);
        for (byte b : number) arrayList.add(index++, b);
    }

    public static void addStringToByteArrayList(String string, ArrayList<Byte> arrayList) {
        for (int i = 0; i < string.length(); i++) {
            arrayList.add((byte) string.charAt(i));
        }
    }

    public static byte[] mergeTwoBytesArrayList(ArrayList<Byte> arrayList1, ArrayList<Byte> arrayList2) {
        byte[] byteArray = new byte[arrayList1.size() + arrayList2.size()];
        int i = 0;
        for (Byte value : arrayList1) {
            byteArray[i++] = value;
        }
        for (Byte value : arrayList2) {
            byteArray[i++] = value;
        }
        return byteArray;
    }

    private static byte[] transformIntToByteArray(int index, int extraBytesNeeded) {
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

}
