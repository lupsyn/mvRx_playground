package com.playground.utils;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;

import android.view.View;

import junit.framework.AssertionFailedError;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.util.concurrent.TimeoutException;

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * From: https://stackoverflow.com/questions/32568080/espresso-custom-viewmatcher-mismatch-description-not-appearing-in-the-log
 */

public class EspressoUtils {
    // this class is copied from Espresso's source code
    // (we need to copy it so that we can replace the `assertThat` function it depends on
    private static class MatchesViewAssertion implements ViewAssertion {
        final Matcher<? super View> viewMatcher;

        private MatchesViewAssertion(final Matcher<? super View> viewMatcher) {
            this.viewMatcher = viewMatcher;
        }

        public void check(View view, NoMatchingViewException noViewException) {
            StringDescription description = new StringDescription();
            description.appendText("'");
            viewMatcher.describeTo(description);
            if (noViewException != null) {
                description.appendText(
                        String.format(
                                "' check could not be performed because view '%s' was not found.\n",
                                noViewException.getViewMatcherDescription()));
                throw noViewException;
            } else {
                description.appendText("' doesn't match the selected view.");
                assertThat(description.toString(), view, viewMatcher);
            }
        }

        /**
         * A replacement for ViewMatchers.assertThat that includes the mismatch description (adapted from the source of ViewMatchers.assertThat
         */
        private static <T> void assertThat(String message, T actual, Matcher<T> matcher) {
            if (!matcher.matches(actual)) {
                final StringDescription mismatch = new StringDescription();
                matcher.describeMismatch(actual, mismatch);

                Description description = new StringDescription();
                description.appendText(message)
                        .appendText("\nExpected: ")
                        .appendDescriptionOf(matcher);

                if (!mismatch.toString().trim().isEmpty()) {
                    description.appendText("\n    But: ").appendText(mismatch.toString());
                }

                description.appendText("\n    Got: ");
                if (actual instanceof View) {
                    description.appendValue(HumanReadables.describe((View) actual));
                } else {
                    description.appendValue(actual);
                }
                description.appendText("\n");
                throw new AssertionFailedError(description.toString());
            }
        }
    }

    public static ViewAssertion matches(final Matcher<View> matcher) {
        return new MatchesViewAssertion(matcher);
    }

    public static ViewAction waitId(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> viewMatcher = withId(viewId);

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }
}