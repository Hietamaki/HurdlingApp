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
}
