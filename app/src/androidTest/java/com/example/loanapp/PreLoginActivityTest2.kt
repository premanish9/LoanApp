package com.example.loanapp


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PreLoginActivityTest2 {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(PreLoginActivity::class.java)

    @Test
    fun preLoginActivityTest2() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(500)

        val materialButton = onView(
            allOf(
                withId(R.id.signInButton), withText("Sign In"),
                childAtPosition(
                    allOf(
                        withId(R.id.lin_form),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(R.id.applyLoanButton), withText("Apply Loan"),
                childAtPosition(
                    allOf(
                        withId(R.id.lin_form),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            3
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.usernameEditText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.usernameLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()

            )
        )

        textInputEditText.perform(click())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.usernameEditText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.usernameLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("1000"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.passwordEditText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.passwordLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("10"), closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(
                withId(android.R.id.button1), withText("Submit"),
                childAtPosition(
                    allOf(
                        withClassName(`is`("com.android.internal.widget.ButtonBarLayout")),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            3
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.img_back),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_content_main),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

       

        val materialButton4 = onView(
            allOf(
                withId(R.id.viewLoansButton), withText("View applied Loan"),
                childAtPosition(
                    allOf(
                        withId(R.id.lin_form),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            3
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val materialButton5 = onView(
            allOf(
                withId(R.id.viewLoansButton), withText("View applied Loan"),
                childAtPosition(
                    allOf(
                        withId(R.id.lin_form),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            3
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())

        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.img_usericon),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_content_main),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(click())

        pressBack()
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
