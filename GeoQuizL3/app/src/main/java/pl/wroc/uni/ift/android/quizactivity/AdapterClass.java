package pl.wroc.uni.ift.android.quizactivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
        TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.mTextView.setText(mDataset.get(position).getTextResId());
    }

    @Override
    public int getItemCount()
    {
        return mDataset.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextView;

        ViewHolder(TextView textView)
        {
            super(textView);
            mTextView = textView;
        }
    }
}
