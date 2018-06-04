package com.udacity.garuolis.bakingapp;

import android.content.ComponentName;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasType;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

        @Rule
        public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
        private IdlingResource mIdlingResource;

        @Before
        public void registerIdlingResource() {
            mIdlingResource = activityTestRule.getActivity().getIdlingResource();
            IdlingRegistry.getInstance().register(mIdlingResource);
        }

        @Test
        public void testListVisibility() {
            onView(withId(R.id.list)).check(matches(isDisplayed()));
        }

        @Test
        public void testListAction() {
            Intents.init();
            onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
            intended(hasComponent(RecipeActivity.class.getName()));
            intended(hasExtraWithKey(RecipeActivity.EXTRA_ID));
            Intents.release();
        }


        @After
        public void unregisterIdlingResource() {
            if (mIdlingResource != null) {
                IdlingRegistry.getInstance().unregister(mIdlingResource);
            }
        }
}
