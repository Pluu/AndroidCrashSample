package com.pluu.androidcrashsample.exception;

import android.text.TextUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.concurrent.Callable;

import com.pluu.androidcrashsample.fabric.ExecutorServiceWrapper;
import com.pluu.androidcrashsample.fabric.ExecutorUtils;

/**
 * Custom Uncaught Exception Handler
 * Created by pluu on 2015-09-02.
 */
public class FranticExceptionHandler
	implements Thread.UncaughtExceptionHandler {

	private final Thread.UncaughtExceptionHandler defaultHandler;
	private final ExecutorServiceWrapper executorServiceWrapper;

	public FranticExceptionHandler(Thread.UncaughtExceptionHandler defaultHandler) {
		this.defaultHandler = defaultHandler;
		this.executorServiceWrapper
			= new ExecutorServiceWrapper(
				ExecutorUtils.buildSingleThreadExecutorService("Crashlytics Exception Handler"));
	}

	@Override
	public void uncaughtException(final Thread thread, final Throwable ex) {
		try {
			this.executorServiceWrapper.executeSyncLoggingException(new Callable() {
				public Void call() throws Exception {
					handleUncaughtException(thread, ex);
					return null;
				}
			});
		} catch (Exception e) {
			Frantic.getLogger()
				   .e(Frantic.LOG_TAG, "An error occurred in the uncaught exception defaultHandler", e);
		} finally {
			Frantic.getLogger()
				   .d(Frantic.LOG_TAG,
					  "Crashlytics completed exception processing. Invoking default exception defaultHandler.");
			this.defaultHandler.uncaughtException(thread, ex);
		}
	}

	private void handleUncaughtException(Thread thread, Throwable th) throws Exception {
		Frantic.getLogger()
			   .i(Frantic.LOG_TAG, getStackTrace(null, th));
	}

	private String getStackTrace(String msg, Throwable th) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);

		if (!TextUtils.isEmpty(msg)) {
			printWriter.println(msg);
		}

		Throwable cause = th;
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}

		final String stacktraceAsString = result.toString();
		printWriter.close();
		return stacktraceAsString;
	}
}
