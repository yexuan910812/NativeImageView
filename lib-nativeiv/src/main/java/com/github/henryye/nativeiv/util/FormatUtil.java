package com.github.henryye.nativeiv.util;

import android.graphics.BitmapFactory;
import android.mtp.MtpConstants;
import android.support.annotation.NonNull;

import com.github.henryye.nativeiv.delegate.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by henryye on 2018/4/11.
 */
public class FormatUtil {
    private static final String TAG = "Ni.FormatUtil";

    public enum PictureFormat {
        JPG, PNG, BMP, GIF, WEBP, UNKNOWN
    }

    private static final Map<String, PictureFormat> sFormatMap = new HashMap<>(5);

    static {
        sFormatMap.put("image/jpeg", PictureFormat.JPG);
        sFormatMap.put("image/gif", PictureFormat.GIF);
        sFormatMap.put("image/png", PictureFormat.PNG);
        sFormatMap.put("image/x-ms-bmp", PictureFormat.BMP);
        sFormatMap.put("image/webp", PictureFormat.WEBP);
    }

    /**
     * Get picture format from given input stream
     * @param stream the given stream
     * @return the format
     */
    public static PictureFormat getImageFormat(@NonNull InputStream stream) {
        // 通过JustDecodeBounds方法，获取图片基本信息
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream markableStream = StreamUtil.compatMarkableInputStream(stream);
        StreamUtil.markStream(markableStream);
        BitmapFactory.decodeStream(markableStream, null, options);
        PictureFormat ret = sFormatMap.get(options.outMimeType);
        if(ret == null) {
            ret = PictureFormat.UNKNOWN;
        }
        try {
            markableStream.reset();
        } catch (IOException e) {
            Log.printErrStackTrace(TAG, e, "hy: the given stream is markable, but still reset error. should not forward");
            ret = PictureFormat.UNKNOWN;
        }
        return ret;
    }

}
