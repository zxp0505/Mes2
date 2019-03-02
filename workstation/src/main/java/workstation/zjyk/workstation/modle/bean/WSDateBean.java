package workstation.zjyk.workstation.modle.bean;

import com.haibin.calendarview.Calendar;

public class WSDateBean {
    private Calendar calendar;
    private String dateType;

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public String getDateType() {
        return dateType == null ? "" : dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }
}
