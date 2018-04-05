package org.liberty.android.fantastischmemo.ui;

import android.app.Activity;
import android.graphics.Color;
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
    private TextView quizQuestionsTextView;

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
        quizQuestionsTextView = (TextView)findViewById(R.id.quiz_questions);

        for(int i=0; i < all_forgotten_cards.size(); i++) {
            Card c = all_forgotten_cards.get(i);
            c.setResult(false);
            //forgotQuestionsTextView.setText(forgotQuestionsTextView.getText() + "Question " + Integer.toString(c.getId()) + ": " + c.getQuestion() + "\nAnswer: " + c.getAnswer()
                   // + "\n\n");
        }

        for(int i=0; i < all_remembered_cards.size(); i++) {
            Card c = all_remembered_cards.get(i);
            c.setResult(true);
            //rememberQuestionsTextView.setText(rememberQuestionsTextView.getText() + "Question " + Integer.toString(c.getId()) + ": " + c.getQuestion() + "\nAnswer: " + c.getAnswer()
                  //  + "\n\n");
        }

        ArrayList<Card> combinedQuestions = new ArrayList<>();
        if (all_forgotten_cards != null)
            combinedQuestions.addAll(all_forgotten_cards);
        if (all_remembered_cards!= null)
            combinedQuestions.addAll(all_remembered_cards);


        for(int i=0; i < combinedQuestions.size(); i++) {
            Card c = combinedQuestions.get (i);
            if (c.getResult ( ) == true) {
                //quizQuestionsTextView.setTextColor(Color.parseColor("#ff00e676"));
                quizQuestionsTextView.setText(quizQuestionsTextView.getText() + "<font color=\"green\">Question " + Integer.toString(c.getId()) + ": " +
                        c.getQuestion() + "\nAnswer: " + c.getAnswer() + "</font>\n\n");
            } else {
                //quizQuestionsTextView.setTextColor(Color.parseColor("#ffff4444"));
                quizQuestionsTextView.setText(quizQuestionsTextView.getText() + "<font color=\"red\">Question " + Integer.toString(c.getId()) + ": " +
                        c.getQuestion() + "\nAnswer: " + c.getAnswer() + "</font>\n\n");
            }
        }


    }

}
