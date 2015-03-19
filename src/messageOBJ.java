import java.io.Serializable;


public class messageOBJ implements Serializable{
	private String message;
	private String username;
	private String target;
	
	public messageOBJ(){
		message = null;
		username = null;
		target = null;
	}
	
	public void setMessageOBJMessage(String clientMessage){
		message = clientMessage;
	}
	
	public void setUsernameOBJMessage(String clientName){
		username = clientName;
	}
	
	public void setTargetOBJMessage(String clientTarget){
		target = clientTarget;
	}
	
	public String getMessageOBJMessage(){
		return message;
	}
	
	public String getUsernameOBJMessage(){
		return username;
	}
	
	public String getTargetOBJMessage(){
		return target;
	}
}
