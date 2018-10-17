package phamf.com.chemicalapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.BaseAdapter;
import android.widget.GridView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.regex.Matcher;

import phamf.com.chemicalapp.Manager.AppThemeManager;
import phamf.com.chemicalapp.Model.ChemicalEquation;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class MainActivityTest{

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSearch_Chemical_Equations_With_SearchView() {
        onView(withId(R.id.btn_search)).perform(click());
        ViewInteraction rcv_search_interaction = onView(withId(R.id.rcv_search));
        rcv_search_interaction.check(matches(isDisplayed()));
        onView(withId(R.id.edt_search)).perform(typeText("HA"));
        onView(ViewMatchers.withId(R.id.rcv_search)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.edt_chem_search)).perform(typeText("BA"));
        onView(ViewMatchers.withId(R.id.rcv_chem_search)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.txt_chem_adding_chemicals)).check(matches(withText("Tang Tan ")));
        onView(withId(R.id.btn_chemical_equation_back)).perform(click());
    }

    @Test
    public void testChange_Theme_And_Night_mode () {
        onView(withId(R.id.btn_setting)).perform(click());
        onView(withId(R.id.btn_background_color_1)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_background_color_1)).perform(click());
        assertEquals(AppThemeManager.getBackgroundColor(), AppThemeManager.backgroundColor_list[0]);
        onView(withId(R.id.btn_background_color_2)).perform(click());
        assertEquals(AppThemeManager.getBackgroundColor(), AppThemeManager.backgroundColor_list[1]);
        onView(withId(R.id.btn_background_color_3)).perform(click());
        assertEquals(AppThemeManager.getBackgroundColor(), AppThemeManager.backgroundColor_list[2]);
        onView(withId(R.id.btn_background_color_4)).perform(click());
        assertEquals(AppThemeManager.getBackgroundColor(), AppThemeManager.backgroundColor_list[3]);
        onView(withId(R.id.btn_background_color_5)).perform(click());
        assertEquals(AppThemeManager.getBackgroundColor(), AppThemeManager.backgroundColor_list[4]);
        onView(withId(R.id.btn_background_color_6)).perform(click());
        assertEquals(AppThemeManager.getBackgroundColor(), AppThemeManager.backgroundColor_list[5]);

        onView(withId(R.id.btn_widget_color_1)).perform(click());
        onView(withId(R.id.txt_bangtuanhoang)).check(matches(hasTextColor(AppThemeManager.textColor_list[0])));
        onView(withId(R.id.txt_lesson)).check(matches(hasTextColor(AppThemeManager.textColor_list[0])));
        onView(withId(R.id.txt_recent_lesson)).check(matches(hasTextColor(AppThemeManager.textColor_list[0])));
        onView(withId(R.id.txt_quick_search)).check(matches(hasTextColor(AppThemeManager.textColor_list[0])));
        onView(withId(R.id.txt_dongphan_danhphap)).check(matches(hasTextColor(AppThemeManager.textColor_list[0])));
        onView(withId(R.id.btn_widget_color_2)).perform(click());
        onView(withId(R.id.txt_bangtuanhoang)).check(matches(hasTextColor(AppThemeManager.textColor_list[1])));
        onView(withId(R.id.btn_widget_color_3)).perform(click());
        onView(withId(R.id.txt_bangtuanhoang)).check(matches(hasTextColor(AppThemeManager.textColor_list[2])));
        onView(withId(R.id.btn_widget_color_4)).perform(click());
        onView(withId(R.id.txt_bangtuanhoang)).check(matches(hasTextColor(AppThemeManager.textColor_list[3])));
        onView(withId(R.id.btn_widget_color_5)).perform(click());
        onView(withId(R.id.txt_bangtuanhoang)).check(matches(hasTextColor(AppThemeManager.textColor_list[4])));
        onView(withId(R.id.btn_widget_color_6)).perform(click());
        onView(withId(R.id.txt_bangtuanhoang)).check(matches(hasTextColor(AppThemeManager.textColor_list[5])));

    }

}