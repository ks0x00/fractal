package bmp;

import java.io.FileOutputStream;
import java.io.IOException;

public class InfoHeader { // 40 bytes
                          // see http://www.microsoft.com
                          // MSDN Home > MSDN Library > Windows GDI > Bitmaps >
                          // Bitmap Reference > Bitmap Structures
                          // BITMAPINFOHEADER
                          // see also BITMAPV4HEADER and BITMAPV5HEADER
    private int biSize; // size of infoheader, unsigned 4 bytes
    private int biWidth; // width of bitmap by pixel, 4 bytes
    private int biHeight; // height of bitmap by pixel, 4 bytes
             // if positive, the bitmap is bottom-up DIB and its origin is the lower-left corner
             //              can be compressed
             // if negative, the bitmap is a top-down DIB and its origin is the upper-left corner
             //              cannot be compressed
    private int biPlanes; // number of planes = 1, 2 bytes
    private int biBitCount; // number of bits-per-pixel, unsigned 2 bytes
                    // possible values
                    //   0: JPEG or PNG format
                    //   1: monochrome
                    //   4: 16 colors
                    //   8: 256 colors
                    //   16: 2^16 colors
                    //   24: 2^24 colors
                    //   32: 2^32 colors
    private int biCompression; // the type of compression for a compressed bottom-up bitmap,
                       //  4 bytes
                       // possible values
                       // BI_RGB = 0: uncompressed
                       // BI_RLE8 = ?: run-length encoded(RLE) format fo bitmaps with 8 bpp
                       // BI_RLE4 = ?: run-length encoded(RLE) format fo bitmaps with 4 bpp
                       // BI_BITFIELDS = ?: not compressed and ...
                       // BI_JPG = ?: JPEG image
                       // BI_PNG = ?: PNG image
    private int biSizeImage; // size, in bytes, of image, unsigned 4 bytes
                // may be set to zero for BI_RGB bitmaps
    private int biXPelsPerMeter; // horizontal resolution, in pixels-per-meter, 4 bytes
    private int biYPelsPerMeter; // vertical resolution, in pixels-per-meter, 4 bytes
    private int biClrUsed; //number of color indexes in the color table
                // that are actually used by the bit map, 4 bytes
                // if biBitCount >= 16, biClrUsed = 0
    private int biClrImportant; // number of color indexes that are required for displaying
                // the bitmap, 4 bytes

    public InfoHeader() { // only for 24-bit color bit map
        biSize = 40;
//        biWidth, biHeight
        biPlanes = 1;
        biBitCount = 24;
        biCompression = 0;
        biSizeImage = 0;
        biXPelsPerMeter = 0xEC4;
        biYPelsPerMeter = 0xEC4;
        biClrUsed = 0;
        biClrImportant = 0;
    }

    public InfoHeader(long width, long height) {
        this();
        biWidth = (int) width;
        biHeight = (int) height;
    }

    public InfoHeader(byte[] b) {
        if(b.length != 40) throw new ImproperInfoHeaderException();
        biSize = Utility.bytesToInt(b[0], b[1], b[2], b[3]);
        biWidth = Utility.bytesToInt(b[4], b[5], b[6], b[7]);
        biHeight = Utility.bytesToInt(b[8], b[9], b[10], b[11]);
        biPlanes = Utility.bytesToInt(b[12], b[13]);
        biBitCount = Utility.bytesToInt(b[14], b[15]);
        biCompression = Utility.bytesToInt(b[16], b[17], b[18], b[19]);
        biSizeImage = Utility.bytesToInt(b[20], b[21], b[22], b[23]);
        biXPelsPerMeter = Utility.bytesToInt(b[24], b[25], b[26], b[27]);
        biYPelsPerMeter = Utility.bytesToInt(b[28], b[29], b[30], b[31]);
        biClrUsed = Utility.bytesToInt(b[32], b[33], b[34], b[35]);
        biClrImportant = Utility.bytesToInt(b[36], b[37], b[38], b[39]);
    }

