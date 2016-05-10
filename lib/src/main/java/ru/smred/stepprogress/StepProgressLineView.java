package ru.smred.stepprogress;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Alexander Smirnov on 05.05.16.
 *
 * @TODO add override mode
 * @TODO rewrite using canvas
 */
public class StepProgressLineView extends RelativeLayout {

    private final int DEFAULT_COLOR_DEFAULT = android.R.color.black;
    private final int DEFAULT_COLOR_ACTIVE = android.R.color.holo_green_light;
    private final int DEFAULT_STEP_CURRENT = 1;
    private final int DEFAULT_STEP_COUNT = 10;

    private int mColorDefault = DEFAULT_COLOR_DEFAULT;
    private int mColorActive = DEFAULT_COLOR_ACTIVE;
    private int mStepCurrent = DEFAULT_STEP_CURRENT;
    private int mStepCount = DEFAULT_STEP_COUNT;
    private LayoutParams mLPDefault, mLPActive;

    public StepProgressLineView(Context context) {
        super(context);
        init(context, null);
    }

    public StepProgressLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StepProgressLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StepProgressLineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null && !isInEditMode()) {
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.StepProgressLineView);
            mColorDefault = arr.getResourceId(R.styleable.StepProgressLineView_color_default,
                    DEFAULT_COLOR_DEFAULT);
            mColorActive = arr.getResourceId(R.styleable.StepProgressLineView_color_active,
                    DEFAULT_COLOR_ACTIVE);
            mStepCurrent = arr.getInt(R.styleable.StepProgressLineView_step_current,
                    DEFAULT_STEP_CURRENT);
            mStepCount = arr.getInt(R.styleable.StepProgressLineView_step_count,
                    DEFAULT_STEP_COUNT);
            arr.recycle();
        }

        setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        mLPDefault = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mLPActive = new RelativeLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);

        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
        setLayoutTransition(layoutTransition);

        View defaultLine = new View(context);
        defaultLine.setLayoutParams(mLPDefault);
        defaultLine.setBackgroundColor(context.getResources().getColor(mColorDefault));

        View activeLine = new View(context);
        activeLine.setLayoutParams(mLPActive);
        activeLine.setBackgroundColor(context.getResources().getColor(mColorActive));

        addView(defaultLine);
        addView(activeLine);
    }

    public void stepNext() {
        if (mStepCurrent < mStepCount) {
            mStepCurrent++;
            updateLayouts();
        }
    }

    public void stepPrevious() {
        if (mStepCurrent > 1) {
            mStepCurrent--;
            updateLayouts();
        }
    }

    public void stepSet(int step) {
        if (step > 0 && step < mStepCount) {
            mStepCurrent = step;
            updateLayouts();
        }
    }

    private void updateLayouts() {
        final float width = getWidth();
        final float widthStep = width / mStepCount;

        mLPDefault.width = (int) width;
        mLPActive.width = (int) (widthStep * mStepCurrent);
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        updateLayouts();

    }
}

