/*
Copyright (C) 2012 Haowen Ning

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

*/
package org.liberty.android.fantastischmemo.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelperManager;
import org.liberty.android.fantastischmemo.common.BaseActivity;
import org.liberty.android.fantastischmemo.dao.MultipleChoiceCardDao;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;

public class CardMCEditor extends BaseActivity{

    MultipleChoiceCard currentMCCard = new MultipleChoiceCard();

    private EditText mcQuestion;
    private EditText mcOption1;
    private EditText mcOption2;
    private EditText mcOption3;
    private EditText mcOption4;

    private RadioButton option1Radio;
    private RadioButton option2Radio;
    private RadioButton option3Radio;
    private RadioButton option4Radio;

    private Button save;
    public static String EXTRA_DBPATH_MC = "dbpath";
    String dbPath;
    private AnyMemoDBOpenHelper dbOpenHelper;

    private MultipleChoiceCardDao multipleChoiceCardDao;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.card_mc_editor_layout);

        dbPath = getIntent().getExtras().getString(EXTRA_DBPATH_MC);

        mcQuestion = (EditText) findViewById(R.id.edit_dialog_question_mc_entry);

        mcOption1 = (EditText) findViewById(R.id.edit_dialog_option1_entry);

        mcOption2 = (EditText) findViewById(R.id.edit_dialog_option2_entry);

        mcOption3 = (EditText) findViewById(R.id.edit_dialog_option3_entry);

        mcOption4 = (EditText) findViewById(R.id.edit_dialog_option4_entry);

        option1Radio = (RadioButton) findViewById(R.id.op_radio_1);

        option2Radio = (RadioButton) findViewById(R.id.op_radio_2);

        option3Radio = (RadioButton) findViewById(R.id.op_radio_3);

        option4Radio = (RadioButton) findViewById(R.id.op_radio_4);

        save = (Button) findViewById(R.id.save_mc);
        save.setOnClickListener(saveOnClickListener);
        dbOpenHelper = AnyMemoDBOpenHelperManager.getHelper(getApplicationContext(), dbPath);
        multipleChoiceCardDao = dbOpenHelper.getMultipleChoiceDao();
        multipleChoiceCardDao.setHelper(dbOpenHelper);
        updateViews();
    }

    protected AnyMemoDBOpenHelper getDbOpenHelper() {
        return dbOpenHelper;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AnyMemoDBOpenHelperManager.releaseHelper(dbOpenHelper);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void updateViews(){

        String questionMCCard = mcQuestion.getText().toString();
        String option1MCCard = mcOption1.getText().toString();
        String option2MCCard = mcOption2.getText().toString();
        String option3MCCard = mcOption3.getText().toString();
        String option4MCCard = mcOption4.getText().toString();

        currentMCCard.setQuestion(questionMCCard);
        currentMCCard.setOption1(option1MCCard);
        currentMCCard.setOption2(option2MCCard);
        currentMCCard.setOption3(option3MCCard);
        currentMCCard.setOption4(option4MCCard);

        if(option1Radio.isChecked()){
            currentMCCard.setAnswer(option1MCCard);
        }else if(option2Radio.isChecked()){
            currentMCCard.setAnswer(option2MCCard);
        }else if(option3Radio.isChecked()){
            currentMCCard.setAnswer(option3MCCard);
        }else{
            currentMCCard.setAnswer(option4MCCard);
        }
    }

    View.OnClickListener saveOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            updateViews();
            multipleChoiceCardDao.addMultipleChoiceCard(currentMCCard);
            CardMCEditor.super.onBackPressed();
        }
    };
}
