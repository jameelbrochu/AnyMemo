package org.liberty.android.fantastischmemo.entity;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.liberty.android.fantastischmemo.dao.MultipleChoiceDaoImpl;

@DatabaseTable(tableName = "multipleChoiceCards", daoClass = MultipleChoiceDaoImpl.class)
public class MultipleChoiceCard  {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(defaultValue = "", width = 8192)
    private String question;

    @DatabaseField(defaultValue = "", width = 8192)
    private String option1;

    @DatabaseField(defaultValue = "", width = 8192)
    private String option2;

    @DatabaseField(defaultValue = "", width = 8192)
    private String option3;

    @DatabaseField(defaultValue = "", width = 8192)
    private String option4;

    @DatabaseField(defaultValue = "", width = 8192)
    private String answer;

    public MultipleChoiceCard() {}

    public MultipleChoiceCard(String question, String option1, String option2, String option3, String option4, String answer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        if (question == null) {
            return "";
        }
        return question;
    }

    public void setQuestion(String question) {
        if (question == null) {
            this.question = "";
        } else{
            this.question = question;
        }
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getAnswer() {
        if (answer == null ) {
            return "";
        }
        return answer;
    }

    public void setAnswer(String answer) {
        if (answer == null) {
            this.answer = "";
        } else {
            this.answer = answer;
        }
    }

}
