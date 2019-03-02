package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: WSWipTray
 * @Description: 在制品在那些托盘
 * @author: yjw
 * @date: 2017年12月20日 上午11:06:03
 */
public class WSWipTray implements Serializable {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 工序名称
     */
    private String procedureName;

    /**
     * 托盘集合
     */
    private List<WSTray> trayList;

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public List<WSTray> getTrayList() {
        return trayList;
    }

    public void setTrayList(List<WSTray> trayList) {
        this.trayList = trayList;
    }
}
