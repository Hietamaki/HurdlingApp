package com.example.sloth.hurdlingapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;

public class VideoActivity extends Activity implements View.OnClickListener {


    //Test Button for trimming
    Button testButton;

    SimpleExoPlayer player;
    String destination;
    PlayerView playerView;

    //Analysis needs the fence markers.
    View fenceMarkers[] = new View[8];

    /**
     * {@link #positionTextView}
     * {@link #durationTextView}
     * These TextViews are meant to display the ExoPlayer's TimeBars values.
     * Unfortunately ExoPlayer's UI didn't have option to display the position and duration in ms.
     * So this is a rough fix for that.
     * Video's duration will be determined when the video has loaded.
     * Video's current position must be determined all the time. That is why there is Handler for it.
     * {@link #textViewUpdateHandler}
     * {@link #delay}
     * There is a handler that has {@link Handler#postDelayed(Runnable, long)} method.
     * this method is called every {@link #delay} and update the value of {@link #positionTextView}.
     */
    TextView positionTextView;
    TextView durationTextView;
    final Handler textViewUpdateHandler = new Handler();
    int delay = 100;

    /**
     * {@link #rangeBar}
     * rangeBar is slider with two draggable elements.
     * rangeBar is used for visualising and changing the values that determines,
     * where the video will be cut.
     * Changing rangeBar will change {@link #endOfCutTextInputLayout}
     * and {@link #startOfCutTextInputLayout} text's values.
     * <p>
     * {@link #rightThumbDefaultValue}
     * Last index of the rangeBar.
     * This will set the amount of ticks rangeBar has.
     * {@link #rightThumbValue}
     * In which tick is the right side of the rangeBar
     * {@link #leftThumbValue}
     * In which tick is the left side of the rangeBar
     */
    RangeBar rangeBar;
    final int rightThumbDefaultValue = 599;
    int rightThumbValue = rightThumbDefaultValue;
    int leftThumbValue = 0;

    //video size in ms
    int videoDuration;

    String videoPath;

    /**
     * There are listeners in {@link #rangeBar} and in {@link #endOfCutTextInputLayout}
     * and {@link #startOfCutTextInputLayout}, that will change each other's values.
     * valueChanged check will prevent stack overflow.
     */
    boolean valueChanged = false;

    //Regex that means integer from 0-999999.
    final String oneToSixDigitsRegex = "^\\d{1,6}$";

