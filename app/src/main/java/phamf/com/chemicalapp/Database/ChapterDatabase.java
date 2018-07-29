package phamf.com.chemicalapp.Database;

import android.content.Context;

import java.util.Collection;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import phamf.com.chemicalapp.Abstraction.OfflineDatabase;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;

public class ChapterDatabase extends OfflineDatabase<RO_Chapter> {

    private static final String DB_Name = "chapterDB.realm";

    private static final int VERSION = 0;

    public ChapterDatabase(Context context) {
        super(context);
        realm = getInstance(DB_Name, VERSION);
    }


    @Override
    public RealmResults<RO_Chapter> readList(String field, int value) {
        RealmResults<RO_Chapter> results = realm.where(RO_Chapter.class).equalTo(field, value).findAll();
        return results;
    }


    @Override
    public void addOrUpdate(Collection<RO_Chapter> chapters) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(chapters);
        realm.commitTransaction();
    }


    @Override
    public void addOrUpdate(RO_Chapter chapter) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(chapter);
        realm.commitTransaction();
    }


    @Override
    public void delete(String field, int value) {
        RealmResults<RO_Chapter> chapters = readList(field, value);
        chapters.deleteAllFromRealm();
    }


    @Override
    public void close() {
        realm.close();
    }

    @Override
    public RealmResults<RO_Chapter> getAll() {
        return realm.where(RO_Chapter.class).findAll();
    }
}
