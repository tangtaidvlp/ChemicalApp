package phamf.com.chemicalapp.Database;

import android.content.Context;

import java.util.Collection;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import phamf.com.chemicalapp.Abstraction.OfflineDatabase;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;

public class LessonDatabase extends OfflineDatabase<RO_Lesson> {

    private Realm realm;

    private static final String DB_Name = "lessonDB.realm";

    private static final int VERSION = 1;

    public LessonDatabase (Context context) {
        super(context);
        realm = getInstance(DB_Name, VERSION);
    }

    @Override
    public RealmResults<RO_Lesson> readList(String field, int value) {
        RealmResults<RO_Lesson> results = realm.where(RO_Lesson.class).equalTo(field, value).findAll();
        return results;
    }

    @Override
    public void addOrUpdate(Collection<RO_Lesson> lessons) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(lessons);
        realm.commitTransaction();
    }

    @Override
    public void addOrUpdate(RO_Lesson lesson) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(lesson);
        realm.commitTransaction();
    }

    @Override
    public void delete(String field, int value) {
        RealmResults<RO_Lesson> lessons = readList(field, value);
        lessons.deleteAllFromRealm();
    }

    @Override
    public void close() {
        realm.close();
    }

    @Override
    public RealmResults<RO_Lesson> getAll() {
        return realm.where(RO_Lesson.class).findAll();
    }
}
