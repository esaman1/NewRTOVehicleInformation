package com.rtovehicleinformation.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {
    public MyTextView(Context paramContext) {
        super(paramContext);
        init();
    }

    public MyTextView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    public MyTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private void init() {
//        if (!isInEditMode()) {
//            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/robotoB.ttf"));
//        }
    }
}
