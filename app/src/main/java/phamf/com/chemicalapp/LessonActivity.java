package phamf.com.chemicalapp;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import phamf.com.chemicalapp.Abstraction.Interface.ILessonActivity;
import phamf.com.chemicalapp.Adapter.ViewPager_Lesson_Adapter;
import phamf.com.chemicalapp.CustomView.LessonViewCreator;
import phamf.com.chemicalapp.CustomView.ViewPagerIndicator;
import phamf.com.chemicalapp.Manager.AppThemeManager;
import phamf.com.chemicalapp.Presenter.LessonActivityPresenter;
import phamf.com.chemicalapp.Supporter.UnitConverter;

import static phamf.com.chemicalapp.CustomView.LessonViewCreator.BOLD_TEXT;
import static phamf.com.chemicalapp.CustomView.LessonViewCreator.PART_DEVIDER;
import static phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.BIG_TITLE;
import static phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.COMPONENT_DEVIDER;
import static phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.CONTENT;
import static phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.SMALL_TITLE;
import static phamf.com.chemicalapp.Supporter.UnitConverter.DpToPixel;


/**
 * @see LessonActivityPresenter
 */
public class LessonActivity extends FullScreenActivity implements ILessonActivity.View , LessonActivityPresenter.DataLoadListener {

    @BindView(R.id.txt_lesson_title) TextView txt_title;

    @BindView(R.id.btn_back_lesson) Button btn_back;

    @BindView(R.id.parent_lesson_activity) ConstraintLayout base_view;

    @BindView(R.id.bg_night_mode_lesson) TextView bg_night_mode;

    @BindView(R.id.bg_hide_content_to_show_indicator) RelativeLayout bg_hide_content_to_show_indicator;

    @BindView(R.id.vpg_indicator) ViewPagerIndicator vpg_indicator;

    @BindView(R.id.vpg_lesson) ViewPager vpg_lesson;
    ViewPager_Lesson_Adapter vpg_lesson_adapter;

    private LessonViewCreator lessonViewCreator;

    private LessonActivityPresenter presenter;

    Animation fade_in, fade_out;

    boolean isOn = false;

    static volatile int time = 500;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lesson);

        ButterKnife.bind(this);

        presenter = new LessonActivityPresenter(this);

        setTheme();

        addControl();

        setUpViewCreator();

        loadAnimation();

        addEvent();

        presenter.loadData();
    }

    public void addControl () {
        FragmentManager fragmentManager = getSupportFragmentManager();
        vpg_lesson_adapter = new ViewPager_Lesson_Adapter(fragmentManager);
        vpg_lesson.setAdapter(vpg_lesson_adapter);
    }

    public void addEvent() {

        btn_back.setOnClickListener(v -> vpg_indicator.hideTitle());

        presenter.setOnDataLoadListener(this);

        vpg_lesson.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!isOn) {
                    bg_hide_content_to_show_indicator.startAnimation(fade_in);
                    isOn = true;
                }

                time = 500;
            }

            @Override
            public void onPageSelected(int position) {
                vpg_indicator.hightLightDotAt(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        txt_title.setOnClickListener(v -> vpg_indicator.showTitle());
    }


    public void setTheme () {
        if (AppThemeManager.isOnNightMode) {
            bg_night_mode.setVisibility(View.VISIBLE);
        } else {
            bg_night_mode.setVisibility(View.INVISIBLE);
        }

        if (AppThemeManager.isCustomingTheme) {
            if (AppThemeManager.isUsingColorBackground)
                base_view.setBackgroundColor(AppThemeManager.getBackgroundColor());
            else
                base_view.setBackground(AppThemeManager.getBackgroundDrawable());
        }
    }

    public void setUpViewCreator () {
        lessonViewCreator = new LessonViewCreator(vpg_lesson_adapter);
        LessonViewCreator.ViewCreator.setMarginBigTitle(DpToPixel(10),DpToPixel(10),DpToPixel(10),DpToPixel(10));
        LessonViewCreator.ViewCreator.setMarginSmallTitle(DpToPixel(10),DpToPixel(10),DpToPixel(10),DpToPixel(10));
        LessonViewCreator.ViewCreator.setMarginContent(DpToPixel(10),DpToPixel(10),DpToPixel(10),DpToPixel(10));

    }

    public void loadAnimation () {

        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fade_in.setDuration(200);
        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fade_out.setDuration(200);

        fade_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                isOn = false;
            }

            public void onAnimationStart(Animation animation) {

            }
            public void onAnimationRepeat(Animation animation) {

            }
        });



        fade_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                Thread thread = new Thread(() -> {
                    while (time > 0) {
                        try {
                            Thread.currentThread().sleep(100);
                            time = time - 100;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    runOnUiThread(() ->
                    {
                        bg_hide_content_to_show_indicator.startAnimation(fade_out);
                    });

                });

                thread.start();
            }

            public void onAnimationStart(Animation animation) {

            }
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void OnDataLoadSuccess(String title, String content) {

        String soft_content = BIG_TITLE + BOLD_TEXT + "Here is Big title" + COMPONENT_DEVIDER
                + SMALL_TITLE + BOLD_TEXT + "Here is Small Title" + COMPONENT_DEVIDER
                + CONTENT + BOLD_TEXT + "Here is Content" + COMPONENT_DEVIDER
                + PART_DEVIDER
                + BIG_TITLE + BOLD_TEXT  + "Here is Big title2" + COMPONENT_DEVIDER
                + SMALL_TITLE + BOLD_TEXT + "Here is Small Title2" + COMPONENT_DEVIDER
                + CONTENT + BOLD_TEXT + "Here is Content2" + COMPONENT_DEVIDER
                + PART_DEVIDER
                + BIG_TITLE + BOLD_TEXT + "Here is Big title3" + COMPONENT_DEVIDER
                + SMALL_TITLE + BOLD_TEXT + "Here is Small Title3" + COMPONENT_DEVIDER
                + CONTENT + BOLD_TEXT + "Here is Content3" + COMPONENT_DEVIDER
                + PART_DEVIDER
                + BIG_TITLE + BOLD_TEXT + "Here is Big title3" + COMPONENT_DEVIDER
                + SMALL_TITLE + BOLD_TEXT + "Here is Small Title3" + COMPONENT_DEVIDER
                + CONTENT + BOLD_TEXT + "Here is Content3" + COMPONENT_DEVIDER;

        lessonViewCreator.separatePart_And_BindDataToViewPG(soft_content);
        vpg_indicator.setTitle_list(vpg_lesson_adapter.getTitles());

    }
}
