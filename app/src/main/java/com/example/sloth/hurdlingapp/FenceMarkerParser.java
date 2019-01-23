package com.example.sloth.hurdlingapp;

import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.View;

public class FenceMarkerParser {
    /**
     * Takes fence markers and returns a matrix where view's coordinates are turned to video's.
     * There are supposed to be 8 fence markers
     * Fence markers are supposed to point in a direction from the center.
     * Pointing order is as follows: top-left, top-right, bottom-left and bottom-right.
     *
     * @param fenceMarkerViews Are all used fence markers. The length is supposed to be 8.
     * @param videoPath        Is path of the video.
     * @param top              Is Media player's top coordinate.
     * @param right            Is media player's right coordinate.
     * @param bottom           Is media player's bottom coordinate.
     * @param left             Is media player's left coordinate.
     * @return (int[index][0 = x, 1 = y]) Matrix of fence markers changed to video's coordinates.
     */
    public static int[][] fenceMarkersToVideoCoordinates(View[] fenceMarkerViews, String videoPath,
                                                         int top, int right, int bottom, int left) {

        //Unified interface for retrieving frame and meta data from an input media file.
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        /**Sets {@link videoPath} as the data source.*/
        metaRetriever.setDataSource(videoPath);
        //Gets the video's actual height from its resolution.
        int height = Integer.valueOf(metaRetriever.extractMetadata(
                MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        //Gets the video's actual width.
        int width = Integer.valueOf(metaRetriever.extractMetadata(
                MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));

        //fenceMarkers will be returned. (int[index][x==0,y==1])
        int[][] fenceMarkers = new int[8][2];
        //Notice that it will loop only 4 times.
        for (int i = 0; i < fenceMarkerViews.length / 2; i++) {
            /**
             * {@link #getFenceMarkerXCoordinate} and {@link #getFenceMarkerOffset()}
             * There are same methods for the Y coordinate.
             *
             * Check if the coordinates are wrong way around.
             * If left coordinate is more right that right coordinate then flip it in the output.
             * Flipping is the reason for this looping only 4 times.
             */
            if (fenceMarkerViews[i].getX() > fenceMarkerViews[i + 4].getX()) {
                fenceMarkers[i][0] = getFenceMarkerXCoordinate(width, left, right,
                        fenceMarkerViews[i + 4].getX() + getFenceMarkerOffset());
                fenceMarkers[i + 4][0] = getFenceMarkerXCoordinate(width, left, right,
                        fenceMarkerViews[i].getX() + getFenceMarkerOffset());
            } else {
                fenceMarkers[i][0] = getFenceMarkerXCoordinate(width, left, right,
                        fenceMarkerViews[i].getX() + getFenceMarkerOffset());
                fenceMarkers[i + 4][0] = getFenceMarkerXCoordinate(width, left, right,
                        fenceMarkerViews[i + 4].getX() + getFenceMarkerOffset());
            }

            fenceMarkers[i][1] = getFenceMarkerYCoordinate(height, top, bottom,
                    fenceMarkerViews[i].getY() + getFenceMarkerOffset());
            fenceMarkers[i + 4][1] = getFenceMarkerYCoordinate(height, top, bottom,
                    fenceMarkerViews[i + 4].getY() + getFenceMarkerOffset());

        }
        return fenceMarkers;
    }

    /**
     * Gets fence marker's arrow's tip's x coordinate as video's coordinate.
     *
     * @param width Is video's actual width from its resolution.
     * @param left  Is media player's left coordinate.
     * @param right Is media player's right coordinate.
     * @param x     Is fence marker's arrow's tip's x coordinate.
     * @return Fence marker's arrow's tip's x coordinate as video's coordinate.
     */
    private static int getFenceMarkerXCoordinate(int width, int left, int right, float x) {
        return (int) Math.round(((double) (x - left) / ((double) right)) * ((double) width));
    }

    /**
     * Gets fence marker's arrow's tip's y coordinate as video's coordinate.
     *
     * @param height Is video's actual height from its resolution.
     * @param top    Is media player's top coordinate.
     * @param bottom Is media player's bottom coordinate.
     * @param y      Is fence marker's arrow's tip's y coordinate.
     * @return Fence marker's arrow's tip's y coordinate as video's coordinate.
     */
    private static int getFenceMarkerYCoordinate(int height, int top, int bottom, float y) {
        return (int) Math.round(((double) (y - top) / ((double) bottom)) * ((double) height));
    }

    /**
     * Gets the width/height between fence marker's left corner and arrow's tip.
     *
     * @return Width between fence marker's left corner and arrow's tip.
     */
    private static int getFenceMarkerOffset() {
        return Constants.FENCE_MARKER_CENTER;
    }
}
