/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package workstation.zjyk.com.scanapp.ui.loading;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import workstation.zjyk.com.scanapp.R;


public class VaryViewHelperController {

    private IVaryViewHelper helper;

    public VaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    public VaryViewHelperController(IVaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    public void showNetworkError(View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.ver_net_error_message);
        TextView textView = layout.findViewById(R.id.message_info);
        textView.setText(helper.getContext().getResources().getString(R.string.common_no_network_msg));

        ImageView imageView = layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.drawable.error_net);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showError(String errorMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.ver_loadinglib_message);
        TextView textView = layout.findViewById(R.id.message_info);
        if (!TextUtils.isEmpty(errorMsg)) {
            textView.setText(errorMsg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string.common_error_msg));
        }

        ImageView imageView = layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.drawable.ic_error);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showEmpty(String emptyMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.ver_loadinglib_message);
        TextView textView = layout.findViewById(R.id.message_info);
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string.common_empty_msg));
        }

        ImageView imageView = layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.drawable.empty_data);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }


    public void showEmpty(int resourceId, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.ver_loadinglib_message);
        TextView textView = layout.findViewById(R.id.message_info);
        textView.setText("");

        ImageView imageView = layout.findViewById(R.id.message_icon);
        imageView.setImageResource(resourceId);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showEmpty(int resourceId, int reqWidth, int reqHeight, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.ver_loadinglib_message);
        TextView textView = layout.findViewById(R.id.message_info);
        textView.setText("");

        ImageView imageView = layout.findViewById(R.id.message_icon);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(reqWidth * 2, reqHeight * 2));
//        Glide.with(imageView.getContext()).load(resourceId).override(reqWidth, reqHeight).into(imageView);

        RequestOptions options = new RequestOptions();
//        options.placeholder(R.drawable.image);
        Glide.with(imageView.getContext()).load(resourceId).apply(options).into(imageView);
        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showLoading(String msg) {
        View layout = helper.inflate(R.layout.ver_loadinglib_loading);
        if (!TextUtils.isEmpty(msg)) {
            TextView textView = layout.findViewById(R.id.loading_msg);
            textView.setText(msg);
        }
        helper.showLayout(layout);
    }

    public void restore() {
        helper.restoreView();
    }
}
