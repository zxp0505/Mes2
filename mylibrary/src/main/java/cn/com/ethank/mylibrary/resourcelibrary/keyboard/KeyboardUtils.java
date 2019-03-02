package cn.com.ethank.mylibrary.resourcelibrary.keyboard;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import cn.com.ethank.mylibrary.R;

public class KeyboardUtils {

    public static void hide(EditText focusedView) {
        focusedView.setText("");
        focusedView.setFocusable(false);
        focusedView.setFocusableInTouchMode(false);
        focusedView.clearFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) focusedView.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
    }

    public static void hideTwo(EditText focusedView) {
        focusedView.setFocusable(false);
        focusedView.setFocusableInTouchMode(false);
        focusedView.clearFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) focusedView.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
    }

    public static void hideKeyboardOnly(EditText focusedView) {
//        focusedView.clearFocus();//edittext必须先clear焦点  否则键盘不会隐藏
        InputMethodManager inputMethodManager = (InputMethodManager) focusedView.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);//0InputMethodManager.HIDE_NOT_ALWAYS
    }

    public static void hideKeyboard(EditText focusedView) {
//        focusedView.clearFocus();//edittext必须先clear焦点  否则键盘不会隐藏
        InputMethodManager inputMethodManager = (InputMethodManager) focusedView.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    public static boolean isShown(Context c, View view) {
        return ((InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE)).isActive(view);
    }

    public static void show(EditText focusedView) {
        focusedView.setFocusable(true);
        focusedView.setFocusableInTouchMode(true);
        focusedView.requestFocus();
        InputMethodManager imm = (InputMethodManager) focusedView.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusedView, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);//HIDE_IMPLICIT_ONLY
    }

    /**
     * edittext光标控制
     *
     * @param editText
     * @param isShow
     */
    public static void showEditCursor(EditText editText, boolean isShow) {
        if (isShow) {
//            editText.setFocusable(true);
//            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            editText.setCursorVisible(true);
        } else {
//            editText.setFocusable(false);
//            editText.setFocusableInTouchMode(false);
            editText.clearFocus();
            editText.setCursorVisible(false);
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 只能手动输入得edittext
     * 禁止扫码枪输入
     *
     * @param editText
     */
    public static void setEdittextOnlyHandInput(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("addTextChangedListener","afterTextChanged");
                editText.removeTextChangedListener(this);
                Integer tag = (Integer) editText.getTag(R.id.tag_first);
                if (tag != null && tag == 1) {
                    s.replace(0, s.length(), "");
                    editText.setTag(R.id.tag_first,0);
                }
                editText.addTextChangedListener(this);
            }
        });
    }

    /**
     * 设置edittext输入去掉空格
     *
     * @param editText
     */
    public static void setEdittextInputNoSpace(EditText editText) {
        String regex = "[\u0020]";//空格
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("onTextChanged", s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("afterTextChanged", editable.toString());
                String str = editable.toString();
                String inputStr = str.replaceAll(regex, "");
                editText.removeTextChangedListener(this);
                // et.setText方法可能会引起键盘变化,所以用editable.replace来显示内容
                editable.replace(0, editable.length(), inputStr.trim());
                editText.addTextChangedListener(this);
            }
        });
    }
}