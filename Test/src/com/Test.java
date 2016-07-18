package com;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Line;

public class Test {

	public static void main(String[] args) {
		List<IPVO> IPs = new ArrayList<IPVO>();

		List<IPVO> conflictIPs = new ArrayList<IPVO>();
		List<IPVO> normalIPs = new ArrayList<IPVO>();
		for(IPVO ipvo:IPs){
			//1、计算IP段是否和冲突IP段重叠
			for(IPVO conflictIP:conflictIPs){
				//求ipvo和conflictIP的交集
				if(ipvo.getStartIp()==conflictIP.getStartIp()){
					if(ipvo.getEndIp()>conflictIP.getEndIp()){
						ipvo.setStartIp(conflictIP.getEndIp()+1);
					}else{
						continue;
					}
				}else if(ipvo.getStartIp()<conflictIP.getStartIp()){
					if(ipvo.getEndIp()>conflictIP.getEndIp()){//此种情况下一个段被截为两个段
						IPVO ipvoadd = new IPVO(ipvo.getStartIp(),conflictIP.getStartIp());
						
					}
				}else{
					
				}
				if(ipvo.getEndIp()<ipvo.getStartIp()){
					continue;
				}
			}
			//2、计算IP段是否和正常IP段重叠
		}
		
	}
	
	public static void merge(){
		
	}
	
	
	

}
