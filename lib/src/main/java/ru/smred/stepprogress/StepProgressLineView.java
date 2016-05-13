package ru.smred.stepprogress;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
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
    private final int DEFAULT_COLOR_DISABLED = android.R.color.darker_gray;
    private final int DEFAULT_FADE_DURATION = 500;
    private final int DEFAULT_STEP_CURRENT = 1;
    private final int DEFAULT_STEP_COUNT = 10;

    private int mExitFadeDuration = DEFAULT_FADE_DURATION;
    private int mColorDefaultDisabled = DEFAULT_COLOR_DISABLED;
    private int mColorActiveDisabled = DEFAULT_COLOR_DISABLED;
    private int mColorDefault = DEFAULT_COLOR_DEFAULT;
    private int mColorActive = DEFAULT_COLOR_ACTIVE;
    private int mStepCurrent = DEFAULT_STEP_CURRENT;
    private int mStepCount = DEFAULT_STEP_COUNT;
    private View mActiveLine, mDefaultLine;

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

    private void init(Context ctx, AttributeSet attrs) {
        if (attrs != null && !isInEditMode()) {
            TypedArray arr = ctx.obtainStyledAttributes(attrs, R.styleable.StepProgressLineView);
            mExitFadeDuration = arr.getInt(R.styleable.StepProgressLineView_color_fade_duration,
                    DEFAULT_FADE_DURATION);
            mColorDefaultDisabled = arr.getResourceId(R.styleable.StepProgressLineView_color_default_disable,
                    DEFAULT_COLOR_DISABLED);
            mColorActiveDisabled = arr.getResourceId(R.styleable.StepProgressLineView_color_active_disable,
                    DEFAULT_COLOR_DISABLED);
            mColorDefault = arr.getResourceId(R.styleable.StepProgressLineView_color_default,
                    DEFAULT_COLOR_DEFAULT);
            mColorActive = arr.getResourceId(R.styleable.StepProgressLineView_color_active,
                    DEFAULT_COLOR_ACTIVE);
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

        LayoutParams lpDefault = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutParams lpActive = new RelativeLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);

        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
        setLayoutTransition(layoutTransition);

        mActiveLine = createView(ctx, lpActive, getStateListDrawable(mColorActive, mColorActiveDisabled));
        mDefaultLine = createView(ctx, lpDefault, getStateListDrawable(mColorDefault, mColorDefaultDisabled));

        addView(mDefaultLine);
        addView(mActiveLine);
    }

    private View createView(Context context, LayoutParams layoutParams, Drawable drawable) {
        View view = new View(context);
        view.setLayoutParams(layoutParams);
        view.setBackground(drawable);

        return view;
    }

    private StateListDrawable getStateListDrawable(int colorEnabled, int colorDisabled) {
        StateListDrawable stateListDrawable = new StateListDrawable();

        stateListDrawable.addState(new int[] { android.R.attr.state_enabled},
                new ColorDrawable(getColor(colorEnabled)));
        stateListDrawable.addState(new int[] { -android.R.attr.state_enabled},
                new ColorDrawable(getColor(colorDisabled)));

        stateListDrawable.setEnterFadeDuration(mExitFadeDuration);
        stateListDrawable.setExitFadeDuration(mExitFadeDuration);

        return stateListDrawable;
    }

    private int getColor(int colorId) {
        return ContextCompat.getColor(getContext(), colorId);
    }

    public void stepNext() {
        stepSet(mStepCurrent + 1);
    }

    public void stepPrevious() {
        stepSet(mStepCurrent - 1);
    }

    public void stepSet(int step) {
        if (step > 0 && step <= mStepCount) {
            mStepCurrent = step;
            updateLayouts();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        mActiveLine.setEnabled(enabled);
        mDefaultLine.setEnabled(enabled);
    }

    private void updateLayouts() {
        final float width = getWidth();
        final float widthStep = width / mStepCount;

        mDefaultLine.getLayoutParams().width = (int) width;
        mActiveLine.getLayoutParams().width = (int) (widthStep * mStepCurrent);
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        updateLayouts();
    }

}
