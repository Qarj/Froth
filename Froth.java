import android.support.test.espresso.AmbiguousViewMatcherException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

public class Froth {

    // Project Home: https://github.com/Qarj/Froth

    public static void checkDisplayed(String expected) {
        try {
            onView(withText(containsString(expected))).check(matches(isDisplayed()));
        } catch  (AmbiguousViewMatcherException e ) {
            // really don't care how many times it is found - it is still a pass
        }
    }

    public static void click(String viewString) {
        onView(allOf(withText(viewString), isDisplayed())).perform(android.support.test.espresso.action.ViewActions.click());
    }

    public static void click(int viewId) {
        onView(withId(viewId)).perform(android.support.test.espresso.action.ViewActions.click());
    }

    public static void checkToast(String expected) {
        onView(withText(containsString(expected))).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    public static void openOptionsMenu() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
    }

}