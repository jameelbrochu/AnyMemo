package org.liberty.android.fantastischmemo.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;

import java.util.List;

public class MultipleChoiceAdapter extends RecyclerView.Adapter<MultipleChoiceAdapter.MultipleChoiceHolder> {

    private Context mContext;
    private List<MultipleChoiceCard> mcCards;

    public MultipleChoiceAdapter(Context mContext, List<MultipleChoiceCard> mCards) {
        this.mContext = mContext;
        this.mcCards = mCards;
    }

    @Override
    public MultipleChoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mcInflater = LayoutInflater.from(mContext);
        view = mcInflater.inflate(R.layout.cardview_item_mc, parent, false);
        return new MultipleChoiceHolder(view);
    }

    @Override
    public void onBindViewHolder(MultipleChoiceHolder multipleChoiceHolder, int position) {
        multipleChoiceHolder.questionText.setText(mcCards.get(position).getQuestion());
        multipleChoiceHolder.option1Text.setText("Option 1: " + mcCards.get(position).getOption1());
        multipleChoiceHolder.option2Text.setText("Option 2: " + mcCards.get(position).getOption2());
        multipleChoiceHolder.option3Text.setText("Option 3: " + mcCards.get(position).getOption3());
        multipleChoiceHolder.option4Text.setText("Option 4: " + mcCards.get(position).getOption4());
        multipleChoiceHolder.answerText.setText("Answer: " + mcCards.get(position).getAnswer());

        // Set onClick listener here
    }

    @Override
    public int getItemCount() {
        return mcCards.size();
    }

    public static class MultipleChoiceHolder extends RecyclerView.ViewHolder {

        TextView questionText;
        TextView option1Text;
        TextView option2Text;
        TextView option3Text;
        TextView option4Text;
        TextView answerText;
        CardView cardView;

        public MultipleChoiceHolder(View itemView) {
            super(itemView);

            questionText = (TextView) itemView.findViewById(R.id.rv_mc_question);
            option1Text = (TextView) itemView.findViewById(R.id.rv_mc_option1);
            option2Text = (TextView) itemView.findViewById(R.id.rv_mc_option2);
            option3Text = (TextView) itemView.findViewById(R.id.rv_mc_option3);
            option4Text = (TextView) itemView.findViewById(R.id.rv_mc_option4);
            answerText = (TextView) itemView.findViewById(R.id.rv_mc_answer);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }

}
