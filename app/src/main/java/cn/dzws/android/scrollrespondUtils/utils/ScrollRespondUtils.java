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

    private boolean viewVisible = true;
    private int targetViewTop;
    private int targetViewLeft;
    private ValueAnimator viableDropAnimator;
    private ValueAnimator closeDropAnimator;


    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高px
     */
    public int getScreenHeight() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 创建了一张白纸
        DisplayMetrics dm = new DisplayMetrics();
        // 给白纸设置宽高
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public ScrollRespondUtils(Context context) {
        this.context = context;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 响应滑动的View
     *
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
                targetHeightStrat = getScreenHeight() - targetViewHeight;
                targetViewLeft = targetView.getLeft();
                Log.d(TAG, "onScrolled targetViewHeight : " + targetViewHeight + " targetViewWidth : " + targetViewWidth + " targetHeightStrat : " + targetHeightStrat + " height : " + getScreenHeight() + " targetViewTop : " + targetViewTop + " targetViewLeft : " + targetViewLeft);

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
     *
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
     *
     * @param dy
     * @return
     */
    public ScrollRespondUtils start(int dy) {

        do {
            //向上
            if (dy > 0) {
                if (dy > mTouchSlop) {
                    //隐藏
                    if (!closeStart && viewVisible) {
                        Log.e(TAG, "ScrollRespondUtils start close");
                        close();
                    }
                }
                break;
            }
            //向下
            if (dy < 0) {
                if (Math.abs(dy) > mTouchSlop) {
                    if (!viewVisible && !visibleStart) {
                        Log.e(TAG, "ScrollRespondUtils start visible");
                        visible();
                    }
                }
                break;
            }

            Log.d(TAG, "ScrollRespondUtils start else ");

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

    /**
     * 点击隐藏，显示
     */
    public void onClickEvent() {
        if (viewVisible) {
            close();
        } else {
            visible();
        }
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
            default:
        }


        return this;
    }

    private void visible() {
        viableDropAnimator = createDropAnimator(targetView, getScreenHeight(), targetViewTop);
        viableDropAnimator.addListener(new Animator.AnimatorListener() {
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
                Log.d(TAG, "visible onAnimationCancel");
                visibleStart = false;
                viewVisible = true;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        if (!viableDropAnimator.isRunning()) {
            viableDropAnimator.start();
            if (closeDropAnimator != null) {
                closeDropAnimator.cancel();
            }
        }
    }

    private void close() {
        closeDropAnimator = createDropAnimator(targetView, targetViewTop, getScreenHeight());
        closeDropAnimator.setDuration(1000);
        closeDropAnimator.addListener(new Animator.AnimatorListener() {
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
                Log.d(TAG, "close onAnimationCancel");

                closeStart = false;
                viewVisible = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        if (!closeDropAnimator.isRunning()) {
            closeDropAnimator.start();
            if (viableDropAnimator != null) {
                viableDropAnimator.cancel();
            }
        }
    }

    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(
            new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    // 得到的值
                    int value = (Integer) valueAnimator.getAnimatedValue();
                    view.layout(targetViewLeft, value, targetViewWidth, value + targetViewHeight);
                }
            }
        );
        return animator;
    }

}
