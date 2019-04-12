package com.random.BookMobile;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegistrationTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void usernameFailTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.registerButton), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.usernameRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.usernameRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("pp"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.passwordRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("e3r4gy678d3"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.emailRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.emailRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("prash488@gmail.com"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.registerButton), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        materialButton2.perform(click());
    }

    @Test
    public void emailFailTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.registerButton), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.usernameRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.usernameRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("prashh67"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.passwordRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("e3r4gy678d3"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.emailRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.emailRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("prash488@gmail"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.registerButton), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        materialButton2.perform(click());
    }

    @Test
    public void emailFailTest2() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.registerButton), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.usernameRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.usernameRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("prashh67"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.passwordRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("e3r4gy678d3"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.emailRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.emailRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("prash488"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.registerButton), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        materialButton2.perform(click());
    }

    @Test
    public void passwordFailTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.registerButton), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.usernameRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.usernameRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("prashh67"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.passwordRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("e3sss"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.emailRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.emailRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("prash488@gmail.com"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.registerButton), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        materialButton2.perform(click());
    }

    @Test
    public void allBlankTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.registerButton), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.usernameRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.usernameRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.passwordRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.emailRegInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.emailRegLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.registerButton), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        materialButton2.perform(click());
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
