package com.github.henryye.nativeiv;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.Nullable;

import com.github.henryye.nativeiv.util.FormatUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by henryye on 2018/4/10.
 */
class NativeBitmap implements BitmapWrapper.IBitmap<Long> {
    private long mNativeHandle = -1;

    private void triggerThrow(InputStream in) throws IOException {
        if(in == null) {
            throw new IOException("Null input stream");
        }
    }

    @Override
    public BitmapWrapper.BitmapType getType() {
        return BitmapWrapper.BitmapType.Native;
    }

    @Override
    public boolean accept(InputStream ins, Bitmap.Config config) {
        FormatUtil.PictureFormat format = FormatUtil.getImageFormat(ins);
        // current only supports png and jpeg, with config argb8888, argb4444 and argb565
        if((format == FormatUtil.PictureFormat.JPG || format == FormatUtil.PictureFormat.PNG) && (config == Bitmap.Config.ARGB_8888 || config == Bitmap.Config.ARGB_4444 || config == Bitmap.Config.RGB_565)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean acceptRegion(InputStream ins, Bitmap.Config config) {
        // 目前不能native decode region
        return false;
    }

    @Override
    public void decodeInputStream(InputStream ins, Bitmap.Config config) throws IOException {
        triggerThrow(ins);
    }

    @Override
    public void decodeInputStreamRegion(InputStream ins, Rect region, Bitmap.Config config) throws IOException {
        triggerThrow(ins);
    }

    @Override
    public void forceSet(Long data) {
        this.mNativeHandle = data;
    }

    @Nullable
    @Override
    public Long provide() {
        return null;
    }

    @Override
    public void recycle() {
        //TODO
    }
}
