package phamf.com.chemicalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import phamf.com.chemicalapp.Abstraction.RCV_Menu_Adapter;
import phamf.com.chemicalapp.Adapter.Chapter_Menu_Adapter;
import phamf.com.chemicalapp.Adapter.Lesson_Menu_Adapter;
import phamf.com.chemicalapp.Presenter.LessonMenuActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;


public class LessonMenuActivity extends AppCompatActivity {

    public static final String LESSON_NAME = "lesson_name";

    public static final String LESSON_INDEX = "lesson_index";

    public static final String LESSON_CONTENT = "lesson_content";



    @BindView(R.id.rcv_chapter_menu) RecyclerView rcv_chapter_menu;
    Chapter_Menu_Adapter chapter_menu_adapter;

    @BindView(R.id.rcv_lesson_menu) RecyclerView rcv_lesson_menu;
    Lesson_Menu_Adapter lesson_menu_adapter;

    @BindView(R.id.btn_menu_back) Button btn_back;

    LessonMenuActivityPresenter activityPresenter;

    Animation suft_right_fade_out;

    Animation fade_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lesson_menu);

        ButterKnife.bind(this);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        activityPresenter = new LessonMenuActivityPresenter(this);

        loadAnim();

        addControl();

        addEvent();

    }


    private void addEvent() {
        chapter_menu_adapter.setOnItemClickListener(new RCV_Menu_Adapter.OnItemClickListener<RO_Chapter>() {
            @Override
            public void onItemClickListener(RO_Chapter item) {
                lesson_menu_adapter.setData(item.getLessons());
                rcv_chapter_menu.startAnimation(suft_right_fade_out);
                rcv_lesson_menu.startAnimation(fade_in);
                rcv_chapter_menu.setVisibility(View.GONE);
            }
        });


        //                |
        //                |
        //                |     Load data from chapter_menu_adapter to lesson_menu_adapter
        //              \ | /
        //               \|/
        //                v


        lesson_menu_adapter.setOnItemClickListener(new RCV_Menu_Adapter.OnItemClickListener<RO_Lesson>() {
            @Override
            public void onItemClickListener(RO_Lesson item) {
                Log.e("CONTENT", item.getContent());
                Intent intent = new Intent(LessonMenuActivity.this, LessonActivity.class);
                intent.putExtra(LESSON_CONTENT, item.getContent());
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void addControl() {
        chapter_menu_adapter = new Chapter_Menu_Adapter(this);
        chapter_menu_adapter.adaptFor(rcv_chapter_menu);
        loadData();
        lesson_menu_adapter = new Lesson_Menu_Adapter(this);
        lesson_menu_adapter.adaptFor(rcv_lesson_menu);

    }

    private void loadAnim () {
        suft_right_fade_out = AnimationUtils.loadAnimation(this, R.anim.suft_right_fade_out);
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        suft_right_fade_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rcv_chapter_menu.setVisibility(View.GONE);
                Toast.makeText(LessonMenuActivity.this, "hehe" + rcv_chapter_menu.getVisibility(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void loadData () {
        RealmResults<RO_Chapter> chapter_list = activityPresenter.getAll_Chapters_FromDB();
        chapter_menu_adapter.setData(chapter_list);
    }

}
