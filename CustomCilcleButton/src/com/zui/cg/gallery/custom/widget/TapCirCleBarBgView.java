package com.zui.cg.gallery.custom.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import com.zui.cg.R;

/**
 * @author stone
 * */
@SuppressLint("NewApi")
public class TapCirCleBarBgView extends View {
	private RectF rectF = new RectF();
	private Paint paint;
	private PaintFlagsDrawFilter paintFlagsDrawFilter;
	private DecelerateInterpolator decelerateInterpolator;
	private OvershootInterpolator overshootInterpolator;

	private Bitmap centerBtnsBitmap[] = new Bitmap[3];
	private int positionResId[] = new int[3];

	private Bitmap mainBtnBitmap;

	private float centerXs[] = new float[4];

	private float maxOvalR = 0;

	private float dotR = 0;

	private float dotY = 0;

	private float leftCenterX = 0;
	
	private float rightCenterX = 0;

	private float mainBtnDegrees;

	private float centerBtnsDegrees;

	private long durationMillis = 300;

	private boolean isOpened = true;
	
	private boolean textselect = false;
	
	private boolean isDirectOpen = false;
	
	private boolean isClick = false;

	private boolean drawDot = false;

	private int position = 0;

	private int sizePosition = -1;

	private int width = 0;

	private int height = 0;

	private int centerX = 0;

	private int centerY = 0;
	
	private int heightDip = 56;
	private int widthDip = 220;

	private int colors[] = new int[3];

	private OnAnimationEndListener animationEndListener;
	
    private BUTTONMODE mode = BUTTONMODE.SINGLEMODE;
    
    private String textmain = "";
    
    public final static String ONETWO = "1/2";
    public final static String ONEFOUR = "1/4";
    public final static String ONEEIGHT = "1/8";
	
    private DisplayMetrics dm = new DisplayMetrics();;
    
    public void setTextMain(String text){
        this.textmain = text;
    }
    
	public int[] getMenuBitmaps(){
	    return positionResId;
	}

	public void setMode(BUTTONMODE mode){
	    this.mode = mode;
	}
	
	public void setMenuBitmaps(int position, int resId) {
		centerBtnsBitmap[position] = resId == 0 ? null : BitmapFactory.decodeResource(getResources(), resId);
		positionResId[position] = resId == 0 ? null : resId;
		invalidate();
	}

	public void setMainBtnBitmap(int resId) {
		this.mainBtnBitmap = resId == 0 ? null : BitmapFactory.decodeResource(getResources(), resId);
		invalidate();
	}

    private Animation animation = new Animation() {

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			if (isOpened) {
				leftCenterX = 0;
                rightCenterX = (width - maxOvalR) * (1 - decelerateInterpolator.getInterpolation(interpolatedTime));
			} else {
				leftCenterX = 0;
                rightCenterX = (width - maxOvalR) * decelerateInterpolator.getInterpolation(interpolatedTime);
			}
			invalidate();
		}
	};

	private Animation rotateAnimation = new Animation() {
		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			centerBtnsDegrees = overshootInterpolator.getInterpolation(interpolatedTime) * 360;
			invalidate();
		}
	};

	public long getDuration() {
		return durationMillis;
	}

	private AnimationListener animListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
