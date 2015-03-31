package com.taxholic.tld;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class SelectArray extends TagSupport{

	private String id;
	private String[] key;
	private String[] value;
	private String selectKey;
	private String style;

	public int doStartTag(){
		
		JspWriter out = pageContext.getOut();
		
		StringBuffer dataList = new StringBuffer();
		
		String style = (this.style != null) ? " style='" + this.style + "'" : "";
		dataList.append("<select name='" + this.id + "' id='" + this.id + "'" + style + ">");
		for(int i = 0; i < this.key.length; i++){
			String selected = this.key[i].equals(this.selectKey) ? " selected" : "";
			dataList.append("<option value='" + this.key[i] + "'" + selected + ">" + this.value[i] + "</option>");
		}
		dataList.append("</select>");
		
		try {
			out.println(dataList);
		} catch(IOException ioe){          
			ioe.printStackTrace(); 
		}
		return SKIP_BODY; 
	}

	public void setId(String id) {
		this.id = id;
	}
	public void setKey(String[] key) {
		this.key = key;
	}
	public void setValue(String[] value) {
		this.value = value;
	}
	public void setSelectKey(String selectKey) {
		this.selectKey = selectKey;
	}
	public void setStyle(String style) {
		this.style = style;
	}

}
