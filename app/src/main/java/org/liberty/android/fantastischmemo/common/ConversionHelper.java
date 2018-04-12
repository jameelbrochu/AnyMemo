package org.liberty.android.fantastischmemo.common;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class VersionHelper {

    protected void setUnusedFields(SQLiteDatabase database) {
        database.execSQL("update cards"
                + " set cardType = 0");
    }

    protected void updateCategory(SQLiteDatabase database) {
        database.execSQL("update cards "
                + " set category_id = 1"
                + " where category_id is null");

        database.execSQL("update cards set updateDate='2010-01-01 00:00:00.000000'," +
                "creationDate='2010-01-01 00:00:00.000000'");
        database.execSQL("update categories set updateDate='2010-01-01 00:00:00.000000'");
        database.execSQL("update learning_data set updateDate='2010-01-01 00:00:00.000000'");
    }

    protected void copyCategories(SQLiteDatabase database) {
        database.execSQL("insert into categories (name)"
                + " select category as name from dict_tbl where category != ''"
                + " and category is not null"
                + " group by category");
        database.execSQL("update cards set category_id = ("
                + " select id as category_id from categories as cat"
                + " join dict_tbl as dic on dic.category = cat.name"
                + " where cards.id = dic._id)");
    }

    protected void copyLearningData(SQLiteDatabase database) {
        database.execSQL("update cards set learningData_id = ("
                + " select _id as learningData_id"
                + " from learn_tbl where learn_tbl._id = cards.id)");
        database.execSQL("insert into learning_data (acqReps, acqRepsSinceLapse, easiness,"
                + " grade, lapses, lastLearnDate, nextLearnDate, retReps, "
                + " retRepsSinceLapse)"
                + " select acq_reps as acqReps , acq_reps_since_lapse as acqRepsSinceLapse,"
                + " easiness, grade, lapses,"
                + " date_learn || ' 00:00:00.000000' as lastLearnDate,"
                + " datetime(julianday(date_learn) + interval) || '.000000' as nextLearnDate,"
                + " ret_reps as retReps, ret_reps_since_lapse as retRepsSinceLapse"
                + " from learn_tbl");
    }

    protected void ensureCountMatches(SQLiteDatabase database) {
        int count_dict = 0, count_learn = 0;
        Cursor result = database.rawQuery("SELECT _id FROM dict_tbl", null);
        count_dict = result.getCount();
        result.close();
        result = database.rawQuery("SELECT _id FROM learn_tbl", null);
        count_learn = result.getCount();
        result.close();
        if(count_learn != count_dict){
            database.execSQL("DELETE FROM learn_tbl");
            database.execSQL("INSERT INTO learn_tbl(_id) SELECT _id FROM dict_tbl");
            database.execSQL("UPDATE learn_tbl SET date_learn = '2010-01-01', interval = 0, grade = 0, easiness = 2.5, acq_reps = 0, ret_reps  = 0, lapses = 0, acq_reps_since_lapse = 0, ret_reps_since_lapse = 0");
        }
    }

    protected void copyAllCards(SQLiteDatabase database) {
        database.execSQL("insert into cards (ordinal, question, answer, note)" +
                " select _id as ordinal, question, answer, note from dict_tbl");
    }

}
