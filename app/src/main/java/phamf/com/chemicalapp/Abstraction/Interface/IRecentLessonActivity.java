package phamf.com.chemicalapp.Abstraction.Interface;

import phamf.com.chemicalapp.Presenter.RecentLessonActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;


public interface IRecentLessonActivity {
    /**
     * @see phamf.com.chemicalapp.RecentLessonsActivity
     */
    interface View {
        void addControl();

        void addEvent();

        void setTheme ();
    }

    /**
     * @see RecentLessonActivityPresenter
     */
    interface Presenter {
        void loadData();

        void setOnDataLoadListener(RecentLessonActivityPresenter.DataLoadListener onDataLoadListener) ;

        void bringToTop (RO_Lesson item);

        void pushCachingData ();
    }
}
