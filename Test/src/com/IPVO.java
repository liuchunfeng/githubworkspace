package com;

public class IPVO {
	private int startIp;
	private int endIp;
	
	public IPVO(int startIp, int endIp) {
		super();
		this.startIp = startIp;
		this.endIp = endIp;
	}
	
	public int getStartIp() {
		return startIp;
	}
	public void setStartIp(int startIp) {
		this.startIp = startIp;
	}
	public int getEndIp() {
		return endIp;
	}
	public void setEndIp(int endIp) {
		this.endIp = endIp;
	}
	
}
