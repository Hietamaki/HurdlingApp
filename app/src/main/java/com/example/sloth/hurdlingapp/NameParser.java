package com.example.sloth.hurdlingapp;

import android.app.Activity;

import java.io.File;

/**
 * This method creates names for video files.
 * This method also parses data from the video's name.
 */
public class NameParser {

    private static final String s = Constants.SPACE_N;
    private static final String video = Constants.IS_VIDEO_N ;
    private static final String edit = Constants.IS_EDITED_N;
    private static final String analysis = Constants.ANALYSIS_FOLDER_NAME_F;
    private static final String extension = Constants.VIDEO_EXTENSION_N;


    /**
     * Creates a name for the video.
     *
     * @param uniqueIndex  Unique value for the name. To separate it from other names.
     * @param fenceIndex   Which gap it is.
     * @param fenceSpacing Value for the distance between fences.
     * @param fenceHeight  Value for the height of the fence.
     * @return The video's name.
     */
    public static String createVideoName(int uniqueIndex, int fenceIndex,
                                         String fenceSpacing, String fenceHeight) {
        return video + s + uniqueIndex + s + fenceSpacing + s + fenceHeight + s + fenceIndex
                + extension;
    }

    /**
     * Creates a name for the edited video.
     *
     * @param oldUniqueIndex Source's unique value for the name. To separate it from other sources.
     * @param fenceIndex     Which gap it is.
     * @param fenceSpacing   Value for the distance between fences.
     * @param fenceHeight    Value for the height of the fence.
     * @param activity       Activity is needed for generating new unique index.
     * @return The video's name.
     */
    public static String createEditName(int oldUniqueIndex, int fenceIndex,
                                        String fenceSpacing, String fenceHeight, Activity activity) {
        StoredData storedData = new StoredData(activity);
        return createEditName(oldUniqueIndex, storedData.getAndIncrementEditVideoIndex(),
                fenceIndex, fenceSpacing, fenceHeight);
    }

    /**
     * Creates a name for the edited video.
     *
     * @param oldUniqueIndex Source's unique value for the name. To separate it from other sources.
     * @param newUniqueIndex Target's unique value for the name. To separate it from other targets.
     * @param fenceIndex     Which gap it is.
     * @param fenceSpacing   Value for the distance between fences.
     * @param fenceHeight    Value for the height of the fence.
     * @return The video's name.
     */
    public static String createEditName(int oldUniqueIndex, int newUniqueIndex, int fenceIndex,
                                        String fenceSpacing, String fenceHeight) {
        return edit + s + oldUniqueIndex + s + newUniqueIndex + s + fenceSpacing + s + fenceHeight
                + s + fenceIndex + extension;
    }

    /**
     * Create a server-side name for the edited video. The name needs only Android unique id and
     * unique id.
     *
     * @param activity
     * @return
     */
    public static String createServerSideName(Activity activity) {
        StoredData storedData = new StoredData(activity);
        return IdManager.getUniqueId(activity) + Constants.SPACE_N + storedData.getEditVideoIndex();
    }

    /**
     * Takes video path and chops it into pieces.
     * Index 0: ..."video", a String.
     * Index 1: uniqueIndex.
     * Index 2: fenceSpacing.
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

    /**
     * Check if name string starts with video or with something else
     * @param name is name string
     * @return true if starts with "video"
     */
    public static boolean isVideo(String name) {
        String[] strings = parseName(name);
        return strings[0].equals(video);
    }

    /**
     * Check if name string starts with analysis or with something else
     * @param name is name string
     * @return true if starts with "analysis"
     */
    public static boolean isAnalysis(String name) {
        String[] strings = parseName(name);
        return strings[0].equals(analysis);
    }
}
