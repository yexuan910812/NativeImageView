package com.github.henryye.nativeiv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by henryye on 2018/4/10.
 */
class LegacyBitmap implements BitmapWrapper.IBitmap<Bitmap> {
    private Bitmap mBmInstance = null;

    private BitmapFactory.Options retrieveOptions(Bitmap.Config config) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = config;
        return options;
    }

    @Override
    public BitmapWrapper.BitmapType getType() {
        return BitmapWrapper.BitmapType.Java;
    }

    @Override
    public boolean accept(InputStream ins, Bitmap.Config config) {
        return true;
    }

    @Override
    public boolean acceptRegion(InputStream ins, Bitmap.Config config) {
        return true;
    }

    @Override
    public void decodeInputStream(InputStream ins, Bitmap.Config config) {
        mBmInstance = BitmapFactory.decodeStream(ins, null, retrieveOptions(config));
    }

    @Override
    public void decodeInputStreamRegion(InputStream ins, Rect region, Bitmap.Config config) throws IOException {
        mBmInstance = BitmapRegionDecoder.newInstance(ins, false).decodeRegion(region, retrieveOptions(config));
    }

    @Override
    public void forceSet(Bitmap data) {
        this.mBmInstance = data;
    }

    @Override
    public Bitmap provide() {
        return mBmInstance;
    }

    @Override
    public void recycle() {
        if(mBmInstance != null) {
            mBmInstance.recycle();
        }
    }
}
