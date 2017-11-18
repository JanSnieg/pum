package pl.wroc.uni.ift.android.quizactivity;

import java.util.ArrayList;
import java.util.List;

class QuestionBank
{
    private static QuestionBank instance = null;
    //Init of mQuestionBank list
    private List<Question> mQuestionBank = getQuestions();

    //Init of QuestionBank Array
    private QuestionBank()
    {
        mQuestionBank = new ArrayList<>();
        mQuestionBank.add(new Question(R.string.question_stolica_polski, true));
        mQuestionBank.add(new Question(R.string.question_stolica_dolnego_slaska, false));
        mQuestionBank.add(new Question(R.string.question_sniezka, true));
        mQuestionBank.add(new Question(R.string.question_wisla, true));
    }

    static QuestionBank getInstance()
    {
        if (instance == null)
            instance = new QuestionBank();
        return instance;
    }

    //Getting mQuestionBank list
    List<Question> getQuestions()
    {
        return mQuestionBank;
    }

    //Getting current Question, according to index
    Question getQuestion(int index)
    {
        return mQuestionBank.get(index);
    }

    //Returning mQuestionBank size
    int size()
    {
        return mQuestionBank.size();
    }
}
