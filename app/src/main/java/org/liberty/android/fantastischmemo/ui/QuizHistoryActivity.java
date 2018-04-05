package org.liberty.android.fantastischmemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;

public class QuizHistoryActivity extends AppCompatActivity {

    private TextView averageTextView;
    private TextView attemptTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_history_activity);

        averageTextView = (TextView) findViewById(R.id.quiz_history_average);
        averageTextView.setText("Average: " );

        attemptTextView = (TextView) findViewById(R.id.quiz_history_attempt);
        attemptTextView.setText("Attempt ");
    }
}
