package bmp;

public class Utility {
    public static int bytesToInt(int a, int b, int c, int d) {
        a |= b << 8;
        a |= c << 16;
        return a | (d << 24);
    }

    public static int bytesToInt(byte a, byte b, byte c, byte d) {
        return bytesToInt(0xff & a, 0xff & b, 0xff & c, 0xff & d);
    }

    public static int bytesToInt(int a, int b, int c) {
        a |= b << 8;
        return a | (c << 16);
    }

    public static int bytesToInt(byte a, byte b, byte c) {
        return bytesToInt(0xff & a, 0xff & b, 0xff & c);
    }

    public static int bytesToInt(int a, int b) {
        return a | (b << 8);
    }

    public static int bytesToInt(byte a, byte b) {
        return bytesToInt(0xff & a, 0xff & b);
    }

    public static int[] intToInts(int x) {
        return new int[] {0xff & x, (0xff00 & x) >>> 8,
                          (0xff0000 & x) >>> 16, (0xff000000 & x) >>> 24};
    }

    public static byte[] intToBytes(int x) {
        return new byte[] {(byte) (0xff & x), (byte) ((0xff00 & x) >>> 8),
                          (byte) ((0xff0000 & x) >>> 16), (byte) ((0xff000000 & x) >>> 24)};
    }

    public static byte[] intToRGBBytes(int x) {
        return new byte[] {(byte) (0xff & x), (byte) ((0xff00 & x) >>> 8),
                          (byte) ((0xff0000 & x) >>> 16)};
    }

    public static String byteToHex(int x) {
        return (x<16 ? "0" : "") + Integer.toHexString(x);
    }

    public static String intToHex(int x) {
        int[] b = intToInts(x);
        return byteToHex(b[0]) + byteToHex(b[1]) + byteToHex(b[2]) + byteToHex(b[3]);
    }

    public static String intToHex(int x, String separator) {
        int[] b = intToInts(x);
        return byteToHex(b[0]) + separator + byteToHex(b[1]) + separator +
               byteToHex(b[2]) + separator + byteToHex(b[3]);
    }

    public static String intToStr(int x) {
        int[] b = intToInts(x);
        return String.valueOf((char) b[0]) + String.valueOf((char) b[1]) +
               String.valueOf((char) b[2]) + String.valueOf((char) b[3]);
    }
}
