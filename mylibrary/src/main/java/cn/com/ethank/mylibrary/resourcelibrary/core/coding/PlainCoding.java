package cn.com.ethank.mylibrary.resourcelibrary.core.coding;

public class PlainCoding extends AbstractCoding {

	@Override
	public byte[] encode(byte[] input) {
		return input;
	}

	@Override
	public byte[] decode(byte[] input) {
		return input;
	}

}
