package workstation.zjyk.line.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import workstation.zjyk.line.R;


/**
 * Created by ping on 2016/12/2.
 */

public class DialogUtil {


    public static class Holder {
        private static final DialogUtil dialogUtil = new DialogUtil();
    }

    public static DialogUtil getInstance() {
        return Holder.dialogUtil;
    }

    public static void showDialog(Context context, String title, final DialogCallBack callBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_dialog, null);
        TextView tv_dialog_title = view.findViewById(R.id.tv_dialog_title);
        tv_dialog_title.setText(title);
        TextView bt_cancle = view.findViewById(R.id.tv_cancle);//取消
        TextView bt_sure = view.findViewById(R.id.tv_sure);//确定
        builder.setView(view);

        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();
        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.OnPositiveClick();
                dialog.dismiss();
            }
        });
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.OnNegativeClick();
                dialog.dismiss();

            }
        });
    }


}
