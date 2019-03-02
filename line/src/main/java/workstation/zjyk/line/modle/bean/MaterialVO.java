package workstation.zjyk.line.modle.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class MaterialVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 物料编号
     */
    private String model;

    /**
     * 物料名称
     */
    private String name;

    /**
     * 数量
     */
    private double count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
