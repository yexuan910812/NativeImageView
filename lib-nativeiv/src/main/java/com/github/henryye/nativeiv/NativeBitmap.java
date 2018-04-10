package com.github.henryye.nativeiv;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.Nullable;

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
        return false;
    }

    @Override
    public boolean acceptRegion(InputStream ins, Bitmap.Config config) {
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
