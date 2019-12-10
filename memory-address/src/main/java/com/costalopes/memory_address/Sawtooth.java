package com.costalopes.memory_address;

import java.lang.reflect.Field;

import com.costalopes.MXBean;
import com.costalopes.memory_address.model.GCMe;

import sun.misc.Unsafe;

public class Sawtooth {

	private static Unsafe unsafe;
	
	static {
		
		try {
			Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafe.setAccessible(true);
			unsafe = (Unsafe) theUnsafe.get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
    public static void main( String[] args ) {

    	for (int i = 0; i < 3200000; i++) {
    		Object mine = new GCMe();
    		long address = addressOf(mine);
    		System.out.println(address);
		}
    	
    	MXBean mxBean = new MXBean();
    	mxBean.printGarbageCollectorInfo();
    	
    }

	private static long addressOf(Object obj) {
		
		Object[] array = new Object[]{obj};
		
		long baseOffset = unsafe.arrayBaseOffset(Object[].class);
		int addressSize = unsafe.addressSize();
		long objectAddress;
		
		switch (addressSize) {
		case 4:
			objectAddress = unsafe.getInt(array, baseOffset);
			break;
		case 8:
			objectAddress = unsafe.getLong(array, baseOffset);
			break;
		default:
			throw new Error("unsupported address size: " + addressSize);
		}
		
		return (objectAddress);
	}
    
}
