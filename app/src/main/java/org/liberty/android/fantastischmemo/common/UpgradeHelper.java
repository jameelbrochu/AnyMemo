package org.liberty.android.fantastischmemo.common;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.liberty.android.fantastischmemo.contract.MultipleChoiceContract;

public class UpgradeHelper {

    protected void dbVersionTwoUpgrade(SQLiteDatabase database) {
        database.execSQL("update cards "
                + " set category_id = 1"
                + " where category_id is null");
    }

    protected void dbVersionThreeUpgrade(SQLiteDatabase database) {
        database.execSQL("update settings set questionTextColor = ? where questionTextColor = ?", new Object[] {null, 0xFFBEBEBE});
        database.execSQL("update settings set answerTextColor = ? where answerTextColor = ?", new Object[] {null, 0xFFBEBEBE} );
        database.execSQL("update settings set questionBackgroundColor = ? where questionBackgroundColor = ?", new Object[] {null, 0xFF000000});
        database.execSQL("update settings set answerBackgroundColor = ? where answerBackgroundColor = ?", new Object[] {null, 0xFF000000});
    }

    protected void dbVersionFourUpgrade(SQLiteDatabase database, String TAG) {
        try {
            database.execSQL("alter table learning_data add column firstLearnDate VARCHAR");
            database.execSQL("update learning_data set firstLearnDate='2010-01-01 00:00:00.000000'");
        } catch (android.database.SQLException e) {
            Log.e(TAG, "Upgrading failed, the column firstLearnData might already exists.", e);
        }
    }

    protected void dbVersionFiveUpgrade(SQLiteDatabase database) {
        database.execSQL("alter table cards add hint String");
    }

    protected void dbVersionSixUpgrade(SQLiteDatabase database) {
        database.execSQL("alter table cards add favourite Boolean");
        database.execSQL("alter table settings add hintToggle Boolean");
    }

    protected void dbVersionSevenUpgrade(SQLiteDatabase database) {
        final String CREATE_MULTIPLE_CHOICE_TABLE = "create table " +
                MultipleChoiceContract.MultipleChoiceCardTable.TABLE_NAME + " (" +
                "_id" + " integer primary key autoincrement, " +
                MultipleChoiceContract.MultipleChoiceCardTable.COLUMN_QUESTION + " string, " +
                MultipleChoiceContract.MultipleChoiceCardTable.COLUMN_OPTION1 + " string, " +
                MultipleChoiceContract.MultipleChoiceCardTable.COLUMN_OPTION2 + " string, " +
                MultipleChoiceContract.MultipleChoiceCardTable.COLUMN_OPTION3 + " string, " +
                MultipleChoiceContract.MultipleChoiceCardTable.COLUMN_OPTION4 + " string, " +
                MultipleChoiceContract.MultipleChoiceCardTable.COLUMN_ANSWER + " string " + ")";
        database.execSQL(CREATE_MULTIPLE_CHOICE_TABLE);
    }

    protected void dbVersionEightUpgrade(SQLiteDatabase database) {
        database.execSQL("alter table settings add hintAudio String");
        database.execSQL("alter table settings add hintAudioLocation String");
    }
}
