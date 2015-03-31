var $treeContent;
$.fn.treeHandler = function() {
	
	var data = [
				   {"data":"Server","attr":{"id":"root","class":" ","rel":"drive"},"state":"close"},
				   {"data":"Spring","attr":{"id":"root","class":" ","rel":"drive"},"state":"close"},
				   {"data":"Java","attr":{"id":"root","class":" ","rel":"drive"},"state":"close"},
				   {"data":"JQuery","attr":{"id":"root","class":" ","rel":"drive"},"state":"close"},
				   {"data":"Javascript","attr":{"id":"root","class":" ","rel":"drive"},"state":"close"},
				   {"data":"Community","attr":{"id":"root","class":" ","rel":"drive"},"state":"close"},
				   {"data":"Link","attr":{"id":"root","class":" ","rel":"drive"},"state":"close"},
				   {"data":"System","attr":{"id":"root","class":" ","rel":"drive"},"state":"close"},
				];
	
	$treeContent = this;
	
	$.ajax({
    	url: "/data/treeMenu.json",
    	type : 'POST',
    	dataType: 'JSON',
		error: function(){
        	tree.getTreeData($treeContent,data)
    	},
		success: function(data){
			tree.getTreeData($treeContent,data)
		}
	});
	 
};

var tree ={
		
	getTreeData : function(obj,data){
		obj.jstree({ 
			
			"json_data" : {
				"data" : data
			},

			"types" : {
				"max_depth" : -2,
				"max_children" : -2,
				"valid_children" : [ "drive" ],
				"types" : {
					// The default type
					"default" : {
						"valid_children" : "none",
						"icon" : {
							"image" : "/js/tree/file.png"
						}
					},
					// The `folder` type
					"folder" : {
						"valid_children" : [ "default", "folder" ],
						"icon" : {
							"image" : "/js/tree/folder.png"
						}
					},
					// The `drive` nodes 
					"drive" : {
						"valid_children" : [ "default", "folder" ],
						"icon" : {
//							"image" : "/js/tree/root.png"
							"image" : "/js/tree/folder.png"
						},
						// those options prevent the functions with the same name to be used on the `drive` type nodes
						// internally the `before` event is used
						"start_drag" : false,
						"move_node" : false,
						"delete_node" : false,
						"remove" : false
					}
				}
			},

	    	"crrm" : { 
	    		"move" : {
	    			"check_move" : function (m) { 
	    				var p = this._get_parent(m.o);
	    				if(!p) return false;
	    				p = p == -1 ? this.get_container() : p;
	    				if(p === m.np) return true;
	    				if(p[0] && m.np[0] && p[0] === m.np[0]) return true;
	    				return false;
	    			}
	    		}
	    	},
	    	"dnd" : {
	    		"drop_target" : false,
	    		"drag_target" : false
	    	},
	    
	    	"themes" : { 
	    			"theme" : "classic", 
	    			"dots" : true, 
	    			"icons" : true 
			},
	    
	    	"plugins" : [ "themes", "json_data","dnd","ui","crrm","contextmenu","types","hotkeys"]
		})
		.bind("create.jstree", function (e, data) {
			 tree.setTreeData();
	/*
			alert( data.rslt.obj.children("a:eq(0)").attr("href") );
			alert( data.rslt.parent.attr("id") );
			data.rslt.obj.attr("id",LPad(data.rslt.position + "",2,"0"));
			alert(data.rslt.obj.attr("id"));
			alert(data.rslt.obj.attr("rel"));
	*/
		})
		.bind("select_node.jstree", function (e, data) { 
			var url = data.rslt.obj.attr("id"); 
			
			if(data.rslt.obj.attr("rel") == 'default' && url ){
				var n = url.indexOf(":");
				
				if(url.substring(0,n) == "file"){
					tree.goEditor(url.substring(n+1));
				}else if(url.substring(0,n) =="link"){
					tree.goLink(url.substring(n+1));
				}
			}

		 })
	},	
		
	setTreeData: function(){
		var data = {
			fileNm : "/data/treeMenu.json",
			content : JSON.encode( $treeContent.jstree("json_data").get_json(-1) ).replace(/open/g,"") 
		}
		
		$.ajax({
			url: '/lib/fileWrite.jsp',
	    	type : 'POST',
			data : $.param(data,true),
			error: function(){
				zpop.alert("저장에 실패했습니다.");
	    	},
			success: function(data){
			}
	    });
		
	},
	
	treeDel: function(obj){
		
		zpop.confirm("[" + trim(obj._get_node(null).text()) + "] 삭제하겠습니까 ?",function(){
			obj.remove(obj.data.ui.hovered || obj._get_node(null)); 
			zpop.remove();
		});
		
	},
	
	goEditor : function(url){
		
		$("#header .editorBtn").unbind("click");
		
		$.ajax({
	    	url: url,
	    	type : 'POST',
			error: function(){
	        	tree.setEditor("",url);
	    	},
			success: function(data){
				tree.setEditor(data,url);
			}
	    });
		
	},
	
	cm : null,
	setEditor : function(msg,url){
		
		var setting = {
		  	 lineNumbers: true,
		   	 styleActiveLine: true,
		     matchBrackets: true,
	       	 theme : 'mbo',
      		 mode: url.indexOf("/java/") == -1 ?  "text/html" :  "text/x-java"
		}
		
		var templete = "<textarea id='editor'>" + msg + "</textarea>";
		
		$(".ui-layout-center").html(templete);
		 this.cm = CodeMirror.fromTextArea(document.getElementById("editor"), setting);
		 
		//this.cm.setSize("100%",$(".ui-layout-center").height() );
		
		$("#header .editorBtn").show().bind("click",function(){
			var data = {
				fileNm :url,
				content : tree.cm.getValue()
			}

			$.ajax({
				url: '/lib/fileWrite.jsp',
		    	type : 'POST',
				data : $.param(data,true),
				error: function(){
					zpop.alert("저장에 실패하였습니다.");
		    	},
				success: function(data){
					zpop.alert("저장되었습니다.");
				}
		    });
		});
	
	},
	
	goLink :function(link){
		$("#header .editorBtn").hide();
		$(".ui-layout-center").html("<iframe style='width:100%;height:100%' src='"+ link + "' frameborder='no'></iframe>");
	}

}

	 