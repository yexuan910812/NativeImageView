package com.github.henryye.nativeiv;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.henryye.nativeiv.delegate.Log;
import com.github.henryye.nativeiv.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by henryye on 2018/4/10.
 */
public class BitmapWrapper {
    private static final String TAG = "Ni.BitmapWrapper";

    private IBitmap<Bitmap> mLegacyBitmapDecoderImp = new LegacyBitmap();
    private IBitmap<Long> mNativeBitmapDecoderImp = new NativeBitmap();
    private IBitmap<Object> mDummyBitmapDecoderImp = new DummyBitmapDecoder();

    private Set<IBitmap> mDecoderImps = new HashSet<>(3);

    private IBitmap mCurrentChosenBitmapImp = mDummyBitmapDecoderImp;

    private final Object DECLOCK = new Object();

    public BitmapWrapper() {
        mDecoderImps.add(mLegacyBitmapDecoderImp);
        mDecoderImps.add(mNativeBitmapDecoderImp);
        mDecoderImps.add(mDummyBitmapDecoderImp);
    }

    enum BitmapType {
        Native,
        Java,
        Undefined
    }

    @SuppressWarnings("unused")
    public void decodeImageFile(@NonNull String filePath, Bitmap.Config config) {
        if(!FileUtil.fileExists(filePath)) {
            Log.e(TAG, "hy: %s not exists");
            return;
        }
        InputStream ins = FileUtil.convertToStream(filePath);
        decodeInputStream(ins, config);
    }

    public void decodeInputStream(InputStream ins, Bitmap.Config config) {
        clearUp();
        if(ins != null) {
            chooseDecoder(ins, config);
            try {
                mCurrentChosenBitmapImp.decodeInputStream(ins, config);
            } catch (IOException e) {
                Log.printErrStackTrace(TAG, e, "hy: decodeInputStream");
            }
        }
    }

    @SuppressWarnings("unused")
    public void decodeImageFileRegion(@NonNull String filePath, Rect regionRect, Bitmap.Config config) {
        if(!FileUtil.fileExists(filePath)) {
            Log.e(TAG, "hy: %s not exists");
            return;
        }
        InputStream ins = FileUtil.convertToStream(filePath);
        decodeRegionInputStream(ins, regionRect, config);
    }

    public void decodeRegionInputStream(InputStream ins, Rect rect, Bitmap.Config config) {
        clearUp();
        if(ins != null) {
            chooseDecoderRegion(ins, config);
            try {
                mCurrentChosenBitmapImp.decodeInputStreamRegion(ins, rect, config);
            } catch (IOException e) {
                Log.printErrStackTrace(TAG, e, "hy: decodeRegionInputStream");
            }
        }
    }

    private void clearUp() {
        for(IBitmap item : mDecoderImps) {
            if(item != null) {
                item.recycle();
            }
        }
    }

    @SuppressWarnings("unused")
    public void recycle() {
        Log.i(TAG, "hy: recycling: %s", mCurrentChosenBitmapImp.getType());
        mCurrentChosenBitmapImp.recycle();
    }

    private void chooseDecoder(InputStream ins, Bitmap.Config config) {
        if(mNativeBitmapDecoderImp.accept(ins, config)) {
            mCurrentChosenBitmapImp = mNativeBitmapDecoderImp;
        }
        if(mLegacyBitmapDecoderImp.accept(ins, config)) {
            mCurrentChosenBitmapImp = mLegacyBitmapDecoderImp;
        }
        mCurrentChosenBitmapImp = mDummyBitmapDecoderImp;
    }

    private void chooseDecoderRegion(InputStream ins, Bitmap.Config config) {
        if(mNativeBitmapDecoderImp.acceptRegion(ins, config)) {
            mCurrentChosenBitmapImp = mNativeBitmapDecoderImp;
        }
        if(mLegacyBitmapDecoderImp.acceptRegion(ins, config)) {
            mCurrentChosenBitmapImp = mLegacyBitmapDecoderImp;
        }
        mCurrentChosenBitmapImp = mDummyBitmapDecoderImp;
    }

    @SuppressWarnings("unused")
    public void setImageBitmap(Bitmap bitmap) {
        clearUp();
        mCurrentChosenBitmapImp = mLegacyBitmapDecoderImp;
        mLegacyBitmapDecoderImp.forceSet(bitmap);
    }

    public interface IBitmap<Type> {
        // accept 顺序 Native->Legacy->Dummy
        BitmapType getType();
        boolean accept(InputStream ins, Bitmap.Config config);
        boolean acceptRegion(InputStream ins, Bitmap.Config config);
        void decodeInputStream(InputStream ins, Bitmap.Config config) throws IOException;
        void decodeInputStreamRegion(InputStream ins, Rect region, Bitmap.Config config) throws IOException;
        void forceSet(Type data);
        @Nullable Type provide();
        void recycle();
    }

    @SuppressWarnings("unused")
    public void setJavaBitmapDecoder(IBitmap<Bitmap> customBitmapDecoder) {
        synchronized (DECLOCK) {
            this.mLegacyBitmapDecoderImp.recycle();
            this.mLegacyBitmapDecoderImp = customBitmapDecoder;
        }
    }


    private class DummyBitmapDecoder implements IBitmap<Object> {

        @Override
        public BitmapType getType() {
            return BitmapType.Undefined;
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
            throw new IOException("Stub");
        }

        @Override
        public void decodeInputStreamRegion(InputStream ins, Rect region, Bitmap.Config config) throws IOException {
            throw new IOException("Stub");
        }

        @Override
        public void forceSet(Object data) {
        }

        @Nullable
        @Override
        public Object provide() {
            return null;
        }

        @Override
        public void recycle() {
        }
    }
}
