package workstation.zjyk.line.modle.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 实体类 - 物料分料
 */
public class PutStorageMaterielVO {


    /**
     * 工位id
     */
    private String workStationId;

    /**
     * 修改物料的详情
     */
    List<Idcount> idcountList = new ArrayList<>();

    public String getTrayCode() {
        return trayCode;
    }

    public void setTrayCode(String trayCode) {
        this.trayCode = trayCode;
    }

    /**
     * 托盘码
     */
    private String trayCode;

    public String getWorkStationId() {
        return workStationId;
    }

    public void setWorkStationId(String workStationId) {
        this.workStationId = workStationId;
    }

    public List<Idcount> getIdcountList() {
        return idcountList;
    }

    public void setIdcountList(List<Idcount> idcountList) {
        this.idcountList = idcountList;
    }


}
