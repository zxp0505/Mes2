package workstation.zjyk.workstation.modle.bean.enumpackage;


public enum WSTrayBindTypeEnum implements BaseEnum {


    LINE_DISTRIBUTE_MATERIEL("线边库分发到工位的物料"),
    WORK_STATION_WIP("工位正常输出在制品"),
    WORK_STATION_REWORK_WIP("工位转返工在制品和物料"),
    WORK_STATION_REWORK_RETURN_WIP("工位返工任务返回在制品"),
    REPAIR_WORK_STATION_WIP("维修转工位在制品和物料"),
    REPAIR_WORK_STATION_RETURN_WIP("维修返回工位在制品和物料"),
    QUALITY_RETURN_MATERIEL("质量返回物料"),

    LINE_EXCEPTION_MATERIEL("线边库异常输出物料"),
    WORK_STATION_EXCEPTION_MATERIEL("工位异常输出物料"),
    WORK_STATION_REPAIR_WIP("工位转维修在制品"),
    WORK_STATION_ASSIST_RETURN_WIP("工位辅助任务返回在制品"),
    QUALITY_TO_CLERK_MATERIEL("质量转文员物料"),
    FQA_TO_REPAIR("FQA转维修不合格品"),
    WORK_STATION_TO_FQA("工位转FQA待检验"),
    REPAIR_TO_FQA("维修转FQA待检验"),
    WORK_STATION_WIP_OR_MATERIEL("工位接收的在制品和物料");// 工位接收转移后的托盘绑定状态


    private final String key;

    WSTrayBindTypeEnum(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

