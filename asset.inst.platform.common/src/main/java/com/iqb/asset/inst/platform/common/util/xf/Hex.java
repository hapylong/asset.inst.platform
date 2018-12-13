package com.iqb.asset.inst.platform.common.util.xf;

import java.io.UnsupportedEncodingException;

public class Hex {
	public static final String DEFAULT_CHARSET_NAME = "UTF-8";
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	private final String charsetName;

	public static byte[] decodeHex(char[] data) throws CoderException {
		int len = data.length;

		if ((len & 0x1) != 0) {
			throw new CoderException("Odd number of characters.");
		}

		byte[] out = new byte[len >> 1];

		int i = 0;
		for (int j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f |= toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}

		return out;
	}

	public static char[] encodeHex(byte[] data) {
		return encodeHex(data, true);
	}

	public static char[] encodeHex(byte[] data, boolean toLowerCase) {
		return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	protected static char[] encodeHex(byte[] data, char[] toDigits) {
		int l = data.length;
		char[] out = new char[l << 1];

		int i = 0;
		for (int j = 0; i < l; i++) {
			out[(j++)] = toDigits[((0xF0 & data[i]) >>> 4)];
			out[(j++)] = toDigits[(0xF & data[i])];
		}
		return out;
	}

	public static String encodeHexString(byte[] data) {
		return new String(encodeHex(data));
	}

	protected static int toDigit(char ch, int index) throws CoderException {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new CoderException("Illegal hexadecimal character " + ch
					+ " at index " + index);
		}
		return digit;
	}

	public Hex() {
		this.charsetName = "UTF-8";
	}

	public Hex(String csName) {
		this.charsetName = csName;
	}

	public byte[] decode(byte[] array) throws CoderException {
		try {
			return decodeHex(new String(array, getCharsetName()).toCharArray());
		} catch (UnsupportedEncodingException e) {
			throw new CoderException(e.getMessage(), e);
		}
	}

	public Object decode(Object object) throws CoderException {
		try {
			char[] charArray = (object instanceof String) ? ((String) object)
					.toCharArray() : (char[]) (char[]) object;
			return decodeHex(charArray);
		} catch (ClassCastException e) {
			throw new CoderException(e.getMessage(), e);
		}
	}

	public byte[] encode(byte[] array) {
		String string = encodeHexString(array);
		if (string == null)
			return null;
		try {
			return string.getBytes(this.charsetName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object encode(Object object) throws CoderException {
		try {
			byte[] byteArray = (object instanceof String) ? ((String) object)
					.getBytes(getCharsetName()) : (byte[]) (byte[]) object;
			return encodeHex(byteArray);
		} catch (ClassCastException e) {
			throw new CoderException(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			throw new CoderException(e.getMessage(), e);
		}
	}

	public String getCharsetName() {
		return this.charsetName;
	}

	public String toString() {
		return super.toString() + "[charsetName=" + this.charsetName + "]";
	}
}