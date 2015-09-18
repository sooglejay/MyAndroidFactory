package com.jsb.test;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.jsb.R;
import com.rey.material.widget.Spinner;

public class SpinnersTest extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aaaaaaa_spinner);

		Spinner spn_label = (Spinner)findViewById(R.id.spinner_label);
		Spinner spn_no_arrow = (Spinner)findViewById(R.id.spinner_no_arrow);
		String[] items = new String[20];
		for(int i = 0; i < items.length; i++)
			items[i] = "Item " + String.valueOf(i + 1);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.aaaa_row_spn, items);
		adapter.setDropDownViewResource(R.layout.aaaaaa_row_spn_dropdown);
		spn_label.setAdapter(adapter);
		spn_no_arrow.setAdapter(adapter);

	}

	public static SpinnersTest newInstance(){
		SpinnersTest fragment = new SpinnersTest();
		
		return fragment;
	}

    private Drawable[] mDrawables = new Drawable[2];
    private int index = 0;


	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
}
