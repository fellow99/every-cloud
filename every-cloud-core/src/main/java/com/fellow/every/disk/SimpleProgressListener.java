package com.fellow.every.disk;

public class SimpleProgressListener implements ProgressListener{

	@Override
	public void started() {
		System.out.print("[>");
	}

	@Override
	public int getUpdateInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void processing(long bytes, long total) {
		System.out.print(".");
	}

	@Override
	public void completed() {
		System.out.println("<]");
		
	}

}
