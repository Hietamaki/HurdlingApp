package com.example.sloth.hurdlingapp;

/**
 * This method creates names for video files.
 * TODO This method (In the future!) also parses data from the video's name.
 */
public class NameParser {
    /**
     * Creates a name for the video
     *
     * @param uniqueIndex Unique value for the name. To separate it from other names.
     * @param fenceIndex  Which gap it is.
     * @param fenceGap    Value for the distance between fences.
     * @param fenceHeight Value for the height of the fence.
     * @return The video's name.
     */
    public static String createName(int uniqueIndex, int fenceIndex, String fenceGap, String fenceHeight) {
        return "video_" + uniqueIndex + "_" + fenceGap + "_" + fenceHeight + "_" + fenceIndex + "_.mp4";
    }
}
