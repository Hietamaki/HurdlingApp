package com.example.sloth.hurdlingapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class RecordingSettingsActivity extends Activity implements View.OnClickListener{
    Button button2;
    Button button3;
    Button button4;
    TextView textView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_settings);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);
        textView = (TextView)findViewById(R.id.textView);
        textView.setOnClickListener(this);
        Log.d("ddd", "ddddd");
        final TextInputLayout textInputLayout = findViewById(R.id.textInputLayout);
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                DataHolder.Instance.setFenceGap(textInputLayout.getEditText().getText().toString());
                changeTextView();
            }
        });
        final TextInputLayout textInputLayout2 = findViewById(R.id.textInputLayout2);
        textInputLayout2.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                DataHolder.Instance.setFenceHeight(textInputLayout2.getEditText().getText().toString());
                changeTextView();
            }
        });
        //TextView textView = findViewById(R.id.textInputLayout);
        //textInputEditText.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.button:
        // do your code
        //Create the intent to start another activity


        Log.d("Change", "change");
        // TODO Auto-generated method stub
        Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    /*    // Get the files/ subdirectory of internal storage
       File mPrivateRootDir = Environment.getExternalStorageDirectory();

        // Get the files/Videos subdirectory;
        File mVideosDir = new File(mPrivateRootDir, "videos");
        Log.d("Test123421421",String.valueOf(mVideosDir.canWrite()));
        i.putExtra(MediaStore.EXTRA_OUTPUT, mVideosDir);
*/
        Log.d("test", "test1");
        Uri mUri = FileProvider.getUriForFile(RecordingSettingsActivity.this, BuildConfig.APPLICATION_ID + ".provider", new File(Environment.getExternalStorageDirectory() + "/videos",
                DataHolder.Instance.getVideoName()));
        //This will increase the index by one
        DataHolder.Instance.increaseVideoIndex();


        //Save video's index to prevent duplicates
        editor.putInt(IdManager.INDEX_PREFERENCE, DataHolder.Instance.getVideoIndex());
        editor.apply();

        changeTextView();
        Log.d("test", mUri.toString());

        i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUri);
        Log.d("test", String.valueOf(new File(Environment.getExternalStorageDirectory(), "/Record").canWrite()));

        Log.d("test", "test4");
        //startActivity(i);
        startActivityForResult(i, RESULT_OK);
        Log.d("Hey onclick", "onclickered");
        //Create the intent to start another activity
      /*(Intent intent = new Intent(RecordingSettingsActivity.this, FileSelectActivity.class);
        startActivity(intent);*/
        break;

        case R.id.button2:
        // do your code
        //Create the intent to start another activity
            button2.setBackgroundColor(Color.GREEN);
            button3.setBackgroundColor(Color.LTGRAY);
            button4.setBackgroundColor(Color.LTGRAY);
            DataHolder.Instance.setFenceIndex(0);
            changeTextView();
            Log.d("test", "test2");
        break;

        case R.id.button3:
        // do your code
            button2.setBackgroundColor(Color.LTGRAY);
            button4.setBackgroundColor(Color.LTGRAY);
            button3.setBackgroundColor(Color.GREEN);
            DataHolder.Instance.setFenceIndex(1);
            changeTextView();
            Log.d("test", "test3");
        break;
            case R.id.button4:
                // do your code
                button2.setBackgroundColor(Color.LTGRAY);
                button3.setBackgroundColor(Color.LTGRAY);
                button4.setBackgroundColor(Color.GREEN);
                DataHolder.Instance.setFenceIndex(2);
                changeTextView();
                Log.d("test", "test4");
                break;
        default:

            Log.d("test", "Default");
            break;
    }
    }

    private void changeTextView()
    {
        textView.setText(getString(R.string.SavedFileName) + DataHolder.Instance.getVideoName());
    }
}



