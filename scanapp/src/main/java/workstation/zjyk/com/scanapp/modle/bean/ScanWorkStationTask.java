package workstation.zjyk.com.scanapp.modle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ScanOrderStatusEnum;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ScanTaskTypeEnum;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ScanWorkConditionStatusEnum;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ScanWorkStationTaskStatusEnum;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ScanYesOrNoEnum;

/**
 * @ClassName: WSWorkStationTask
 * @Description: 工位任务
 * @author: yjw
 * @date: 2017年12月20日 上午10:02:01
 */
public class ScanWorkStationTask implements Parcelable {

    /**
     * 生产序号
     */
    private String productNumber;

    /**
     * 产品型号
     */
    private String productModelTypeName;

    /**
     * 工作条件是否就绪
     */
    private ScanWorkConditionStatusEnum workConditionStatus;

    /**
     * 订单生产编号
     */
    private String serialNumber;

    /**
     * 货号
     */
    private String model;

    /**
     * 工序名称
     */
    private String procedureName;

    /**
     * 任务数量(计划生产数)
     */
    private int planCount;

    /**
     * 是否是首件
     */
    private ScanYesOrNoEnum firstProduct;

    /**
     * 任务类型
     */
    private ScanTaskTypeEnum taskType;
    /**
     * 历史任务---对应的item---任务类型
     */
    private ScanTaskTypeEnum historyTaskType;

    /**
     * 是否置顶
     */
    private int isTop; //0不置顶 1置顶

    /**
     * 任务id
     */
    private String id;
    /**
     * 是否着急发货
     */
    private ScanYesOrNoEnum worryDistribute;

    /**
     * 任务状态
     */
    private ScanWorkStationTaskStatusEnum stationTaskStatusEnum;
    /**
     * 订单状态(任务状态)
     */
    private ScanOrderStatusEnum status;

    /**
     * 交付时间（交货时间）
     */
    private String deliverTime;

    /**
     * 任务下达时间
     */
    private String taksStartTime;
    /**
     * 已经输出数量
     */
    private int workStationOutHistoryCount;

    /**
     * 描述
     */
    private String description;

    public ScanYesOrNoEnum getWorryDistribute() {
        return worryDistribute;
    }

    public void setWorryDistribute(ScanYesOrNoEnum worryDistribute) {
        this.worryDistribute = worryDistribute;
    }

    public ScanWorkStationTaskStatusEnum getStationTaskStatusEnum() {
        return stationTaskStatusEnum;
    }

    public void setStationTaskStatusEnum(ScanWorkStationTaskStatusEnum stationTaskStatusEnum) {
        this.stationTaskStatusEnum = stationTaskStatusEnum;
    }

    public ScanWorkConditionStatusEnum getWorkConditionStatus() {
        return workConditionStatus;
    }

    public void setWorkConditionStatus(ScanWorkConditionStatusEnum workConditionStatus) {
        this.workConditionStatus = workConditionStatus;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductModelTypeName() {
        return productModelTypeName;
    }

    public void setProductModelTypeName(String productModelTypeName) {
        this.productModelTypeName = productModelTypeName;
    }

    public int getPlanCount() {
        return planCount;
    }

    public void setPlanCount(int planCount) {
        this.planCount = planCount;
    }

    public ScanYesOrNoEnum getFirstProduct() {
        return firstProduct;
    }

    public void setFirstProduct(ScanYesOrNoEnum firstProduct) {
        this.firstProduct = firstProduct;
    }

    public ScanTaskTypeEnum getTaskType() {
        return taskType;
    }

    public void setTaskType(ScanTaskTypeEnum taskType) {
        this.taskType = taskType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ScanOrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ScanOrderStatusEnum status) {
        this.status = status;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getTaksStartTime() {
        return taksStartTime;
    }

    public void setTaksStartTime(String taksStartTime) {
        this.taksStartTime = taksStartTime;
    }

    public int getWorkStationOutHistoryCount() {
        return workStationOutHistoryCount;
    }

    public void setWorkStationOutHistoryCount(int workStationOutHistoryCount) {
        this.workStationOutHistoryCount = workStationOutHistoryCount;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public ScanTaskTypeEnum getHistoryTaskType() {
        return historyTaskType;
    }

    public void setHistoryTaskType(ScanTaskTypeEnum historyTaskType) {
        this.historyTaskType = historyTaskType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productNumber);
        dest.writeString(this.productModelTypeName);
        dest.writeInt(this.workConditionStatus == null ? -1 : this.workConditionStatus.ordinal());
        dest.writeString(this.serialNumber);
        dest.writeString(this.model);
        dest.writeString(this.procedureName);
        dest.writeInt(this.planCount);
        dest.writeInt(this.firstProduct == null ? -1 : this.firstProduct.ordinal());
        dest.writeInt(this.taskType == null ? -1 : this.taskType.ordinal());
        dest.writeInt(this.historyTaskType == null ? -1 : this.historyTaskType.ordinal());
        dest.writeInt(this.isTop);
        dest.writeString(this.id);
        dest.writeInt(this.worryDistribute == null ? -1 : this.worryDistribute.ordinal());
        dest.writeInt(this.stationTaskStatusEnum == null ? -1 : this.stationTaskStatusEnum.ordinal());
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
        dest.writeString(this.deliverTime);
        dest.writeString(this.taksStartTime);
        dest.writeInt(this.workStationOutHistoryCount);
        dest.writeString(this.description);
    }

    public ScanWorkStationTask() {
    }

    protected ScanWorkStationTask(Parcel in) {
        this.productNumber = in.readString();
        this.productModelTypeName = in.readString();
        int tmpWorkConditionStatus = in.readInt();
        this.workConditionStatus = tmpWorkConditionStatus == -1 ? null : ScanWorkConditionStatusEnum.values()[tmpWorkConditionStatus];
        this.serialNumber = in.readString();
        this.model = in.readString();
        this.procedureName = in.readString();
        this.planCount = in.readInt();
        int tmpFirstProduct = in.readInt();
        this.firstProduct = tmpFirstProduct == -1 ? null : ScanYesOrNoEnum.values()[tmpFirstProduct];
        int tmpTaskType = in.readInt();
        this.taskType = tmpTaskType == -1 ? null : ScanTaskTypeEnum.values()[tmpTaskType];
        int tmpHistoryTaskType = in.readInt();
        this.historyTaskType = tmpHistoryTaskType == -1 ? null : ScanTaskTypeEnum.values()[tmpHistoryTaskType];
        this.isTop = in.readInt();
        this.id = in.readString();
        int tmpWorryDistribute = in.readInt();
        this.worryDistribute = tmpWorryDistribute == -1 ? null : ScanYesOrNoEnum.values()[tmpWorryDistribute];
        int tmpStationTaskStatusEnum = in.readInt();
        this.stationTaskStatusEnum = tmpStationTaskStatusEnum == -1 ? null : ScanWorkStationTaskStatusEnum.values()[tmpStationTaskStatusEnum];
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : ScanOrderStatusEnum.values()[tmpStatus];
        this.deliverTime = in.readString();
        this.taksStartTime = in.readString();
        this.workStationOutHistoryCount = in.readInt();
        this.description = in.readString();
    }

    public static final Creator<ScanWorkStationTask> CREATOR = new Creator<ScanWorkStationTask>() {
        @Override
        public ScanWorkStationTask createFromParcel(Parcel source) {
            return new ScanWorkStationTask(source);
        }

        @Override
        public ScanWorkStationTask[] newArray(int size) {
            return new ScanWorkStationTask[size];
        }
    };
}
