package org.liberty.android.fantastischmemo.contract;

import android.provider.BaseColumns;

/*org.liberty.android.fantastischmemo.contract is used as a container for different constants that are used in the multiple
choice card table */
public final class MultipleChoiceContract {

    private MultipleChoiceContract() {}

    public static class MultipleChoiceCardTable {
        public static final String TABLE_NAME = "multipleChoiceCards";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER = "answer";
    }
}