    public InfoHeader(int[] b) {
        if(b.length != 40) throw new ImproperInfoHeaderException();
        biSize = Utility.bytesToInt(b[0], b[1], b[2], b[3]);
        biWidth = Utility.bytesToInt(b[4], b[5], b[6], b[7]);
        biHeight = Utility.bytesToInt(b[8], b[9], b[10], b[11]);
        biPlanes = Utility.bytesToInt(b[12], b[13]);
        biBitCount = Utility.bytesToInt(b[14], b[15]);
        biCompression = Utility.bytesToInt(b[16], b[17], b[18], b[19]);
        biSizeImage = Utility.bytesToInt(b[20], b[21], b[22], b[23]);
        biXPelsPerMeter = Utility.bytesToInt(b[24], b[25], b[26], b[27]);
        biYPelsPerMeter = Utility.bytesToInt(b[28], b[29], b[30], b[31]);
        biClrUsed = Utility.bytesToInt(b[32], b[33], b[34], b[35]);
        biClrImportant = Utility.bytesToInt(b[36], b[37], b[38], b[39]);
    }

    public void setWidth(long width) {
        biWidth = (int) width;
    }

    public void setHeight(long height) {
        biHeight = (int) height;
    }

    public long getSize() {return 0xffffffffL & biSize;}
    public long getWidth() {return 0xffffffffL & biWidth;}
    public long getHeight() {return 0xffffffffL & biHeight;}
    public int getPlanes() {return biPlanes;}
    public int getBitCount() { return biBitCount;}
    public int getCompression() {return biCompression;}
    public long getImageSize() {return 0xffffffffL & biSizeImage;}
    public long getXPelsPerMeter() {return 0xffffffffL & biXPelsPerMeter;}
    public long getYPelsPerMeter() {return 0xffffffffL & biYPelsPerMeter;}
    public int getColorUsed() {return biClrUsed;}
    public int getColorImportant() {return biClrImportant;}

    public String toHexString() {
        return Utility.intToHex(biSize) + " " + Utility.intToHex(biWidth) + " " +
               Utility.intToHex(biHeight) + " " +
               Utility.intToHex(biPlanes).substring(0, 6) +
               Utility.intToHex(biBitCount).substring(0, 6) +
               Utility.intToHex(biCompression) + " " + Utility.intToHex(biSizeImage) + " " +
               Utility.intToHex(biXPelsPerMeter) + " " +
               Utility.intToHex(biYPelsPerMeter) + " " +
               Utility.intToHex(biClrUsed) + " " + Utility.intToHex(biClrImportant);
    }

    public String toFileString() {
        return Utility.intToStr(biSize) + Utility.intToStr(biWidth) +
               Utility.intToStr(biHeight) + Utility.intToStr(biPlanes).substring(0, 2) +
               Utility.intToStr(biBitCount).substring(0, 2) +
               Utility.intToStr(biCompression) + Utility.intToStr(biSizeImage) +
               Utility.intToStr(biXPelsPerMeter) +
               Utility.intToStr(biYPelsPerMeter) +
               Utility.intToStr(biClrUsed) + Utility.intToStr(biClrImportant);
    }

    public void appendTo(FileOutputStream fos) throws IOException {
        fos.write(Utility.intToBytes(biSize));
        fos.write(Utility.intToBytes(biWidth));
        fos.write(Utility.intToBytes(biHeight));
        fos.write(Utility.intToBytes(biPlanes), 0, 2);
        fos.write(Utility.intToBytes(biBitCount), 0, 2);
        fos.write(Utility.intToBytes(biCompression));
        fos.write(Utility.intToBytes(biSizeImage));
        fos.write(Utility.intToBytes(biXPelsPerMeter));
        fos.write(Utility.intToBytes(biYPelsPerMeter));
        fos.write(Utility.intToBytes(biClrUsed));
        fos.write(Utility.intToBytes(biClrImportant));
    }

}
