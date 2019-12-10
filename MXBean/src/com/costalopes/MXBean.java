package com.costalopes;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class MXBean {

	public void printGarbageCollectorInfo() {
		
		List<GarbageCollectorMXBean> mxbeanList = ManagementFactory.getGarbageCollectorMXBeans();
		
		for (GarbageCollectorMXBean bean : mxbeanList) {
			
			System.out.println("Name: " + bean.getName());
			System.out.println("Number of collections: " + bean.getCollectionCount());
			System.out.println("Collection time: " + bean.getCollectionTime());
			System.out.println("Pool names");
			
			for (String poolName : bean.getMemoryPoolNames()) {
				System.out.println("\t" + poolName);
			}
			
			System.out.println();
		}
		
	}
	
}
