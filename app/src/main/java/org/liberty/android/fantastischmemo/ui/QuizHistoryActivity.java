package org.liberty.android.fantastischmemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.entity.History;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class QuizHistoryActivity extends AppCompatActivity {

    public static final String EXTRA_DBPATH = "dbpath";

    private TextView averageTextView;
    private TextView attemptTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_history_activity);

        ArrayList<History> histories = this.getIntent().getParcelableArrayListExtra("HISTORY");

        Double average = computeAverage(histories);

        averageTextView = (TextView) findViewById(R.id.quiz_history_average);
        averageTextView.setText("Average: " + average + "\n\n");

        attemptTextView = (TextView) findViewById(R.id.quiz_history_attempt);

        int attempt = 1;
        for(History history : histories){
            Timestamp timestamp = new Timestamp(history.getTimeStamp());
            Date date = new Date(timestamp.getTime());

            attemptTextView.setText("Attempt " + attempt + ": " + history.getMark() + "% (" + date + ")\n\n");
        }
    }

    private Double computeAverage(ArrayList<History> histories) {
        double total = 0;
        for(History history : histories){
            total += history.getMark();
        }
        return total/histories.size();
    }
}
