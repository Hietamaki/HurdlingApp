package com.example.sloth.hurdlingapp;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VideoSender {

    public static final VideoSender Instance = new VideoSender();
    private Uri fileUri;

    /**
     * Uploads a chosen video file to server with data about the video
     *
     * @param activity    Is current activity.
     * @param requestFile Is the file that is wanted to be upload to server and for analysis.
     * @param newFileName Is the name for the file in server-side.
     */
    public void fileUpload(Activity activity, final File requestFile, final String newFileName,
                           final String json) {
        /*
         * Most file-related method calls need to be in
         * try-catch blocks.
         */
        // Use the FileProvider to get a content URI
        try {
            fileUri = FileProvider.getUriForFile(activity,
                    "com.example.sloth.hurdlingapp.fileprovider", requestFile);
        } catch (IllegalArgumentException e) {
            Log.e("File Selector",
                    "The selected file can't be shared: ");
            e.printStackTrace();
        }

        //Get Mime type from the path.
        final String contentType = getMimeType(requestFile.getPath());

        //Check if the type is video that is mp4. If not; then end.
        if (!contentType.equals("video/mp4")) {
            return;
        }
        if (fileUri != null) {
            // Creates thread that will upload video to server.
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    //Creates a client that timeouts in 300 seconds instead of the default 10 seconds
                    //Has now enough wait time that the analysis can finish in time.
                    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
                    clientBuilder.readTimeout(300, TimeUnit.SECONDS);
                    OkHttpClient client = clientBuilder.build();
                    /**
                     * {@link Constants#UPLOADED_FILE_P} name saved to server-side.
                     */
                    RequestBody formBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file", newFileName +
                                            Constants.VIDEO_EXTENSION_F,
                                    RequestBody.create(MediaType.parse("text/plain"), requestFile))
                            .addFormDataPart("json","json", RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))
                            .build();
                    Request request = new Request.Builder().url(Constants.URL_SERVER)
                            .post(formBody).build();

                    //In server php script handles the request.
                    //TODO create more pleasant url in the future.
                   try {
                        Response response = client.newCall(request).execute();
                        if (!response.isSuccessful()) {
                            throw new IOException("ERROR : " + response);
                        }
                        else
                        {
                            //Prints a log after the server has returned it.
                            Log.d("hello", "Here is the response: " + response.body().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }

    }

    /**
     * Gets the Mime type. Should be "video/mp4".
     *
     * @param path video's path
     * @return The Mime type
     */
    private String getMimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }


}
