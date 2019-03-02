package workstation.zjyk.line.modle.bean;

/**
 * 实体类 — 线边库库存
 */
public class LineMaterielStorageManagerVo {
    /**
     *
     */
    int position;

    /**
     * 物料
     */
    private String materielId;

    /**
     * 外部系统唯一标识
     */
    private String oId;

    /**
     * 物料名称
     */
    private String name;

    /**
     * 物料型号
     */
    private String model;

    /**
     * 数量
     */
    private int count;

    /**
     * 修改数量
     */
    private int modifyCount;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getModifyCount() {
        return modifyCount;
    }

    public void setModifyCount(int modifyCount) {
        this.modifyCount = modifyCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMaterielId() {
        return materielId;
    }

    public void setMaterielId(String materielId) {
        this.materielId = materielId;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


}
