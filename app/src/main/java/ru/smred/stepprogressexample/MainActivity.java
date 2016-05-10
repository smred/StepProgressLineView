package ru.smred.stepprogressexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.smred.stepprogress.StepProgressLineView;

public class MainActivity extends AppCompatActivity {
    private StepProgressLineView mStepProgressLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStepProgressLineView = (StepProgressLineView) findViewById(R.id.step_progress);
    }

    public void beginAnim(View view) {
        mStepProgressLineView.stepNext();
    }

    public void stepBack(View view) {
        mStepProgressLineView.stepPrevious();
    }

    public void setStep(View view) {
        mStepProgressLineView.stepSet(7);
    }

}