//			drawDot = false;
	        drawDot = true;
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			if (!isOpened) {
				clearAnimation();
//				startAnimation(rotateAnimation);
			} else if (animationEndListener != null) {
				animationEndListener.onAnimationEnd();
			}
		}
	};

	private AnimationListener rotateAnimListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
			drawDot = true;
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			if (animationEndListener != null)
				animationEndListener.onAnimationEnd();
		}
	};

	public TapCirCleBarBgView(Context context) {
		this(context, null);
	}

	public TapCirCleBarBgView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
		initLength();
		initAnimation();
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.myattrs);
		isDirectOpen = a.getBoolean(R.styleable.myattrs_isdefopen, false);
		if(isDirectOpen){
		    isOpened = false;
		    startAnimation(animation);
		}
	}
	
    private void drawRateMainSingleIcon(Bitmap bitmap, Canvas canvas, float radius, float pointX,
            float degrees, int position, String text) {
        // Bitmap bitmapp = Bitmap.createBitmap(56, 56, Config.ARGB_8888);
        // Canvas canv = new Canvas(bitmapp);
        // canv.drawColor(Color.BLACK);

        canvas.save();

        if (isClick
                && sizePosition == 0) {
            canvas.scale(0.8f, 0.8f, pointX, centerY);
        }

        rectF.set(pointX - radius / 3, centerY - radius / 3 - DisplayUtil.dip2px(3, dm.density), pointX + radius / 3, centerY + radius
                / 3 - DisplayUtil.dip2px(3, dm.density));
        canvas.drawBitmap(bitmap, null, rectF, paint);
        canvas.restore();

        paint.setTextSize(DisplayUtil.sp2px(10, dm.scaledDensity));
        paint.setColor(0xffffffff);
        canvas.drawText("速度", DisplayUtil.dip2px(19, dm.density),
                DisplayUtil.dip2px(45, dm.density), paint);

        // rectF.set(6, 5, 56, 55);
        // canvas.drawBitmap(bitmapp, null, rectF, paint);
        canvas.restore();
        // if(null != bitmapp
        // && !bitmapp.isRecycled()){
        // bitmapp.recycle();
        // bitmapp = null;
        // }
    }
	
    private void drawRateMainSingle(Canvas canvas, float radius, float pointX, float degrees, int position, String text){
      canvas.save();
      if (isClick 
              && sizePosition == 0){
          canvas.scale(0.8f, 0.8f, pointX, centerY);
      }
      paint.setColor(0xff40bf45);
      paint.setTextSize(DisplayUtil.sp2px(17, dm.scaledDensity));
      canvas.drawText(text, DisplayUtil.dip2px(15, dm.density), DisplayUtil.dip2px(28, dm.density), paint);
      paint.setTextSize(DisplayUtil.sp2px(10, dm.scaledDensity));
      paint.setColor(0xffffffff);
      canvas.drawText("速度", DisplayUtil.dip2px(19, dm.density), DisplayUtil.dip2px(45, dm.density), paint);
      canvas.restore();
  }
	
    private void drawRateMain(Canvas canvas, float radius, float pointX, float degrees, int position, String text){
        canvas.save();
        if (isClick 
                && sizePosition == 0){
            canvas.scale(0.8f, 0.8f, pointX, centerY);
        }
        paint.setColor(0xfffafafa);
        paint.setTextSize(DisplayUtil.sp2px(17, dm.scaledDensity));
        canvas.drawText(text, DisplayUtil.dip2px(15, dm.density), DisplayUtil.dip2px(28, dm.density), paint);
        paint.setTextSize(DisplayUtil.sp2px(10, dm.scaledDensity));
        canvas.drawText("速度", DisplayUtil.dip2px(19, dm.density), DisplayUtil.dip2px(45, dm.density), paint);
        canvas.restore();
    }
	private void drawRate(Canvas canvas, float radius, float pointX, float degrees, int position, String text){
        Paint pt = new Paint();
        Rect rct = new Rect();
        pt.getTextBounds(text, 0, text.length(), rct);
        int w = rct.width();
        int h = rct.height();
	    paint.setColor(0xffffffff);
        if (this.position == position + 1) {
            paint.setColor(0xc042bf47);
        }
        canvas.save();

        paint.setTextSize(DisplayUtil.sp2px(14, dm.scaledDensity));
        if(position == 0){
            if(isClick
                    && sizePosition - 1 == position){
                canvas.scale(0.8f, 0.8f, pointX, DisplayUtil.dip2px(13, dm.density) + h);
            }
            canvas.drawText(text, pointX, DisplayUtil.dip2px(13, dm.density) + h, paint);
        } 
        if(position == 1){
            if(isClick
                    && sizePosition - 1 == position){
                canvas.scale(0.8f, 0.8f, pointX + w, DisplayUtil.dip2px(13, dm.density) + h);
            }
            canvas.drawText(text, pointX + w, DisplayUtil.dip2px(13, dm.density) + h, paint);
        }
        if(position == 2){
            if(isClick
                    && sizePosition - 1 == position){
                canvas.scale(0.8f, 0.8f, pointX + 2 * w, DisplayUtil.dip2px(13, dm.density) + h);
            }
            canvas.drawText(text, pointX + 2 * w, DisplayUtil.dip2px(13, dm.density) + h, paint);
        }   

        canvas.restore();
	}
	
	private void initPaint() {
		colors[0] = getResources().getColor(R.color.tabbar_main);
		colors[1] = getResources().getColor(R.color.tabbar_secondly);
		colors[2] = getResources().getColor(R.color.background_color);
		paint = new Paint();
		paint.setColor(colors[0]);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
	}

	private void initLength() {
		((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
		width = DisplayUtil.dip2px(widthDip, dm.density);
		height = DisplayUtil.dip2px(heightDip, dm.density);
		centerX = height / 2;
		centerY = height / 2;
		maxOvalR = height / 2;
	    leftCenterX = centerX;
	    rightCenterX = centerX;
	    centerXs[0] = centerX;
	    centerXs[1] = 2*centerXs[0] + DisplayUtil.dip2px(17, dm.density);
	    centerXs[2] = centerXs[1] + DisplayUtil.dip2px(28, dm.density);
	    centerXs[3] = centerXs[2] + DisplayUtil.dip2px(28, dm.density);
		dotR = DisplayUtil.dip2px(5/2, dm.density);
		dotY = height - DisplayUtil.dip2px(13, dm.density) - DisplayUtil.dip2px(11, dm.density);
		setLayoutParams(new RelativeLayout.LayoutParams(width, height));
	}

	private void initAnimation() {
		decelerateInterpolator = new DecelerateInterpolator(2f);
		overshootInterpolator = new OvershootInterpolator(1.0f);
		animation.setDuration(durationMillis);
		animation.setAnimationListener(animListener);
		rotateAnimation.setDuration(durationMillis);
		rotateAnimation.setAnimationListener(rotateAnimListener);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.setDrawFilter(paintFlagsDrawFilter);
		switch(mode){
		    case SINGLEMODE_ICON:
	              paint.setColor(0x7F000000);
	                canvas.drawCircle(centerX, centerY, maxOvalR, paint);	                
	                  if (mainBtnBitmap != null) {
	                       drawRateMainSingleIcon(mainBtnBitmap, canvas, maxOvalR, centerXs[0], mainBtnDegrees, 0, textmain);
	                    }
		        break;
		    case SINGLEMODE :
		        paint.setColor(0x7F000000);
		        canvas.drawCircle(centerX, centerY, maxOvalR, paint);
	              if (mainBtnBitmap != null) {
	                   drawRateMainSingle(canvas, maxOvalR, centerXs[0], mainBtnDegrees, 0, textmain);
	                }
		        break;
		    case CHOICEMODE :

		        if (rightCenterX != leftCenterX && rightCenterX - maxOvalR >= 0){
		             paint.setColor(0x7F000000);
		             rectF.set(rightCenterX - maxOvalR, 0, rightCenterX + maxOvalR, maxOvalR * 2);
		             canvas.drawArc(rectF, -90, 180, false, paint);
		                
		             canvas.drawRect(maxOvalR, 0, rightCenterX, maxOvalR * 2, paint);
		        }
                paint.setColor(0xff42bf47);
                canvas.drawCircle(centerX, centerY, maxOvalR, paint);
                
		        if (drawDot && 0 != position && !isOpened) {
		            for(int i = 1; i < centerXs.length; i++){
		                
		                Paint pt = new Paint();
		                Rect rct = new Rect();
		                pt.getTextBounds(textmain, 0, textmain.length(), rct);
		                int w = rct.width();
		                int h = rct.height();
		                paint.setColor(0x3fffffff);
		                float centerX = centerXs[i];
		                rectF.set(centerX + dotR + i * w - w/2 - 3, dotY - dotR + h, centerX + 3 * dotR + i * w - w/2 - 3, dotY + dotR + h);
		                canvas.drawArc(rectF, 0, 360, false, paint);
		            }
		        }

	            if (drawDot && 0 != position && !isOpened) {
	                paint.setColor(0xc042bf47);
	                float centerX = centerXs[position];
                    Paint pt = new Paint();
                    Rect rct = new Rect();
                    pt.getTextBounds(textmain, 0, textmain.length(), rct);
                    int w = rct.width();
                    int h = rct.height();
                    rectF.set(centerX + dotR + position * w - w/2 - 3, dotY - dotR + h, centerX + 3 * dotR + position * w - w/2 - 3, dotY + dotR + h);
	                canvas.drawArc(rectF, 0, 360, false, paint);
	             }
		        
		        if (mainBtnBitmap != null) {
		            paint.setAlpha(255);
		            drawRateMain(canvas, maxOvalR, centerXs[0], mainBtnDegrees, 0, textmain);
		        }

		        for (int i = 0; i < centerBtnsBitmap.length; i++) {
		            if(!isOpened){
	                       if (centerBtnsBitmap[i] != null) {
	                           String text = ONEEIGHT;
	                           switch(i){
	                               case 0:
	                                   text = ONEEIGHT;
	                                   break;
	                               case 1:
	                                   text = ONEFOUR;
	                                   break;
	                               case 2:
	                                   text = ONETWO;
	                                   break;
	                           }
	                           drawRate(canvas, maxOvalR, centerXs[i + 1], centerBtnsDegrees, i, text);
                        }
		            }
		        }
		        break;
		    default:
		        break;
		}

		super.onDraw(canvas);
	}
	
	private void drawIcon(Bitmap bitmap, Canvas canvas, float radius, float pointX, float degrees, int position, int mode) {
		canvas.save();
		
        if (isClick 
                && mode == 0 
                && sizePosition == 0){
            canvas.scale(0.8f, 0.8f, pointX, centerY);
        }
        
        if(isClick
                && mode == 1
                && sizePosition - 1 == position){
            canvas.scale(0.8f, 0.8f, pointX, centerY);
        }
		canvas.rotate(degrees, pointX, centerY);
		rectF.set(pointX - radius / 3, centerY - radius / 3, pointX + radius / 3, centerY + radius / 3);
		canvas.drawBitmap(bitmap, null, rectF, paint);
		canvas.restore();
	}
	
	public void onClick(int position) {
	    if(0 == position){
	        isOpened = !isOpened;
	        startAnimation(animation);   
	    } else {
	        this.position = position;
	    }
	    invalidate();
	}

	public void initPosition(int position) {
	    this.position = position;
	}

	public void onDown(int position) {
		sizePosition = position;
		isClick = true;
		invalidate();
	}

	public void onUp(int position) {
	    sizePosition = -1;
	    isClick = false;
		invalidate();
	}

	public void setOnAnimationEndListener(OnAnimationEndListener animationEndListener) {
		this.animationEndListener = animationEndListener;
	}

	public interface OnAnimationEndListener {
		void onAnimationEnd();
	}
}
