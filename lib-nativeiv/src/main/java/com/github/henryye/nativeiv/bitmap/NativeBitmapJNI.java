package com.github.henryye.nativeiv.bitmap;

import android.support.annotation.Keep;

import java.io.InputStream;

/**
 * Created by henryye on 2018/4/10.
 */
public class NativeBitmapJNI {
    @Keep
    public native static long decodeInputStream(InputStream ins, int format);
}
