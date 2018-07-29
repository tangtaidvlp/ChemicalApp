package phamf.com.chemicalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import phamf.com.chemicalapp.Adapter.RCV_Menu_Adapter;

public class LessonMenuActivity extends AppCompatActivity {


    @BindView(R.id.rcv_chapter_menu) RecyclerView rcv_chapter_menu;
    RCV_Menu_Adapter chapter_menu_adapter;

    @BindView(R.id.rcv_lesson_menu) RecyclerView rcv_lesson_menu;
    RCV_Menu_Adapter lesson_menu_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_menu);

    }

}
