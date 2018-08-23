package com.example.sloth.hurdlingapp;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;


        import java.io.File;

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

        // TODO Auto-generated method stub
        Intent i=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    /*    // Get the files/ subdirectory of internal storage
       File mPrivateRootDir = Environment.getExternalStorageDirectory();

        // Get the files/Videos subdirectory;
        File mVideosDir = new File(mPrivateRootDir, "videos");
        Log.d("Test123421421",String.valueOf(mVideosDir.canWrite()));
        i.putExtra(MediaStore.EXTRA_OUTPUT, mVideosDir);
*/
    Log.d("test", "test1");
        Uri mUri = FileProvider.getUriForFile(RecordingSettingsActivity.this, BuildConfig.APPLICATION_ID + ".provider",new File(Environment.getExternalStorageDirectory()+"/videos", "video_"+IdManager.Instance.getUniqueId(RecordingSettingsActivity.this) + "_"+ DataHolder.Instance.getVideoIndex() +"_.mp4"));
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
    }

}



