package zjgz.mesapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjgz on 2017/11/18.
 */

public class My extends Activity {
    @BindView(R.id.tv_one)
    TextView tvOne;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tvOne.setText("首次测试");
        tvOne.setOnClickListener(v -> {

        });
    }
}
