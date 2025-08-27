package fractal;

public class Bit {
	public static final int exchange4(int x) {
		return ((x & 0xF0F0F) << 4) | ((x >> 4) & 0xF0F0F);
	}

	public static final int revExchange4(int x) {
		x = ((x & 0xFFFF) << 16) | ((x >> 16) & 0xFFFF);
		x = ((x & 0xFF00FF) << 8) | ((x >> 8) & 0xFF00FF);
		x = ((x & 0xF0F0F0F) << 4) | ((x >> 4) & 0xF0F0F0F);
		return (((x & 0x33333333) << 2) | ((x >> 2) & 0x33333333)) >>> 8;
	}

	public static final int rev8Bit(int x) { // 0123 4567
		x = ((x & 0xF) << 4) | ((x >> 20) & 0xF); // 4567 0123
		x = ((x & 0x33) << 2) | ((x >> 2) & 0x33); // 6745 2301
		return ((x & 0x55) << 1) | ((x >> 1) & 0x55); // 7654 3210
	}

	public static final int rev24Bit(int x) {
		x = ((x & 0xFFFF) << 16) | ((x >> 16) & 0xFFFF);
		x = ((x & 0xFF00FF) << 8) | ((x >> 8) & 0xFF00FF);
		x = ((x & 0xF0F0F0F) << 4) | ((x >> 4) & 0xF0F0F0F);
		x = ((x & 0x33333333) << 2) | ((x >> 2) & 0x33333333);
		return (((x & 0x55555555) << 1) | ((x >> 1) & 0x55555555)) >>> 8;
	}
}
