package workstation.zjyk.com.scanapp.modle.bean;

import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ExceptionHandleTypeEnum;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ExceptionTypeEnum;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ScanYesOrNoEnum;

/**
 * 质量处理接收实体
 *
 * @author Administrator
 */
public class ScanQualityHandleVO {
    /**
     * 处理人
     */
    private String personId;

    /**
     * 异常类型
     */
    private ExceptionTypeEnum exceptionType;

    /**
     * 异常处理方式
     */
    private ExceptionHandleTypeEnum handleType;

    /**
     * 是否需要补料
     */
    private ScanYesOrNoEnum needSupply;

    /**
     * 异常原因
     */
    private String reason;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public ExceptionTypeEnum getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionTypeEnum exceptionType) {
        this.exceptionType = exceptionType;
    }

    public ExceptionHandleTypeEnum getHandleType() {
        return handleType;
    }

    public void setHandleType(ExceptionHandleTypeEnum handleType) {
        this.handleType = handleType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ScanYesOrNoEnum getNeedSupply() {
        return needSupply;
    }

    public void setNeedSupply(ScanYesOrNoEnum needSupply) {
        this.needSupply = needSupply;
    }
}
