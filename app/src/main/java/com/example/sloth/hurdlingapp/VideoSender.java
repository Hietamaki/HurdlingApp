package com.example.sloth.hurdlingapp;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;

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
     * Uploads a chosen file to server
     *
     * @param activity    Is current activity.
     * @param requestFile Is the file that is wanted to be upload to server and for analysis.
     * @param newFileName Is the name for the file in server-side.
     */
    public void fileUpload(Activity activity, final File requestFile, final String newFileName) {

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

                    OkHttpClient client = new OkHttpClient();

                    RequestBody file_body = RequestBody.create(
                            MediaType.parse(contentType), requestFile);

                    /**
                     * {@link Constants#UPLOADED_FILE_X} name saved to server-side.
                     * {@link Constants#TYPE_X} sends the Mime type to server-side.
                     */
                    RequestBody request_body = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart(Constants.TYPE_X, contentType)
                            .addFormDataPart(Constants.UPLOADED_FILE_X, newFileName +
                                    Constants.VIDEO_EXTENSION_F, file_body
                            ).build();

                    //In server php script handles the request.
                    //TODO create more pleasant url in the future.
                    Request request = new Request.Builder()
                            .url("http://192.168.43.244/testing/save_file.php")
                            .post(request_body).build();
                    try {
                        Response response = client.newCall(request).execute();
                        if (!response.isSuccessful()) {
                            throw new IOException("ERROR : " + response);
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
