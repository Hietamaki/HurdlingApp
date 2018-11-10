package com.example.sloth.hurdlingapp;

import android.os.Environment;

import java.io.File;

public class Constants {
    //SharedPreference keys
    public final static String INDEX_S = "index";

    //Intent keys
    public final static String VIDEO_FILE_PATH_I = "video_file_path";

    //Files
    public final static String PROVIDER_EXTENSION_F = ".provider";
    public final static String VIDEO_FOLDER_F = "/videos";
    public final static String EDITED_FOLDER_F = "/videos";
    public final static String VIDEO_EXTENSION_F = ".mp4";

    //NameParser
    public final static String SPACE_N = "_";
    public final static String IS_VIDEO_N = "video" + SPACE_N;
    public final static String IS_EDITED_N = "edit" + SPACE_N;
    public final static String VIDEO_EXTENSION_N = SPACE_N + VIDEO_EXTENSION_F;

    //PHP
    public final static String UPLOADED_FILE_P = "uploaded_file";
    public final static String TYPE_P = "type";

    //XML
    public final static String FENCE_MARKER_X = "fenceMarker";

    //Lengths
    public final static int FENCE_MARGIN_L = 56;
    public final static int FENCE_SIZE_L = 8;

}
