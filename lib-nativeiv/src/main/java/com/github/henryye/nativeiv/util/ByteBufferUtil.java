package com.github.henryye.nativeiv.util;

import android.support.annotation.NonNull;

import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by henryye on 2018/4/11.
 */
public class ByteBufferUtil {
    private static final String TAG = "Ni.ByteBufferUtil";

    // 默认 16KB
    private static final int DIRECT_CAPACITY = 16 * 1024;

//    public static ByteBuffer writeToDirectByteBuffer(@NonNull InputStream ins) {
//        ByteBuffer directBB = ByteBuffer.allocateDirect(DIRECT_CAPACITY);
//    }
}
