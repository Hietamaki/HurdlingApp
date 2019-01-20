package com.example.sloth.hurdlingapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
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
    StoredData storedData;
    /**
     * {@link #gapButton1}
     * {@link #gapButton2}
     * {@link #gapButton3}
     * {@link RecordingSettingsActivity#onClick(View)}
     * When one of the buttons is pressed it will be highlighted and other buttons are unhighlighted.
     * Also when a button is pressed the value is stored with {@link #setFenceIndex(int)}.
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

    private int videoIndex;
    private int fenceIndex;
    private String fenceHeight;
    private String fenceGap;

    /**
     * Get an unique value for the name. To separate it from other names.
     *
     * @return Unique value for the name. To separate it from other names.
     */
    public int getVideoIndex() {
        return videoIndex;
    }

    /**
     * Set an unique value for the name. To separate it from other names.
     *
     * @param videoIndex Unique value for the name. To separate it from other names.
     */
    public void setVideoIndex(int videoIndex) {
        this.videoIndex = videoIndex;
    }

    /**
     * Get an index of which fence gap is recorded. Starts from 0.
     *
     * @return Index of which fence gap is recorded.
     */
    public int getFenceIndex() {
        return fenceIndex;
    }

    /**
     * Set an index of which fence gap is recorded. Starts from 0.
     *
     * @param fenceIndex Index of which fence gap is recorded.
     */
    public void setFenceIndex(int fenceIndex) {
        this.fenceIndex = fenceIndex;
    }

    /**
     * get a value for the height of the fence.
     *
     * @return Value for the height of the fence.
     */
    public String getFenceHeight() {
        return fenceHeight;
    }

    /**
     * Set a Value for the height of the fence.
     *
     * @param fenceHeight Value for the height of the fence.
     */
    public void setFenceHeight(String fenceHeight) {
        this.fenceHeight = fenceHeight;
    }

    /**
     * get a value for the distance between fences.
     *
     * @return Value for the distance between fences.
     */
    public String getFenceGap() {
        return fenceGap;
    }

    /**
     * Set a value for the distance between fences.
     *
     * @param fenceGap Value for the distance between fences.
     */
    public void setFenceGap(String fenceGap) {
        this.fenceGap = fenceGap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_settings);

        //Create an instance for saving/loading SharedPreferences.
        storedData = new StoredData(this);

        //Load unique videoIndex and store it for further use.
        videoIndex = storedData.getVideoIndex();

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
                setFenceGap(fenceHeightInputLayout.getEditText().getText().toString());
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
                setFenceHeight(fenceGapInputLayout.getEditText().getText().toString());
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
                videoCaptureIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                Uri mUri = FileProvider.getUriForFile(RecordingSettingsActivity.this,
                        BuildConfig.APPLICATION_ID + Constants.PROVIDER_EXTENSION_F,
                        new File(Environment.getExternalStorageDirectory() +
                                Constants.VIDEO_FOLDER_F, NameParser.createVideoName(
                                storedData.getAndIncrementVideoIndex(),
                                getFenceIndex(), getFenceGap(), getFenceHeight())));
                //Increment unique videoIndex and store it.
                //Will increment even if video recording fails.
                //Display changes in the video name.
                videoIndex = storedData.getVideoIndex();
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
                setFenceIndex(0);
                changeTextView();
                break;

            case R.id.button3:
                //gapButton2 pressed.
                gapButton1.setBackgroundColor(Color.LTGRAY);
                gapButton2.setBackgroundColor(Color.GREEN);
                gapButton3.setBackgroundColor(Color.LTGRAY);
                setFenceIndex(1);
                changeTextView();
                break;
            case R.id.button4:
                //gapButton3 pressed.
                gapButton1.setBackgroundColor(Color.LTGRAY);
                gapButton2.setBackgroundColor(Color.LTGRAY);
                gapButton3.setBackgroundColor(Color.GREEN);
                setFenceIndex(2);
                changeTextView();
                break;
            default:
                break;
        }
    }

    /**
     * Display which name is used for saving. Gets the name from
     * {@link NameParser#createVideoName(int, int, String, String)}.
     */
    private void changeTextView() {
        videoNameTextView.setText(getString(R.string.SavedFileName)
                + NameParser.createVideoName(videoIndex, fenceIndex, fenceGap, fenceHeight));
    }
}



