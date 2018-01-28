import android.support.test.espresso.AmbiguousViewMatcherException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

public class Froth {

    public static void checkDisplayed(String expected) {
        try {
            onView(withText(containsString(expected))).check(matches(isDisplayed()));
        } catch  (AmbiguousViewMatcherException e ) {
            // really don't care how many times it is found
        }
    }

}