package workstation.zjyk.line.modle.bean;

import java.io.Serializable;

public class BaseBean<T> implements Serializable {

	private int code;
	private T data;
	private int total;
	private String message;


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
