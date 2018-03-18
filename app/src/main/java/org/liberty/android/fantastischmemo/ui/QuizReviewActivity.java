package org.liberty.android.fantastischmemo.ui;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.entity.Card;

import java.util.ArrayList;
import java.util.List;

public class QuizReviewActivity extends Activity {

    public static String FORGOT_CARDS = "forgot_cards";
    public static String REMEMBERED_CARDS = "remembered_cards";

    List<Card> all_forgotten_cards = new ArrayList<>();
    List<Card> all_remembered_cards = new ArrayList<>();


    private TextView titleTextView;
    private TextView forgotTextView;
    private TextView rememberedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.quiz_review_activity );
        try {
            Bundle extras = getIntent().getExtras();
            all_forgotten_cards = extras.getParcelable(FORGOT_CARDS);
            all_remembered_cards = extras.getParcelable(REMEMBERED_CARDS);
        }
        catch (NullPointerException e) {
            e.getMessage();
        }
        titleTextView = (TextView) findViewById(R.id.quiz_review_title);
        titleTextView.setText("Quiz Review");
        forgotTextView = (TextView) findViewById(R.id.quiz_forgot_title);
        forgotTextView.setText("Forgot");



        rememberedTextView = (TextView) findViewById(R.id.quiz_remembered_title);
        rememberedTextView.setText("Remembered");

    }

}
