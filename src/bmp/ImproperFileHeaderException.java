package bmp;

@SuppressWarnings("serial")
public class ImproperFileHeaderException extends RuntimeException {
    public ImproperFileHeaderException() {
        super("Improper Bitmap File Header");
    }
    public ImproperFileHeaderException(String s) {
        super(s+"�� Bit Map������ �ƴմϴ�.");
    }
}
