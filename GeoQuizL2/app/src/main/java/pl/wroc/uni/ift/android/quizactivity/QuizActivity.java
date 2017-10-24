package pl.wroc.uni.ift.android.quizactivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity
{


    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private TextView mQuestionTextView;

    public Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_stolica_polski, true),
            new Question(R.string.question_stolica_dolnego_slaska, false),
            new Question(R.string.question_sniezka, true),
            new Question(R.string.question_wisla, true)
    };

    private int mCurrentIndex = 0;
    private int mPlayerScore = 0;
    private int mAnwseredQuestions = 0;

    //    Bundles are generally used for passing data between various Android activities.
    //    It depends on you what type of values you want to pass, but bundles can hold all
    //    types of values and pass them to the new activity.
    //    see: https://stackoverflow
    // .com/questions/4999991/what-is-a-bundle-in-an-android-application
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        // inflating view objects
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        checkAnswer(true);
                    }
                }
        );

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new TextView.OnClickListener()

        {
            @Override
            public void onClick(View view)
            {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });

        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new TextView.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mCurrentIndex == 0)
                    mCurrentIndex = mQuestionsBank.length - 1;
                else
                    mCurrentIndex -= 1;
                updateQuestion();
            }
        });

        updateQuestion();
    }

    private void unblockButtons()
    {
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
        mQuestionTextView.setEnabled(true);
    }

    private void blockButtons()
    {
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }

    private void blockNavigationButtons()
    {
        mNextButton.setEnabled(false);
        mPreviousButton.setEnabled(false);
        mQuestionTextView.setEnabled(false);
    }

    private void checkIfDone()
    {
        if (mAnwseredQuestions == mQuestionsBank.length)
        {
            blockButtons();
            blockNavigationButtons();
            showResult();
        }
    }

    private void showResult()
    {
        String resultText = "You have " + mPlayerScore + " correct anwsers";
        Toast toast = Toast.makeText(this,resultText,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    private void updateQuestion()
    {
        unblockButtons();
        checkIfDone();
        {
            if (mQuestionsBank[mCurrentIndex].mIsAnswered)
            {
                blockButtons();
                String answeredText = "You have already answered this question";
                Toast toast = Toast.makeText(this, answeredText, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 0);
                toast.show();
            }
                int question = mQuestionsBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);
        }
    }

    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();
        int toastMessageId = 0;

            if (userPressedTrue == answerIsTrue)
            {
                toastMessageId = R.string.correct_toast;
                mQuestionsBank[mCurrentIndex].mIsAnswered = true;
                mAnwseredQuestions++;
                mPlayerScore ++;
            }
            else
            {
                toastMessageId = R.string.incorrect_toast;
                mQuestionsBank[mCurrentIndex].mIsAnswered = true;
                mAnwseredQuestions++;
            }
        Toast message = Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT);
        message.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 150);
        message.show();
    }
}
