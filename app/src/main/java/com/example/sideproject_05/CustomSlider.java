package com.example.sideproject_05;


import android.animation.ValueAnimator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.SeekBar;
import android.content.Context;
import android.util.AttributeSet;
import android.os.Handler;

public class CustomSlider extends android.support.v7.widget.AppCompatSeekBar implements SeekBar.OnSeekBarChangeListener {

    Context ctx;
    static boolean drag = false;
    static boolean dragENTER = false;
    float dragStart_x = 0;
    float dragStart_y = 0;
    float dragMove_x = 0;
    float dragMove_y = 0;
    float lastPos_x = 0;

    static SeekBar seekBar;
    public int valResult = 0;
    int lastSeekbarVal = 0;
    String dragDirection = "";

    int lastProgress = 0;
    int lastProgress_LEFT = 0;
    int lastProgress_RIGHT = 0;
    float currentRotate = 0;

    boolean scrollStopTest = false;
    boolean scrollStopBool = false;
    private Handler mHandler;
    private Runnable mRunnable;

    float pointer_before = 0;
    float pointer_current = 0;
    private Handler mHandlerSet_Before;
    private Runnable mRunnable_Before;
    private Handler mHandlerSet_Current;
    private Runnable mRunnable_Current;

    public CustomSlider(Context context) {
        super(context);
    }

    public CustomSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.getThumb().mutate().setAlpha(0);

        MainActivity.sliderX = seekBar.getThumbOffset();
        ctx = seekBar.getContext();
        mHandler = new Handler();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        String progressF = String.valueOf(progress);
        MainActivity.seekBar_tv.setText(progressF);
        MainActivity.seekBar_holder_tv.setText(progressF);
        MainActivity.slder_header_contain.setX((MainActivity.sliderX + seekBar.getX()) + progress);

