package phamf.com.chemicalapp;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;


public class LearningRecentLessonTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testLearn_One_Lesson_Then_Learn_It_Again_In_Recent_Lesson() {

        ViewInteraction btn_lesson = onView(withId(R.id.btn_lesson));
        btn_lesson.perform(click());

        ViewInteraction rcv_chapter = onView(ViewMatchers.withId(R.id.rcv_chapter_menu));
        ViewInteraction rcv_lesson = onView(ViewMatchers.withId(R.id.rcv_lesson_menu));

        rcv_chapter.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        rcv_lesson.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        ViewInteraction btn_lesson_back = onView(withId(R.id.btn_back_lesson));
        btn_lesson_back.perform(click());

        ViewInteraction btn_lesson_menu_back = onView(withId(R.id.btn_lesson_menu_back));
        btn_lesson_menu_back.perform(click());

        ViewInteraction btn_recent_lesson= onView(withId(R.id.btn_recent_lesson));
        btn_recent_lesson.perform(click());

        ViewInteraction rcv_recent_lesson = onView(ViewMatchers.withId(R.id.rcv_recent_lesson_menu));
        rcv_recent_lesson.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.txt_lesson_title)).check(matches(not(withText(""))));
        btn_lesson_back.perform(click());
        ViewInteraction btn_recent_lesson_back = onView(withId(R.id.btn_recent_lesson_back));
        btn_recent_lesson_back.perform(click());

    }

}
