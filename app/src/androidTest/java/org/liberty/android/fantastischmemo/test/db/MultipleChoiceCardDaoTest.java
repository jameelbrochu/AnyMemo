package org.liberty.android.fantastischmemo.test.db;


import android.database.sqlite.SQLiteDatabase;
import android.support.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelperManager;
import org.liberty.android.fantastischmemo.dao.MultipleChoiceCardDao;
import org.liberty.android.fantastischmemo.entity.MultipleChoiceCard;
import org.liberty.android.fantastischmemo.test.AbstractExistingDBTest;
import org.mockito.Mock;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class MultipleChoiceCardDaoTest extends AbstractExistingDBTest {


    private AnyMemoDBOpenHelper newDbHelper;
    private SQLiteDatabase db;
    private MultipleChoiceCardDao multipleChoiceCardDao;

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

    @Before
    public void setUpMultipleChoiceTableAndDao() {
        db = newDbHelper.getWritableDatabase();
        fillMultipleChoiceTable(db);
        multipleChoiceCardDao = newDbHelper.getMultipleChoiceDao();
        multipleChoiceCardDao.setHelper(newDbHelper);
    }

    //test data for multiple choice table
    private void fillMultipleChoiceTable(SQLiteDatabase db) {
        MultipleChoiceCard mc1 = new MultipleChoiceCard("What color is the sky?", "green", "blue", "pink", "orange", "blue");
        newDbHelper.insertMultipleChoiceCard(mc1, db);
        MultipleChoiceCard mc2 = new MultipleChoiceCard("What color is grass?", "green", "blue", "pink", "orange", "green");
        newDbHelper.insertMultipleChoiceCard(mc2, db);
        MultipleChoiceCard mc3 = new MultipleChoiceCard("What color is snow?", "green", "blue", "white", "orange", "white");
        newDbHelper.insertMultipleChoiceCard(mc3, db);
    }

    @SmallTest
    @Test
    public void testGetAllMultipleChoiceCards() throws Exception {
        List<MultipleChoiceCard> mcCards =  multipleChoiceCardDao.getAllMultipleChoiceCards();
        assertNotNull(mcCards);
        assertEquals(mcCards.size(), 3);
    }

    @SmallTest
    @Test
    public void testGetMultipleChoiceCardById() throws Exception {
        MultipleChoiceCard mc = multipleChoiceCardDao.getMultipleChoiceCard(1);
        assertEquals(mc.getAnswer(), "blue");
        assertEquals(mc.getQuestion(), "What color is the sky?");
        assertEquals(mc.getOption1(), "green");
        assertEquals(mc.getOption2(), "blue");
        assertEquals(mc.getOption3(), "pink");
        assertEquals(mc.getOption4(), "orange");
    }

    @SmallTest
    @Test
    public void testAddANewMultipleChoiceCard() throws Exception {
        MultipleChoiceCard mc = new MultipleChoiceCard("What color is a banana?", "blue", "purple", "yellow", "orange", "yellow");
        MultipleChoiceCard newCard = multipleChoiceCardDao.addMultipleChoiceCard(mc);
        assertNotNull(newCard);
        assertNotNull(multipleChoiceCardDao.getMultipleChoiceCard(4).getAnswer());
        assertEquals(multipleChoiceCardDao.getAllMultipleChoiceCards().size(), 4);
        assertEquals(multipleChoiceCardDao.getMultipleChoiceCard(4).getAnswer(), newCard.getAnswer());
        assertEquals(multipleChoiceCardDao.getMultipleChoiceCard(4).getQuestion(), newCard.getQuestion());
        assertEquals(multipleChoiceCardDao.getMultipleChoiceCard(4).getOption1(), newCard.getOption1());
        assertEquals(multipleChoiceCardDao.getMultipleChoiceCard(4).getOption2(), newCard.getOption2());
        assertEquals(multipleChoiceCardDao.getMultipleChoiceCard(4).getOption3(), newCard.getOption3());
        assertEquals(multipleChoiceCardDao.getMultipleChoiceCard(4).getOption4(), newCard.getOption4());
    }

    @SmallTest
    @Test
    public void testDeleteLastMultipleChoiceCardRow() {
        MultipleChoiceCard cardToDelete = multipleChoiceCardDao.getMultipleChoiceCard(3);
        assertNotNull(cardToDelete);
        assertEquals(multipleChoiceCardDao.getAllMultipleChoiceCards().size(), 3);
        multipleChoiceCardDao.deleteMultipleChoiceCard(cardToDelete);
        List<MultipleChoiceCard> updatedList = multipleChoiceCardDao.getAllMultipleChoiceCards();
        assertEquals(updatedList.size(), 2);
        assertNull(multipleChoiceCardDao.getMultipleChoiceCard(3));
    }

    @SmallTest
    @Test
    public void testDeleteMultipleChoiceCard() {
        MultipleChoiceCard cardToDelete = multipleChoiceCardDao.getMultipleChoiceCard(2);
        assertNotNull(cardToDelete);
        assertEquals(multipleChoiceCardDao.getAllMultipleChoiceCards().size(), 3);
        assertEquals(cardToDelete.getAnswer(), "green");
        assertEquals(cardToDelete.getQuestion(), "What color is grass?");
        assertEquals(cardToDelete.getOption1(), "green");
        assertEquals(cardToDelete.getOption2(), "blue");
        assertEquals(cardToDelete.getOption3(), "pink");
        assertEquals(cardToDelete.getOption4(), "orange");

        multipleChoiceCardDao.deleteMultipleChoiceCard(cardToDelete);
        List<MultipleChoiceCard> updatedList = multipleChoiceCardDao.getAllMultipleChoiceCards();
        assertEquals(updatedList.size(), 2);
        assertNotNull(multipleChoiceCardDao.getMultipleChoiceCard(2));
        assertEquals(multipleChoiceCardDao.getMultipleChoiceCard(2).getAnswer(), "white");
        assertEquals(multipleChoiceCardDao.getMultipleChoiceCard(2).getQuestion(), "What color is snow?");
        assertEquals(multipleChoiceCardDao.getMultipleChoiceCard(2).getOption1(), "green");
        assertEquals(multipleChoiceCardDao.getMultipleChoiceCard(2).getOption2(), "blue");
        assertEquals(multipleChoiceCardDao.getMultipleChoiceCard(2).getOption3(), "white");
        assertEquals(multipleChoiceCardDao.getMultipleChoiceCard(2).getOption4(), "orange");
    }

    @SmallTest
    @Test
    public void testNextMultipleChoiceCard() {
        MultipleChoiceCard currentCard = multipleChoiceCardDao.getMultipleChoiceCard(2);
        MultipleChoiceCard nextCard =  multipleChoiceCardDao.getNextMultipleChoiceCard(currentCard);
        assertEquals(nextCard.getId(),3);
    }

    @SmallTest
    @Test
    public void testPrevMultipleChoiceCard() {
        MultipleChoiceCard currentCard = multipleChoiceCardDao.getMultipleChoiceCard(2);
        MultipleChoiceCard prevCard =  multipleChoiceCardDao.getPrevMultipleChoiceCard(currentCard);
        assertEquals(prevCard.getId(),1);
    }

    @SmallTest
    @Test
    public void testUpdateExistingMultipleChoiceCard() {
        MultipleChoiceCard currentCard = multipleChoiceCardDao.getMultipleChoiceCard(2);
        currentCard.setOption4("beige");
        multipleChoiceCardDao.updateMultipleChoiceCard(currentCard);

        MultipleChoiceCard updatedCard = multipleChoiceCardDao.getMultipleChoiceCard(2);
        assertEquals(updatedCard.getId(), 2);
        assertEquals(updatedCard.getOption4(), "beige");
    }

}
