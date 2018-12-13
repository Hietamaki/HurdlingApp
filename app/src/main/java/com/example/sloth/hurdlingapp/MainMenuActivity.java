package com.example.sloth.hurdlingapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Application's starting activity.
public class MainMenuActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button recordMenuButton = (Button) findViewById(R.id.button);
        recordMenuButton.setOnClickListener(this);
        Button sendMenuButton = (Button) findViewById(R.id.button2);
        sendMenuButton.setOnClickListener(this);
        Button watchMenuButton = (Button) findViewById(R.id.button3);
        watchMenuButton.setOnClickListener(this);


        //Version of the software running on the hardware device is older than Android Marshmallow.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Check if this activity doesn't have permission for reading external storage.
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                //Create a popup, where it ask the permission.
                requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                return;
            }
            //Check if this activity doesn't have permission for writing external storage.
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                //Create a popup, where it ask the permission.
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                return;
            }
            //Check if this activity doesn't have permission for Internet.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                    != PackageManager.PERMISSION_GRANTED) {
                //Create a popup, where it ask the permission.
                requestPermissions(new String[]{Manifest.permission.INTERNET}, 102);
                return;
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                //Pressing recordMenuButton starts RecordingSettingsActivity.
                Intent intent = new Intent(MainMenuActivity.this, RecordingSettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.button2:
                //Pressing sendMenuButton starts RecordingSettingsActivity.
                Intent intent2 = new Intent(MainMenuActivity.this, FileSelectActivity.class);
                startActivity(intent2);
                break;

            case R.id.button3:
                //Pressing watchMenuButton.


                break;

            default:
                break;
        }

    }
}
