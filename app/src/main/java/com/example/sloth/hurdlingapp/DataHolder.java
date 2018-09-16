package com.example.sloth.hurdlingapp;

public class DataHolder {
    public static final DataHolder Instance = new DataHolder();
    private boolean dataSet;
    public boolean isDataSet(){
        return dataSet;
    }
    public void markDataAsSet()
    {
        dataSet = true;
    }
    private int videoIndex;
    private String fenceHeight;

    public String getVideoPath() {
        return videoPath;
    }

    public String getVideoName()
    {
        return "video_" +  getNextVideoIndex() + "_" + getFenceGap() + "_" +getFenceHeight() + "_" +getFenceIndex()+"_.mp4";
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    private String videoPath;
    public String getFenceHeight() {
        return fenceHeight;
    }

    public void setFenceHeight(String fenceHeight) {
        this.fenceHeight = fenceHeight;
    }


    private String fenceGap;
    public String getFenceGap() {
        return fenceGap;
    }

    public void setFenceGap(String fenceGap) {
        this.fenceGap = fenceGap;
    }

    public int getFenceIndex() {
        return fenceIndex;
    }

    public void setFenceIndex(int fenceIndex) {
        this.fenceIndex = fenceIndex;
    }

    private int fenceIndex;

    public int  getVideoIndex() {
        videoIndex++;
        return videoIndex - 1;
    }

    public int getNextVideoIndex()
    {
        return videoIndex;
    }
}
