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
    private RadioGroup rbGroup;
    private RadioButton rbOption1;
    private RadioButton rbOption2;
    private RadioButton rbOption3;
    private RadioButton rbOption4;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultRb;
    private int mcCardCounter;
    private int mcCardCountTotal;
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
        textViewQuestion = (TextView)findViewById(R.id.mc_text_view_question);
        rbGroup = (RadioGroup)findViewById(R.id.mc_radio_group);
        rbOption1 = (RadioButton)findViewById(R.id.mc_radio_button1);
        rbOption2 = (RadioButton)findViewById(R.id.mc_radio_button2);
        rbOption3 = (RadioButton)findViewById(R.id.mc_radio_button3);
        rbOption4 = (RadioButton)findViewById(R.id.mc_radio_button4);
        buttonConfirmNext = (Button)findViewById(R.id.mc_button_confirm_next);

        textColorDefaultRb = rbOption1.getTextColors();

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
                    if(rbOption1.isChecked() || rbOption2.isChecked() || rbOption3.isChecked() || rbOption4.isChecked()) {
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
        rbOption1.setTextColor(textColorDefaultRb);
        rbOption2.setTextColor(textColorDefaultRb);
        rbOption3.setTextColor(textColorDefaultRb);
        rbOption4.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if(mcCardCounter < mcCardCountTotal) {
            currentMCCard = multipleChoiceCardList.get(mcCardCounter);

            textViewQuestion.setText(currentMCCard.getQuestion());
            rbOption1.setText(currentMCCard.getOption1());
            rbOption2.setText(currentMCCard.getOption2());
            rbOption3.setText(currentMCCard.getOption3());
            rbOption4.setText(currentMCCard.getOption4());

            mcCardCounter++;
            textViewId.setText("ID: " + currentMCCard.getId() + " Total: " + mcCardCountTotal);

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
            case 1: answerSelected = currentMCCard.getOption1();
            case 2: answerSelected = currentMCCard.getOption2();
            case 3: answerSelected = currentMCCard.getOption3();
            case 4: answerSelected = currentMCCard.getOption4();
                break;
            default: break;
        }

/*        if(answerSelected.equals(currentMCCard.getAnswer())) {
            score++;
            textViewMCScore.setText("Score: " + score);
        }*/

        showSolution();
    }

    private void showSolution() {
        rbOption1.setTextColor(Color.RED);
        rbOption2.setTextColor(Color.RED);
        rbOption3.setTextColor(Color.RED);
        rbOption4.setTextColor(Color.RED);

        if(currentMCCard.getAnswer().equals(currentMCCard.getOption1())) {
            rbOption1.setTextColor(Color.GREEN);
            textViewQuestion.setText("Answer 1 is correct");
        } else if (currentMCCard.getAnswer().equals(currentMCCard.getOption2())) {
            rbOption2.setTextColor(Color.GREEN);
            textViewQuestion.setText("Answer 2 is correct");
        } else if(currentMCCard.getAnswer().equals(currentMCCard.getOption3())) {
            rbOption3.setTextColor(Color.GREEN);
            textViewQuestion.setText("Answer 3 is correct");
        } else {
            rbOption4.setTextColor(Color.GREEN);
            textViewQuestion.setText("Answer 4 is correct");
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
