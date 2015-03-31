<%


BufferedReader bf = request.getReader();

if(bf == null){
	System.out.println("buffer = null");
	bf= null;
}	
Object obj = JSONValue.parse(bf); 
 
JSONObject jobj = (JSONObject)obj;
if(jobj==null){
	System.out.println("jobj = null");
	jobj = null;
 }
Map historyURL = new HashMap<>();
Iterator it = jobj.keySet().iterator();
while(it.hasNext()){
	String key = (String) it.next();
	String visitTime = key.substring(0, key.lastIndexOf('.'));
	historyURL.put(visitTime,(String)jobj.get(key));
}


%>