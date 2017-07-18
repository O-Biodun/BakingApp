package com.example.biodun.bakingapp;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BakingAppTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;
    @Before
    public void registerIdlingResource(){
        MainActivity activity=mActivityTestRule.getActivity();
        mIdlingResource=new BakingAppIdlingResource(activity);
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void bakingAppTest() {
        ViewInteraction textView = onView(
                allOf(withText("Baking App"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Baking App")));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.masterPageRv),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                0),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.masterPageRv),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                0),
                        isDisplayed()));
        recyclerView2.check(matches(isDisplayed()));

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.masterPageRv), isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.ingredientRv),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.detailPageViewPager),
                                        0),
                                0),
                        isDisplayed()));
        recyclerView4.check(matches(isDisplayed()));

        ViewInteraction appCompatTextView = onView(
                allOf(withText("Steps"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction recyclerView5 = onView(
                allOf(withId(R.id.stepsRv),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.detailPageViewPager),
                                        1),
                                0),
                        isDisplayed()));
        recyclerView5.check(matches(isDisplayed()));

    }
    @After
    public void unRegisterIdlingResource(){


        Espresso.unregisterIdlingResources(mIdlingResource);
    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
