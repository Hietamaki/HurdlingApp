package com.example.sloth.hurdlingapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class RecordingSettingsActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_settings);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        //Create the intent to start another activity
        Intent intent = new Intent(RecordingSettingsActivity.this, FileSelectActivity.class);
        startActivity(intent);
    }
static final int REQUEST_VIDEO_CAPTURE = 1;

    private void dispatchTakeVideoIntent(View view) {


    }


}
