package workstation.zjyk.workstation.modle.bean;

/**
 * Created by zhangxiaoping on 2019/3/2 14:00
 */
public class WSWranInfo {
    String personName;
    String orderNumber;

    public String getPersonName() {
        return personName == null ? "" : personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getOrderNumber() {
        return orderNumber == null ? "" : orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
