package cn.ohalo.result.data;

public class Result {

	public Result(boolean success, String msg, Object obj) {
		super();
		this.success = success;
		this.msg = msg;
		this.obj = obj;
	}

	public Result() {
	}

	boolean success;

	String msg;

	private Object obj;

	public static Result success(String msg,Object obj){
		return new Result(true,msg,obj);
	}

	public static Result error(String msg,Object obj){
		return new Result(false,msg,obj);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}