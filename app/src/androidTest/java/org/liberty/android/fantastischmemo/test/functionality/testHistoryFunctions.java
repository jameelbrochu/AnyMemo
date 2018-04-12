package org.liberty.android.fantastischmemo.test.functionality;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.filters.SmallTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelperManager;
import org.liberty.android.fantastischmemo.dao.CardDao;
import org.liberty.android.fantastischmemo.dao.CategoryDao;
import org.liberty.android.fantastischmemo.dao.HistoryDao;
import org.liberty.android.fantastischmemo.dao.LearningDataDao;
import org.liberty.android.fantastischmemo.entity.Card;
import org.liberty.android.fantastischmemo.entity.Category;
import org.liberty.android.fantastischmemo.entity.History;
import org.liberty.android.fantastischmemo.entity.LearningData;
import org.liberty.android.fantastischmemo.entity.ReviewOrdering;
import org.liberty.android.fantastischmemo.test.AbstractExistingDBTest;
import org.liberty.android.fantastischmemo.ui.helper.HistoryHelper;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class testHistoryFunctions extends AbstractExistingDBTest {

    private AnyMemoDBOpenHelper newDbHelper;
    private SQLiteDatabase db;
    private HistoryDao historyDao;

    public static final String dbPath = "/sdcard/historytestdb.db";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        File newdbFile = new File(dbPath);
        newdbFile.delete();
        newDbHelper = AnyMemoDBOpenHelperManager.getHelper(getContext(), dbPath);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        AnyMemoDBOpenHelperManager.releaseHelper(newDbHelper);
        File newdbFile = new File(dbPath);
        newdbFile.delete();
    }

    @Before
    public void setUpHistoryTableAndDao() {
        db = newDbHelper.getWritableDatabase();
        fillHistoryTable(db);
        historyDao = newDbHelper.getHistoryDao();
        historyDao.setHelper(newDbHelper);
    }

    private void fillHistoryTable(SQLiteDatabase db) {
        Date date = new Date();
        Long timeStamp = date.getTime();
        History history1 = new History("/test", 100, timeStamp-10);
        newDbHelper.insertHistory(history1, db);
        History history2 = new History("/test", 99, timeStamp-9);
        newDbHelper.insertHistory(history2, db);
        History history3 = new History("/test", 88, timeStamp-8);
        newDbHelper.insertHistory(history3, db);
        History history4 = new History("/test", 77, timeStamp-7);
        newDbHelper.insertHistory(history4, db);
        History history5 = new History("/test", 66, timeStamp-6);
        newDbHelper.insertHistory(history5, db);
        History history6 = new History("/test", 55, timeStamp-5);
        newDbHelper.insertHistory(history6, db);
        History history7 = new History("/test", 44, timeStamp-4);
        newDbHelper.insertHistory(history7, db);
        History history8 = new History("/test", 33, timeStamp-3);
        newDbHelper.insertHistory(history8, db);
        History history9 = new History("/test", 22, timeStamp-2);
        newDbHelper.insertHistory(history9, db);
        History history10 = new History("/test", 11, timeStamp-1);
        newDbHelper.insertHistory(history10, db);
    }


    @SmallTest
    @Test
    public void testInsertFunction() {
        int score = 50;
        List<History> hist = newDbHelper.getHistoryForDB("/test");
        int count = hist.size();
        History result = HistoryHelper.addToHistory(score, count, hist, "/test", historyDao);
        hist = newDbHelper.getHistoryForDB("/test");
        assertEquals(10, hist.size());
        assertEquals(50.0, result.getMark());

    }

    @SmallTest
    @Test
    public void testAverage(){
        List<History> hist = newDbHelper.getHistoryForDB("/test");
        ArrayList<History> histories = new ArrayList<>();
        for (History history: hist){
            histories.add(history);
        }
        double average = HistoryHelper.computeAverage(histories);
        assertEquals(59.5, average);
    }

    @SmallTest
    @Test
    public void testDate(){
        History history1 = new History("/test", 100, 15346);
        String expected = "12/31/69 8:00 PM";
        String est = HistoryHelper.estDate(history1.getTimeStamp());
        assertEquals(expected, est);



    }
}

