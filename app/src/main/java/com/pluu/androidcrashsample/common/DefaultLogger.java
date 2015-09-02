package com.pluu.androidcrashsample.common;

import android.util.Log;

/**
 * Default Logger
 * Created by nohhs on 2015-09-02.
 */
public class DefaultLogger implements Logger {

	private final int MAX_LOG_LENGTH = 4000;

	private int logLevel;

	public DefaultLogger(int logLevel) {
		this.logLevel = logLevel;
	}

	public DefaultLogger() {
		this.logLevel = Log.INFO;
	}

	public boolean isLoggable(String tag, int level) {
		return this.logLevel <= level;
	}

	public int getLogLevel() {
		return this.logLevel;
	}

	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	public void d(String tag, String text, Throwable throwable) {
		if(this.isLoggable(tag, Log.DEBUG)) {
			log(Log.DEBUG, tag, text, throwable);
		}
	}

	public void v(String tag, String text, Throwable throwable) {
		if(this.isLoggable(tag, Log.VERBOSE)) {
			log(Log.VERBOSE, tag, text, throwable);
		}
	}

	public void i(String tag, String text, Throwable throwable) {
		if(this.isLoggable(tag, Log.INFO)) {
			log(Log.INFO, tag, text, throwable);
		}
	}

	public void w(String tag, String text, Throwable throwable) {
		if(this.isLoggable(tag, Log.WARN)) {
			log(Log.WARN, tag, text, throwable);
		}
	}

	public void e(String tag, String text, Throwable throwable) {
		if(this.isLoggable(tag, Log.ERROR)) {
			log(Log.ERROR, tag, text, throwable);
		}
	}

	public void d(String tag, String text) {
		this.d(tag, text, null);
	}

	public void v(String tag, String text) {
		this.v(tag, text, null);
	}

	public void i(String tag, String text) {
		this.i(tag, text, null);
	}

	public void w(String tag, String text) {
		this.w(tag, text, null);
	}

	public void e(String tag, String text) {
		this.e(tag, text, null);
	}

	public void log(int priority, String tag, String msg) {
		this.log(priority, tag, msg, false);
	}

	public void log(int priority, String tag, String msg, boolean forceLog) {
		if(forceLog || this.isLoggable(tag, priority)) {
			Log.println(priority, tag, msg);
		}
	}

	private void log(int priority, String tag, String message, Throwable t) {
		if (t != null) {
			message = message + '\n' + Log.getStackTraceString(t);
		}

		if (message.length() < MAX_LOG_LENGTH) {
			if (priority == Log.ASSERT) {
				Log.wtf(tag, message);
			} else {
				Log.println(priority, tag, message);
			}
			return;
		}

		// Split by line, then ensure each line can fit into Log's maximum length.
		for (int i = 0, length = message.length(); i < length; i++) {
			int newline = message.indexOf('\n', i);
			newline = newline != -1 ? newline : length;
			do {
				int end = Math.min(newline, i + MAX_LOG_LENGTH);
				String part = message.substring(i, end);
				if (priority == Log.ASSERT) {
					Log.wtf(tag, part);
				} else {
					Log.println(priority, tag, part);
				}
				i = end;
			} while (i < newline);
		}
	}
}
