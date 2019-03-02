package workstation.zjyk.workstation.util.dialog.callback;

/**
 * Created by ping on 2016/12/2.
 */

public interface WSDialogCallBackThree {
    /**
     * 确定
     */
    void OnPositiveClick(Object obj);

    /**
     * 扫描码校验
     *
     * @param code
     */
    void checkScanCode(String code);

    /**
     * 取消
     */
    void OnNegativeClick();
}