    /**
     * {@link #endOfCutTextInputLayout} and {@link #startOfCutTextInputLayout}
     * Values in these TextInputLayouts determines where to cut the video.
     * Changing these TextInputLayouts will change the value of {@link #rangeBar}.
     */
    TextInputLayout endOfCutTextInputLayout;
    TextInputLayout startOfCutTextInputLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        playerView = findViewById(R.id.playerView_step_video);
        playerView.setPlayer(player);

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "com.example.sloth.hurdlingapp"),
                (DefaultBandwidthMeter) bandwidthMeter);

        /**
         * In {@link FileSelectActivity} the videoPath is stored to Intent.
         * */
        Intent intent = getIntent();
        videoPath = intent.getStringExtra(Constants.VIDEO_FILE_PATH_I);
        //Assumes it's mp4
        Uri mp4VideoUri = Uri.parse(videoPath);

        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mp4VideoUri);
        // Prepare the player with the source.
        player.prepare(videoSource);


        rangeBar = findViewById(R.id.rangebar);
        rangeBar.setTickCount(rightThumbDefaultValue + 1);
        //0 means that ticks are invisible
        rangeBar.setTickHeight(0);
        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                /**
                 *There are listeners in {@link #rangeBar} and in {@link #endOfCutTextInputLayout}
                 *and {@link #startOfCutTextInputLayout}, that will change each other's values.
                 *ValueChanged check will prevent stack overflow.
                 *If the textInputLayouts have just changed its value, this will be true.
                 *If true, this won't edit the textInputLayouts and the textInputLayouts' listeners
                 *won't activate and basically preventing stack overflow.
                 */
                if (!valueChanged) {
                    valueChanged = true;
                    //Check if the left or (in else if) the right value of rangeBar has edited.
                    if (leftThumbIndex != leftThumbValue) {
                        //Store left thumb tick value.
                        setLeftThumbValue(leftThumbIndex);
                        //Yes, this will set LeftTextInputLayout to display the rangeBar's value.
                        setLeftTextFieldValue();
                    } else if (rightThumbIndex != rightThumbValue) {
                        //Store right thumb tick value.
                        setRightThumbValue(rightThumbIndex);
                        //Yes, this will set RightTextInputLayout to display the rangeBar's value.
                        setRightTextFieldValue();
                    }
                } else {
                    valueChanged = false;
                }
            }
        });

        startOfCutTextInputLayout = findViewById(R.id.textInputLayout3);
        startOfCutTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /**
                 *There are listeners in {@link #rangeBar} and in {@link #endOfCutTextInputLayout}
                 *and {@link #startOfCutTextInputLayout}, that will change each other's values.
                 *valueChanged check will prevent stack overflow.
                 *If rangeBar has just changed its value, this will be true.
                 *If true, this won't edit rangeBar and rangeBar's listener won't activate and
                 *basically preventing stack overflow.
                 */
                if (!valueChanged) {
                    String newText = String.valueOf(s);
                    //Updates the value only, if it's a integer between 0-999999
                    if (newText.matches(oneToSixDigitsRegex)) {
                        valueChanged = true;
                        int newTextAsInteger = Integer.valueOf(newText);
                        //Check if the value is over the max value.
                        if (newTextAsInteger > videoDuration) {
                            //set it to the max value, if it's over it
                            newTextAsInteger = videoDuration;

                        }
                        //Change the ms value to rangeBar's left thumb tick value and store it.
                        setLeftThumbValue(getTickCountValue(newTextAsInteger));
                        //Yes, this will set the rangeBar to display the value.
                        setThumbRangeBarValues();

                        //Check if the value was over the max value. (Was the value edited)
                        if (Integer.valueOf(newText) != newTextAsInteger) {
                            valueChanged = true;
                            //set TextInputLayout to max value.
                            //valueChanged is true, so this won't fire extra onTextChanged
                            startOfCutTextInputLayout.getEditText()
                                    .setText(String.valueOf(getVideoValue(leftThumbValue)));
                        }
                    }
                } else {
                    valueChanged = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        endOfCutTextInputLayout = findViewById(R.id.textInputLayout4);
        endOfCutTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /**
                 *There are listeners in {@link #rangeBar} and in {@link #endOfCutTextInputLayout}
                 *and {@link #startOfCutTextInputLayout}, that will change each other's values.
                 *valueChanged check will prevent stack overflow.
                 *If rangeBar has just changed its value, this will be true.
                 *If true, this won't edit rangeBar and rangeBar's listener won't activate and
                 *basically preventing stack overflow.
                 */
                if (!valueChanged) {
                    String newText = String.valueOf(s);
                    //Updates the value only, if it's a integer between 0-999999
                    if (newText.matches(oneToSixDigitsRegex)) {
                        valueChanged = true;
                        int newTextAsInteger = Integer.valueOf(newText);
                        //Check if the value is over the max value.
                        if (newTextAsInteger > videoDuration) {
                            //set it to the max value, if it's over it
                            newTextAsInteger = videoDuration;
                        }


                        //Change the ms value to rangeBar's right thumb tick value and store it.
                        setRightThumbValue(getTickCountValue(newTextAsInteger));
                        //Yes, this will set the rangeBar to display the value.
                        setThumbRangeBarValues();

                        //Check if the value was over the max value. (Was the value edited)
                        if (Integer.valueOf(newText) != newTextAsInteger) {
                            valueChanged = true;
                            //set TextInputLayout to max value.
                            //valueChanged is true, so this won't fire extra onTextChanged
                            endOfCutTextInputLayout.getEditText()
                                    .setText(String.valueOf(getVideoValue(rightThumbValue)));
                        }

                    }
                } else {
                    valueChanged = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        /**
         *for info about the handler
         *@see positionTextView or
         *@see durationTextView or
         *@see textViewUpdateHandler or
         *@see delay.
         */
        positionTextView = findViewById(R.id.exo_position_custom);
        durationTextView = findViewById(R.id.exo_duration_custom);
        textViewUpdateHandler.postDelayed(new Runnable() {
            public void run() {
                positionTextView.setText(String.valueOf(player.getContentPosition()));
                textViewUpdateHandler.postDelayed(this, delay);
            }
        }, delay);


        //Add Listener that will recognise, when the video is loaded.
        player.addListener(new Player.DefaultEventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                //Check if video is loaded, so its length can be determined.
                if (playbackState == Player.STATE_READY) {
                    //Cast long to int. Video won't be longer than max integer value.
                    videoDuration = (int) player.getDuration();
                    //Display the ExoPlayer's video duration in ms now that it's loaded.
                    durationTextView.setText(String.valueOf(videoDuration));

                    //Set the cut's end value to end of the video (and start to start)...
                    setRightThumbValue(rightThumbDefaultValue);
                    //...then display it.
                    setThumbRangeBarValues();
                    setRightTextFieldValue();
                    setLeftTextFieldValue();

                    //Remove the listener so it won't fire twice
                    player.removeListener(this);
                }
            }
        });

        //Trim test button
        testButton = (Button) findViewById(R.id.button5);
        testButton.setOnClickListener(this);

        // Assign the touch listener to markers for moving purpose.
        for (int i = 0; i < fenceMarkers.length; i++) {
            String ID = Constants.FENCE_MARKER_X + i;
            int resID = getResources().getIdentifier(ID, "id", getPackageName());
            fenceMarkers[i] = findViewById(resID);
            fenceMarkers[i].setOnTouchListener(new MyTouchListener());
        }
        // Add drag and drop listener to drag and drop background.
        findViewById(R.id.dragBackground1).setOnDragListener(new MyDragListener());


    }

    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    float x = event.getX();
                    float y = event.getY();
                    View view = (View) event.getLocalState();
                    view.setVisibility(View.VISIBLE);
                    view.setY(y - ((float) view.getHeight() / 2));
                    view.setX(x - ((float) view.getWidth() / 2));
                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                default:
                    break;
            }
            return true;
        }
    }

    // Touch listener for dragging.
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void onClick(View view) {
        //Send button clicked
        //Trims the video and when done the uploadFile method will be invoked.
        destination = VideoEditor.Instance.trimVideo(VideoActivity.this,
                Integer.valueOf(getLeftTextFieldValue()),
                Integer.valueOf(getRightTextFieldValue()), videoPath, () -> uploadFile());
    }

    /**
     * Uploads the file to the local server.
     * Notice that local ip is hard coded.
     */
    public void uploadFile() {
        VideoSender.Instance.fileUpload(this, new File(destination), "pholder");
    }

    /**
     * Sets the {@link #rightThumbValue} in ticks
     *
     * @param rightThumbValue rangeBar's end side in its ticks {@link RangeBar#setTickCount(int)}
     */
    void setRightThumbValue(int rightThumbValue) {
        this.rightThumbValue = rightThumbValue;
    }

    /**
     * Sets the {@link #leftThumbValue} in ticks
     *
     * @param leftThumbValue rangeBar's start side in its ticks {@link RangeBar#setTickCount(int)}
     */
    void setLeftThumbValue(int leftThumbValue) {
        this.leftThumbValue = leftThumbValue;
    }

    /**
     * Takes the {@link #leftThumbValue} and {@link #rightThumbValue} and displays them in rangeBar.
     */
    void setThumbRangeBarValues() {
        rangeBar.setThumbIndices(leftThumbValue, rightThumbValue);
    }

    /**
     * Takes the {@link #leftThumbValue} and displays it on {@link #startOfCutTextInputLayout}.
     */
    void setLeftTextFieldValue() {
        startOfCutTextInputLayout.getEditText().setText(
                String.valueOf(getVideoValue(leftThumbValue)));
    }

    String getLeftTextFieldValue() {
        return startOfCutTextInputLayout.getEditText().getText().toString();
    }

    /**
     * Takes the {@link #rightThumbValue} and displays it on {@link #endOfCutTextInputLayout}.
     */
    void setRightTextFieldValue() {
        endOfCutTextInputLayout.getEditText().setText(
                String.valueOf(getVideoValue(rightThumbValue)));
    }

    String getRightTextFieldValue() {
        return endOfCutTextInputLayout.getEditText().getText().toString();
    }

    /**
     * Gets {@link #rangeBar}'s tick value out of milliseconds
     *
     * @param value value in milliseconds
     * @return value in {@link #rangeBar}'s ticks
     */
    int getTickCountValue(int value) {
        return (int) ((double) rightThumbDefaultValue * (double) value / (double) videoDuration);
    }

    /**
     * Gets milliseconds out of {@link #rangeBar}'s tick value
     *
     * @param value value in {@link #rangeBar}'s ticks
     * @return value in milliseconds
     */
    int getVideoValue(int value) {
        return (int) ((double) videoDuration * (double) value / (double) rightThumbDefaultValue);
    }


}
