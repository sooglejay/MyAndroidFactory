package com.jiandanbaoxian.test;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jiandanbaoxian.R;

import java.util.ArrayList;

/**
 * Created by sooglejay on 16/1/12.
 */
public class VoiceRecognitionActivity extends Activity {
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    Button btn;
    Activity ac;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recognition);
        btn = (Button) findViewById(R.id.btn);
        tv = (TextView) findViewById(R.id.tv);
        ac = this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
                    startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
                } catch (ActivityNotFoundException e) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://market.android.com/details?id=APP_PACKAGE_NAME"));
                    startActivity(browserIntent);

                }
            }
        });
    }

    /**
     * Where you can handle the result of the voice recgnition.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            final CharSequence[] items = new CharSequence[matches.size()];
            for (int i = 0; i < matches.size(); i++) {
                items[i] = matches.get(i);
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    //Do what you want with the text
                    Toast.makeText(ac, "text: " + items[item].toString(), Toast.LENGTH_LONG).show();

                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
