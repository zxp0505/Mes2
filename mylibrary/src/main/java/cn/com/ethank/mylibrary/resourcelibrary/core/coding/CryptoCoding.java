package cn.com.ethank.mylibrary.resourcelibrary.core.coding;


import cn.com.ethank.yunge.app.crypt.Native;

public class CryptoCoding extends AbstractCoding{

	@Override
	public byte[] encode(byte[] input) {
		return Native.encryptCommon(input);
	}

	@Override
	public byte[] decode(byte[] input) {
		return Native.decryptCommon(input);
	}

}
