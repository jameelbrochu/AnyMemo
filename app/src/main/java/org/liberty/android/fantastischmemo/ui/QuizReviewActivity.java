package org.liberty.android.fantastischmemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.entity.Card;

import java.util.ArrayList;

public class QuizReviewActivity extends Activity {

    public static String FORGOT_CARDS = "FORGOT_CARDS";
    public static String REMEMBERED_CARDS = "REMEMBERED_CARDS";

    ArrayList<Card> all_forgotten_cards = new ArrayList<>();
    ArrayList<Card> all_remembered_cards = new ArrayList<>();


    private TextView titleTextView;
    private TextView forgotTextView;
    private TextView rememberedTextView;

    private TextView forgotQuestionsTextView;
    private TextView rememberQuestionsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_review_activity);

        all_forgotten_cards = this.getIntent().getParcelableArrayListExtra(FORGOT_CARDS);
        all_remembered_cards = this.getIntent().getParcelableArrayListExtra(REMEMBERED_CARDS);
        titleTextView = (TextView) findViewById(R.id.quiz_review_title);
        titleTextView.setText("Quiz Review");
        forgotTextView = (TextView) findViewById(R.id.quiz_forgot_title);
        forgotTextView.setText("Forgot");
        forgotQuestionsTextView = (TextView) findViewById(R.id.quiz_forgot_questions);
        rememberedTextView = (TextView) findViewById(R.id.quiz_remembered_title);
        rememberedTextView.setText("Remembered");
        rememberQuestionsTextView = (TextView)findViewById(R.id.quiz_remember_questions);
        setForgotCards();
        setRememberedCards();
    }

    private void setForgotCards() {
        for(int i=0; i < all_forgotten_cards.size(); i++) {
            Card c = all_forgotten_cards.get(i);
            forgotQuestionsTextView.setText(forgotQuestionsTextView.getText() + "Question " + Integer.toString(c.getId()) + ": " + c.getQuestion() + "\nAnswer: " + c.getAnswer()
                    + "\n\n");
        }
    }

    private void setRememberedCards() {
        for(int i=0; i < all_remembered_cards.size(); i++) {
            Card c = all_remembered_cards.get(i);
            rememberQuestionsTextView.setText(rememberQuestionsTextView.getText() + "Question " + Integer.toString(c.getId()) + ": " + c.getQuestion() + "\nAnswer: " + c.getAnswer()
                    + "\n\n");
        }
    }

}
