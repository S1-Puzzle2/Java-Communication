package at.fhv.puzzle2.communication.connection.util;

public abstract class ByteArrayOperations {
    public static byte[] appendByteToArray(byte[] destination, byte src) {
        byte[] tmpArray = new byte[destination.length + 1];
        System.arraycopy(destination, 0, tmpArray, 0, destination.length);
        tmpArray[tmpArray.length - 1] = src;

        return tmpArray;
    }

    public static byte[] appendByteArray(byte[] destination, byte[] source) {
        return appendByteArray(destination, source, source.length);
    }

    public static byte[] appendByteArray(byte[] destination, byte[] source, int length) {
        byte[] tmpArray = new byte[destination.length + length];
        System.arraycopy(destination, 0, tmpArray, 0, destination.length);

        System.arraycopy(source, 0, tmpArray, destination.length, length);

        return tmpArray;
    }
}
