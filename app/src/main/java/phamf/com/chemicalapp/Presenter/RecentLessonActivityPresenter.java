package phamf.com.chemicalapp.Presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Abstraction.Interface.IRecentLessonActivity;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.Manager.RecentLearningLessonDataManager;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.RO_Model.Recent_LearningLessons;
import phamf.com.chemicalapp.RecentLessonsActivity;


/**
 * @see RecentLessonsActivity
 */
public class RecentLessonActivityPresenter extends Presenter<RecentLessonsActivity> implements IRecentLessonActivity {

    private DataLoadListener onDataLoadListener;

    OfflineDatabaseManager offline_DBManager;

    Recent_LearningLessons recent_learningLessons;

    RecentLearningLessonDataManager recentLearningLessonDataManager;


    public RecentLessonActivityPresenter(@NonNull RecentLessonsActivity view) {
        super(view);
        offline_DBManager = new OfflineDatabaseManager(view);
    }

    public void loadData() {
        if (onDataLoadListener == null) return;

        if (offline_DBManager.readOneOf(Recent_LearningLessons.class) == null) {
            Recent_LearningLessons recent_learningLessons = new Recent_LearningLessons();
            offline_DBManager.addOrUpdateDataOf(Recent_LearningLessons.class, recent_learningLessons);
        }

        recentLearningLessonDataManager = new RecentLearningLessonDataManager(offline_DBManager);
        recent_learningLessons = offline_DBManager.readOneOf(Recent_LearningLessons.class);
        ArrayList<RO_Lesson> data = new ArrayList<>();
        data.addAll(recent_learningLessons.getRecent_learning_lessons());
        onDataLoadListener.onDataLoadSuccess(data);
    }

    public void setOnDataLoadListener(DataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
    }

    public void bringToTop(RO_Lesson item) {
        recentLearningLessonDataManager.bringToTop(item);
    }

    public void pushCachingData () {
        recentLearningLessonDataManager.updateDB();
    }


    public interface DataLoadListener {
        void onDataLoadSuccess(ArrayList<RO_Lesson> ro_lessons);
    }
}
