package pl.wroc.uni.ift.android.quizactivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder>
{
    private List<Question> mDataset;

    AdapterClass(List<Question> mDataset)
    {
        this.mDataset = mDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        QuestionBank questionBank = QuestionBank.getInstance();
        Question question = questionBank.getQuestion(position);
        boolean answer = questionBank.getQuestion(position).isAnswerTrue();
        ((ViewHolder) holder).question.setText(question.getTextResId());
        ((ViewHolder) holder).answer.setText(String.valueOf(answer));
    }

    @Override
    public int getItemCount()
    {
        return mDataset.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView question;
        public TextView answer;

        ViewHolder(View view)
        {
            super(view);
            question = view.findViewById(R.id.question);
            answer = view.findViewById(R.id.answer);
        }
    }
}
