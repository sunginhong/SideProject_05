package com.example.sideproject_05;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

public class Utils_Calc {
    protected Context context;

    public Utils_Calc(){

    }

    public static float ModulateCalc(float value, float rangeA, float rangeB, float rangeC, float rangeD){
        float fromHigh = 0;
        float fromLow = 0;
        float toHigh = 0;
        float toLow = 0;
        float result = 0;
        Double resultF = 0.0;

        fromLow = rangeA;
        fromHigh = rangeB;
        toLow = rangeC;
        toHigh = rangeD;

        result = toLow + (((value - fromLow) / (fromHigh - fromLow)) * (toHigh - toLow));
        return result;
    }

    public interface DelayCallback{
        void afterDelay();
    }

//    Utils.delay(4, new Utils.DelayCallback() {
//        @Override
//        public void afterDelay() {
//
//        }
//    });

    public static void delay(int secs, final DelayCallback delayCallback){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, secs * 100); // afterDelay will be executed after (secs*1000) milliseconds.
    }

    public static void delayMin(int secs, final DelayCallback delayCallback){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, secs * 10); // afterDelay will be executed after (secs*1000) milliseconds.
    }

}

