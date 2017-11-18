package pl.wroc.uni.ift.android.quizactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class QuestionListActivity extends AppCompatActivity
{

    public static Intent newIntent(Context context)
    {
        return new Intent(context, QuestionListActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.questions_list);

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new AdapterClass(QuestionBank.getInstance().getQuestions());
        mRecyclerView.setAdapter(mAdapter);
    }
}
