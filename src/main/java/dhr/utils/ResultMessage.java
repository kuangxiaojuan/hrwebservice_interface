package dhr.utils;

public class ResultMessage {
	
	private String result;
	private String message;
	
	public ResultMessage(String result,String message){
		this.result = result;
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public String toString(){
		return JsonUtil.obj2json(this);
	}
	
}
