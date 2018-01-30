# Froth
Froth utility library for Android Espresso tests

## About

Espresso for Android seems to be a great way to create "Large" or "End to End" tests for Android apps.

It also seems to be overly precise for End to End tests. For example, it tends to be very tied
to implementation detail - View ids, and what type of view is it (a list, a recycler view...).

The intention of this library is to simplify working with Espresso, and eventually hopefully
provide some heuristics that make writing tests easier for common scenarios.

## Usage

### checkDisplayed(String expected)

Checks that a view can be found that contains `expected` text.

AmbiguousViewMatcherException is handled - if that exception is returned by Espresso
it confirms that at least one match is found - which is a pass!

```
    Froth.checkDisplayed("Android Espresso");
```

## click(String viewString)

Click the view that is an exact match for viewString.

```
    Froth.click("Edit");
```

## click(int id)

Click the view that matches the resource id.

```
    Froth.click(R.id.edit_destinations);
```

## checkToast(String toast)

Check that a toast is displayed that contains specified string.

```
    Froth.checkDisplayed("Congratulations on installing Pointy Arrow");
```

## openOptionsMenu()

Open the overflow or options menu.

```
    Froth.openOptionsMenu();
```

