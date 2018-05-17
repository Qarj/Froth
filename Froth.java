package qarj.listyshop;

import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.espresso.NoMatchingViewException;
import android.view.View;

import junit.framework.AssertionFailedError;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.security.SecureRandom;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsNot.not;

public class Froth {

    // Project Home: https://github.com/Qarj/Froth

    public static void checkDisplayed(String expected) {
        boolean found = _isTextDisplayedInMainContent(expected);
        if (!found) {
            found = _isTextDisplayedInAHint(expected);
        }
        if (!found) {
            assertTrue("checkDisplayed [" + expected + "] was not found", false);
        }
    }

    private static boolean _isTextDisplayedInMainContent(String expected) {
        try {
            onView(withText(containsString(expected))).check(matches(isDisplayed()));
            return true;
        } catch  (AmbiguousViewMatcherException e ) {
            // really don't care how many times it is found - it is still a pass
            return true;
        } catch (NoMatchingViewException | AssertionFailedError e) {
            // oh dear!
        }
        return false;
    }

    private static boolean _isTextDisplayedInAHint(String expected) {
        try {
            onView(withHint(containsString(expected))).check(matches(isDisplayed()));
            return true;
        } catch  (AmbiguousViewMatcherException e ) {
            // really don't care how many times it is found - it is still a pass
            return true;
        } catch (NoMatchingViewException | AssertionFailedError e) {
            // oh dear!
        }
        return false;
    }

    public static boolean isVisible(String target) {
        boolean found = _isTextDisplayedInMainContent(target);
        if (!found) {
            found = _isTextDisplayedInAHint(target);
        }
        return found;
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

    public static void checkNotDisplayed(String notExpected) {
        try {
            onView(withText(containsString(notExpected))).check(matches(not(isDisplayed())));
        } catch (NoMatchingViewException e) {
            // this is a pass too
        } catch (AmbiguousViewMatcherException e) {
            assertTrue("checkNotDisplayed - string found more than once!",false);
        }
    }

    public static void keys(String anchor, String text) {
        // Currently just supports hint
        onView(allOf(withHint(anchor), isDisplayed()))
                .perform(typeText(text), closeSoftKeyboard());

        //https://stackoverflow.com/questions/29378552/in-espresso-how-to-choose-one-the-view-who-have-same-id-to-avoid-ambiguousviewm
        //Needs more work to figure this out:
        //        onView(withIndex(allOf(withHint(anchor), isDisplayed()), 0))
        //                .perform(typeText(text), closeSoftKeyboard());
    }

    public static String random() {
       return randomAlpha(5);
    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    static String randomAlpha(int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    //https://stackoverflow.com/questions/29378552/in-espresso-how-to-choose-one-the-view-who-have-same-id-to-avoid-ambiguousviewm
    //FrostRocket
    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                description.appendText(" ");
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }
}
