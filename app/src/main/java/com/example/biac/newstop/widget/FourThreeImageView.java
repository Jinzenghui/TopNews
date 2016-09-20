package com.example.biac.newstop.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by BIAC on 2016/9/18.
 */
public class FourThreeImageView extends ForegroundImageView {

    public FourThreeImageView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public FourThreeImageView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec){
        int fourThreeHeight = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthSpec)*3/4,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, heightSpec);
    }
}
