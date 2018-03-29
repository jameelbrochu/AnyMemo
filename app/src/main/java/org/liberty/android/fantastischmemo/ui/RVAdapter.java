package org.liberty.android.fantastischmemo.ui;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MultipleChoiceViewHolder>{

    public static class MultipleChoiceViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView questionText;
        TextView option1Text;
        TextView option2Text;
        TextView option3Text;
        TextView option4Text;
        TextView answerText;

        MultipleChoiceViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            questionText = (TextView)itemView.findViewById(R.id.question);
            option1Text = (TextView)itemView.findViewById(R.id.option1);
            option2Text = (TextView)itemView.findViewById(R.id.option2);
            option3Text = (TextView)itemView.findViewById(R.id.option3);
            option4Text = (TextView)itemView.findViewById(R.id.option4);
            answerText = (TextView)itemView.findViewById(R.id.answer);
        }
    }
    List<MultipleChoiceCard> mcCards;

    RVAdapter(List<MultipleChoiceCard> mcCards){
        this.mcCards = mcCards;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MultipleChoiceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        MultipleChoiceViewHolder mcvh = new MultipleChoiceViewHolder(v);
        return mcvh;
    }

    @Override
    public void onBindViewHolder(MultipleChoiceViewHolder multipleChoiceHolder, int i) {
        multipleChoiceHolder.questionText.setText(mcCards.get(i).getQuestion());
        multipleChoiceHolder.option1Text.setText(mcCards.get(i).getOption1());
        multipleChoiceHolder.option2Text.setText(mcCards.get(i).getOption2());
        multipleChoiceHolder.option3Text.setText(mcCards.get(i).getOption3());
        multipleChoiceHolder.option4Text.setText(mcCards.get(i).getOption4());
        multipleChoiceHolder.answerText.setText(mcCards.get(i).getAnswer());

    }

    @Override
    public int getItemCount() {
        return mcCards.size();
    }
}
