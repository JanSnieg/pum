package pl.wroc.uni.ift.android.quizactivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity
{
    public static final String ANSWER = "ANSWER";

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
    private int mAnsweredQuestions = 0;


    // List 3 new variables
    private int mHintNumber = 3;
    private static final String TAG = "QuizActivity";
    private Button mCheatButton;
    private static int CHEAT_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setTitle(R.string.app_name);
        // inflating view objects
        setContentView(R.layout.activity_quiz);


        //Added This statement to check if there isn't any saved states
        // if there isn't this saves it.
        if (savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt("QUESTION_INDEX");
            mAnsweredQuestions = savedInstanceState.getInt("ANSWERED_QUESTIONS");
            Log.i(TAG, String.format("onCreate(): Restoring saved index %d", mCurrentIndex));

            if (mQuestionsBank == null) // check if mQuestion bank was correctly imported
                Log.e(TAG, "Question bank array was not correctly returned from Bundle");
            else
                Log.i(TAG, "Question bank array was correctly returned from Bundle");
        }

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

        //Cheat button init
        mCheatButton = (Button) findViewById(R.id.cheatButton);
        mCheatButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String mHintString = "Zostało Ci: " + String.valueOf(mHintNumber-1) + " podpowiedzi.";
                showDialog(QuizActivity.this, "Czy na pewno chcesz podejrzeć odpowiedź?", String.valueOf(mHintString));
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
        mCheatButton.setEnabled(false);
    }

    private void blockNavigationButtons()
    {
        mNextButton.setEnabled(false);
        mPreviousButton.setEnabled(false);
        mQuestionTextView.setEnabled(false);
    }

    private void checkIfDone()
    {
        if (mAnsweredQuestions == mQuestionsBank.length)
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
        if (mQuestionsBank[mCurrentIndex].mIsCheated)
            mCheatButton.setEnabled(false);
        else
            mCheatButton.setEnabled(true);

        checkCheatButton();
    }

    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();
        int toastMessageId;

            if (userPressedTrue == answerIsTrue)
            {
                toastMessageId = R.string.correct_toast;
                mQuestionsBank[mCurrentIndex].mIsAnswered = true;
                mAnsweredQuestions++;
                mPlayerScore ++;
            }
            else
            {
                toastMessageId = R.string.incorrect_toast;
                mQuestionsBank[mCurrentIndex].mIsAnswered = true;
                mAnsweredQuestions++;
            }
        checkCheatButton();
        Toast message = Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT);
        message.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 150);
        message.show();
    }

    //List 3 methods

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHEAT_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                checkCheatButton();
                lockCheatButton();
            }
        }
    }

    private void checkCheatButton()
    {
        if (mHintNumber == 0 || mQuestionsBank[mCurrentIndex].mIsCheated)
            mCheatButton.setEnabled(false);
    }

    private void lockCheatButton()
    {
        mQuestionsBank[mCurrentIndex].mIsCheated = true;
    }

    public void showCheat()
    {
        mQuestionsBank[mCurrentIndex].mIsCheated = true;    //Setting ability to cheat off
        Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
        boolean answer = mQuestionsBank[mCurrentIndex].isAnswerTrue();
        intent.putExtra(ANSWER, answer);
        startActivityForResult(intent,CHEAT_REQUEST);
    }
    public void showDialog(Activity activity, String title, final CharSequence message)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Show", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                showCheat();
                mHintNumber --;
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putInt("QUESTION_INDEX", this.mCurrentIndex);
        outState.putInt("ANSWERED_QUESTIONS", this.mAnsweredQuestions);
        outState.putInt("PLAYER_SCORE", this.mPlayerScore);

        super.onSaveInstanceState(outState);
    }

}
