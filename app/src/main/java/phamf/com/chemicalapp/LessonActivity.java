package phamf.com.chemicalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import phamf.com.chemicalapp.CustomView.LessonViewCreator;

public class LessonActivity extends AppCompatActivity {

    @BindView(R.id.btn_back_lesson) Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lesson);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        String content = (bundle != null) ? bundle.getString(LessonMenuActivity.LESSON_CONTENT, "Nothing") : null;

        LinearLayout linearLayout = findViewById(R.id.board);

        LessonViewCreator lessonViewCreator = new LessonViewCreator(this, linearLayout);

        lessonViewCreator.setMarginBigTitle(10, 10, 10, 10);
        lessonViewCreator.setMarginContent(10, 10, 10, 10);
        lessonViewCreator.setMarginSmallTitle(10, 10, 10, 10);
        lessonViewCreator.addView(content);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
