package cn.dzws.android.scrollrespondUtils.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

/**
 * @author Lwang
 * @time 2018/1/2.
 */

public class ScrollRespondUtils {
    private String TAG = getClass().getSimpleName();
    /**
     * 判断移动的距离
     */
    private final int mTouchSlop;
    private View targetView;
    private OnParentScrollListener onParentScrollListener;
    private Context context;
    private float startY;
    private float startX;
    private int targetViewHeight;
    private int targetViewWidth;

    private int targetHeightStrat;
    private boolean closeStart = false;
    private boolean visibleStart = false;
//    private int newState;


    private boolean viewVisible = true;
    private int targetViewTop;


    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高px
     */
    public int getScreenHeight() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.heightPixels;
    }

    public ScrollRespondUtils(Context context) {
        this.context = context;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 响应滑动的View
     * @param view
     * @return
     */
    public ScrollRespondUtils setRespondView(View view) {
        this.targetView = view;
        targetView.post(new Runnable() {
            @Override
            public void run() {
                targetViewTop = targetView.getTop();
                targetViewHeight = targetView.getHeight();
                targetViewWidth = targetView.getWidth();
                targetHeightStrat = getScreenHeight() - targetViewHeight - 100;
                Log.d(TAG, "onScrolled targetViewHeight : " + targetViewHeight + " targetViewWidth : " + targetViewWidth + " targetHeightStrat : " + targetHeightStrat + " height : " + getScreenHeight() + " targetViewTop : " + targetViewTop);

            }
        });

        return this;
    }

    public ScrollRespondUtils setOnParentScrollListener(OnParentScrollListener onParentScrollListener) {
        this.onParentScrollListener = onParentScrollListener;
        return this;
    }

    /**
     * ScrollView
     * @param view
     * @param x
     * @param y
     * @param oldX
     * @param oldY
     * @return
     */
    public ScrollRespondUtils start(View view, int x, int y, int oldX, int oldY) {
       return start(y - oldY);
    }

    /**
     * RecyclerView
     * @param dy
     * @return
     */
    public ScrollRespondUtils start(int dy) {

        do {
            if (dy > 0) { //向上
                if (dy > mTouchSlop) { //隐藏
                    Log.d(TAG, "start dy : " + dy + " !closeStart : " + !closeStart);
                    if (!closeStart && viewVisible) {
                        close();
                    }
                }
                break;
            }
            if (dy < 0) { //向下
                if (Math.abs(dy) > mTouchSlop) {
                    Log.d(TAG, "start dy : " + dy + " !viewVisible && (!visibleStart || newState == 0) : " + (!viewVisible && (!visibleStart)));
                    Log.d(TAG, "start dy : " + dy + " !viewVisible  : " + !viewVisible + " !visibleStart : " + !visibleStart);
                    if (!viewVisible && !visibleStart) {
                        visible();
                    }
                }
                break;
            }
        } while (false);
        return this;
    }

    private ScrollRespondUtils isStop(int newState) {
//        this.newState = newState;
        if (newState == 0) {
            int temp = -(mTouchSlop + 1);
//            start(temp);
        }
        return this;
    }

    public ScrollRespondUtils start(MotionEvent event) {

        if (event == null) {
            throw new UnsupportedOperationException("MotionEvent can't be null...");
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = event.getY();
                startX = event.getX();
                // 初始化标记
                break;
            case MotionEvent.ACTION_MOVE:

                // 获取当前手指位置
                float endY = event.getY();
                float endX = event.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。

                do {
                    //点击事件
                    if (distanceX < mTouchSlop) {
                        break;
                    }
                    //横向滑动
                    if (distanceX > mTouchSlop && distanceX > distanceY) {
                        break;
                    }
                    //竖向滑动
                    if (distanceX > mTouchSlop && distanceX < distanceY) {
                        break;
                    }


                } while (false);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 初始化标记
                break;

        }


        return this;
    }

    private void visible() {
        ValueAnimator dropAnimator = createDropAnimator(targetView, getScreenHeight(), targetViewTop);
        dropAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.d(TAG, "visible onAnimationStart");
                visibleStart = true;
                viewVisible = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.d(TAG, "visible onAnimationEnd");
                visibleStart = false;
                viewVisible = true;
                if (onParentScrollListener != null) {
                    onParentScrollListener.onVisibility();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        dropAnimator.start();
    }

    public void close() {
        ValueAnimator dropAnimator = createDropAnimator(targetView, targetViewTop, getScreenHeight());
        dropAnimator.setDuration(1000);
        dropAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                closeStart = true;
                Log.d(TAG, "close onAnimationStart");
                viewVisible = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "close onAnimationEnd");
                closeStart = false;
                viewVisible = false;
                if (onParentScrollListener != null) {
                    onParentScrollListener.onGone();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        dropAnimator.start();
    }

    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        //   int value = (Integer)valueAnimator.getAnimatedValue();
                        int value = (Integer) valueAnimator.getAnimatedValue();// 得到的值
                        int height = view.getHeight();
                        Log.d(TAG, "onAnimationUpdate value : " + value + " height : " + height);
                        view.layout(0, value, targetViewWidth, value + targetViewHeight);
                    }
                }
        );
        return animator;
    }


}
