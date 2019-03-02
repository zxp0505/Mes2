package cn.com.ethank.mylibrary.resourcelibrary.core.coding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	public static String md5Encoding(byte[] content) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(content);
			return toHex(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String toHex(byte[] buf) {

		StringBuilder sBuilder = new StringBuilder(MD5Util.MD5LENGTH * 2);
		for (int i = 0; i < buf.length; i++) {
			int hi = buf[i] >> 4 & 0x0f;
			int lo = buf[i] & 0x0f;
			sBuilder.append((char) (hi > 9 ? (hi - 10 + 'a') : (hi + '0')));
			sBuilder.append((char) (lo > 9 ? (lo - 10 + 'a') : (lo + '0')));
		}
		return sBuilder.toString();

	}

	public static String getMd5ByFile(File file) throws FileNotFoundException {
		String value = null;
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public static final int MD5LENGTH = 64;
}
