package pl.wroc.uni.ift.android.quizactivity;

/**
 * Created by jansnieg on 24/10/2017.
 */


class Question
{

    private int mTextResId;
    private boolean mAnswerTrue;
    boolean mIsAnswered;
    boolean mIsCheated;

    Question(int textResId, boolean answerTrue)
    {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mIsAnswered = false;
        mIsCheated = false;
    }

    int getTextResId()
    {
        return mTextResId;
    }

    boolean isAnswerTrue()
    {
        return mAnswerTrue;
    }

}
