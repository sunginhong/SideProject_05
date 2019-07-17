package com.example.sideproject_05;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class Utils_Anim {
    protected Context context;

    public Utils_Anim(){
    }

    public static void TransAnim(View view, float startX, float endX, float startY, float endY, int duration) {
        TranslateAnimation anim = new TranslateAnimation(
                startX, endX,
                startY, endY );
        anim.setFillAfter(true);
        anim.setInterpolator(new DecelerateInterpolator((float) 1.5));
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    public static void bgColorAnim(View view, Object startColor, Object endColor, int duration ){
        final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(view, "backgroundColor", new ArgbEvaluator(), startColor, endColor);
        backgroundColorAnimator.setDuration(duration);
        backgroundColorAnimator.start();
    }

    public static void drawableAlphaAnim(final View view, int startAlpha, int endAlpha, int duration){
        ValueAnimator anim = ValueAnimator.ofFloat(startAlpha, endAlpha);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        anim.setInterpolator(new DecelerateInterpolator((float) 1.5));
        anim.setDuration(duration);
        anim.start();
    }

    public static void SclaeAnim(View view, float startScaleX, float endScaleX, float startScaleY,float endScaleY, float originX, float originY, int duration) {
        ScaleAnimation anim = new ScaleAnimation( startScaleX, endScaleX, startScaleY, endScaleY, Animation.RELATIVE_TO_SELF, originX, Animation.RELATIVE_TO_SELF, originY  );
        anim.setFillAfter(true);
        anim.setInterpolator(new DecelerateInterpolator((float) 1.5));
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    public static void SclaeTransAnim(View view, float startX, float endX, float startY, float endY, float startScaleX, float endScaleX, float startScaleY,float endScaleY, float originX, float originY, int duration) {
        TranslateAnimation anim1 = new TranslateAnimation(startX, endX, startY, endY );
        ScaleAnimation anim2 = new ScaleAnimation( startScaleX, endScaleX, startScaleY, endScaleY, Animation.RELATIVE_TO_SELF, originX, Animation.RELATIVE_TO_SELF, originY  );

        AnimationSet setAnim = new AnimationSet(true);
        setAnim.setFillAfter(true);
        setAnim.setInterpolator(new DecelerateInterpolator((float) 1.5));
        setAnim.setDuration(duration);
        setAnim.addAnimation(anim1);
        setAnim.addAnimation(anim2);
        view.startAnimation(setAnim);
    }

    public static void SclaeAlphaAnim(View view, float startScaleX, float endScaleX, float startScaleY,float endScaleY, float originX, float originY, float startAlpha, float endAlpha, int duration) {
        ScaleAnimation anim1 = new ScaleAnimation( startScaleX, endScaleX, startScaleY, endScaleY, Animation.RELATIVE_TO_SELF, originX, Animation.RELATIVE_TO_SELF, originY  );
        Animation anim2 = new AlphaAnimation( startAlpha, endAlpha );

        AnimationSet setAnim = new AnimationSet(true);
        setAnim.setFillAfter(true);
        setAnim.setInterpolator(new DecelerateInterpolator((float) 1.5));
        setAnim.setDuration(duration);
        setAnim.addAnimation(anim1);
        setAnim.addAnimation(anim2);
        view.startAnimation(setAnim);
    }

    public static void TransAlphaAnim(View view, float startX, float endX, float startY, float endY, float startAlpha, float endAlpha, int duration) {
        TranslateAnimation anim1 = new TranslateAnimation(
                startX, endX,
                startY, endY );
        Animation anim2 = new AlphaAnimation( startAlpha, endAlpha );

        AnimationSet setAnim = new AnimationSet(true);
        setAnim.setFillAfter(true);
        setAnim.setInterpolator(new DecelerateInterpolator((float) 1.5));
        setAnim.setDuration(duration);
        setAnim.addAnimation(anim1);
        setAnim.addAnimation(anim2);
        view.startAnimation(setAnim);
    }

    public static void AlphaAnim(View view, float startAlpha, float endAlpha, int duration) {
        Animation anim = new AlphaAnimation( startAlpha, endAlpha );
        anim.setFillAfter(true);
        anim.setInterpolator(new DecelerateInterpolator((float) 1.5));
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    public static void RotateAnim(View view, float startRotate, float endRotate, float originX, float originY, int duration) {
        RotateAnimation anim = new RotateAnimation(startRotate, endRotate, Animation.RELATIVE_TO_SELF, originX, Animation.RELATIVE_TO_SELF, originY);
        anim.setFillAfter(true);
        anim.setInterpolator(new DecelerateInterpolator((float) 1.5));
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    public static void ModulatetTransXAnim(View view, float value, float rangeA, float rangeB, float rangeC, float rangeD){
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
        view.setTranslationX(result);
    }
}