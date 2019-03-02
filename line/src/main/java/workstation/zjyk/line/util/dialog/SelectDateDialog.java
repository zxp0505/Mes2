package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.util.DialogCallBackTwo;

/**
 * Created by zjgz on 2017/10/31.
 */

public class SelectDateDialog extends CommonDialog implements CalendarView.OnDateSelectedListener, CalendarView.OnMonthChangeListener, DialogInterface.OnDismissListener {
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    String title;
    DialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.tv_month_day)
    TextView tvMonthDay;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_lunar)
    TextView tvLunar;
    @BindView(R.id.ib_calendar)
    ImageView ibCalendar;
    @BindView(R.id.tv_current_day)
    TextView tvCurrentDay;
    @BindView(R.id.fl_current)
    FrameLayout flCurrent;
    @BindView(R.id.rl_tool)
    RelativeLayout rlTool;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    Calendar selectCalendar;
    @BindView(R.id.tv_recent)
    TextView tvRecent;

    public SelectDateDialog(Context context, String title, DialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView();
    }

    public SelectDateDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected SelectDateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }


    private void initDialogView() {
        setContentView(R.layout.dialog_select_date);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        tvYear.setVisibility(View.VISIBLE);
        tvLunar.setVisibility(View.VISIBLE);
        calendarView.setOnDateSelectedListener(this);
        calendarView.setOnMonthChangeListener(this);
        tvMonthDay.setText(calendarView.getCurMonth() + "月" + calendarView.getCurDay() + "日");
        tvCurrentDay.setText(calendarView.getCurDay() + "");
        setOnDismissListener(this);


    }

    Map<String, String> dateMap = new HashMap<>();

    public void setDateList(List<String> dateList) {
        dateMap.clear();
        for (int i = 0; i < dateList.size(); i++) {
            String date = dateList.get(i);
            date = date.replaceAll("-", "");
            dateMap.put(date, "");
        }
        int curMonth = calendarView.getCurMonth();
        int curYear = calendarView.getCurYear();
        calendarView.setDateMarkMap(dateMap);
        calendarView.setRange(curYear - 1, curMonth, curYear, curMonth+1);
    }


    @OnClick({R.id.btn_cancel, R.id.btn_sure, R.id.tv_current_day, R.id.tv_recent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dialogCallBackTwo.OnNegativeClick();
                dismiss();
                break;
            case R.id.btn_sure:
                if (selectCalendar != null && !isRecent) {
                    dialogCallBackTwo.OnPositiveClick(selectCalendar);
                    dismiss();
                } else if (isRecent) {
                    dialogCallBackTwo.OnPositiveClick("");
                    dismiss();
                } else {
                    ToastUtil.showInfoCenterShort("请选择查看的日期");
                }
                break;
            case R.id.tv_current_day:
                calendarView.scrollToCurrent();
                break;
            case R.id.tv_recent:
                setCurrentDay(null);
                break;
        }
    }


    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {
        selectCalendar = calendar;
        setCurrentDay(calendar);
//        tvYear.setText(calendar.getYear() + "");
//        tvLunar.setText(calendar.getMonth() + "");
    }

    private void setCurrentDay(Calendar calendar) {
        if (calendar != null) {
            isRecent = false;
            tvMonthDay.setText(calendar.getYear() + "年" + calendar.getMonth() + "月" + calendar.getDay() + "日");
        } else {
            isRecent = true;
            tvMonthDay.setText("近三个月");

        }
    }

    @Override
    public void onMonthChange(int year, int month) {
        tvYear.setText(year + "年");
        tvLunar.setText(month + "月");
    }

    boolean isRecent = false;//是否是最近三个月

    @Override
    public void onDismiss(DialogInterface dialog) {
        isRecent = false;
        calendarView.scrollToCurrent();
    }
}
