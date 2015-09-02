package com.pluu.androidcrashsample.common;

/**
 * Logger interface
 * Created by pluu on 2015-09-02.
 */
public interface Logger {
	boolean isLoggable(String var1, int var2);

	int getLogLevel();

	void setLogLevel(int var1);

	void d(String var1, String var2, Throwable var3);

	void v(String var1, String var2, Throwable var3);

	void i(String var1, String var2, Throwable var3);

	void w(String var1, String var2, Throwable var3);

	void e(String var1, String var2, Throwable var3);

	void d(String var1, String var2);

	void v(String var1, String var2);

	void i(String var1, String var2);

	void w(String var1, String var2);

	void e(String var1, String var2);

	void log(int var1, String var2, String var3);

	void log(int var1, String var2, String var3, boolean var4);
}
