package com.costalopes;

import java.util.Date;
import java.util.WeakHashMap;

public class Main {

	public static void main(String[] args) {
		
		WeakHashMap<Person, PersonMetaData> weakMap = new WeakHashMap<>();
		
		Person kevin = new Person();
		
		weakMap.put(kevin, new PersonMetaData());
		
		PersonMetaData personMetadata = weakMap.get(kevin);
		
		System.out.println(personMetadata);
		
		kevin = null;
		System.gc();
		
		// se nao fcair dentro do if eh pq a VM que esta sendo usada nao obedece toda vez a chamada para o gc()
		if (weakMap.containsValue(personMetadata)) {
			System.out.println("Still contains key");
		} else {
			System.out.println("Key gone");
		}
		
	}
	
}

final class Person {
	
}

class PersonMetaData {
	
	private Date date;

	public PersonMetaData() {
		date = new Date();
	}

	@Override
	public String toString() {
		return "PersonMetaData [date=" + date + "]";
	}
	
}