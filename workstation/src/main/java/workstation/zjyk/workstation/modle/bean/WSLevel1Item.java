package workstation.zjyk.workstation.modle.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import workstation.zjyk.workstation.ui.adapter.WSAdapterTrayList;

/**
 * Created by luoxw on 2016/8/10.
 */

public class WSLevel1Item implements MultiItemEntity {
    public String title;
    public String count;
    public String number;//编号

    public WSLevel1Item(String title, String count) {
        this.count = count;
        this.title = title;
    }

    public WSLevel1Item(String title, String count, String number) {
        this.count = count;
        this.title = title;
        this.number = number;
    }

    @Override
    public int getItemType() {
        return WSAdapterTrayList.TYPE_LEVEL_1;
    }

}