package phamf.com.chemicalapp.Database;

import android.content.Context;

import java.util.Collection;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class OfflineDatabaseManager  {

    Realm realm;

    public OfflineDatabaseManager (Context context) {
        Realm.init(context);
        realm = Realm.getInstance(new RealmConfiguration.Builder().name("database.realm").schemaVersion(0).build());
    }


    public <E extends RealmObject> RealmResults<E> readAllDataOf(Class<E> dataType) {
        return realm.where(dataType).findAll();
    }

    public <E extends RealmObject> RealmResults<E> readSomeDataOf(Class<E> dataType, String whereField, int value) {
        return realm.where(dataType).equalTo(whereField, value).findAll();
    }

    public <E extends RealmObject> void addOrUpdateDataOf (Class<E> dataType, Collection<E> value) {
        realm.beginTransaction();
        realm.insertOrUpdate(value);
        realm.commitTransaction();
    }

    public <E extends RealmObject> void addOrUpdateDataOf (Class<E> dataType, E value) {
        realm.beginTransaction();
        realm.insertOrUpdate(value);
        realm.commitTransaction();
    }

    public <E extends RealmObject> void deleteAllDataOf(Class<E> dataType) {
        RealmResults<E> results = realm.where(dataType).findAll();
        results.deleteAllFromRealm();
    }

    public <E extends RealmObject> void deleteSomeDataOf(Class<E> dataType) {
        RealmResults<E> results = realm.where(dataType).findAll();
        results.deleteAllFromRealm();
    }

    public void close () {
        if (!realm.isClosed())
        realm.close();
    }



//    @Override
//    public RealmResults<RO_Chapter> readList(String field, int value) {
//        RealmResults<RO_Chapter> results = realm.where(RO_Chapter.class).equalTo(field, value).findAll();
//        return results;
//    }
//
//
//    @Override
//    public void addOrUpdate(Collection<RO_Chapter> chapters) {
//        realm.beginTransaction();
//        realm.copyToRealmOrUpdate(chapters);
//        realm.commitTransaction();
//    }
//
//
//    @Override
//    public void addOrUpdate(RO_Chapter chapter) {
//        realm.beginTransaction();
//        realm.copyToRealmOrUpdate(chapter);
//        realm.commitTransaction();
//    }
//
//
//    @Override
//    public void delete(String field, int value) {
//        RealmResults<RO_Chapter> chapters = readList(field, value);
//        chapters.deleteAllFromRealm();
//    }
//
//
//    @Override
//    public void close() {
//        realm.close();
//    }
//
//    @Override
//    public RealmResults<RO_Chapter> getAll() {
//        return realm.where(RO_Chapter.class).findAll();
//    }

}
