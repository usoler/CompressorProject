package domain.dataObjects;

import java.util.ArrayList;

public class ByteArrayListUtils {

    public static void addIntToByteArrayList(int num, int bytesNeeded, ArrayList<Byte> arrayList) {
        byte[] number = ByteUtils.transformIntToByteArray(num, bytesNeeded - 1);
        for (byte b : number) arrayList.add(b);
    }

    public static void addIntToByteArrayList(int num, int bytesNeeded, int index, ArrayList<Byte> arrayList) {
        byte[] number = ByteUtils.transformIntToByteArray(num, bytesNeeded - 1);
        for (byte b : number) arrayList.add(index++, b);
    }

    public static void addStringToByteArrayList(String string, ArrayList<Byte> arrayList) {
        for (int i = 0; i < string.length(); i++) {
            arrayList.add((byte)string.charAt(i));
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
        return  byteArray;
    }

}
