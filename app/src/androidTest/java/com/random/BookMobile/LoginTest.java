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
public class LoginTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginTest() {

        // test 1
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.usernameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.usernameLogin),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("someString1"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.passwordInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordLogin),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("422"), closeSoftKeyboard());


        ViewInteraction materialButton = onView(
                allOf(withId(R.id.loginButton), withText("Log In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());
// test 2
        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.usernameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.usernameLogin),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("prash456"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.passwordInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordLogin),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("167"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.loginButton), withText("Log In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton2.perform(click());
// test 3
        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.usernameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.usernameLogin),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("prash"), closeSoftKeyboard());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.passwordInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordLogin),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.loginButton), withText("Log In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton3.perform(click());
// test 4
        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.usernameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.usernameLogin),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("praasdfassh   "), closeSoftKeyboard());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.passwordInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordLogin),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText8.perform(replaceText("dfnjasfnaksfna"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.loginButton), withText("Log In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton4.perform(click());
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
