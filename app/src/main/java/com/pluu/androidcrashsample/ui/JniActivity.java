package com.pluu.androidcrashsample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import com.pluu.androidcrashsample.R;

public class JniActivity extends AppCompatActivity {

	static {
		System.loadLibrary("test_google_breakpad");
	}

	native void initNative(String path);
	native void crashService();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crash);
		ButterKnife.bind(this);

		// Save Dump Path
		initNative(getExternalCacheDir().getAbsolutePath());
	}

	@OnClick(R.id.btnCrash)
	public void crashClick() {
		crashService();
	}

}
