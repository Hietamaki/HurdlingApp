package com.example.sloth.hurdlingapp;

import android.os.Environment;

import java.io.File;

public class Constants {
    //SharedPreference keys
    public final static String INDEX_S = "index";
    public final static String EDIT_INDEX_S = "editIndex";

    //Intent keys
    public final static String VIDEO_FILE_PATH_I = "video_file_path";
    public final static String FILE_SELECT_TYPE_I = "file_select_type";

    //Files
    public final static String PROVIDER_EXTENSION_F = ".provider";
    public final static String VIDEO_FOLDER_NAME_F = "videos";
    public final static String VIDEO_FOLDER_F = "/" + VIDEO_FOLDER_NAME_F;
    public final static String ANALYSIS_FOLDER_NAME_F = "analysis";
    public final static String ANALYSIS_FOLDER_F = "/" + ANALYSIS_FOLDER_NAME_F;
    public final static String VIDEO_EXTENSION_F = ".mp4";

    //NameParser
    public final static String SPACE_N = "_";
    public final static String IS_EDITED_N = "edit";
    public final static String VIDEO_EXTENSION_N = SPACE_N + VIDEO_EXTENSION_F;

    //PHP
    public final static String UPLOADED_FILE_P = "uploaded_file";
    public final static String TYPE_P = "type";

    //XML
    public final static String FENCE_MARKER_X = "fenceMarker";

    //Lengths
    public final static int FENCE_MARKER_CENTER = 32;

    //Regexes
    public final static String REGEX_COORDINATE_POINT_FORMAT = "\\d{1,4} \\d{1,4}";

}
