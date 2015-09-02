package com.pluu.androidcrashsample.exception;

import android.util.Log;

import com.pluu.androidcrashsample.common.DefaultLogger;
import com.pluu.androidcrashsample.common.Logger;

/**
 * Frantic Crash Wrapper Class
 * Created by pluu on 2015-09-02.
 */
public class Frantic {

	public static final String LOG_TAG = Frantic.class.getSimpleName();

	private static Frantic singleton;

	private static final Logger DEFAULT_LOGGER = new DefaultLogger(Log.VERBOSE);

	static Frantic getInstance() {
		if(singleton == null) {
			throw new IllegalStateException("Must Initialize Frantic before using getInstance()");
		} else {
			return singleton;
		}
	}

	public static Frantic install() {
		if (singleton == null) {
			Class<Frantic> furiousClass = Frantic.class;
			synchronized (furiousClass) {
				if (singleton == null) {
					singleton = new Frantic();
					singleton.init();
				}
			}
		}
		return singleton;
	}


	private Frantic() { }

	private void init() {
		// Crash Exception Handler
		Thread.UncaughtExceptionHandler defaultHandler
			= Thread.getDefaultUncaughtExceptionHandler();



		FranticExceptionHandler handler
			= new FranticExceptionHandler(defaultHandler);

		Thread.setDefaultUncaughtExceptionHandler(handler);

		getLogger().i(LOG_TAG, "Init Complete");
	}

	public static Logger getLogger() {
		return DEFAULT_LOGGER;
	}

}
