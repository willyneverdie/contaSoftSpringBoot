package com.hp.contaSoft.thread;

public class TestLegacyThreads {

	
	public static void main(String[] args) {
		
		
		for(int i=0; i<10 ;i++) {
			new Thread( new ParallelTask(i) ).start();
		}
		
		System.out.println("I am the main thread!");
		
	}
	
}
