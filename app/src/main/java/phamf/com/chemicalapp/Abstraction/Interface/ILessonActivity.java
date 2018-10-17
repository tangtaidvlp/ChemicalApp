package phamf.com.chemicalapp.Abstraction.Interface;

import phamf.com.chemicalapp.Presenter.LessonActivityPresenter;

public interface ILessonActivity {
    /**
     * @see phamf.com.chemicalapp.LessonActivity
     */
    interface View {
        void addControl ();

        void addEvent();

        void setTheme ();

        void setUpViewCreator ();
    }

    /**
     * @see phamf.com.chemicalapp.Presenter.LessonActivityPresenter
     */
    interface Presenter {

        void setOnDataLoadListener (LessonActivityPresenter.DataLoadListener onDataLoadListener);

        void loadData () ;

    }

}
