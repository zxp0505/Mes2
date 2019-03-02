package cn.com.ethank.yunge.app.crypt;



public class CryptoCoding extends AbstractCoding{

	@Override
	public byte[] encode(byte[] input) {
		return Native.encryptCommon(input);
//		return YungeEncrypt.encode(input);
	}

	@Override
	public byte[] decode(byte[] input) {
		return Native.decryptCommon(input);
//		return YungeEncrypt.decode(input);
	}

}
