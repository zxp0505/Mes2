package cn.com.ethank.mylibrary.resourcelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

/**
 * Created by zjgz on 2018/2/7.
 */

public class NavationUtil {
    public static void toggleHideyBar(Activity context) {

        context.getWindow().getDecorView().setSystemUiVisibility(

                         View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        //                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

//        // BEGIN_INCLUDE (get_current_ui_flags)
//        // The UI options currently enabled are represented by a bitfield.
//        // getSystemUiVisibility() gives us that bitfield.
//        int uiOptions = context.getWindow().getDecorView().getSystemUiVisibility();
//        int newUiOptions = uiOptions;
//        // END_INCLUDE (get_current_ui_flags)
//        // BEGIN_INCLUDE (toggle_ui_flags)
//        boolean isImmersiveModeEnabled =
//                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
//        if (isImmersiveModeEnabled) {
//            Log.i("123", "Turning immersive mode mode off. ");
//        } else {
//            Log.i("123", "Turning immersive mode mode on.");
//        }
//
//        // Navigation bar hiding:  Backwards compatible to ICS.
//        if (Build.VERSION.SDK_INT >= 14) {
//            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//        }
//
//        // Status bar hiding: Backwards compatible to Jellybean
//        if (Build.VERSION.SDK_INT >= 16) {
////            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
//        }
//
//        // Immersive mode: Backward compatible to KitKat.
//        // Note that this flag doesn't do anything by itself, it only augments the behavior
//        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
//        // all three flags are being toggled together.
//        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
//        // Sticky immersive mode differs in that it makes the navigation and status bars
//        // semi-transparent, and the UI flag does not get cleared when the user interacts with
//        // the screen.
//        if (Build.VERSION.SDK_INT >= 18) {
//            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//        }
//
//        context.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);//上边状态栏和底部状态栏滑动都可以调出状态栏
////        context.getWindow().getDecorView().setSystemUiVisibility(4108);//这里的4108可防止从底部滑动调出底部导航栏
//        //END_INCLUDE (set_ui_flags)
    }
}
