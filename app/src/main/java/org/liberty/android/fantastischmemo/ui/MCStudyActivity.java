package org.liberty.android.fantastischmemo.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelperManager;
import org.liberty.android.fantastischmemo.dao.MultipleChoiceCardDao;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MCStudyActivity extends AppCompatActivity {

    public static String EXTRA_DBPATH_MC = "dbpath";

    private TextView textViewId;
    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewMessage;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultRb;
    private int mcCardCounter;
    private int mcCardCountTotal;
    private int score;
    private MultipleChoiceCard currentMCCard;
    private String answerSelected;

    private boolean answered;

    List<MultipleChoiceCard> multipleChoiceCardList = new ArrayList<>();
    MultipleChoiceCardDao multipleChoiceCardDao;
    AnyMemoDBOpenHelper dbOpenHelper;
    String dbPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mc_study_activity);

        dbPath = getIntent().getExtras().getString(EXTRA_DBPATH_MC);

        textViewId = (TextView)findViewById(R.id.mc_text_view_id);
        textViewScore = (TextView)findViewById(R.id.mc_text_view_score);
        textViewQuestion = (TextView)findViewById(R.id.mc_text_view_question);
        rbGroup = (RadioGroup)findViewById(R.id.mc_radio_group);
        rb1 = (RadioButton)findViewById(R.id.mc_radio_button1);
        rb2 = (RadioButton)findViewById(R.id.mc_radio_button2);
        rb3 = (RadioButton)findViewById(R.id.mc_radio_button3);
        rb4 = (RadioButton)findViewById(R.id.mc_radio_button4);
        textViewMessage = (TextView)findViewById(R.id.mc_text_view_message);
        buttonConfirmNext = (Button)findViewById(R.id.mc_button_confirm_next);

        textColorDefaultRb = rb1.getTextColors();

        dbOpenHelper = AnyMemoDBOpenHelperManager.getHelper(getApplicationContext(), dbPath);
        multipleChoiceCardDao = dbOpenHelper.getMultipleChoiceDao();
        multipleChoiceCardDao.setHelper(dbOpenHelper);
        multipleChoiceCardList = multipleChoiceCardDao.getAllMultipleChoiceCards();

        mcCardCountTotal = multipleChoiceCardList.size();
        Collections.shuffle(multipleChoiceCardList);

        showNextMultipleChoiceCard();

        buttonConfirmNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!answered) {
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(MCStudyActivity.this, "Please select an answer.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextMultipleChoiceCard();
                }
            }
        });
    }

    private void showNextMultipleChoiceCard() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();
        rb1.setEnabled(true);
        rb2.setEnabled(true);
        rb3.setEnabled(true);
        rb4.setEnabled(true);
        textViewMessage.setText("");

        if(mcCardCounter < mcCardCountTotal) {
            currentMCCard = multipleChoiceCardList.get(mcCardCounter);

            textViewQuestion.setText(currentMCCard.getQuestion());
            rb1.setText(currentMCCard.getOption1());
            rb2.setText(currentMCCard.getOption2());
            rb3.setText(currentMCCard.getOption3());
            rb4.setText(currentMCCard.getOption4());

            mcCardCounter++;
            textViewId.setText("Question: " + mcCardCounter);
            textViewScore.setText("Score: " + score + "/" + mcCardCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");
        } else {
            finishMultipleChoice();
        }
    }

    private void checkAnswer() {
        answered = true;

        RadioButton rbSelected = (RadioButton)findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        switch(answerNr) {
            case 1:
                answerSelected = currentMCCard.getOption1();
                break;
            case 2:
                answerSelected = currentMCCard.getOption2();
                break;
            case 3:
                answerSelected = currentMCCard.getOption3();
                break;
            case 4:
                answerSelected = currentMCCard.getOption4();
                break;
            default: break;
        }

        if(answerSelected.equals(currentMCCard.getAnswer())) {
            score++;
            textViewScore.setText("Score: " + score + "/" + mcCardCountTotal);
        }

        showSolution();
    }

    private void showSolution() {
        rb1.setTextColor(Color.parseColor("#ffff4444"));
        rb2.setTextColor(Color.parseColor("#ffff4444"));
        rb3.setTextColor(Color.parseColor("#ffff4444"));
        rb4.setTextColor(Color.parseColor("#ffff4444"));
        rb1.setEnabled(false);
        rb2.setEnabled(false);
        rb3.setEnabled(false);
        rb4.setEnabled(false);

        if(currentMCCard.getAnswer().equals(currentMCCard.getOption1())) {
            rb1.setTextColor(Color.parseColor("#ff00e676"));
            textViewMessage.setText("Answer 1 is correct");
        } else if (currentMCCard.getAnswer().equals(currentMCCard.getOption2())) {
            rb2.setTextColor(Color.parseColor("#ff00e676"));
            textViewMessage.setText("Answer 2 is correct");
        } else if(currentMCCard.getAnswer().equals(currentMCCard.getOption3())) {
            rb3.setTextColor(Color.parseColor("#ff00e676"));
            textViewMessage.setText("Answer 3 is correct");
        } else {
            rb4.setTextColor(Color.parseColor("#ff00e676"));
            textViewMessage.setText("Answer 4 is correct");
        }

        if(mcCardCounter < mcCardCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }

    }

    private void finishMultipleChoice() {
        finish();
    }
}
