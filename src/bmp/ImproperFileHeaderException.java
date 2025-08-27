package bmp;

@SuppressWarnings("serial")
public class ImproperFileHeaderException extends RuntimeException {
    public ImproperFileHeaderException() {
        super("Improper Bitmap File Header");
    }
    public ImproperFileHeaderException(String s) {
        super(s+"는 Bit Map파일이 아닙니다.");
    }
}
