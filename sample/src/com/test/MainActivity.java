package com.test;

import android.app.Activity;
import android.os.Bundle;

import com.zui.cg.gallery.custom.widget.BUTTONMODE;
import com.zui.cg.gallery.custom.widget.TapCirCleBarView;
import com.zui.cg.gallery.custom.widget.TapCirCleBarView.OnTabBarClickListener;

/**
 * @author stone
 * */
public class MainActivity extends Activity{
    public static final String TAG = "huzedong";
    private TapCirCleBarView tabBarAnimView;
    private TapCirCleBarView tabBarAnimView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initTabBarAnimView();
    }

    private void initView() {
        tabBarAnimView = (TapCirCleBarView) findViewById(R.id.tabBarAnimView);
        tabBarAnimView.setMode(BUTTONMODE.CHOICEMODE);
        
        tabBarAnimView2 = (TapCirCleBarView) findViewById(R.id.tabBarAnimView2);
        tabBarAnimView2.setMode(BUTTONMODE.SINGLEMODE);
    }

    private void initListener() {
        tabBarAnimView.setOnTabBarClickListener(onTabBarClickListener);
        
        tabBarAnimView2.setOnTabBarClickListener(onTabBarClickListener);
    }

    private void initTabBarAnimView() {
        tabBarAnimView.setMainBitmap(R.drawable.nearby_icon);
        tabBarAnimView.bindBtnsForPos(0, R.drawable.nearby_icon);
        tabBarAnimView.bindBtnsForPos(1, R.drawable.profile_icon);
        tabBarAnimView.bindBtnsForPos(2, R.drawable.chats_icon);
        tabBarAnimView.initializePosition(1);
        
        tabBarAnimView2.setMainBitmap(R.drawable.nearby_icon);
        tabBarAnimView2.bindBtnsForPos(0, R.drawable.nearby_icon);
        tabBarAnimView2.bindBtnsForPos(1, R.drawable.profile_icon);
        tabBarAnimView2.bindBtnsForPos(2, R.drawable.chats_icon);
    }

    private OnTabBarClickListener onTabBarClickListener = new OnTabBarClickListener() {

        @Override
        public void onMainMenuClick(int position) {
//            Log.e("huzedong", "================= : " + position);
//            tabBarAnimView2.setVisibility(View.GONE);
            
        }

        @Override
        public void onRightMainClick(int position, String value) {
//            Log.e("huzedong", "================= : " + position + " : " + value);
            tabBarAnimView2.setTextMain(value);
            
        }
    };

}
