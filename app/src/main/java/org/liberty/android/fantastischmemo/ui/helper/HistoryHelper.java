package org.liberty.android.fantastischmemo.ui.helper;

import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.dao.HistoryDao;
import org.liberty.android.fantastischmemo.entity.History;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

    public static Double computeAverage (ArrayList<History> histories){
        double total = 0;
        for (History history : histories) {
            total += history.getMark();
        }
        return Math.floor((total/histories.size())*100)/100;
    }

    public static String estDate(Long timeStamp) {
        //Daylight savings offset
        long offset = ((1*1000)*60)*60;
        Date date = new Date(timeStamp + offset);
        DateFormat gmtFormat = new SimpleDateFormat();
        TimeZone estTime = TimeZone.getTimeZone("EST");
        gmtFormat.setTimeZone(estTime);
        return gmtFormat.format(date);
    }
}
