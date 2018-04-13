package org.liberty.android.fantastischmemo.ui.quiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.entity.History;
import org.liberty.android.fantastischmemo.ui.helper.HistoryHelper;

import java.util.ArrayList;

public class QuizHistoryActivity extends AppCompatActivity {

    public static final String EXTRA_DBPATH = "dbpath";

    private TextView averageTextView;
    private TextView attemptTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_history_activity);

        ArrayList<History> histories = this.getIntent().getParcelableArrayListExtra("HISTORY");
        averageTextView = (TextView) findViewById(R.id.quiz_history_average);
        attemptTextView = (TextView) findViewById(R.id.quiz_history_attempt);
        displayQuizInfo(histories);

    }

    private void displayQuizInfo(ArrayList<History> histories) {
        if (histories.size() > 0) {
            Double average = HistoryHelper.computeAverage(histories);

            averageTextView.setText("\nAverage: " + average + "%\n");

            int attempt = 1;
            for (History history : histories) {
                String estDate = HistoryHelper.estDate(history.getTimeStamp());
                attemptTextView.append("Attempt " + attempt + ": " + history.getMark() + "% (" + estDate + ")\n\n");
                attempt++;
            }
        } else {
            attemptTextView.setText("You have not attempted this quiz!");
        }
    }
}
