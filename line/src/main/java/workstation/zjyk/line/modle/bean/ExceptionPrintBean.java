package workstation.zjyk.line.modle.bean;

import java.util.List;

/**
 * Created by zjgz on 2017/11/14.
 */

public class ExceptionPrintBean {
//    serialNumber:--订单生产编号
//    workStationName:--工位名称
//    personName:--人员姓名
//    code:--异常编码
//    materielList:{--物料清单
//        oid:--物料编号
//        name:--物料名称
//        model:--物料型号

    private String serialNumber;
    private String workStationName;
    private String personName;
    private String code;
    private List<ExceptionDetailBean> materielList;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getWorkStationName() {
        return workStationName;
    }

    public void setWorkStationName(String workStationName) {
        this.workStationName = workStationName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ExceptionDetailBean> getMaterielList() {
        return materielList;
    }

    public void setMaterielList(List<ExceptionDetailBean> materielList) {
        this.materielList = materielList;
    }
}
