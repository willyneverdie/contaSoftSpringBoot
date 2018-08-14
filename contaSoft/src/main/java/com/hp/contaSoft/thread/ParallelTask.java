package com.hp.contaSoft.thread;

public class ParallelTask implements Runnable {

	private int id;
	
	public ParallelTask(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		System.out.println("thread="+id);

	}

}
