package phamf.com.chemicalapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

public class BangTuanHoangActivityTest {

    @Rule
    public ActivityTestRule<BangTuanHoangActivity> rule = new ActivityTestRule<>(BangTuanHoangActivity.class);

    @Test
    public void testSee_A_Chemical_Element_In_Periodic_table () {
        onData(anything()).inAdapterView(withId(R.id.grv_bang_tuan_hoan)).atPosition(0).perform(click());
        onView(withId(R.id.txt_chem_element_name)).check(matches(not(withText(""))));
        onView(withId(R.id.txt_chem_element_symbol)).check(matches(not(withText(""))));
        onView(withId(R.id.txt_chem_element_e_p_partical_count)).check(matches(not(withText(""))));
        onView(withId(R.id.txt_chem_element_notron_count)).check(matches(not(withText(""))));
        onView(withId(R.id.txt_chem_element_element_type)).check(matches(not(withText(""))));}

    @Test
    public void test_Search_2_Chemical_Elements_in_BangTuanHoan_by_clicking_bottom_button () {

        onView(withId(R.id.btn_bottom_bangtuanhoan_search)).perform(click());
        onView(withId(R.id.edt_bangtuanhoan_search)).perform(typeText("N"));
        onView(ViewMatchers.withId(R.id.rcv_bangtuanhoan_chem_element_search))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        pressBack();

        onView(withId(R.id.btn_bottom_bangtuanhoan_search)).perform(click());
        onView(withId(R.id.edt_bangtuanhoan_search)).perform(clearText());
        onView(withId(R.id.edt_bangtuanhoan_search)).perform(typeText("B"));
        onView(ViewMatchers.withId(R.id.rcv_bangtuanhoan_chem_element_search))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        pressBack();

    }

    @Test
    public void testSearch_A_Chem_Element_By_clicking_directly_on_edt_search () {
        onData(anything()).inAdapterView(withId(R.id.grv_bang_tuan_hoan)).atPosition(0).perform(click());
        onView(withId(R.id.edt_chem_element_search)).perform(click());
        onView(withId(R.id.edt_chem_element_search)).perform(typeText("N"));
        onView(ViewMatchers.withId(R.id.rcv_chem_element_search))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.txt_chem_element_name)).check(matches(not(withText(""))));
        onView(withId(R.id.txt_chem_element_symbol)).check(matches(not(withText(""))));
        onView(withId(R.id.txt_chem_element_e_p_partical_count)).check(matches(not(withText(""))));
        onView(withId(R.id.txt_chem_element_notron_count)).check(matches(not(withText(""))));
        onView(withId(R.id.txt_chem_element_element_type)).check(matches(not(withText(""))));
    }

    @Test
    public void testSearchChem_Inside_Chemical_Element_Activity () {
        onData(anything()).inAdapterView(withId(R.id.grv_bang_tuan_hoan)).atPosition(0).perform(click());
        onView(withId(R.id.rcv_chem_element_search)).check(matches(not(isDisplayed())));
        onView(withId(R.id.btn_bottom_chemical_element_turn_On_search)).perform(click());
        onView(withId(R.id.rcv_chem_element_search)).check(matches(isDisplayed()));
        onView(withId(R.id.edt_chem_element_search)).perform(typeText("N"));
        onView(ViewMatchers.withId(R.id.rcv_chem_element_search))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rcv_chem_element_search)).check(matches(not(isDisplayed())));
        onView(withId(R.id.btn_bottom_chemical_element_turn_On_search)).perform(click());
        onView(withId(R.id.rcv_chem_element_search)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_top_chemical_element_turn_Off_search)).perform(click());
        onView(withId(R.id.rcv_chem_element_search)).check(matches(not(isDisplayed())));
    }

    @Test
    @Before
    public void test_turn_on_and_off_search_view_in_bang_tuan_hoan_activity() {
        onView(withId(R.id.rcv_bangtuanhoan_chem_element_search)).check(matches(not(isDisplayed())));
        onView(withId(R.id.btn_top_bangtuanhoan_turn_off_search)).check(matches(not(isDisplayed())));

        onView(withId(R.id.btn_bottom_bangtuanhoan_search)).perform(click());

        onView(withId(R.id.rcv_bangtuanhoan_chem_element_search)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_top_bangtuanhoan_turn_off_search)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.rcv_bangtuanhoan_chem_element_search))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        pressBack();

        onView(withId(R.id.rcv_bangtuanhoan_chem_element_search)).check(matches(not(isDisplayed())));
        onView(withId(R.id.btn_top_bangtuanhoan_turn_off_search)).check(matches(not(isDisplayed())));
    }
}