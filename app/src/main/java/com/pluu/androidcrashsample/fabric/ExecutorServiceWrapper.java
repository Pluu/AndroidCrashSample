package com.pluu.androidcrashsample.fabric;

import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import com.pluu.androidcrashsample.exception.Frantic;

/**
 * Created by nohhs on 2015-09-02.
 */
public class ExecutorServiceWrapper {
	private final ExecutorService executorService;

	public ExecutorServiceWrapper(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public <T> T executeSyncLoggingException(Callable<T> callable) {
		try {
			return Looper.getMainLooper() == Looper.myLooper()
				? this.executorService.submit(callable).get(4L, TimeUnit.SECONDS)
				: this.executorService.submit(callable).get();
		} catch (RejectedExecutionException var3) {
			Frantic.getLogger()
				   .d(Frantic.LOG_TAG,
					  "Executor is shut down because we\'re handling a fatal crash.");
			return null;
		} catch (Exception e) {
			Frantic.getLogger()
				   .e(Frantic.LOG_TAG,
					  "Failed to execute task.", e);
			return null;
		}
	}

	public Future<?> executeAsync(final Runnable runnable) {
		try {
			return this.executorService.submit(new Runnable() {
				public void run() {
					try {
						runnable.run();
					} catch (Exception e) {
						Frantic.getLogger()
							   .e(Frantic.LOG_TAG,
								  "Failed to execute task.", e);
					}

				}
			});
		} catch (RejectedExecutionException e) {
			Frantic.getLogger()
				   .d(Frantic.LOG_TAG,
					  "Executor is shut down because we\'re handling a fatal crash.");
			return null;
		}
	}

	public <T> Future<T> executeAsync(final Callable<T> callable) {
		try {
			return this.executorService.submit(new Callable() {
				public T call() throws Exception {
					try {
						return callable.call();
					} catch (Exception e) {
						Frantic.getLogger()
							   .e(Frantic.LOG_TAG, "Failed to execute task.", e);
						return null;
					}
				}
			});
		} catch (RejectedExecutionException e) {
			Frantic.getLogger()
				   .d(Frantic.LOG_TAG,
					  "Executor is shut down because we\'re handling a fatal crash.");
			return null;
		}
	}
}
