package com.example.sloth.hurdlingapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class RecordingSettingsActivity extends Activity implements View.OnClickListener {
    /**
     * {@link #gapButton1}
     * {@link #gapButton2}
     * {@link #gapButton3}
     * {@link RecordingSettingsActivity#onClick(View)}
     * When one of the buttons is pressed it will be highlighted and other buttons are unhighlighted.
     * Also when a button is pressed
     * the value is stored with {@link DataHolder.DataWriter#setFenceIndex(int)}.
     * Then that value is used to construct the name of the video
     * Then the value is parsed from the name and used in the analysis to determine camera positions
     * in relation to each other.
     */
    Button gapButton1;
    Button gapButton2;
    Button gapButton3;
    /**
     * {@link RecordingSettingsActivity#onClick(View)} Displays the videoName.
     */
    TextView videoNameTextView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_settings);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();


        Button recordButton = (Button) findViewById(R.id.button);
        recordButton.setOnClickListener(this);
        gapButton1 = (Button) findViewById(R.id.button2);
        gapButton1.setOnClickListener(this);
        gapButton2 = (Button) findViewById(R.id.button3);
        gapButton2.setOnClickListener(this);
        gapButton3 = (Button) findViewById(R.id.button4);
        gapButton3.setOnClickListener(this);
        videoNameTextView = (TextView) findViewById(R.id.textView);
        videoNameTextView.setOnClickListener(this);

        final TextInputLayout fenceHeightInputLayout = findViewById(R.id.textInputLayout);
        fenceHeightInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                DataHolder.Instance.new DataWriter().setFenceGap(
                        fenceHeightInputLayout.getEditText().getText().toString());
                changeTextView();
            }
        });
        final TextInputLayout fenceGapInputLayout = findViewById(R.id.textInputLayout2);
        fenceGapInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                DataHolder.Instance.new DataWriter().setFenceHeight(
                        fenceGapInputLayout.getEditText().getText().toString());
                changeTextView();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                //Video record button pressed.
                Intent videoCaptureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                videoCaptureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                Uri mUri = FileProvider.getUriForFile(RecordingSettingsActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        new File(Environment.getExternalStorageDirectory() + "/videos",
                                DataHolder.Instance.getVideoName()));

                DataHolder.Instance.new DataWriter().increaseVideoUniqueIndex();


                //Save video's index to prevent duplicates between instances.
                editor.putInt(PreferenceConstants.INDEX, DataHolder.Instance.getVideoUniqueIndex());
                editor.apply();

                changeTextView();

                /**
                 *{@link MediaStore#EXTRA_OUTPUT}
                 *{@link Intent#putExtra(String, Parcelable)}
                 *Adds to Intent extra task.
                 *Makes intent put its output to {@link mUri}.
                 */
                videoCaptureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUri);
                startActivity(videoCaptureIntent);
                break;

            case R.id.button2:
                //gapButton1 pressed.
                gapButton1.setBackgroundColor(Color.GREEN);
                gapButton2.setBackgroundColor(Color.LTGRAY);
                gapButton3.setBackgroundColor(Color.LTGRAY);
                DataHolder.Instance.new DataWriter().setFenceIndex(0);
                changeTextView();
                break;

            case R.id.button3:
                //gapButton2 pressed.
                gapButton1.setBackgroundColor(Color.LTGRAY);
                gapButton2.setBackgroundColor(Color.GREEN);
                gapButton3.setBackgroundColor(Color.LTGRAY);
                DataHolder.Instance.new DataWriter().setFenceIndex(1);
                changeTextView();
                break;
            case R.id.button4:
                //gapButton3 pressed.
                gapButton1.setBackgroundColor(Color.LTGRAY);
                gapButton2.setBackgroundColor(Color.LTGRAY);
                gapButton3.setBackgroundColor(Color.GREEN);
                DataHolder.Instance.new DataWriter().setFenceIndex(2);
                changeTextView();
                break;
            default:
                break;
        }
    }

    /**
     * Display which name is used for saving. Gets the name from {@link DataHolder#getVideoName()}.
     */
    private void changeTextView() {
        videoNameTextView.setText(getString(R.string.SavedFileName)
                + DataHolder.Instance.getVideoName());
    }
}



