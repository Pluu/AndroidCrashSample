package com.pluu.androidcrashsample.fabric;

/**
 * Fbaric fork BackgroundPriorityRunnable
 */
public abstract class BackgroundPriorityRunnable implements Runnable {
	public BackgroundPriorityRunnable() {
	}

	public final void run() {
		android.os.Process.setThreadPriority(10);
		this.onRun();
	}

	protected abstract void onRun();
}
