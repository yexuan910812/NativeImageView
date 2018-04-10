package com.github.henryye.nativeiv.bitmap;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by henryye on 2018/4/10.
 */
public class ConfigConverter {
    public static final int N_ARGB_8888 = 0;
    public static final int N_ARGB_4444 = 1;
    public static final int N_RGB_565 = 2;

    public static final int N_UNSUPPORTED = 2;

    public static int convertToNativeEnum(@NonNull Bitmap.Config config) {
        switch (config) {
            case ARGB_8888:
                return N_ARGB_8888;
            case ARGB_4444:
                return N_ARGB_4444;
            case RGB_565:
                return N_RGB_565;
            default:
                return N_UNSUPPORTED;
        }
    }

}
