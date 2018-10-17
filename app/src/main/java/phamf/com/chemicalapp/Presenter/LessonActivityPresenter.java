package phamf.com.chemicalapp.Presenter;

import android.content.Intent;
import android.support.annotation.NonNull;

import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Abstraction.Interface.ILessonActivity;
import phamf.com.chemicalapp.LessonActivity;
import phamf.com.chemicalapp.LessonMenuActivity;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;

public class LessonActivityPresenter extends Presenter<LessonActivity> implements ILessonActivity.Presenter{


    public LessonActivityPresenter(@NonNull LessonActivity view) {
        super(view);
    }


    private DataLoadListener onDataLoadListener;


    public void setOnDataLoadListener (DataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
    }

    /**
     * @see LessonActivity
     */
    public void loadData () {
        Intent intent = view.getIntent();
        RO_Lesson ro_lesson = intent.getParcelableExtra(LessonMenuActivity.LESSON_NAME);
        if (ro_lesson != null) {
            if (onDataLoadListener != null) {
                onDataLoadListener.OnDataLoadSuccess(ro_lesson.getName(), ro_lesson.getContent());
            }
        }
    }


    /**
     * @see LessonActivity
     */
    public interface DataLoadListener {

        void OnDataLoadSuccess(String title, String content);

    }
}
