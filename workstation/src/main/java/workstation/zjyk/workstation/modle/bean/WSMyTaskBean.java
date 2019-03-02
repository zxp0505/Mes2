package workstation.zjyk.workstation.modle.bean;

/**
 * Created by zjgz on 2017/12/7.
 */

public class WSMyTaskBean {
   private int workStationCondition;//0:正常 1：不正常
    private  String order;
    private  String product;//产品
    private  String procedure;//工序
    private  int  count;

    public int getWorkStationCondition() {
        return workStationCondition;
    }

    public void setWorkStationCondition(int workStationCondition) {
        this.workStationCondition = workStationCondition;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
