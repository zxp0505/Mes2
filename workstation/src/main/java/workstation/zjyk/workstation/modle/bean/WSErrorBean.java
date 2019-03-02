package workstation.zjyk.workstation.modle.bean;

/**
 * Created by zjgz on 2018/3/26.
 */

public class WSErrorBean {
    int errorCode;
    private String errorMessage;
    private Throwable throwable;
    private String dataMessage;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getDataMessage() {
        return dataMessage;
    }

    public void setDataMessage(String dataMessage) {
        this.dataMessage = dataMessage;
    }
}
