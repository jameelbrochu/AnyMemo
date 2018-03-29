package org.liberty.android.fantastischmemo.ui;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;


public class CardViewMCActivity extends Activity {

    TextView questionText;
    TextView option1Text;
    TextView option2Text;
    TextView option3Text;
    TextView option4Text;
    TextView answerText;

    public static String QUESTION = "question";
    public static String OPTION1 = "option1";
    public static String OPTION2 = "option2";
    public static String OPTION3 = "option3";
    public static String OPTION4 = "option4";
    public static String ANSWER = "answer";

    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view_mc_activity);

        questionText = (TextView)findViewById(R.id.question);
        option1Text = (TextView)findViewById(R.id.option1);
        option2Text = (TextView) findViewById(R.id.option2);
        option3Text = (TextView) findViewById(R.id.option3);
        option4Text = (TextView) findViewById(R.id.option4);
        answerText = (TextView) findViewById(R.id.answer);

        question = getIntent().getStringExtra(QUESTION);
        option1 = getIntent().getStringExtra(OPTION1);
        option2 = getIntent().getStringExtra(OPTION2);
        option3 = getIntent().getStringExtra(OPTION3);
        option4 = getIntent().getStringExtra(OPTION4);
        answer = getIntent().getStringExtra(ANSWER);

        questionText.setText(question);
        option1Text.setText(option1);
        option2Text.setText(option2);
        option3Text.setText(option3);
        option4Text.setText(option4);
        answerText.setText(answer);


    }

}
