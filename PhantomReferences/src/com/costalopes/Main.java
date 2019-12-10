package com.costalopes;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		ReferenceQueue<Person> queue = new ReferenceQueue<>();
		List<FinalizePerson> phantonReferencesList = new ArrayList<>();
		List<Person> people = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			Person p = new Person();
			people.add(p);
			phantonReferencesList.add(new FinalizePerson(p, queue));
		}
		
		people = null;
		System.gc();
		
		for (FinalizePerson reference : phantonReferencesList) {
			System.out.println(reference.isEnqueued());
		}
		
		Reference<? extends Person> referenceFromQueue;
		while ((referenceFromQueue = queue.poll()) != null) {
			((FinalizePerson) referenceFromQueue).cleanup();
			referenceFromQueue.clear();
		}
		
	}
	
}

class Person {
	
}

class FinalizePerson extends PhantomReference<Person> {

	public FinalizePerson(Person person, ReferenceQueue<? super Person> queue) {
		super(person, queue);
	}

	public void cleanup() {
		System.out.println("person is finalizing resources");
	}
	
}