package org.liberty.android.fantastischmemo.ui.quiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.entity.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QuizReviewActivity extends AppCompatActivity {

    private List<Card> combinedQuestions = new ArrayList<>();
    private RecyclerView quizRV;

    public static final String FORGOT_CARDS = "FORGOT_CARDS";
    public static final String REMEMBERED_CARDS = "REMEMBERED_CARDS";
    public static final String QUIZ_SCORE = "QUIZ_SCORE";

    ArrayList<Card> all_forgotten_cards = new ArrayList<>();
    ArrayList<Card> all_remembered_cards = new ArrayList<>();
    String quizScore;

    private TextView quizScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_review_activity);
        all_forgotten_cards = this.getIntent().getParcelableArrayListExtra(FORGOT_CARDS);
        all_remembered_cards = this.getIntent().getParcelableArrayListExtra(REMEMBERED_CARDS);
        quizScore = this.getIntent().getStringExtra(QUIZ_SCORE);
        quizRV = (RecyclerView)findViewById(R.id.recyclerview_quiz);
        quizScoreTextView = (TextView)findViewById(R.id.quiz_score);
        quizScoreTextView.setText("Score: " + quizScore);

        setForgotCards();
        setRememberedCards();
        Collections.sort(combinedQuestions, new Comparator<Card>() {
                @Override
                //ascending sorting of ArrayList
                public int compare(Card card1, Card card2) {
                    return Integer.valueOf(card1.getId().compareTo(card2.getId()));
                }
            });

        //initialize adapter
        QuizReviewAdapter mcAdapter = new QuizReviewAdapter(this, combinedQuestions);
        quizRV.setLayoutManager(new GridLayoutManager(this, 1));
        quizRV.setAdapter(mcAdapter);
    }

    private void setForgotCards() {
        for (Card c : all_forgotten_cards) {
            c.setResult(false);
        }
        if (all_forgotten_cards != null) {
            combinedQuestions.addAll(all_forgotten_cards);
        }
    }

    private void setRememberedCards() {
        for (Card c : all_remembered_cards) {
            c.setResult(true);
        }
        if (all_remembered_cards!= null) {
            combinedQuestions.addAll(all_remembered_cards);
        }
    }
}
