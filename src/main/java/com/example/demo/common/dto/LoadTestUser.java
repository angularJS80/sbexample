package com.example.demo.common.dto;

public class LoadTestUser {
	public static final String postRegUrl = "http://52.193.154.247:28080/sbexample/api/registerAsync";
	public static final String postloginUrl = "http://localhost:38080/sbexample2/api/authenticateAsync";
	
	String postUrl = "";
	int startNum = 0;
	String actionFlag="reg";
	
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
	int loadCnt = 100;
	
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
