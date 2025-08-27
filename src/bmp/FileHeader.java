package bmp;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileHeader { // 14 bytes
    private final byte bfType0 = (byte)0x42; // = B
    private final byte bfType1 = (byte)0x4D; // = M
// 2 bytes bfType; = BM
    private int bfSize; // = size of file, 4 bytes unsigned
    private int bfReserved1; // reserved, 2 bytes = 0
    private int bfReserved2; // reserved, 2 bytes = 0
    private int bfOffBytes; // = the offset, 4 bytes, the starting byte of bitmap bits

    public FileHeader() {
        bfReserved1 = 0;
        bfReserved2 = 0;
        bfOffBytes = 0x36;
    }

    public FileHeader(long size) {
        this();
        bfSize = (int) size;
    }

    public FileHeader(byte[] h) {
        if(h.length != 14 || h[0] != bfType0 || h[1] != bfType1)
            throw new ImproperFileHeaderException();
        bfSize = Utility.bytesToInt(h[2], h[3], h[4], h[5]);
        bfReserved1 = Utility.bytesToInt(h[6], h[7]);
        bfReserved2 = Utility.bytesToInt(h[8], h[9]);
        bfOffBytes = Utility.bytesToInt(h[10], h[11], h[12], h[13]);
    }

    public FileHeader(int[] h) {
        if(h.length != 14 || h[0] != (int) bfType0 || h[1] != (int) bfType1)
            throw new ImproperFileHeaderException();
        bfSize = Utility.bytesToInt(h[2], h[3], h[4], h[5]);
        bfReserved1 = Utility.bytesToInt(h[6], h[7]);
        bfReserved2 = Utility.bytesToInt(h[8], h[9]);
        bfOffBytes = Utility.bytesToInt(h[10], h[11], h[12], h[13]);
    }

    public void setFileSize(long size) {
        bfSize = (int) size;
    }
    public long getFileSize() {return 0xffffffffl & bfSize;}
    public int getReserved1() {return bfReserved1;}
    public int getReserved2() {return bfReserved2;}
    public long getOffBytes() {return 0xffffffffl & bfOffBytes;}

    public String toHexString() {
        return "42 4d " + Utility.intToHex(bfSize) + " " +
               Utility.intToHex(bfReserved1).substring(0,6) +
               Utility.intToHex(bfReserved2).substring(0,6) +
               Utility.intToHex(bfOffBytes);
    }

    public String toFileString() {
        return "BM" + Utility.intToStr(bfSize) +
               Utility.intToStr(bfReserved1).substring(0,2) +
               Utility.intToStr(bfReserved2).substring(0,2) +
               Utility.intToStr(bfOffBytes);
    }

    public void appendTo(FileOutputStream fos) throws IOException {
        fos.write(bfType0);
        fos.write(bfType1);
        fos.write(Utility.intToBytes(bfSize));
        fos.write(Utility.intToBytes(bfReserved1), 0, 2);
        fos.write(Utility.intToBytes(bfReserved2), 0, 2);
        fos.write(Utility.intToBytes(bfOffBytes));
    }

    public String toString() {
        return "size = " + getFileSize() + ", offBytes = " + getOffBytes();
    }
}
