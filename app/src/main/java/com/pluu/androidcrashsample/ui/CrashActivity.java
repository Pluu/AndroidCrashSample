package com.pluu.androidcrashsample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import com.pluu.androidcrashsample.R;

public class CrashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crash);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.btnCrash)
	public void crashClick() {
		int k = 10 / 0;
	}

}
