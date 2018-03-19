package org.liberty.android.fantastischmemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.entity.Card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.quiz_review_activity );
        try {
            //Bundle extras = getIntent().getExtras();
            all_forgotten_cards = this.getIntent().getParcelableArrayListExtra(FORGOT_CARDS);
            all_remembered_cards = this.getIntent().getParcelableArrayListExtra(REMEMBERED_CARDS);
        }
        catch (NullPointerException e) {
            e.getMessage();
        }
        titleTextView = (TextView) findViewById(R.id.quiz_review_title);
        titleTextView.setText("Quiz Review");
        forgotTextView = (TextView) findViewById(R.id.quiz_forgot_title);
        forgotTextView.setText("Forgot");

        forgotQuestionsTextView = (TextView)findViewById(R.id.quiz_forgot_questions);

/*
        ArrayList<Card> forgotCards = new ArrayList<>();
        Card fcard1 = new Card();
        fcard1.setQuestion("Question1");
        fcard1.setId(1);
        fcard1.setAnswer("Answer1");
        forgotCards.add (fcard1);

        Card fcard2 = new Card();
        fcard2.setQuestion("Question2");
        fcard2.setId(2);
        fcard2.setAnswer("Answer2");
        forgotCards.add (fcard2);
*/

        for(int i=0; i < all_forgotten_cards.size(); i++){
            Card c = all_forgotten_cards.get(i);
            forgotQuestionsTextView.setText(forgotQuestionsTextView.getText() + "Question " + Integer.toString(c.getId()) + ":\nAnswer: " + c.getAnswer()
                    + "\n\n");
        }

        rememberedTextView = (TextView) findViewById(R.id.quiz_remembered_title);
        rememberedTextView.setText("Remembered");

        rememberQuestionsTextView = (TextView)findViewById(R.id.quiz_remember_questions);

        for(int i=0; i < all_remembered_cards.size(); i++){
            Card c = all_remembered_cards.get(i);
            rememberQuestionsTextView.setText(rememberQuestionsTextView.getText() + "Question " + Integer.toString(c.getId()) + ":\nAnswer: " + c.getAnswer()
                    + "\n\n");
        }

    }

}
