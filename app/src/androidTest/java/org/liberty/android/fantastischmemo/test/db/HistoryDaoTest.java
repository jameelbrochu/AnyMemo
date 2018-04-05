package org.liberty.android.fantastischmemo.test.db;

/**
 * Created by SnegaT on 2018-04-05.
 */
import android.database.sqlite.SQLiteDatabase;
import android.support.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelperManager;
import org.liberty.android.fantastischmemo.dao.HistoryDao;
import org.liberty.android.fantastischmemo.entity.History;
import org.liberty.android.fantastischmemo.test.AbstractExistingDBTest;
import org.mockito.Mock;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class HistoryDaoTest extends AbstractExistingDBTest{

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
        History history1 = new History("/test", 100, 1233);
        newDbHelper.insertHistory(history1, db);
        History history2 = new History("/test", 99, 1234);
        newDbHelper.insertHistory(history2, db);
        History history3 = new History("/test", 88, 1235);
        newDbHelper.insertHistory(history3, db);
    }

    @SmallTest
    @Test
    public void testCountForDB(){
        int count = newDbHelper.getCountHistoryforDB("/test");
        assertEquals(3, count);
    }

    @SmallTest
    @Test
    public void testReadForDB(){
        List<History> hist = newDbHelper.getHistoryForDB("/test");
        History hist1 = hist.get(0);
        assertEquals("/test", hist1.getdbPath());
        assertEquals(100,hist1.getMark(), .99);
        assertEquals(1233, hist1.getTimeStamp(), .99);
    }

    @SmallTest
    @Test
    public void testDeleteForDB(){
        List<History> hist = newDbHelper.getHistoryForDB("/test");
        History hist1 = hist.get(0);
        newDbHelper.deleteHistory(hist1);
        hist = newDbHelper.getHistoryForDB("/test");
        assertEquals(2, hist.size());
    }

    @SmallTest
    @Test
    public void testCreateForDB(){
        History history = new History("/test2", 99, 1236);
        newDbHelper.insertHistory(history, db);
        List<History> hist = newDbHelper.getHistoryForDB("/test2");
        History hist1 = hist.get(0);
        assertEquals(1,hist.size());
        assertEquals("/test2", hist1.getdbPath());
        assertEquals(99,hist1.getMark(), .99);
        assertEquals(1236, hist1.getTimeStamp(), .99);

    }
}
