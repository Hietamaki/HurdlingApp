package com.example.sloth.hurdlingapp;

import android.app.Activity;
import android.os.Environment;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

public class VideoEditor {
    public final static VideoEditor Instance = new VideoEditor();


    /**
     * This method trims the video depending on the parameters.
     * For this to work the video must tick at some second.
     * For example 1 - 999 is not legit start and endposition and doesn't save video data.
     * But for example 900 - 1100 is legit start and endposition and saves video data.
     *
     * @param activity      Is needed by FFmpeg and for changing video unique index.
     * @param startPosition Is the start position of trim.
     * @param endPosition   Is the end position of trim.
     * @param videoPath     Is the name of the video.
     * @param callback      Is the callback that is called when the video has been trimmed.
     * @return Edited video's name
     */
    public String trimVideo(Activity activity, int startPosition, int endPosition, String videoPath,
                            Runnable callback) {
        String[] parsedValues = NameParser.parseName(videoPath);
        return trimVideo(activity, startPosition, endPosition, Integer.valueOf(parsedValues[1]),
                Integer.valueOf(parsedValues[4]), parsedValues[2], parsedValues[3], callback);
    }

    /**
     * @param activity         Is needed by FFmpeg and for changing video unique index.
     * @param startPosition    Is the start position of trim.
     * @param endPosition      Is the end position of trim.
     * @param videoUniqueIndex Is source's unique index. Only used for renaming purpose.
     * @param fenceIndex       Shows what gap was recorded. Only used for renaming purpose.
     * @param fenceSpacing         Is the distance between fences. Only used for renaming purpose.
     * @param fenceHeight      Is the height of the fences. Only used for renaming purpose.
     * @param callback         Is the callback that is called when the video has been trimmed.
     * @return Edited video's name
     */
    public String trimVideo(Activity activity, int startPosition, int endPosition,
                            int videoUniqueIndex, int fenceIndex,
                            String fenceSpacing, String fenceHeight, final Runnable callback) {

        FFmpeg ffmpeg = FFmpeg.getInstance(activity);

        //Loads binary according to users cpu type.
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure() {

                }

                @Override
                public void onSuccess() {

                }

                @Override
                public void onFinish() {

                }
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle if FFmpeg is not supported by device

        }
        String destination = null;

        try {
            //Source of the video file.
            //External storage directory + videos + "/" + video's name.
            String source =
                    Environment.getExternalStorageDirectory().toString() + Constants.VIDEO_FOLDER_F
                            + "/" + NameParser.createVideoName(videoUniqueIndex, fenceIndex,
                            fenceSpacing, fenceHeight);

            //Destination of the edited file.
            //External storage directory + videos + "/" + edited video's name.
            //createEditName will increase video unique index by one.
            destination =
                    Environment.getExternalStorageDirectory() + Constants.ANALYSIS_FOLDER_F +
                            "/" + NameParser.createEditName(videoUniqueIndex, fenceIndex,
                            fenceSpacing, fenceHeight, activity);

            // to execute "ffmpeg -version" command you just need to pass "-version"
            //"-i" is input file url
            //"-ss" is starting position
            //"-to" is ending position
            //"-c", "copy" is copy to url
            ffmpeg.execute(new String[]{
                            "-i", source,
                            "-ss", MsConverter.getFormattedTime(startPosition),
                            "-to", MsConverter.getFormattedTime(endPosition),
                            "-c", "copy", destination},
                    new ExecuteBinaryResponseHandler() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onProgress(String message) {

                        }

                        @Override
                        public void onFailure(String message) {

                        }

                        @Override
                        public void onSuccess(String message) {

                        }

                        @Override
                        public void onFinish() {
                            callback.run();
                        }
                    });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running

        }
        return destination;

    }
}
