/**
 *  저장위치 : window7
 * 		C:\Users\shkr\AppData\Roaming\fileDownLoad\Local Store\#SharedObjects\fileDownLoad.swf/fileDownLoad.sol
 */
 
package
{
	
	import flash.net.SharedObject;
	
	public class SharedObjectUtil{
		
		private var so:SharedObject;
		
		public function SharedObjectUtil(fileNm:String, key:String, val : String):void{
			
			so = SharedObject.getLocal(fileNm);
			
			if(so.data.filePath == undefined || so.data.filePath == ""){
				createSo(key, val);
			}
			
		}
		
		public function createSo(key : String, val:String):void{
			setSo(key,val);
		}
		
		public function setSo(key:String, data:*):void{
			so.data[key] = data;
		}
		
		public function getSo(key:String):*{
			 return (so.data[key] != undefined) ? so.data[key] : "";
		}
		
		public function clearSo():void{
			so.clear();
		}
	
	}
	
}