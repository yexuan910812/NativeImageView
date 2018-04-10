package com.github.henryye.nativeiv.util;

import com.github.henryye.nativeiv.delegate.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by henryye on 2018/4/10.
 */
public class FileUtil {
    private static final String TAG = "Ni.FileUtil";

    public static boolean fileExists(String filePath) {
        boolean exists;
        try {
            exists = new File(filePath).exists();
        } catch (Exception e) {
            Log.printErrStackTrace(TAG, e, "hy: exception in file exists");
            exists = false;
        }
        return exists;
    }

    public static InputStream convertToStream(String filePath) {
        File file = new File(filePath);
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            Log.printErrStackTrace(TAG, e, "hy: exception in convertToStream");
            return null;
        }
    }
}
