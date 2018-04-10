package com.github.henryye.nativeiv.util;

import android.support.annotation.NonNull;

import junit.framework.Assert;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by henryye on 2018/4/10.
 */
public class StreamUtil {
    private static final String TAG = "Ni.StreamUtil";

    private static final int DEFAULT_MARK_LENGTH = 8 * 1024 * 1024;

    public static InputStream compatMarkableInputStream(@NonNull InputStream stream) {
        if (!stream.markSupported()) {
            if (stream instanceof FileInputStream) {
                stream = new FileSeekingInputStream((FileInputStream) stream);
            } else {
                stream = new BufferedInputStream(stream);
            }
        }
        return stream;
    }

    public static void markStream(@NonNull InputStream ins) {
        Assert.assertTrue(ins.markSupported());
        ins.mark(DEFAULT_MARK_LENGTH);
    }
}
