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

    public int getVideoIndex()
    {
        videoIndex++;
        return videoIndex - 1;
    }
}
