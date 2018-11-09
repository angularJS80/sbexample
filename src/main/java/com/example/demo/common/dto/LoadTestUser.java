package com.example.demo.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel (value = "LoadTestUser object ", description ="로드테스트 인자") 
public class LoadTestUser {
	@ApiModelProperty(required=true,value="rootUrl",example="http://localhost:38080/sbexample")
	public String rootUrl = "";	

	@ApiModelProperty(required=true,value="actionFlag",example="reg")
	public String actionFlag="reg";
	
	public String postUrl = "";
	
	@ApiModelProperty(required=true,value="startNum",example="1")
	public int startNum = 1;
	
	@ApiModelProperty(required=true,value="loadCnt",example="1")
	public int loadCnt = 1;
	
	public String getRootUrl() {
		return rootUrl;
	}
	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	
	public String getActionFlag() {
		return actionFlag;
	}
	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}
	public String getPostUrl() {
		return postUrl;
	}
	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getLoadCnt() {
		return loadCnt;
	}
	public void setLoadCnt(int loadCnt) {
		this.loadCnt = loadCnt;
	}
	
	
	
	
}
