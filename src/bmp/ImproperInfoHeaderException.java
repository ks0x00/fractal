package bmp;

@SuppressWarnings("serial")
public class ImproperInfoHeaderException extends RuntimeException {
    public ImproperInfoHeaderException() {
        super("Improper Bitmap Info Header");
    }
}
