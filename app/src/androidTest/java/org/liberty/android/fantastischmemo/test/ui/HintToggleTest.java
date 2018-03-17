package org.liberty.android.fantastischmemo.test.ui;

/**
 * Created by corey on 3/17/2018.
 */

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.liberty.android.fantastischmemo.integrationtest.TestHelper;
import org.liberty.android.fantastischmemo.test.AbstractExistingDBTest;
import org.liberty.android.fantastischmemo.ui.QuizActivity;
import org.liberty.android.fantastischmemo.R;
import org.liberty.android.fantastischmemo.ui.SettingsScreen;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.assertion.ViewAssertions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HintToggleTest {

    @Rule
    public ActivityTestRule<SettingsScreen> settingsScreenActivityTestRule = new ActivityTestRule<>(
            SettingsScreen.class);

    @Test
    public void testHintToggleChecked() {
        //Click on hint toggle checkbox
        onView(withId(R.id.hint_modeToggle)).perform(click());
        //Check that hint was displayed
        onView(withId(R.id.field3)).check(matches(isDisplayed()));

    }

}
