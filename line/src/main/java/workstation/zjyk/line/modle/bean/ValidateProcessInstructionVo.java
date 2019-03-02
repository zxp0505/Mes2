package workstation.zjyk.line.modle.bean;

/**
 * 实体类 - 校验 工艺图的md5
 * 
 */

public class ValidateProcessInstructionVo {


	protected String status;// 状态码
	
	protected String url;// 新地址

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}