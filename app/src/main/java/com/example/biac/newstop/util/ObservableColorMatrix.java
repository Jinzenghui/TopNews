package com.example.biac.newstop.util;

import android.graphics.ColorMatrix;
import android.util.Property;

/**
 * Created by BIAC on 2016/9/18.
 */
public class ObservableColorMatrix extends ColorMatrix {

    private float saturation = 1f;

    public ObservableColorMatrix(){
        super();
    }

    public float getSaturation(){
        return saturation;
    }

    @Override
    public void setSaturation(float saturation){
        this.saturation = saturation;
        super.setSaturation(saturation);
    }

    public static final Property<ObservableColorMatrix, Float> SATURATION
            = new AnimUtils.FloatProperty<ObservableColorMatrix>("saturation"){

        @Override
        public Float get(ObservableColorMatrix object) {
            return object.getSaturation();
        }

        @Override
        public void setValue(ObservableColorMatrix object, float value) {
            object.setSaturation(value);
        }
    };

}
