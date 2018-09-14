package com.example.sloth.hurdlingapp;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                Log.d("hello", "world1");
                return;
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
                Log.d("hello", "world2");
                return;
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.INTERNET},102);
                Log.d("hello", "world3");
                return;
            }
        }
    }



        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.button:
                    // do your code
                    //Create the intent to start another activity
                    Intent intent = new Intent(MainMenuActivity.this, RecordingSettingsActivity.class);
                    startActivity(intent);
                    break;

                case R.id.button2:
                    // do your code
                    //Create the intent to start another activity
                    Intent intent2 = new Intent(MainMenuActivity.this, FileSelectActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.button3:
                    // do your code

                    break;


                default:
                    break;
            }

        }
}
