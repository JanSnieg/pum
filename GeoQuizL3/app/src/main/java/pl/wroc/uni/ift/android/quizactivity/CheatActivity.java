package pl.wroc.uni.ift.android.quizactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by jansnieg on 24/10/2017.
 */

public class CheatActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        getAnwser();
    }

    private void getAnwser()
    {
        boolean mAnswer = getIntent().getBooleanExtra(QuizActivity.ANSWER, false);
        TextView cheatTextView = (TextView) findViewById(R.id.text_view_answer);

        if (mAnswer)
            cheatTextView.setText("True");
        else
            cheatTextView.setText("False");
    }
}
