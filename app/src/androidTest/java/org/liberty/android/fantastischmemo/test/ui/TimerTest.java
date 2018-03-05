/*
package org.liberty.android.fantastischmemo.test.ui;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.liberty.android.fantastischmemo.integrationtest.TestHelper;
import org.liberty.android.fantastischmemo.test.AbstractExistingDBTest;
import org.liberty.android.fantastischmemo.ui.QuizActivity;
import org.liberty.android.fantastischmemo.R;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.assertion.ViewAssertions;

import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TimerTest extends AbstractExistingDBTest {

    private Intent intent;

    @Rule
    public ActivityTestRule<QuizActivity> quizActivityRule = new ActivityTestRule<QuizActivity>(QuizActivity.class, true, false);

    @Before
    public void init(){
        intent = new Intent();
        intent.putExtra(QuizActivity.EXTRA_DBPATH, TestHelper.SAMPLE_DB_PATH);
        intent.putExtra(QuizActivity.EXTRA_START_CARD_ORD, 1);
        intent.putExtra(QuizActivity.EXTRA_QUIZ_SIZE, 20);
        intent.putExtra(QuizActivity.EXTRA_SHUFFLE_CARDS, false);
    }

    @Test
    public void TimerTest(){
        // Set timer mode to true
        intent.putExtra(QuizActivity.EXTRA_TIMER_MODE, true);
        intent.putExtra(QuizActivity.EXTRA_COUNTDOWN, 10);
        // Start the activity
        quizActivityRule.launchActivity(intent);

        // Ensure that the countdown is displayed
        Espresso.onView(ViewMatchers.withId(R.id.countdown_text))
                .check(ViewAssertions.matches(not(ViewMatchers.withText(""))));

        // Wait for timer to expire
        int timer = 10000;
        try {
            Thread.sleep(timer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Make sure that the incomplete message is displayed after timer expires
        String expectedString = "Sorry, you did not complete the quiz on time.";
        Espresso.onView(ViewMatchers.withText(R.string.quiz_not_completed))
                .check(ViewAssertions.matches(ViewMatchers.withText(expectedString)));
    }

    @Test
    public void NoTimerTest() {
        // Set timer mode to false
        intent.putExtra(QuizActivity.EXTRA_TIMER_MODE, false);
        // Start the activity
        quizActivityRule.launchActivity(intent);

        // Ensure no countdown is displayed
        Espresso.onView(ViewMatchers.withId(R.id.countdown_text))
                .check(ViewAssertions.matches(ViewMatchers.withText("")));
    }
}
*/
