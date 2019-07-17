package com.example.sideproject_05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static TextView seekBar_tv;
    static TextView seekBar_holder_tv;
    private TextView seekBar_holder_title;
    static View slder_header_canvas;
    static FrameLayout seekBar_fl;
    static FrameLayout slder_header_contain;
    static FrameLayout seekBar_info_contain;
    static FrameLayout seekBar_info_contain_fl;
    static FrameLayout seekBar_child_fl;
    static int sliderX = 0;
    static int sliderY = 0;
    static int screenWidth = 0;
    static int screenHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        seekBar_tv = (TextView) findViewById(R.id.seekBar_tv);
        seekBar_tv.setText("0");
        seekBar_holder_tv = (TextView) findViewById(R.id.seekBar_holder_tv);
        seekBar_holder_tv.setText("0");
        seekBar_holder_title = (TextView) findViewById(R.id.seekBar_holder_title);
        seekBar_holder_title.setText("Quanitity");

        seekBar_fl = (FrameLayout) findViewById(R.id.seekBar_fl);
        seekBar_child_fl = (FrameLayout) findViewById(R.id.seekBar_child_fl);
        slder_header_contain = (FrameLayout) findViewById(R.id.slder_header_contain);
        slder_header_canvas = (View) findViewById(R.id.slder_header_canvas);
        seekBar_info_contain = (FrameLayout) findViewById(R.id.seekBar_info_contain);
        seekBar_info_contain.setX(seekBar_fl.getPaddingLeft()/2 + 5*2);
        seekBar_info_contain.setAlpha(0);
        seekBar_info_contain_fl = (FrameLayout) findViewById(R.id.seekBar_info_contain_fl);
        seekBar_info_contain.setClipChildren(false);

//        seekBar_info_contain_fl.setRotation(45);
    }
}
