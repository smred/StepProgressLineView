package ru.smred.stepprogressexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
        EditText editText = (EditText) findViewById(R.id.step_number_edit_text);
        int number = 1;

        if (editText != null) {
            try {
                number = Integer.parseInt(editText.getText().toString());
            } catch (NumberFormatException nfe) {
                Log.e("Example", "Could not parse " + nfe);
            }
        } else {
            Log.e("Example", "EditText is null");
        }

        mStepProgressLineView.stepSet(number);
    }

}
