package com.example.biac.newstop.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.biac.newstop.R;
import com.example.biac.newstop.util.AnimUtils;
import com.example.biac.newstop.util.ColorUtils;
import com.example.biac.newstop.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BIAC on 2016/9/20.
 */
public class ElasticDragDismissFrameLayout extends FrameLayout {

    private float dragDismissDistance = Float.MAX_VALUE;
    private float dragDismissFraction = -1f;
    private float dragDismissScale = 1f;
    private boolean shouldScale = false;
    private float dragElacticity = 0.8f;

    private float totalDrag;
    private boolean draggingDown = false;
    private boolean draggingUp = false;

    float scaleHotizontal;
    float scaleAlpha;

    boolean isHorizontal;
    boolean isRecervory;

    private List<ElasticDragDismissCallback> callbacks;

    public ElasticDragDismissFrameLayout(Context context) {
        super(context, null, 0);
    }

    public ElasticDragDismissFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public ElasticDragDismissFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ElasticDragDismissFrameLayout, 0, 0);

        if(a.hasValue(R.styleable.ElasticDragDismissFrameLayout_dragDismissDistance)){
            dragDismissDistance = a.getDimensionPixelSize(R.styleable.ElasticDragDismissFrameLayout_dragDismissDistance, 0);
        }else if(a.hasValue(R.styleable.ElasticDragDismissFrameLayout_dragDismissFraction)){
            dragDismissFraction = a.getFloat(R.styleable.ElasticDragDismissFrameLayout_dragDismissFraction, dragDismissFraction);
        }

        if(a.hasValue(R.styleable.ElasticDragDismissFrameLayout_dragDismissScale)){
            dragDismissScale = a.getFloat(R.styleable.ElasticDragDismissFrameLayout_dragDismissScale, dragDismissScale);
            shouldScale = dragDismissScale != 1f;
        }

        if(a.hasValue(R.styleable.ElasticDragDismissFrameLayout_dragElasticity)){
            dragElacticity = a.getFloat(R.styleable.ElasticDragDismissFrameLayout_dragElasticity, dragElacticity);
        }

        a.recycle();
    }

    public ElasticDragDismissFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public static abstract class ElasticDragDismissCallback{

        void onDrag(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels){

        }

        void onDragDismissed(){

        }
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes){
        return true;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed){
        if(Math.abs(dx)-Math.abs(dy) > 1){
            isHorizontal = true;
            isRecervory = true;
            horizontal(dx);

        }

        if(Math.abs(dx) - Math.abs(dy) < 3){
            isHorizontal = false;
        }

        if(draggingDown && dy > 0 || draggingUp && dy < 0){
            dragScale(dy);
            consumed[1] = dy;
        }
    }

    private void horizontal(int scroll){
        if(scroll == 0)
            return;

        if(isHorizontal&&!draggingDown&&!draggingUp){
            totalDrag += scroll;
            float dragFraction = (float)Math.log10(1+(Math.abs(totalDrag) / dragDismissDistance));

            final float scale = 1 - ((1 - dragDismissScale) * dragFraction);
            scaleHotizontal = 1 - dragFraction;
            if(scaleHotizontal < 0.8f){
                scaleHotizontal = 0.8f;
            }

            scaleAlpha = 1-dragFraction;

            if(scaleHotizontal > 0.8f && scaleHotizontal < 1){
                setScaleX(scaleHotizontal);
                setScaleY(scaleHotizontal*1.1f);
                if(scaleAlpha > 0.8f && scaleAlpha < 1.0f){
                    setAlpha(scaleAlpha);
                }
            }
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed){
        dragScale(dyConsumed);
    }

    @Override
    public void onStopNestedScroll(View child){
        if(!isHorizontal&&Math.abs(totalDrag) <= dragDismissDistance){
            if(isRecervory){
                totalDrag = 0;
                draggingDown = draggingUp = false;
                setTranslationY(0f);
                setScaleX(1f);
                setScaleY(1f);
                setAlpha(1f);

                isHorizontal = false;
            }
        }

        if(Math.abs(totalDrag) >= dragDismissDistance){
            dispatchDismissCallback();
        }else{
            animate().translationY(0f)
                    .scaleX(1f)
                    .scaleY(1f).alpha(1f)
                    .setDuration(200L)
                    .setInterpolator(AnimUtils.getFastOutLinearInInterpolator(getContext()))
                    .setListener(null)
                    .start();
            totalDrag = 0;
            draggingDown = draggingUp = false;
            dispatchDragCallback(0f, 0f, 0f, 0f);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        if(dragDismissFraction > 0f){
            dragDismissDistance = h*dragDismissFraction;
        }
    }

    public void addListener(ElasticDragDismissCallback listener) {
        if (callbacks == null) {
            callbacks = new ArrayList<>();
        }
        callbacks.add(listener);
    }


    public void removeListener(ElasticDragDismissCallback listener){
        if(callbacks != null && callbacks.size() > 0){
            callbacks.remove(listener);
        }
    }

    private void dragScale(int scroll){

        if(scroll == 0) return;

        totalDrag += scroll;

        if(scroll < 0 && !draggingUp && !draggingDown){
            draggingDown = true;
            if(shouldScale) setPivotY(getHeight());
        }else if(scroll > 0 && !draggingDown && !draggingUp){
            draggingUp = true;
            if(shouldScale) setPivotY(0f);
        }

        float dragFraction = (float)Math.log10(1+(Math.abs(totalDrag)/dragDismissDistance));

        float dragTo = dragFraction*dragDismissDistance*dragElacticity;

        if(draggingUp){
            dragTo *= -1;
        }

        setTranslationY(dragTo);

        if(shouldScale){
            final float scale = 1 - ((1 - dragDismissScale) * dragFraction);
            setScaleX(scale);
            setScaleY(scale);
        }

        if((draggingDown && totalDrag >= 0) || (draggingUp && totalDrag <= 0)){
            totalDrag = dragTo = dragFraction = 0;
            draggingDown = draggingUp = false;
            setTranslationY(0f);
            setScaleX(1f);
            setScaleY(1f);
        }

        dispatchDragCallback(dragFraction, dragTo, Math.min(1f, Math.abs(totalDrag)/dragDismissDistance), totalDrag);

    }

    private void dispatchDragCallback(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels){
        if(callbacks != null && !callbacks.isEmpty()){
            for(ElasticDragDismissCallback callback:callbacks){
                callback.onDrag(elasticOffset, elasticOffsetPixels, rawOffset, rawOffsetPixels);
            }
        }
    }

    private void dispatchDismissCallback(){
        if(callbacks != null && !callbacks.isEmpty()){
            for(ElasticDragDismissCallback callback:callbacks){
                callback.onDragDismissed();
            }
        }
    }

    public static class SystemChromeFader extends ElasticDragDismissCallback{

        private final Activity activity;
        private int statusBarAlpha;
        private int navBarAlpha;
        private final boolean fadeNavBar;

        public SystemChromeFader(Activity activity){
            this.activity = activity;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                statusBarAlpha = Color.alpha(activity.getWindow().getStatusBarColor());
                navBarAlpha = Color.alpha(activity.getWindow().getNavigationBarColor());
            }

            fadeNavBar = ViewUtils.isNavBarOnBottom(activity);
        }


        @Override
        public void onDrag(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

                if(elasticOffsetPixels > 0){

                    activity.getWindow().setStatusBarColor(ColorUtils.modifyAlpha(activity.getWindow().getStatusBarColor(), (int)((1f - rawOffset)*statusBarAlpha)));
                }else if(elasticOffsetPixels == 0){
                    activity.getWindow().setStatusBarColor(ColorUtils.modifyAlpha(activity.getWindow().getStatusBarColor(), statusBarAlpha));
                    activity.getWindow().setStatusBarColor(ColorUtils.modifyAlpha(activity.getWindow().getNavigationBarColor(), navBarAlpha));
                }else if(fadeNavBar){
                    activity.getWindow().setNavigationBarColor(ColorUtils.modifyAlpha(activity.getWindow().getNavigationBarColor(), (int)((1f - rawOffset)*navBarAlpha)));
                }

            }

        }

        @Override
        public void onDragDismissed(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                activity.finishAfterTransition();
            }else{
                activity.finish();
            }
        }
    }
}








































