package workstation.zjyk.workstation.modle.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import workstation.zjyk.workstation.ui.adapter.WSAdapterTrayList;

/**
 * Created by luoxw on 2016/8/10.
 */
public class WSLevel0Item extends AbstractExpandableItem<WSLevel1Item> implements MultiItemEntity {
    public String title;
    public String count;

    public WSLevel0Item(String title, String count) {
        this.count = count;
        this.title = title;
    }

    @Override
    public int getItemType() {
        return WSAdapterTrayList.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
