package phamf.com.chemicalapp.Presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;

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

/**
 *@see LessonMenuActivity
 */
public class LessonMenuActivityPresenter extends Presenter<LessonMenuActivity> implements ILessonMenuActivity.Presenter{

    private OfflineDatabaseManager offlineDB_manager;

    private DataLoadListener onDataLoadListener;

    private Recent_LearningLessons recent_learningLessons;

    private RealmList<RO_Lesson> lessons = new RealmList<>();

    private RecentLearningLessonDataManager recentLearningLessonDataManager;

    public LessonMenuActivityPresenter(@NonNull LessonMenuActivity view) {
        super(view);
        offlineDB_manager = new OfflineDatabaseManager(context);
    }

    public RealmResults<RO_Chapter> getAll_Chapters_FromDB () {
        return offlineDB_manager.readAllDataOf(RO_Chapter.class);
    }

    public void loadData() {
        if (onDataLoadListener == null) return;

        if (offlineDB_manager.readOneOf(Recent_LearningLessons.class) == null) {
            Recent_LearningLessons recent_learningLessons = new Recent_LearningLessons();
            offlineDB_manager.addOrUpdateDataOf(Recent_LearningLessons.class, recent_learningLessons);
        }

        recentLearningLessonDataManager = new RecentLearningLessonDataManager(offlineDB_manager);
        RealmResults<RO_Chapter> chapters_list_data = getAll_Chapters_FromDB();
        ArrayList<RO_Chapter> data = new ArrayList<>();

        data.addAll(chapters_list_data);
        onDataLoadListener.onDataLoadedSuccess(data);
    }

    public void pushCachingDataToDB () {
        recentLearningLessonDataManager.updateDB();
    }

    /** Bring this lesson to top of recent learning lesson list in realm database  **/
    public void bringToTop (RO_Lesson ro_lesson) {
        recentLearningLessonDataManager.bringToTop(ro_lesson);
    }

    public void setOnDataLoadListener(DataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
    }

    public interface DataLoadListener {

        void onDataLoadedSuccess (ArrayList<RO_Chapter> ro_chapters);

    }

}
