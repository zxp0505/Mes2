package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: WSReworkTrayInfo
 * @Description: 返工托盘信息
 * @author: yjw
 * @date: 2018年1月12日 上午11:41:12
 */
public class WSReworkTrayInfo implements Serializable {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 工位名称
     */
    private List<WSWorkStationInfo> workStationList;


    public List<WSWorkStationInfo> getWorkStationList() {
        return workStationList;
    }

    public void setWorkStationList(List<WSWorkStationInfo> workStationList) {
        this.workStationList = workStationList;
    }

}
