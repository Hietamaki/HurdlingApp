package com.example.sloth.hurdlingapp;

//Global data holder.
//Holds mostly recordingSettingsActivity data.
public class DataHolder {
    public static final DataHolder Instance = new DataHolder();
    /**
     * TODO: If data is set skip recording settings
     */
    protected boolean isDataSet;

    protected int videoUniqueIndex;
    protected String videoPath;

    protected int fenceIndex;
    protected String fenceHeight;
    protected String fenceGap;


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

class DataWriter extends DataHolder {
    DataHolder dataHolder;

    public DataWriter(DataHolder dataHolder)
    {
        this.dataHolder = dataHolder;
    }

    public void markDataAsSet() {
        dataHolder.isDataSet = true;
    }

    public void setVideoUniqueIndex(int value) {
        dataHolder.videoUniqueIndex = value;
    }

    public void increaseVideoUniqueIndex() {
        dataHolder.videoUniqueIndex++;
    }

    public void setVideoPath(String value) {
        dataHolder.videoPath = value;
    }

    public void setFenceIndex(int value) {
        dataHolder.fenceIndex = value;
    }

    public void setFenceHeight(String value) {
        dataHolder.fenceHeight = value;
    }

    public void setFenceGap(String value) {
        dataHolder.fenceGap = value;
    }
}

