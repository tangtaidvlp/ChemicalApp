package phamf.com.chemicalapp;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

public class LearningLessonTest {
    @Rule
    public ActivityTestRule<LessonMenuActivity> rule = new ActivityTestRule<>(LessonMenuActivity.class);

    @Test
    public void testLearn_One_Lesson () {
        rule.launchActivity(new Intent(rule.getActivity(), LessonMenuActivity.class));
        ViewInteraction btn_back = onView(withId(R.id.btn_back_lesson));
        ViewInteraction txt_lesson_title = onView(withId(R.id.txt_lesson_title));
        ViewInteraction rcv_chapter = onView(ViewMatchers.withId(R.id.rcv_chapter_menu));
        ViewInteraction rcv_lesson = onView(ViewMatchers.withId(R.id.rcv_lesson_menu));

        rcv_chapter.check(matches(isDisplayed()));
        rcv_chapter.perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        rcv_lesson.check(matches(isDisplayed()));
        rcv_lesson.perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        txt_lesson_title.check(matches(not(withText(""))));

        ViewInteraction viewPager = onView(withId(R.id.vpg_lesson));
        viewPager.perform(swipeLeft());
        viewPager.perform(swipeLeft());
        viewPager.perform(swipeRight());
        viewPager.perform(swipeRight());
        viewPager.perform(swipeLeft());
        viewPager.perform(swipeLeft());

        btn_back.perform(click());
    }

    @Test
    public void testLearn_2_Lesson () {
        rule.launchActivity(new Intent(rule.getActivity(), LessonMenuActivity.class));
        ViewInteraction btn_back = onView(withId(R.id.btn_back_lesson));
        ViewInteraction txt_lesson_title = onView(withId(R.id.txt_lesson_title));
        ViewInteraction btn_turn_on_chapter_menu = onView(withId(R.id.btn_turn_on_chapter_menu));
        ViewInteraction rcv_chapter = onView(ViewMatchers.withId(R.id.rcv_chapter_menu));
        ViewInteraction rcv_lesson = onView(ViewMatchers.withId(R.id.rcv_lesson_menu));

        rcv_chapter.check(matches(isDisplayed()));
        rcv_chapter.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        rcv_lesson.check(matches(isDisplayed()));
        rcv_lesson.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        txt_lesson_title.check(matches(not(withText(""))));
        btn_back.perform(click());

        rcv_chapter.check(matches(not(isDisplayed())));
        rcv_lesson.check(matches(isDisplayed()));

        btn_turn_on_chapter_menu.perform(click());

        rcv_chapter.check(matches(isDisplayed()));
        rcv_lesson.check(matches(not(isDisplayed())));

        rcv_chapter.perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        rcv_chapter.check(matches(not(isDisplayed())));
        rcv_lesson.check(matches(isDisplayed()));

        rcv_lesson.perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        txt_lesson_title.check(matches(not(withText(""))));

        ViewInteraction viewPager = onView(withId(R.id.vpg_lesson));
        viewPager.perform(swipeRight());
        viewPager.perform(swipeRight());

    }



}
