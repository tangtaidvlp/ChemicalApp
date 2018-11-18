package phamf.com.chemicalapp.Presenter;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Abstraction.Interface.ILessonMenuActivity;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.LessonMenuActivity;
import phamf.com.chemicalapp.Manager.RecentLearningLessonDataManager;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.RO_Model.Recent_LearningLessons;
import phamf.com.chemicalapp.Supporter.ROConverter;

import static phamf.com.chemicalapp.Supporter.ROConverter.toRO_Chapters_ArrayList;

/**
 *@see LessonMenuActivity
 */
public class LessonMenuActivityPresenter extends Presenter<LessonMenuActivity> implements ILessonMenuActivity.Presenter{

    private DataLoadListener onDataLoadListener;

    private OfflineDatabaseManager offlineDB_manager;

    private RecentLearningLessonDataManager recentLearningLessonDataManager;

    private RealmResults<RO_Chapter> data;

    public LessonMenuActivityPresenter(@NonNull LessonMenuActivity view) {
        super(view);
    }

    public void loadData() {

        offlineDB_manager = new OfflineDatabaseManager(context);

        // At the first time, check whether or not an object in realm db storing
        // a recent_learning_lessons object
        // If there's no object, create new one
        // If true, do nothing
        if (offlineDB_manager.readOneOf(Recent_LearningLessons.class) == null) {
            Recent_LearningLessons recent_learningLessons = new Recent_LearningLessons();
            offlineDB_manager.addOrUpdateDataOf(Recent_LearningLessons.class, recent_learningLessons);
        }

        recentLearningLessonDataManager = new RecentLearningLessonDataManager();
        recentLearningLessonDataManager.setOnGetDataSuccess(recent_Ces -> {
            data = offlineDB_manager.readAsyncAllDataOf(RO_Chapter.class, ro_chapters ->
                    onDataLoadListener.onDataLoadedSuccess(toRO_Chapters_ArrayList(ro_chapters)
                    ));
        });
        recentLearningLessonDataManager.getData(offlineDB_manager);





    }

    public void pushCachingDataToDB () {
        recentLearningLessonDataManager.updateDB();
    }

    public void clearAllListenerToDatabase () {
        data.removeAllChangeListeners();
    }

    /** Bring this lesson to top of recent learning lesson list in realm database  **/
    public void bringToTop (RO_Lesson ro_lesson) {
        recentLearningLessonDataManager.bringToTop(ro_lesson);
    }

    public void setOnDataLoadListener(DataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
    }

    /** @see LessonMenuActivity
     */
    public interface DataLoadListener {

        void onDataLoadedSuccess (ArrayList<RO_Chapter> ro_chapters);

    }

}
