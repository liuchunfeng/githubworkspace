package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test2 {

	public static void main(String[] args) {
		List<IPVO> IPs = new ArrayList<IPVO>();
		List<IPVO> conflictIPs = new ArrayList<IPVO>();
		
		for(int i=0;i<IPs.size();i++){
			for(int j=i+1;j<IPs.size();j++){
				IPVO ip1 = IPs.get(i);
				IPVO ip2 = IPs.get(j);
				IPVO ips = getConflictIPs(ip1,ip2);
				if(ips != null){
					conflictIPs.add(ips);
				}
			}
		}
	}
	
	public static IPVO getConflictIPs(IPVO ip1,IPVO ip2){
		int[] ips = new int[]{ip1.getStartIp(),ip1.getEndIp(),ip2.getStartIp(),ip2.getEndIp()};
		Arrays.sort(ips);
		if(ips[3]-ips[0]>=(ip1.getEndIp()-ip1.getStartIp())+(ip2.getEndIp()-ip2.getStartIp())+1){
			return null;
		}else{
			return new IPVO(ips[1],ips[2]);
		}
	}

}
