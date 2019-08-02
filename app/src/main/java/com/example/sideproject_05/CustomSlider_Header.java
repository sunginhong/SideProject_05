package com.example.sideproject_05;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.renderscript.Sampler;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.util.AttributeSet;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


public class CustomSlider_Header extends View {
    Context context;
    private static Paint fill;
    private static Paint border;
    private static Path path;

    private static int animationDuration = 300;

    private static float corEffect_Max = 0;
    private static float corEffect_Min = 12;
    private static float corEffect_Result = corEffect_Min;

    private static float strokeWidth_Max = 12;
    private static float strokeWidth_Min = 4;
    private static float strokeWidth_Result = strokeWidth_Min;
    private static float strokeWidth = strokeWidth_Result;

    public static int margin = 0;
    private static int sliderHeader_Width = 0;
    private static int calc_width;
    private static float minP;
    private static float maxP;
    private static float baloonMarginY = 0;

    public CustomSlider_Header(Context context) {
        super(context);
        init();
    }

    public CustomSlider_Header(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSlider_Header(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        border = new Paint();
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeJoin(Paint.Join.ROUND);
        border.setStrokeCap(Paint.Cap.ROUND);
        border.setDither(true);
        border.setAntiAlias(true);

        fill = new Paint();
        fill.setColor(Color.WHITE);
        fill.setDither(true);
        fill.setAntiAlias(true);

        Handler delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                calc();
            }
        }, 100);

    }

    private void calc(){
        margin = 5;
        sliderHeader_Width = MainActivity.slder_header_canvas.getWidth();
        corEffect_Max = sliderHeader_Width;
        calc_width = sliderHeader_Width/4;
        minP = margin + calc_width;
        maxP = sliderHeader_Width - margin - calc_width;

        strokeWidth_Result = strokeWidth_Max;
        border.setPathEffect(new CornerPathEffect(corEffect_Min));
        fill.setPathEffect(new CornerPathEffect(corEffect_Min));
//        baloonMarginY = MainActivity.seekBar_child_fl.getY() + MainActivity.seekBar_fl.getPaddingTop();
        baloonMarginY = MainActivity.seekBar_fl.getPaddingTop();

//        MainActivity.seekBar_info_contain_fl.setPivotX(0.5f);
        MainActivity.seekBar_info_contain_fl.setPivotY(1.0f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pathDraw();
        canvas.drawPath(path, fill);
        canvas.drawPath(path, border);
        invalidate();
    }

    static void pathDraw(){
        path = new Path();
        border.setAntiAlias(true);
        border.setColor(Color.rgb(91, 55, 184));
        border.setStrokeWidth(strokeWidth_Result);
        border.setStyle(Paint.Style.STROKE);
        path.reset();

        path.moveTo(minP, minP);
        path.lineTo(minP, maxP);
        path.lineTo(maxP, maxP);
        path.lineTo(maxP, minP);
        path.close();
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    static void headerAnimation(Boolean bool){
        if (bool){

            ValueAnimator anim_MinP = ValueAnimator.ofFloat(margin + calc_width, margin + 0);
            anim_MinP.setDuration(animationDuration);
            anim_MinP.setInterpolator(new DecelerateInterpolator(Float.valueOf(String.valueOf(1.5))));
            anim_MinP.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    minP = value;
                    MainActivity.slder_header_canvas.invalidate();

                    if (MainActivity.seekBar_info_contain.getAlpha() == 0){
//                    if (value <= margin+calc_width){
                        MainActivity.seekBar_info_contain.setAlpha(1);

//                        Utils_Anim.AlphaAnim(MainActivity.seekBar_info_contain, 0, 1, 300);
                        Utils_Anim.TransAlphaAnim(MainActivity.seekBar_info_contain, 0, 0, baloonMarginY, 0, 0, 1, 300);
                        Utils_Anim.SclaeAnim(MainActivity.seekBar_info_contain_fl, 0.0f, 1.0f, 0.0f, 1.0f, 0.5f, 1.0f, 300);

//                        SpringForce spring_up = new SpringForce(1)
//                                .setDampingRatio(0.5f)
//                                .setStiffness(500);
//
//                        SpringAnimation scaleXAnimation = new SpringAnimation(MainActivity.seekBar_info_contain_fl, DynamicAnimation.SCALE_X).setMinValue(0).setSpring(spring_up).setStartValue(0);
//                        scaleXAnimation.start();
//
//                        SpringAnimation scaleYAnimation = new SpringAnimation(MainActivity.seekBar_info_contain_fl, DynamicAnimation.SCALE_Y).setMinValue(0).setSpring(spring_up).setStartValue(0);
//                        scaleYAnimation.start();
//
//                        MainActivity.seekBar_info_contain_fl.setScaleX(1);
//                        MainActivity.seekBar_info_contain_fl.setScaleY(1);
                    }
                }
            });
            anim_MinP.start();

            ValueAnimator anim_MaxP = ValueAnimator.ofFloat(sliderHeader_Width - margin - calc_width, sliderHeader_Width - margin - 0);
            anim_MaxP.setDuration(animationDuration);
            anim_MaxP.setInterpolator(new DecelerateInterpolator(Float.valueOf(String.valueOf(1.5))));
            anim_MaxP.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    maxP = value;
                    MainActivity.slder_header_canvas.invalidate();
                }
            });
            anim_MaxP.start();

            ValueAnimator anim_Stroke = ValueAnimator.ofFloat(strokeWidth_Max, strokeWidth_Min);
            anim_Stroke.setDuration(animationDuration);
            anim_Stroke.setInterpolator(new DecelerateInterpolator(Float.valueOf(String.valueOf(1.5))));
            anim_Stroke.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    strokeWidth_Result = value;
                    MainActivity.slder_header_canvas.invalidate();
                }
            });
            anim_Stroke.start();

            ValueAnimator anim_Round = ValueAnimator.ofFloat(corEffect_Min, corEffect_Max);
            anim_Round.setDuration(animationDuration);
            anim_Round.setInterpolator(new DecelerateInterpolator(Float.valueOf(String.valueOf(1.5))));
            anim_Round.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    border.setPathEffect(new CornerPathEffect(value));
                    fill.setPathEffect(new CornerPathEffect(value));
                    MainActivity.slder_header_canvas.invalidate();
                }
            });
            anim_Round.start();

        } else {

            ValueAnimator anim_MinP = ValueAnimator.ofFloat(margin + 0, margin + calc_width);
            anim_MinP.setDuration(animationDuration);
            anim_MinP.setInterpolator(new DecelerateInterpolator(Float.valueOf(String.valueOf(1.5))));
            anim_MinP.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    minP = value;
                    MainActivity.slder_header_canvas.invalidate();

                    if (value == margin){
//                        Utils_Anim.AlphaAnim(MainActivity.seekBar_info_contain, 1, 0, 300);
                        Utils_Anim.TransAlphaAnim(MainActivity.seekBar_info_contain, 0, 0, 0, baloonMarginY, 1, 0, 300);
                        Utils_Anim.SclaeAnim(MainActivity.seekBar_info_contain_fl, 1.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f, 300);

//                        SpringForce spring_down = new SpringForce(0)
//                                .setDampingRatio(0.5f)
//                                .setStiffness(10);
//
//                        SpringAnimation scaleXAnimation = new SpringAnimation(MainActivity.seekBar_info_contain_fl, DynamicAnimation.SCALE_X).setMinValue(0).setSpring(spring_down).setStartValue(1);
//                        scaleXAnimation.start();
//
//                        SpringAnimation scaleYAnimation = new SpringAnimation(MainActivity.seekBar_info_contain_fl, DynamicAnimation.SCALE_Y).setMinValue(0).setSpring(spring_down).setStartValue(1);
//                        scaleYAnimation.start();

                        Utils_Calc.delayMin(30, new Utils_Calc.DelayCallback() {
                            @Override
                            public void afterDelay() {
                                MainActivity.seekBar_info_contain.setAlpha(0);
                            }
                        });
                    }
                }
            });
            anim_MinP.start();

            ValueAnimator anim_MaxP = ValueAnimator.ofFloat(sliderHeader_Width - margin - 0, sliderHeader_Width - margin - calc_width);
            anim_MaxP.setDuration(animationDuration);
            anim_MaxP.setInterpolator(new DecelerateInterpolator(Float.valueOf(String.valueOf(1.5))));
            anim_MaxP.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    maxP = value;
                    MainActivity.slder_header_canvas.invalidate();
                }
            });
            anim_MaxP.start();

            ValueAnimator anim_Stroke = ValueAnimator.ofFloat(strokeWidth_Min, strokeWidth_Max);
            anim_Stroke.setDuration(animationDuration);
            anim_Stroke.setInterpolator(new DecelerateInterpolator(Float.valueOf(String.valueOf(1.5))));
            anim_Stroke.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    strokeWidth_Result = value;
                    MainActivity.slder_header_canvas.invalidate();
                }
            });
            anim_Stroke.start();

            ValueAnimator anim_Round = ValueAnimator.ofFloat(corEffect_Max, corEffect_Min);
            anim_Round.setDuration(animationDuration);
            anim_Round.setInterpolator(new DecelerateInterpolator(Float.valueOf(String.valueOf(1.5))));
            anim_Round.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    border.setPathEffect(new CornerPathEffect(value));
                    fill.setPathEffect(new CornerPathEffect(value));
                    MainActivity.slder_header_canvas.invalidate();
                }
            });
            anim_Round.start();
        }
    }
}
