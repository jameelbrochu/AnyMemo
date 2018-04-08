package org.liberty.android.fantastischmemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.entity.History;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

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
            String estDate = estDate(history.getTimeStamp());
            attemptTextView.append("Attempt " + attempt + ": " + history.getMark() + "% (" + estDate + ")\n\n");
            attempt++;
        }
    }

    private Double computeAverage(ArrayList<History> histories) {
        double total = 0;
        for(History history : histories){
            total += history.getMark();
        }
        return Math.floor((total/histories.size())*100)/100;
    }

    private String estDate(Long timeStamp){
        //Daylight savings offset
        long offset = ((1*1000)*60)*60;
        Date date = new Date(timeStamp + offset);
        DateFormat gmtFormat = new SimpleDateFormat();
        TimeZone estTime = TimeZone.getTimeZone("EST");
        gmtFormat.setTimeZone(estTime);
        return gmtFormat.format(date);
    }
}
