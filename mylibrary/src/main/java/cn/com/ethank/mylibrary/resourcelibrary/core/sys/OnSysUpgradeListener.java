package cn.com.ethank.mylibrary.resourcelibrary.core.sys;

public interface OnSysUpgradeListener {
	void onSysUpgraded(int currentBuild, int previousBuild);
}
