//package org.liberty.android.fantastischmemo.test.ui;
//
//import android.support.test.espresso.ViewInteraction;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//import android.test.suitebuilder.annotation.LargeTest;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.hamcrest.TypeSafeMatcher;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.liberty.android.fantastischmemo.R;
//import org.liberty.android.fantastischmemo.ui.AnyMemo;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.scrollTo;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
//import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.is;
//
//@LargeTest
//@RunWith(AndroidJUnit4.class)
//public class HintToggleCheckboxTest {
//
//    @Rule
//    public ActivityTestRule<AnyMemo> mActivityTestRule = new ActivityTestRule<>(AnyMemo.class);
//
//    @Test
//    public void hintToggleCheckboxTest() {
//        ViewInteraction appCompatButton = onView(
//                allOf(withId(android.R.id.button1), withText("OK"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                3)));
//        appCompatButton.perform(scrollTo(), click());
//
//        ViewInteraction appCompatButton2 = onView(
//                allOf(withId(R.id.recent_item_more_button), withText("MORE"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.FrameLayout")),
//                                        0),
//                                3),
//                        isDisplayed()));
//        appCompatButton2.perform(click());
//
//        ViewInteraction linearLayout = onView(
//                allOf(withId(R.id.settings),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                5)));
//        linearLayout.perform(scrollTo(), click());
//
//        ViewInteraction appCompatCheckBox = onView(
//                allOf(withId(R.id.hint_modeToggle), withText("Hint Mode Toggle"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                22)));
//        appCompatCheckBox.perform(scrollTo(), click());
//
//        ViewInteraction actionMenuItemView = onView(
//                allOf(withId(R.id.save), withContentDescription("Save"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.action_bar),
//                                        1),
//                                0),
//                        isDisplayed()));
//        actionMenuItemView.perform(click());
//
//        ViewInteraction appCompatButton3 = onView(
//                allOf(withId(R.id.recent_item_more_button), withText("MORE"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.FrameLayout")),
//                                        0),
//                                3),
//                        isDisplayed()));
//        appCompatButton3.perform(click());
//
//        ViewInteraction linearLayout2 = onView(
//                allOf(withId(R.id.settings),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                5)));
//        linearLayout2.perform(scrollTo(), click());
//
//    }
//
//    private static Matcher<View> childAtPosition(
//            final Matcher<View> parentMatcher, final int position) {
//
//        return new TypeSafeMatcher<View>() {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("Child at position " + position + " in parent ");
//                parentMatcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//                ViewParent parent = view.getParent();
//                return parent instanceof ViewGroup && parentMatcher.matches(parent)
//                        && view.equals(((ViewGroup) parent).getChildAt(position));
//            }
//        };
//    }
//}
