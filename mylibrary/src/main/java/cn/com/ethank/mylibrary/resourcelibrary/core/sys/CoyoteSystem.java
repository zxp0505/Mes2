package cn.com.ethank.mylibrary.resourcelibrary.core.sys;

public final class CoyoteSystem {

	private static ICoyoteSystem SYS;

	public static ICoyoteSystem getCurrent() {
		return SYS;
	}

	public static void setCurrent(ICoyoteSystem sys) {
		SYS = sys;
	}
}
