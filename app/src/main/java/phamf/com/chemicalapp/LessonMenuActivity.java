package phamf.com.chemicalapp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import phamf.com.chemicalapp.Abstraction.AbstractClass.RCV_Menu_Adapter;
import phamf.com.chemicalapp.Abstraction.Interface.ILessonMenuActivity;
import phamf.com.chemicalapp.Adapter.Chapter_Menu_Adapter;
import phamf.com.chemicalapp.Adapter.Lesson_Menu_Adapter;
import phamf.com.chemicalapp.Manager.AppThemeManager;
import phamf.com.chemicalapp.Presenter.LessonMenuActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.Manager.FullScreenManager;


/**
 * Presenter
 * @see LessonMenuActivityPresenter
 */
public class LessonMenuActivity extends FullScreenActivity implements ILessonMenuActivity.View, LessonMenuActivityPresenter.DataLoadListener{

    public static final String LESSON_NAME = "lesson_name";


    @BindView(R.id.rcv_chapter_menu) RecyclerView rcv_chapter_menu;
    Chapter_Menu_Adapter chapter_menu_adapter;

    @BindView(R.id.rcv_lesson_menu) RecyclerView rcv_lesson_menu;
    Lesson_Menu_Adapter lesson_menu_adapter;

    @BindView(R.id.btn_lesson_menu_back) Button btn_back;

    @BindView(R.id.btn_turn_on_chapter_menu) Button btn_turn_on_chapter_menu;

    @BindView(R.id.txt_lesson_menu_title) TextView txt_title;

    @BindView(R.id.bg_night_mode_lesson_menu) TextView bg_night_mode;

    @BindView(R.id.parent_lesson_menu_activity) ConstraintLayout base_view;

    @BindView(R.id.circle_pb_lesson_menu)
    ProgressBar circle_progress_bar_lesson_menu;

    Animation turnOnChapterButton_disappear, chapter_disappear, lesson_disappear;

    Animation turnOnChapterButton_appear, chapter_appear, lesson_appear;


    LessonMenuActivityPresenter activityPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lesson_menu);

        ButterKnife.bind(this);

        overridePendingTransition(R.anim.fade_in , R.anim.fade_out);

        activityPresenter = new LessonMenuActivityPresenter(this);

        activityPresenter.setOnDataLoadListener(this);

        setTheme();

        loadAnim();

        addControl();

        addEvent();

        activityPresenter.loadData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityPresenter.clearAllListenerToDatabase();
        activityPresenter.pushCachingDataToDB();
    }

    @Override
    public void onDataLoadedSuccess(ArrayList<RO_Chapter> data) {
        chapter_menu_adapter.setData(data);
        circle_progress_bar_lesson_menu.setVisibility(View.GONE);
    }

    public void addControl() {
        chapter_menu_adapter = new Chapter_Menu_Adapter(this);
        chapter_menu_adapter.adaptFor(rcv_chapter_menu);
        lesson_menu_adapter = new Lesson_Menu_Adapter(this);
        lesson_menu_adapter.adaptFor(rcv_lesson_menu);

        if (AppThemeManager.isOnNightMode) {
            bg_night_mode.setVisibility(View.VISIBLE);
        } else {
            bg_night_mode.setVisibility(View.INVISIBLE);
        } }

    public void addEvent() {

        btn_turn_on_chapter_menu.setOnClickListener(v -> {
            rcv_chapter_menu.startAnimation(chapter_appear);
            btn_turn_on_chapter_menu.startAnimation(turnOnChapterButton_disappear);
            rcv_lesson_menu.startAnimation(lesson_disappear);
            txt_title.setText("Chương");
        });

        btn_back.setOnClickListener(v -> finish());

        chapter_menu_adapter.setOnItemClickListener(item -> {
            rcv_chapter_menu.startAnimation(chapter_disappear);
            rcv_lesson_menu.startAnimation(lesson_appear);
            btn_turn_on_chapter_menu.startAnimation(turnOnChapterButton_appear);
            lesson_menu_adapter.setData(item.getLessons());
            txt_title.setText(item.getName());
        });
        //                |
        //                |
        //                |     Load data from chapter_menu_adapter to lesson_menu_adapter
        //              \ | /
        //               \|/
        //                v
        lesson_menu_adapter.setOnItemClickListener(item -> {
            Intent intent = new Intent(LessonMenuActivity.this, LessonActivity.class);
            intent.putExtra(LESSON_NAME, item);
            startActivity(intent);
            activityPresenter.bringToTop(item);
        });


    }

    public void setTheme () {
        if (AppThemeManager.isCustomingTheme) {
            if (AppThemeManager.isUsingColorBackground)
                base_view.setBackgroundColor(AppThemeManager.getBackgroundColor());
            else
                base_view.setBackground(AppThemeManager.getBackgroundDrawable());
        }
    }

    public void loadAnim () {

        chapter_appear = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        chapter_disappear= AnimationUtils.loadAnimation(this, R.anim.suft_right_fade_out);

        turnOnChapterButton_appear = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        turnOnChapterButton_disappear= AnimationUtils.loadAnimation(this, R.anim.suft_right_fade_out);

        lesson_appear = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        lesson_disappear = AnimationUtils.loadAnimation(this, R.anim.suft_right_fade_out);

        chapter_appear.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                rcv_chapter_menu.setVisibility(View.VISIBLE);
            }

            public void onAnimationEnd(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }
        });

        chapter_disappear.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                rcv_chapter_menu.setVisibility(View.GONE);
            }

            public void onAnimationStart(Animation animation) { }
            public void onAnimationRepeat(Animation animation) { }
        });

        lesson_disappear.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationEnd(Animation animation) {
                rcv_lesson_menu.setVisibility(View.GONE);
            }

            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {

            }
        });

        lesson_appear.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                rcv_lesson_menu.setVisibility(View.VISIBLE);
            }

            public void onAnimationEnd(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }
        });

        turnOnChapterButton_appear.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                btn_turn_on_chapter_menu.setVisibility(View.VISIBLE);
            }

            public void onAnimationEnd(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }
        });

        turnOnChapterButton_disappear.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                btn_turn_on_chapter_menu.setVisibility(View.GONE);
            }

            public void onAnimationStart(Animation animation) { }
            public void onAnimationRepeat(Animation animation) { }
        });


    }

}
