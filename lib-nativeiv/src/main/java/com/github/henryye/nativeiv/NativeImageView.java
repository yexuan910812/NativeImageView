package com.github.henryye.nativeiv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by henryye on 2018/4/10.
 */
public class NativeImageView extends FrameLayout {
    private static final String TAG = "Ni.NativeImageView";

    public NativeImageView(@NonNull Context context) {
        super(context);
    }

    public NativeImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NativeImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
