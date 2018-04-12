package org.liberty.android.fantastischmemo.ui.helper;

import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.dao.HistoryDao;
import org.liberty.android.fantastischmemo.entity.History;

import java.util.Date;
import java.util.List;

/**
 * Created by SnegaT on 2018-04-12.
 */

public class HistoryHelper {

    public HistoryHelper(){}

    public static History addToHistory(int score, int count, List<History> historyForDeck, String dbPath, HistoryDao historyDao) {
        if (count == 10) {
            History oldest = historyForDeck.get(0);
            for (History history : historyForDeck) {
                if (history.getTimeStamp() < oldest.getTimeStamp()) {
                    oldest = history;
                }
            }
            historyDao.deleteHistory(oldest);
        }

        Date date = new Date();
        Long timeStamp = date.getTime();
        History result = new History();
        result.setdbPath(dbPath);
        result.setMark(score);
        result.setTimeStamp(timeStamp);
        historyDao.insertHistory(result);

        return result;

    }
}
