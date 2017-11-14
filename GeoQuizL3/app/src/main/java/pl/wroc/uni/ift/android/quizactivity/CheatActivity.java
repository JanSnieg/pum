package pl.wroc.uni.ift.android.quizactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class CheatActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        getAnswer();
    }

    private void getAnswer()
    {
        final boolean mAnswer = getIntent().getBooleanExtra(QuizActivity.ANSWER, false);
        TextView cheatTextView = (TextView) findViewById(R.id.text_view_answer);

        if (mAnswer)
            cheatTextView.setText("True");
        else
            cheatTextView.setText("False");

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", 1);
        setResult(Activity.RESULT_OK, returnIntent);
    }
}
