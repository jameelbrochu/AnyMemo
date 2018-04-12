package org.liberty.android.fantastischmemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.entity.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QuizReviewActivity extends Activity {

    private List<Card> combinedQuestions = new ArrayList<>();
    private RecyclerView quizRV;

    public static String FORGOT_CARDS = "FORGOT_CARDS";
    public static String REMEMBERED_CARDS = "REMEMBERED_CARDS";

    ArrayList<Card> all_forgotten_cards = new ArrayList<>();
    ArrayList<Card> all_remembered_cards = new ArrayList<>();


    private TextView titleTextView;
    private TextView quizScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_review_activity);
        try {
            all_forgotten_cards = this.getIntent().getParcelableArrayListExtra(FORGOT_CARDS);
            all_remembered_cards = this.getIntent().getParcelableArrayListExtra(REMEMBERED_CARDS);
        }
        catch (NullPointerException e) {
            e.getMessage();
        }
        titleTextView = (TextView) findViewById(R.id.quiz_review_title);
        titleTextView.setText("Quiz Review");
        quizRV = (RecyclerView) findViewById(R.id.recyclerview_quiz);
        quizScoreTextView = (TextView)findViewById(R.id.quiz_score);

        for(int i=0; i < all_forgotten_cards.size(); i++) {
            Card c = all_forgotten_cards.get(i);
            c.setResult(false);
        }

        for(int i=0; i < all_remembered_cards.size(); i++) {
            Card c = all_remembered_cards.get(i);
            c.setResult(true);
        }

        if (all_forgotten_cards != null)
            combinedQuestions.addAll(all_forgotten_cards);
        if (all_remembered_cards!= null)
            combinedQuestions.addAll(all_remembered_cards);

        //initialize adapter
        QuizReviewAdapter mcAdapter = new QuizReviewAdapter(this, combinedQuestions);
        quizRV.setLayoutManager(new GridLayoutManager(this, 1));
        quizRV.setAdapter(mcAdapter);


    }

}
