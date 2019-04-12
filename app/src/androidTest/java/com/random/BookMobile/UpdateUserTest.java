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
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UpdateUserTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void updateUserTest() {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.usernameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.usernameLogin),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("prashanth1"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.passwordInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordLogin),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("pr4shs3cur3"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.loginButton), withText("Log In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.mainNav_Profile), withContentDescription("Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_page),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.Personal), withText("Update User Details"),
                        childAtPosition(
                                withParent(withId(R.id.main_view_pager)),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.inputEmail), withText("prash480@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.updateEmail),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("prash480@gmail"));

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.inputEmail), withText("prash480@gmail"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.updateEmail),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.inputPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.updatePassword),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.ConfirmChangePersonalInfo), withText("Save Changes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.ConfirmChangePersonalInfo), withText("Save Changes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.inputEmail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.updateEmail),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(replaceText("pras"), closeSoftKeyboard());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.inputPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.updatePassword),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("ppppppeeee3"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.ConfirmChangePersonalInfo), withText("Save Changes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.inputEmail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.updateEmail),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText8.perform(replaceText("prash4888@gmail.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.inputPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.updatePassword),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText9.perform(replaceText("uu"), closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.ConfirmChangePersonalInfo), withText("Save Changes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton6.perform(click());
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
