package phamf.com.chemicalapp.Presenter;

import android.app.Activity;
import android.support.annotation.NonNull;

import phamf.com.chemicalapp.Abstraction.Presenter;
import phamf.com.chemicalapp.LessonActivity;
import phamf.com.chemicalapp.Model.Lesson;

public class LessonActivityPresenter extends Presenter<LessonActivity> {


    public LessonActivityPresenter(@NonNull LessonActivity view) {
        super(view);
    }

    @Override
    public void loadData () {

    }
}
