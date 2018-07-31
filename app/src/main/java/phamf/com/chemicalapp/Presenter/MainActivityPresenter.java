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
import phamf.com.chemicalapp.Model.Chapter;
import phamf.com.chemicalapp.Model.ChemicalEquation;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.Supporter.ROConverter;

public class MainActivityPresenter implements OnlineDatabaseManager.OnDataLoaded{


    MainActivity view;


    Context context;


    ChemicalEquationDatabase ce_database;


    ChapterDatabase chapter_database;


    LessonDatabase lesson_database;


    OnlineDatabaseManager OnlineDB_manager;


    public MainActivityPresenter (@NonNull MainActivity view) {

        this.view = view;
        this.context = view.getBaseContext();

        OnlineDB_manager = new OnlineDatabaseManager();
        OnlineDB_manager.setOnDataLoaded(this);

        ce_database = new ChemicalEquationDatabase(this.context);
        chapter_database = new ChapterDatabase(this.context);
        lesson_database = new LessonDatabase(this.context);
    }



    public void update_CE_OFFDB () {
        OnlineDB_manager.getAll_CE_Data();
    }

    public void update_Chapter_OFFDB () {
        OnlineDB_manager.getAll_Chapter_Data();
    }



    public RealmResults<RO_Chapter> get_Chapters_FromDB (String field, int value) {
        return chapter_database.readList(field, value);
    }

    public RealmResults<RO_ChemicalEquation> get_CEs_FromDB (String field, int value) {
        return ce_database.readList(field, value);
    }



    public RealmResults<RO_Chapter> get_All_Chapters_FromDB () {
        return chapter_database.getAll();
    }

    public RealmResults<RO_ChemicalEquation> get_All_CEs_FromDB () {
        return ce_database.getAll();
    }



    public void closeDB() {
        ce_database.close();
        lesson_database.close();
        chapter_database.close();
    }


    //Called by "update_XXX" function above
    @Override
    public void onChapterLoadedFromFirebase(ArrayList<Chapter> chapters) {
        chapter_database.addOrUpdate(ROConverter.toRO_Chapters(chapters));
    }

    @Override
    public void onCE_LoadedFromFirebase(ArrayList<ChemicalEquation> equations) {
         ce_database.addOrUpdate(ROConverter.toRO_CEs(equations));
    }
}

