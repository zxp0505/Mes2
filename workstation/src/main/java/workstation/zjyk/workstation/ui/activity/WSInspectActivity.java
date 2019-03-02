package workstation.zjyk.workstation.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.ui.fragment.WSInspectTaskListFragment;

/**
 * 巡检
 */
public class WSInspectActivity extends WSBaseActivity {


    @BindView(R.id.fl_root)
    FrameLayout flRoot;
    private WSInspectTaskListFragment mInspectTaskListFragment;

    @Override
    protected void creatPresent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inspect;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void initOnCreate() {
        initFragment();
    }

    private void initFragment() {
        mInspectTaskListFragment = findFragment(WSInspectTaskListFragment.class);
        if (mInspectTaskListFragment == null) {
            mInspectTaskListFragment = WSInspectTaskListFragment.newInstance();
            loadRootFragment(R.id.fl_root, mInspectTaskListFragment);
        }
    }


}
