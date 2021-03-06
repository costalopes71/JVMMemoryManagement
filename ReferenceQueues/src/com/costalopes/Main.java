package com.costalopes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Person person = new Person();
		ReferenceQueue<Person> referenceQueue = new ReferenceQueue<>();
		PersonCleaner cleaner = new PersonCleaner();
		PersonWeakReference weakReference = new PersonWeakReference(person, referenceQueue, cleaner);
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(() -> {
			try {
				PersonWeakReference wr = (PersonWeakReference) referenceQueue.remove();
				wr.clean();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		person = null;
		System.gc();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Press any key");
		br.readLine();
		
		executorService.shutdown();
		
	}
	
}

final class Person {
	
	
}

class PersonCleaner {

	public void clean() {
		System.out.println("Cleaned");
	}
	
}

class PersonWeakReference extends WeakReference<Person> {

	private PersonCleaner cleaner;

	public PersonWeakReference(Person referent, ReferenceQueue<? super Person> q, PersonCleaner cleaner) {
		super(referent, q);
		this.cleaner = cleaner;
	}

	public void clean() {
		cleaner.clean();
	}
	
}