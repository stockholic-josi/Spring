package
{
 [RemoteClass(alias="com.ufancy.chat.server.ChatMsg")]
 
 public class ChatMsg{
  
	  public var cmd:int;
	  public var msg:String;
	  public var userId:String;
  
	  public function getCmd():int{
	   	return cmd;
	  }
  
	  public function setCmd(cmd:int):void{
	   	this.cmd = cmd;
	  }
	  
	  public function getMsg():String{
	   	return msg;
	  }
	  
	  public function setMsg(msg:String):void{
	   	this.msg = msg;
	  }
	  
	  public function geUserId():String{
	   	return userId;
	  }
	  
	  public function setUserId(userId:String):void{
	   	this.userId = userId;
	  }
  
 	}
}