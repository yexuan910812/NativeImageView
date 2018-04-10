package com.github.henryye.nativeiv;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.henryye.nativeiv.delegate.Log;
import com.github.henryye.nativeiv.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by henryye on 2018/4/10.
 */
public class BitmapWrapper {
    private static final String TAG = "Ni.BitmapWrapper";

    private IBitmap<Bitmap> mLegacyBitmapDecoderImp = new LegacyBitmap();
    private IBitmap<Long> mNativeBitmapDecoderImp = new NativeBitmap();
    private IBitmap<Object> mDummyBitmapDecoderImp = new DummyBitmapDecoder();

    private IBitmap mCurrentChosenBitmapImp = mDummyBitmapDecoderImp;

    private final Object DECLOCK = new Object();

    enum BitmapType {
        Native,
        Java,
        Undefined
    }

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
                Log.printErrStackTrace(TAG, e, "hy: decodeImageFile");
            }
        }
    }

    private void clearUp() {
        mLegacyBitmapDecoderImp.recycle();
        mNativeBitmapDecoderImp.recycle();
        mDummyBitmapDecoderImp.recycle();
    }

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

    public void setImageBitmap(Bitmap bitmap) {
        clearUp();
        mLegacyBitmapDecoderImp = mLegacyBitmapDecoderImp;
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
