package org.liberty.android.fantastischmemo.test.db;


import android.database.sqlite.SQLiteDatabase;
import android.support.test.filters.SmallTest;

import org.junit.Test;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelperManager;
import org.liberty.android.fantastischmemo.dao.MCDao;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;
import org.liberty.android.fantastischmemo.test.AbstractExistingDBTest;
import org.mockito.Mock;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class MCCardDaoTest extends AbstractExistingDBTest {


    AnyMemoDBOpenHelper newDbHelper;
    @Mock
    SQLiteDatabase db;
    @Mock
    MCDao mcDao;

    public static final String dbPath = "/sdcard/mctestdb.db";

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

    @SmallTest
    @Test
    public void testGetAllMCCards() throws Exception {
        db = newDbHelper.getWritableDatabase();
        newDbHelper.fillMCTable(db);
        List<MultipleChoiceCard> mcCards =  newDbHelper.getAllMultipleChoiceCards();
        assertNotNull(mcCards);
        assertEquals(mcCards.size(), 3);
    }

    @SmallTest
    @Test
    public void testGetMCCardsById() throws Exception {
        db = newDbHelper.getWritableDatabase();
        newDbHelper.fillMCTable(db);
        mcDao = newDbHelper.getMCDao();
        mcDao.setHelper(newDbHelper);
        /*when(mcDao.getHelper()).thenReturn(newDbHelper);*/
        MultipleChoiceCard mc = mcDao.getMCCard(1);
        assertEquals(mc.getAnswer(), "blue");
    }

    @SmallTest
    @Test
    public void testAddANewMCCard() throws Exception {
        db = newDbHelper.getWritableDatabase();
        newDbHelper.fillMCTable(db);
        mcDao = newDbHelper.getMCDao();
        mcDao.setHelper(newDbHelper);
        MultipleChoiceCard mc = new MultipleChoiceCard("What color is a banana?", "blue", "purple", "yellow", "orange", "yellow");
        MultipleChoiceCard newCard = mcDao.addMCCard(mc);
        assertNotNull(newCard);
        assertNotNull(mcDao.getMCCard(4).getAnswer());
        assertEquals(mcDao.getAllMCCards().size(), 4);
        assertEquals(mcDao.getMCCard(4).getAnswer(), newCard.getAnswer());
    }

    @SmallTest
    @Test
    public void testDeleteLastMCCard() {
        db = newDbHelper.getWritableDatabase();
        newDbHelper.fillMCTable(db);
        mcDao = newDbHelper.getMCDao();
        mcDao.setHelper(newDbHelper);
        MultipleChoiceCard cardToDelete = mcDao.getMCCard(3);
        assertNotNull(cardToDelete);
        assertEquals(mcDao.getAllMCCards().size(), 3);
        List<MultipleChoiceCard> updatedList = mcDao.deleteMCCard(cardToDelete);
        assertEquals(updatedList.size(), 2);
        assertNull(mcDao.getMCCard(3));
    }

    @SmallTest
    @Test
    public void testDeleteMCCard() {
        db = newDbHelper.getWritableDatabase();
        newDbHelper.fillMCTable(db);
        mcDao = newDbHelper.getMCDao();
        mcDao.setHelper(newDbHelper);
        MultipleChoiceCard cardToDelete = mcDao.getMCCard(2);
        assertNotNull(cardToDelete);
        assertEquals(mcDao.getAllMCCards().size(), 3);
        assertEquals(cardToDelete.getAnswer(), "green");
        List<MultipleChoiceCard> updatedList = mcDao.deleteMCCard(cardToDelete);
        assertEquals(updatedList.size(), 2);
        assertNotNull(mcDao.getMCCard(2));
        assertEquals(mcDao.getMCCard(2).getAnswer(), "white");

    }

    //TO-DO
    public void testNextMCCard() {}

    //TO-DO
    public void testPrevMCCard() {}

}
