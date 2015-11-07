package com.jsb.test;

import java.io.IOException;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;

import com.jsb.R;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


public class GifTest extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aaaa_test_gif);
		GifImageView gifImageView =(GifImageView) findViewById(R.id.giv_demo);
		try {
			GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.anim_flag_iceland);
			gifImageView.setImageDrawable(gifDrawable);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
