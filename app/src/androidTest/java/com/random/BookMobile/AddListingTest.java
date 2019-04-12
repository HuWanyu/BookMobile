package com.random.BookMobile;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.espresso.DataInteraction;
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

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddListingTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void addListingTest() {
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
                allOf(withId(R.id.mainNav_AddListing), withContentDescription("Add Listing"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_page),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.listButton), withText("Add Listing"),
                        childAtPosition(
                                withParent(withId(R.id.main_view_pager)),
                                6),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.bookTitleText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bookTitleLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("hadasdvw33"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.conSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatCheckedTextView.perform(click());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.locText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.locationLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("Sim3i"), closeSoftKeyboard());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.priceText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.priceLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("22"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.listButton), withText("Add Listing"),
                        childAtPosition(
                                withParent(withId(R.id.main_view_pager)),
                                6),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.priceText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.priceLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(replaceText("22"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.ampmSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView2.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.datePickerButton), withText("Set Date"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton5.perform(scrollTo(), click());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.locText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.locationLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("Simei"), closeSoftKeyboard());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.bookTitleText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bookTitleLabel),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText8.perform(replaceText("LOL"), closeSoftKeyboard());


        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.listButton), withText("Add Listing"),
                        childAtPosition(
                                withParent(withId(R.id.main_view_pager)),
                                6),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.mainNav_Profile), withContentDescription("Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_page),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.LogOut), withText("Logout"),
                        childAtPosition(
                                withParent(withId(R.id.main_view_pager)),
                                2),
                        isDisplayed()));
        materialButton7.perform(click());


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
