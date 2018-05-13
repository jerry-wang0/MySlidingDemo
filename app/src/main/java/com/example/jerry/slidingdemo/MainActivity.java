package com.example.jerry.slidingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.jerry.slidingdemo.scrollview.ScreenUtil;
import com.example.jerry.slidingdemo.scrollview.ScrollLayout;
import com.example.mylibrary.SlidingMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    SlidingMenu menu;
    @BindView(R.id.item_title_left)
    TextView mLeft;
    @BindView(R.id.scroll_down_layout)
    ScrollLayout mScrollLayout;
    @BindView(R.id.main_view)
    ConstraintLayout mMain;
    @BindView(R.id.text_foot)
    TextView foot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        //menu.setShadowDrawable(true);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        menu.setShadowDrawable(R.drawable.sliding_menu_shadow);
//        menu.setShadowWidthRes(R.dimen.menu_width);
        menu.setBehindOffsetRes(R.dimen.menu_width);
        menu.setOffsetFadeDegree(0.35f);
        menu.setFadeDegree(0.35f);
        menu.setFadeEnabled(true);
        menu.setMenu(R.layout.menu_main_layout);

        mLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle(true);
            }
        });
        initScrollLayout();
    }
    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                mScrollLayout.getBackground().setAlpha(255 - (int) precent);
            }
            if (foot.getVisibility() == View.VISIBLE)
                foot.setVisibility(View.GONE);
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
                foot.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };

    private void initScrollLayout() {
        mMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mScrollLayout.scrollToExit();
            }
        });
        /**设置 setting*/
        mScrollLayout.setMinOffset(0);
        mScrollLayout.setMaxOffset((int) (ScreenUtil.getScreenHeight(this) * 0.7));
        mScrollLayout.setMaxOffsetEnable(true);
        mScrollLayout.setExitOffset(ScreenUtil.dip2px(this, 50));
        mScrollLayout.setIsSupportExit(true);
        mScrollLayout.setAllowHorizontalScroll(true);
        //mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollLayout.setToExit();
        mScrollLayout.getBackground().setAlpha(0);
    }

    @OnClick (R.id.text_foot)
    protected void click() {
        mScrollLayout.scrollToOpen();
    }

    @OnClick (R.id.button_to_b)
    protected void clickToB() {
        Intent intent = new Intent(getApplicationContext(), ActivityB.class);
        startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (menu.isMenuShowing()) {
            menu.toggle(true);
        } else {
            super.onBackPressed();
        }
    }
}
