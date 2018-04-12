package org.liberty.android.fantastischmemo.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.entity.Card;

import java.util.List;

public class QuizReviewAdapter extends RecyclerView.Adapter<QuizReviewAdapter.QuizHolder> {

    private Context context;
    private List<Card> cards;

    public QuizReviewAdapter(Context context, List<Card> cards) {
        this.context = context;
        this.cards = cards;
    }

    @Override
    public QuizHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mcInflater = LayoutInflater.from(context);
        view = mcInflater.inflate(R.layout.cardview_item_quiz_review, parent, false);
        return new QuizHolder(view);
    }

    @Override
    public void onBindViewHolder(QuizHolder quizHolder, int position) {
        quizHolder.quizQuestionText.setText("Question " + cards.get(position).getId() + ": " + cards.get(position).getQuestion());
        quizHolder.quizAnswerText.setText("Answer: " + cards.get(position).getAnswer());

        if(cards.get(position).getResult() == false) {
            quizHolder.quizQuestionText.setTextColor(Color.parseColor("#ffff4444"));
            quizHolder.quizAnswerText.setTextColor(Color.parseColor("#ffff4444"));
        } else if(cards.get(position).getResult() == true) {
            quizHolder.quizQuestionText.setTextColor(Color.parseColor("#ff00e676"));
            quizHolder.quizAnswerText.setTextColor(Color.parseColor("#ff00e676"));
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class QuizHolder extends RecyclerView.ViewHolder {

        TextView quizQuestionText;
        TextView quizAnswerText;
        CardView cardView;

        public QuizHolder(View itemView) {
            super(itemView);

            quizQuestionText = (TextView) itemView.findViewById(R.id.rv_quiz_question);
            quizAnswerText = (TextView) itemView.findViewById(R.id.rv_quiz_answer);
            cardView = (CardView) itemView.findViewById(R.id.quiz_cardview_id);
        }
    }

}