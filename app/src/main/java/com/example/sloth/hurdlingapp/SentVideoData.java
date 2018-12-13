package com.example.sloth.hurdlingapp;

public class SentVideoData {

    /**
     * {@link #version}
     * This is for separating changed versions.
     */
    private final int version = 1;
    /**
     * {@link #fenceGap}
     * {@link #fenceHeight}
     * {@link #fenceIndex}
     * {@link #cornerLocations}
     * These are needed for the analysis script.
     */
    private String fenceGap;
    private String fenceHeight;
    private String fenceIndex;
    private int[][] cornerLocations = new int[8][2];
    /**
     * {@link #searchParameters}
     * User or server can search parameters to find files faster and also to categorise the files.
     */
    private String[] searchParameters;

    public SentVideoData(String fenceGap, String fenceHeight, String fenceIndex,
                         int[][] cornerLocations, String[] searchParameters) {
        setFenceGap(fenceGap);
        setFenceHeight(fenceHeight);
        setFenceIndex(fenceIndex);
        setCornerLocations(cornerLocations);
        setSearchParameters(searchParameters);
    }

    public void setFenceGap(String fenceGap) {
        this.fenceGap = fenceGap;
    }

    public void setFenceHeight(String fenceHeight) {
        this.fenceHeight = fenceHeight;
    }

    public void setFenceIndex(String fenceIndex) {
        this.fenceIndex = fenceIndex;
    }

    public void setCornerLocations(int[][] cornerLocations) {
        this.cornerLocations = cornerLocations;
    }

    public void setSearchParameters(String[] searchParameters) {
        this.searchParameters = searchParameters;
    }

}
