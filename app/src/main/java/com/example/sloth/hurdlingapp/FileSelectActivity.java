package com.example.sloth.hurdlingapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FileSelectActivity extends Activity {
    private Intent mResultIntent;
    // The path to the root of this app's internal storage
    private File mPrivateRootDir;
    // The path to the "Videos" subdirectory
    private File mVideosDir;
    // Array of files in the Videos subdirectory
    File[] mVideoFiles;
    // Array of filenames corresponding to mVideoFiles
    String[] mVideoFilename;

    private ListView mFileListView;
    private Uri fileUri;

    DataWriter dataWriter = new DataWriter(DataHolder.Instance);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_select);


        // Set up an Intent to send back to apps that request a file
        mResultIntent = new Intent("com.example.sloth.hurdlingapp.ACTION_RETURN_FILE");
        // Get the files/ subdirectory of internal storage
        mPrivateRootDir = Environment.getExternalStorageDirectory();

        // Get the files/Videos subdirectory;
        mVideosDir = new File(mPrivateRootDir, "videos");

        // Get the files in the Videos subdirectory

        mVideoFiles = mVideosDir.listFiles();
        mVideoFilename = new String[mVideoFiles.length];
        for (int i = 0; i < mVideoFiles.length; i++) {
            mVideoFilename[i] = new String(mVideoFiles[i].getName());

        }

        // Set the Activity's result to null to begin with
        setResult(Activity.RESULT_CANCELED, null);

        /*
         * Display the file names in the ListView mFileListView.
         * Back the ListView with the array mVideoFilenames, which
         * you can create by iterating through mVideoFiles and
         * calling File.getAbsolutePath() for each File
         */
        mFileListView = findViewById(R.id.list_view);

        //Array to view
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_list_view, mVideoFilename);

        mFileListView.setAdapter(adapter);

        // Define a listener that responds to clicks on a file in the ListView
        mFileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            /*
             * When a filename in the ListView is clicked, get its
             * content URI and send it to the requesting app
             */
            public void onItemClick(AdapterView<?> adapterView,
                                    View view,
                                    int position,
                                    long rowId) {
                // Grant temporary read permission to the content URI
                /*
                 * Get a File for the selected file name.
                 * Assume that the file names are in the
                 * mVideoFilename array.
                 */
                final File requestFile = new File(mVideosDir, mVideoFilename[position]);
                /*
                 * Most file-related method calls need to be in
                 * try-catch blocks.
                 */
                // Use the FileProvider to get a content URI
                try {
                    fileUri = FileProvider.getUriForFile(
                            FileSelectActivity.this,
                            "com.example.sloth.hurdlingapp.fileprovider", requestFile);
                } catch (IllegalArgumentException e) {
                    Log.e("File Selector",
                            "The selected file can't be shared: ");
                    e.printStackTrace();
                }
                if (fileUri != null) {

                    dataWriter.setVideoPath(requestFile.getPath());
                    //TODO check if legit name
                    Intent intent = new Intent(FileSelectActivity.this,
                            VideoActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