        if (progress > lastSeekbarVal) {
            /*Seekbar moved to the right*/
            dragDirection = "RIGHT";
        } else if (progress < lastSeekbarVal) {
            /*Seekbar moved to the left*/
            dragDirection = "LEFT";
        }
//        lastProgress = progress;
        lastSeekbarVal = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // called when the user first touches the SeekBar
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // called after the user finishes moving the SeekBar
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!drag){
                    dragStart_x = MainActivity.slder_header_contain.getX() - e.getRawX();
                    dragStart_y = MainActivity.slder_header_contain.getY() - e.getRawY();

                    CustomSlider_Header.headerAnimation(true);
                    infoTransX(valResult, MainActivity.slder_header_contain.getX());
                    MainActivity.seekBar_info_contain_fl.setRotation(0);
                    MainActivity.seekBar_info_contain_fl.setX(0);
                    lastProgress = lastSeekbarVal;
//                    final Animation animScale = AnimationUtils.loadAnimation(ctx, R.anim.anim_scale);
//                    MainActivity.seekBar_info_contain.startAnimation(animScale);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                drag = true;

                if (drag){
                    seekBar.setProgress(valResult);

                    float sliderHeaderX_Min = (seekBar.getX()+(MainActivity.slder_header_contain.getWidth()/2 * 0));
                    float sliderHeaderX_Max = (seekBar.getX()+seekBar.getWidth()-(MainActivity.slder_header_contain.getWidth()*1)-MainActivity.slder_header_contain.getWidth()/2 * 0);

                    dragMove_x = e.getRawX() + dragStart_x;
                    dragMove_y = e.getRawY() + dragStart_y;
                    MainActivity.slder_header_contain.setX(dragMove_x);

                    if (MainActivity.slder_header_contain.getX() <= sliderHeaderX_Min){
                        MainActivity.slder_header_contain.setX(sliderHeaderX_Min);
                    }
                    if (MainActivity.slder_header_contain.getX() >= sliderHeaderX_Max ){
                        MainActivity.slder_header_contain.setX(sliderHeaderX_Max);
                    }

                    float slideHeader_result = Utils_Calc.ModulateCalc(MainActivity.slder_header_contain.getX(), sliderHeaderX_Min, sliderHeaderX_Max, 0, 100);
                    valResult = Math.round(slideHeader_result);

                    MainActivity.seekBar_tv.setText(String.valueOf(valResult));
                    MainActivity.seekBar_holder_tv.setText(String.valueOf(valResult));
                    infoTransX(valResult, MainActivity.slder_header_contain.getX());

                    if (dragDirection == "RIGHT") {
                        lastProgress_LEFT = 0;
                        lastProgress = lastSeekbarVal;
                        lastProgress_RIGHT = valResult-lastProgress;

                        float result = Math.round(Utils_Calc.ModulateCalc(lastProgress_RIGHT, 0, 45/3, MainActivity.seekBar_info_contain_fl.getRotation(), -45));
                        if (result <= 45 && result >= -45){
                            MainActivity.seekBar_info_contain_fl.setRotation(result);
                            MainActivity.seekBar_info_contain_fl.setX(result);
                        }
                    }
                    if (dragDirection == "LEFT") {
                        lastProgress_RIGHT = 0;
                        lastProgress = lastSeekbarVal;
                        lastProgress_LEFT = valResult - lastProgress;

                        float result = Math.round(Utils_Calc.ModulateCalc(lastProgress_LEFT, 0, -45/3, MainActivity.seekBar_info_contain_fl.getRotation(), 45));
                        if (result >= -45 && result <= 45){
                            MainActivity.seekBar_info_contain_fl.setRotation(result);
                            MainActivity.seekBar_info_contain_fl.setX(result);
                        }
                    }

                    onStopTrackingTouch(seekBar);
                    currentRotate = MainActivity.seekBar_info_contain_fl.getRotation();

                    mHandlerSet_Before = new Handler();
                    mRunnable_Before = new Runnable() {
                        @Override
                        public void run() {
                            // 실제 동작
                            pointer_before = MainActivity.seekBar_info_contain_fl.getX();
                            mHandlerSet_Before.removeCallbacks(mRunnable_Before);

                        }
                    };
                    mHandlerSet_Before.postDelayed(mRunnable_Before, 10); // 3초후에 실행

                    mHandlerSet_Current = new Handler();
                    mRunnable_Current = new Runnable() {
                        @Override
                        public void run() {
                            // 실제 동작
                            pointer_current = MainActivity.seekBar_info_contain_fl.getX();
                            mHandlerSet_Current.removeCallbacks(mRunnable_Current);

                        }
                    };
                    mHandlerSet_Current.postDelayed(mRunnable_Current, 100); // 3초후에 실행
                    if (pointer_before == pointer_current){
                        scrollStopBool = true;
                        mHandler.removeCallbacks(mRunnable);
//                        originHandler();
                    } else {
                        scrollStopBool = false;
                        mHandler.removeCallbacks(mRunnable);
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                drag = false;
                CustomSlider_Header.headerAnimation(false);
                lastProgress_RIGHT = 0;
                lastProgress_LEFT = 0;

                break;

            default:
                break;
        }
        return true;
    }

    public void originHandler() {
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(scrollStopBool) {
                    //Do something after 100ms
                    ValueAnimator anim_return = ValueAnimator.ofFloat(MainActivity.seekBar_info_contain_fl.getRotation(), 0);
                    anim_return.setDuration(300);
                    anim_return.setInterpolator(new DecelerateInterpolator(Float.valueOf(String.valueOf(1.5))));
                    anim_return.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float result = (float) animation.getAnimatedValue();
                            MainActivity.seekBar_info_contain_fl.setRotation(result);
                            MainActivity.seekBar_info_contain_fl.setX(result);
                            if (result <= 0){
                                scrollStopBool = false;
                            }
                        }
                    });
                    anim_return.start();
                }
            }
        }, 10);
    }

    private void infoTransX(float process,float contain_getX){
        MainActivity.seekBar_info_contain.setX(contain_getX + MainActivity.seekBar_fl.getPaddingLeft()/2 + CustomSlider_Header.margin*2);
    }

}
