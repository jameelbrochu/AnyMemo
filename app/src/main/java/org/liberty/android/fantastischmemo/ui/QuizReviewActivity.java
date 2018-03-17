package org.liberty.android.fantastischmemo.ui;

import android.os.Bundle;
import android.app.Activity;

import org.liberty.android.fantastischmemo.R;

public class QuizReviewActivity extends Activity {

    public static String FORGOT_CARDS = "forgot_cards";
    public static String REMEMBERED_CARDS = "remembered_cards";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.quiz_review_activity );
    }

}
