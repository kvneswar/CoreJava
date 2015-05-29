package com.model;

import com.customannotations.FileInfo;
import com.customannotations.RecordInfo;

@FileInfo(delimiter="\\|")
public class Tokenize {

	private String token1;
	private String token2;
	private String token3;
	
	public String getToken1() {
		return token1;
	}
	@RecordInfo(position=1, regExPattern="[A-Za-z0-9]+")
	public void setToken1(String token1) {
		this.token1 = token1;
	}
	public String getToken2() {
		return token2;
	}
	@RecordInfo(position=2, regExPattern="[A-Za-z0-9]+")
	public void setToken2(String token2) {
		this.token2 = token2;
	}
	public String getToken3() {
		return token3;
	}
	@RecordInfo(position=3, regExPattern="[A-Za-z0-9]+")
	public void setToken3(String token3) {
		this.token3 = token3;
	}
	
	@Override
	public String toString(){
		return token1+", "+token2+", "+token3;
	}
	
}
