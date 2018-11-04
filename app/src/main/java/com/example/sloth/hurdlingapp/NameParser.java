package com.example.sloth.hurdlingapp;

import android.app.Activity;

import java.io.File;

/**
 * This method creates names for video files.
 * This method also parses data from the video's name.
 */
public class NameParser {

    private static final String video_ = Constants.IS_VIDEO_N;
    private static final String edit_ = Constants.IS_EDITED_N;
    private static final String s = Constants.SPACE_N;
    private static final String extension = Constants.VIDEO_EXTENSION_N;


    /**
     * Creates a name for the video.
     *
     * @param uniqueIndex Unique value for the name. To separate it from other names.
     * @param fenceIndex  Which gap it is.
     * @param fenceGap    Value for the distance between fences.
     * @param fenceHeight Value for the height of the fence.
     * @return The video's name.
     */
    public static String createVideoName(int uniqueIndex, int fenceIndex,
                                         String fenceGap, String fenceHeight) {
        return video_ + uniqueIndex + s + fenceGap + s + fenceHeight + s + fenceIndex + extension;
    }

    /**
     * Creates a name for the edited video.
     *
     * @param oldUniqueIndex Source's unique value for the name. To separate it from other sources.
     * @param fenceIndex     Which gap it is.
     * @param fenceGap       Value for the distance between fences.
     * @param fenceHeight    Value for the height of the fence.
     * @param activity       Activity is needed for generating new unique index.
     * @return The video's name.
     */
    public static String createEditName(int oldUniqueIndex, int fenceIndex,
                                        String fenceGap, String fenceHeight, Activity activity) {
        StoredData storedData = new StoredData(activity);
        return createEditName(oldUniqueIndex, storedData.getAndIncrementVideoIndex(),
                fenceIndex, fenceGap, fenceHeight);
    }

    /**
     * Creates a name for the edited video.
     *
     * @param oldUniqueIndex Source's unique value for the name. To separate it from other sources.
     * @param newUniqueIndex Target's unique value for the name. To separate it from other targets.
     * @param fenceIndex     Which gap it is.
     * @param fenceGap       Value for the distance between fences.
     * @param fenceHeight    Value for the height of the fence.
     * @return The video's name.
     */
    public static String createEditName(int oldUniqueIndex, int newUniqueIndex, int fenceIndex,
                                        String fenceGap, String fenceHeight) {
        return edit_ + oldUniqueIndex + s + newUniqueIndex + s + fenceGap + s + fenceHeight
                + s + fenceIndex + extension;
    }

    /**
     * Takes video path and chops it into pieces.
     * Index 0: "video", a String.
     * Index 1: uniqueIndex.
     * Index 2: fenceGap.
     * Index 3: fenceHeight.
     * Index 4: fenceIndex.
     * Index 5: ".mp4", a String
     * TODO make it work even though videoPath has "_" characters.
     *
     * @param name Video's name.
     * @return video's name split into values.
     */
    public static String[] parseName(String name) {
        return name.split(s);
    }
}
