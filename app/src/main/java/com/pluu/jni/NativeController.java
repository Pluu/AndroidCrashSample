package com.pluu.jni;

import android.util.Log;

/**
 * Created by PLUUSYSTEM on 2015-06-11.
 */
public class NativeController {
	public static int NativeCrashCallback(String fileName) {

		Log.d("FranticJni", "NativeCrashCallback Called");
		Log.d("FranticJni", "Dump FileName=" + fileName);

		return 0;
	}
}