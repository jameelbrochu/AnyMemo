package org.liberty.android.fantastischmemo.ui;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;

public class QuizReviewActivity extends Activity {

    private TextView titleTextView;
    private TextView forgotTextView;
    private TextView rememberedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.quiz_review_activity );

        titleTextView = (TextView) findViewById(R.id.quiz_review_title);
        titleTextView.setText("Quiz Review");
        forgotTextView = (TextView) findViewById(R.id.quiz_forgot_title);
        forgotTextView.setText("Forgot");



        rememberedTextView = (TextView) findViewById(R.id.quiz_remembered_title);
        rememberedTextView.setText("Remembered");

    }

}
