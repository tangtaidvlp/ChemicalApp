package phamf.com.chemicalapp.Abstraction.Interface;


import android.support.annotation.NonNull;

import io.realm.RealmResults;
import phamf.com.chemicalapp.LessonMenuActivity;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;

public interface ILessonMenuActivity {

    /**
     * @see phamf.com.chemicalapp.LessonMenuActivity
     */

    interface View {
        void addControl();

        void addEvent();

        void setTheme ();

        void loadAnim ();
    }

    /**
     * @see phamf.com.chemicalapp.Presenter.LessonMenuActivityPresenter
     */
    interface Presenter {

        void loadData();

        void pushCachingDataToDB ();

        /** Bring lesson to top of recent learning lesson list in realm database  **/
        void bringToTop (RO_Lesson ro_lesson);
    }

}
