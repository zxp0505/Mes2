package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: ReturnWork
 * @Description: 返工记录
 * @author: yjw
 * @date: 2018年2月1日 下午5:15:47
 */
public class WSReturnOrRepair implements Serializable {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 总输出数
     */
    private int totalOutCount;

    /**
     * 总返回数
     */
    private int totalReturnCount;

    /**
     * 历史记录列表
     */
    private List<WSReturnOrRepairHistory> historyList;

    public int getTotalOutCount() {
        return totalOutCount;
    }

    public void setTotalOutCount(int totalOutCount) {
        this.totalOutCount = totalOutCount;
    }

    public int getTotalReturnCount() {
        return totalReturnCount;
    }

    public void setTotalReturnCount(int totalReturnCount) {
        this.totalReturnCount = totalReturnCount;
    }

    public List<WSReturnOrRepairHistory> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<WSReturnOrRepairHistory> historyList) {
        this.historyList = historyList;
    }
}
