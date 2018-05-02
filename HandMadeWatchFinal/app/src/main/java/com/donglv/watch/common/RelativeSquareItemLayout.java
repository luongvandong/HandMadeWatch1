package com.donglv.watch.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Dr.Cuong on 2/6/2017.
 */

public class RelativeSquareItemLayout extends RelativeLayout {
    public RelativeSquareItemLayout(Context context) {
        super(context);
    }

    public RelativeSquareItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RelativeSquareItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }
}
