package bmp;

@SuppressWarnings("serial")
public class Not24BitException extends RuntimeException {
    public Not24BitException() {
        super("Bit Map is not 24 bit true color");
    }

    public Not24BitException(String s) {
        super("Bit Map File " + s + "는 24 bit true color BMP가 아닙니다.");
    }
}
