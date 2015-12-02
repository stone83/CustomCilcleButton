package com.zui.cg.gallery.custom.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.zui.cg.R;

/**
 * @author stone
 * */
public class TapCirCleBarView extends RelativeLayout implements View.OnClickListener, View.OnTouchListener {

	private TapCirCleBarBgView tabBackview;
	private View views[] = new View[4];
	private boolean btnsClickable = false;
	private int pos = 0;
	private OnTabBarClickListener onTabBarClickListener;

	private TapCirCleBarBgView.OnAnimationEndListener onAnimationEndListener = new TapCirCleBarBgView.OnAnimationEndListener() {

		@Override
		public void onAnimationEnd() {

		}
	};

	public TapCirCleBarView(Context context) {
		this(context, null);
	}

	public TapCirCleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.widget_tabbar, this, true);
		initView();
		initListener();
		initClickable(btnsClickable = false);
	}

	private void initView() {
		tabBackview = (TapCirCleBarBgView) findViewById(R.id.tabBackView);
		views[0] = findViewById(R.id.centerIV);
		views[1] = findViewById(R.id.firstIV);
		views[2] = findViewById(R.id.secondIV);
		views[3] = findViewById(R.id.thirdIV);
		for (int i = 0; i < views.length; i++) {
			views[i].setTag(i);
		}
	}
	   
	private void initListener() {
		for (View view : views) {
			view.setOnClickListener(this);
			view.setOnTouchListener(this);
		}
		tabBackview.setOnAnimationEndListener(onAnimationEndListener);
	}

	private void initClickable(boolean clickable) {
		views[1].setClickable(clickable);
		views[2].setClickable(clickable);
		views[3].setClickable(clickable);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int position = (Integer) v.getTag();
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			tabBackview.onDown(position);
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			tabBackview.onUp(position);
		}
		return false;
	}

	@Override
	public void onClick(View v) {
	    
		int clickLocation[] = new int[2];
		v.getLocationOnScreen(clickLocation);
		clickLocation[0] = clickLocation[0] + v.getWidth() / 2;
		clickLocation[1] = clickLocation[1] + v.getHeight() / 2;
		int position = (Integer) v.getTag();
        if (position == 0) {
            initClickable(btnsClickable = !btnsClickable);
        } else {
            initClickable(true);
        }
        tabBackview.onClick(position);
        String value = "";
        switch(position){
            case 0:
                onTabBarClickListener.onMainMenuClick(position);
                break;
            case 1:
                value = TapCirCleBarBgView.ONEEIGHT;
                tabBackview.setMainBtnBitmap(tabBackview.getMenuBitmaps()[0]);
                tabBackview.setTextMain(value);
                onTabBarClickListener.onRightMainClick(position, value);
                break;
            case 2: 
                value = TapCirCleBarBgView.ONEFOUR;
                tabBackview.setMainBtnBitmap(tabBackview.getMenuBitmaps()[1]);
                onTabBarClickListener.onRightMainClick(position, value);
                tabBackview.setTextMain(value);
                break;
            case 3:
                value = TapCirCleBarBgView.ONETWO;
                tabBackview.setMainBtnBitmap(tabBackview.getMenuBitmaps()[2]);
                onTabBarClickListener.onRightMainClick(position, value);
                tabBackview.setTextMain(value);
                break;
        }
        
	}

	public void setTextMain(String text){
	    tabBackview.setTextMain(text);
	    tabBackview.invalidate();
	}
	
	public void initializePosition(int pos) {
		this.pos = pos;
		tabBackview.initPosition(pos);
		onTabBarClickListener.onRightMainClick(pos, TapCirCleBarBgView.ONEEIGHT);
		tabBackview.setTextMain(TapCirCleBarBgView.ONEEIGHT);
	}
    private void setPosition(int pos) {
        this.pos = pos;
        tabBackview.initPosition(pos);
    }
	public void bindBtnsForPos(int pos, int menuBitmapId) {
		tabBackview.setMenuBitmaps(pos, menuBitmapId);
		if (this.pos != pos) {
		    setPosition(pos);
		}
	}
	
	public void setMainBitmap(int mainBitmap) {
		tabBackview.setMainBtnBitmap(mainBitmap);
	}
	
    public void setMode(BUTTONMODE mode){
        tabBackview.setMode(mode);
    }
    
	public void setOnTabBarClickListener(OnTabBarClickListener onTabBarClickListener) {
		this.onTabBarClickListener = onTabBarClickListener;
	}
	
	public interface OnTabBarClickListener {

		void onMainMenuClick(int position);

		void onRightMainClick(int position, String value);

	}	
}