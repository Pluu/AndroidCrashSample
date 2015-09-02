package com.pluu.androidcrashsample;

import android.app.Application;

import com.pluu.androidcrashsample.exception.Frantic;

/**
 * Application Controller
 * Created by pluu on 2015-09-02.
 */
public class AppController extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Frantic.install();
	}
}
