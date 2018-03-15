/*package org.liberty.android.fantastischmemo.test.ui;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelper;
import org.liberty.android.fantastischmemo.common.AnyMemoDBOpenHelperManager;
import org.liberty.android.fantastischmemo.entity.Card;
import org.liberty.android.fantastischmemo.integrationtest.TestHelper;
import org.liberty.android.fantastischmemo.test.AbstractExistingDBTest;
import org.liberty.android.fantastischmemo.ui.StudyActivity;

import java.io.File;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class FavouritesDatabaseTest extends AbstractExistingDBTest {

    private static final String SHUFFLE_CARDS = "shufflecards";
    private Intent intent;
    private String favouritesDBPath = "/sdcard/favourites.db";

    @Rule
    public ActivityTestRule<StudyActivity> studyActivityRule = new ActivityTestRule<StudyActivity>(StudyActivity.class, true, false);

    @Before
    public void setup(){
        intent = new Intent();
        intent.putExtra(StudyActivity.EXTRA_DBPATH, TestHelper.SAMPLE_DB_PATH);
        intent.putExtra(SHUFFLE_CARDS, "false");
        studyActivityRule.launchActivity(intent);
    }

    @After
    public void teardown(){
        if(studyActivityRule.getActivity() != null) {
            studyActivityRule.getActivity().emptyFavourtiesDeck();
            AnyMemoDBOpenHelper favourtiesDbHelper = studyActivityRule.getActivity().getFavouritesDbHelper();

            if (favourtiesDbHelper != null) {
                AnyMemoDBOpenHelperManager.releaseHelper(favourtiesDbHelper);
            }
        }
        File newdbFile = new File(favouritesDBPath);
        newdbFile.delete();
    }

    @Test
    public void favouriteCard(){

        //Create a studyActivity instance
        StudyActivity studyActivity = studyActivityRule.getActivity();

        //Make sure the favourites database is empty
        List<Card> favourites = studyActivity.getAllFavourites();
        Assert.assertTrue(favourites.isEmpty());

        //Get a card to add to the favourites database
        Card cardToAdd = helper.getCardDao().getById(1);

        //Insert a card into the favourites database
        studyActivity.favouriteCard(cardToAdd);

        //Ensure the card was added to the favourites database
        favourites = studyActivity.getAllFavourites();

        Assert.assertEquals(1, favourites.size());

    }
}*/
