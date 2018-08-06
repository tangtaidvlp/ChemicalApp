package phamf.com.chemicalapp.Presenter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import io.realm.RealmResults;
import phamf.com.chemicalapp.Abstraction.Presenter;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.LessonMenuActivity;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;

public class LessonMenuActivityPresenter extends Presenter<LessonMenuActivity> {

    private OfflineDatabaseManager OfflineDB_manager;

    public LessonMenuActivityPresenter(@NonNull LessonMenuActivity view) {
        super(view);
        OfflineDB_manager = new OfflineDatabaseManager(context);
    }

    public RealmResults<RO_Chapter> getAll_Chapters_FromDB () {
        return OfflineDB_manager.readAllDataOf(RO_Chapter.class);
    }

    @Override
    public void loadData() {

    }

}
