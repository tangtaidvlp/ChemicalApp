package phamf.com.chemicalapp.Presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import phamf.com.chemicalapp.Database.ChapterDatabase;
import phamf.com.chemicalapp.Database.ChemicalEquationDatabase;
import phamf.com.chemicalapp.Database.LessonDatabase;
import phamf.com.chemicalapp.Database.OnlineDatabaseManager;
import phamf.com.chemicalapp.MainActivity;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.Supporter.ROConverter;

public class MainActivityPresenter {


    MainActivity view;


    Context context;


    ChemicalEquationDatabase ce_database;


    ChapterDatabase chapter_database;


    LessonDatabase lesson_database;


    OnlineDatabaseManager OD_manager;


    public MainActivityPresenter (@NonNull MainActivity view) {

        this.view = view;

        this.context = view.getBaseContext();

        ce_database = new ChemicalEquationDatabase(this.context);

        chapter_database = new ChapterDatabase(this.context);

        lesson_database = new LessonDatabase(this.context);

        OD_manager = new OnlineDatabaseManager();
    }


    public void updateCE_DB () {
        ce_database.addOrUpdate(ROConverter.toRO_CEs(OD_manager.getAll_CE_Data()));
    }


    public void updateChapter_DB () {
        chapter_database.addOrUpdate(ROConverter.toRO_Chapters(OD_manager.getAll_Chapter_Data()));
    }


    public void updateLesson_DB () {
        lesson_database.addOrUpdate(ROConverter.toRO_Lessons(OD_manager.getAll_Lesson_Data()));
    }


    public RealmResults<RO_Lesson> getLessonsFromDB (String field, int value) {
        return lesson_database.readList(field, value);
    }


    public RealmResults<RO_Chapter> getChaptersFromDB (String field, int value) {
        return chapter_database.readList(field, value);
    }


    public RealmResults<RO_ChemicalEquation> getCEFromDB (String field, int value) {
        return ce_database.readList(field, value);
    }

    public RealmResults<RO_Lesson> getLessonsFromDB () {
        return lesson_database.getAll();
    }


    public RealmResults<RO_Chapter> getChaptersFromDB () {
        return chapter_database.getAll();
    }


    public RealmResults<RO_ChemicalEquation> getCEFromDB () {
        return ce_database.getAll();
    }


    public void closeDB() {
        ce_database.close();
        lesson_database.close();
        chapter_database.close();
    }
}

