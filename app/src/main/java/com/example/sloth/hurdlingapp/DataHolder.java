package com.example.sloth.hurdlingapp;

//Global data holder.
//Holds mostly recordingSettingsActivity data.
public class DataHolder {
    public static final DataHolder Instance = new DataHolder();
    /**
     * TODO: If data is set skip recording settings
     */
    private boolean isDataSet;

    private int videoUniqueIndex;
    private String videoPath;

    private int fenceIndex;
    private String fenceHeight;
    private String fenceGap;

    public class DataWriter {

        public void markDataAsSet() {
            isDataSet = true;
        }

        public void setVideoUniqueIndex(int value) {
            videoUniqueIndex = value;
        }

        public void increaseVideoUniqueIndex() {
            videoUniqueIndex++;
        }

        public void setVideoPath(String value) {
            videoPath = value;
        }

        public void setFenceIndex(int value) {
            fenceIndex = value;
        }

        public void setFenceHeight(String value) {
            fenceHeight = value;
        }

        public void setFenceGap(String value) {
            fenceGap = value;
        }


    }

    public boolean isDataSet() {
        return isDataSet;
    }

    public int getVideoUniqueIndex() {
        return videoUniqueIndex;
    }


    public String getVideoPath() {
        return videoPath;
    }

    public int getFenceIndex() {
        return fenceIndex;
    }

    public String getFenceHeight() {
        return fenceHeight;
    }

    public String getFenceGap() {
        return fenceGap;
    }

    //Return a video's name that is depended on most of the DataHolder's values.
    public String getVideoName() {
        return "video_" + getVideoUniqueIndex() + "_" + getFenceGap() + "_" + getFenceHeight() + "_" + getFenceIndex() + "_.mp4";
    }

}
