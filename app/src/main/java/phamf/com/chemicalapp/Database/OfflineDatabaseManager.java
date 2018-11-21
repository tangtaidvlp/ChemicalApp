package phamf.com.chemicalapp.Database;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;

public class OfflineDatabaseManager  {

    private Realm realm;

    public OfflineDatabaseManager (Context context) {
        Realm.init(context);
        realm = Realm.getInstance(new RealmConfiguration.Builder().name("database.realm").schemaVersion(1).build());
    }

    public <E extends RealmObject> RealmResults<E> readAllDataOf(Class<E> dataType) {
        return realm.where(dataType).findAll();
    }

    public <E extends RealmObject> RealmResults<E> readSomeDataOf(Class<E> dataType, String whereField, int value) {
        return realm.where(dataType).equalTo(whereField, value).findAll();
    }

    public <E extends RealmObject> E readOneObjectOf(Class<E> dataType, String whereField, int value) {
        return realm.where(dataType).equalTo(whereField, value).findFirst();
    }

    public <E extends RealmObject> E readOneObjectOf(Class<E> dataType, String whereField, String value) {
        return realm.where(dataType).equalTo(whereField, value).findFirst();
    }

        public <E extends RealmObject> void addOrUpdateDataOf (Class<E> dataType, Collection<E> value) {
        realm.beginTransaction();
        realm.insertOrUpdate(value);
        realm.commitTransaction();
    }

    public <E extends RealmObject> void addOrUpdateDataOf (Class<E> dataType, E... value) {
        realm.beginTransaction();
        if (value.length == 1) {
            realm.insertOrUpdate(value[0]);
        } else if (value.length > 1) {
            ArrayList<E> list = new ArrayList();
            for (E e : value) {
                list.add(e);
            }
            realm.insertOrUpdate(list);
        }
        realm.commitTransaction();
    }

    public <E extends RealmObject> void deleteAllDataOf(Class<E> dataType) {
        RealmResults<E> results = realm.where(dataType).findAll();
        results.deleteAllFromRealm();
    }

    public <E extends RealmObject> void deleteSomeDataOf(Class<E> dataType, String field, int value) {
        RealmResults<E> results = realm.where(dataType).equalTo(field, value).findAll();
        results.deleteAllFromRealm();
    }

    public <E extends RealmObject> E readOneOf (Class<E> dataType) {
        E result = realm.where(dataType).findFirst();
        return result;
    }

    public <E extends RealmObject> E readAsyncOneOf (Class<E> dataType, RealmChangeListener<E> querySucessListener) {
        E result = realm.where(dataType).findFirstAsync();
        result.addChangeListener(querySucessListener);
        return result;
    }

    public void beginTransaction () {
        realm.beginTransaction();
    }

    public void commitTransaction () {
        realm.commitTransaction();
    }

    public <E extends RealmObject> RealmResults<E> readAsyncAllDataOf(Class<E> dataType, RealmChangeListener<RealmResults<E>> realmChangeListener) {
        RealmResults<E> results = realm.where(dataType).findAllAsync();
        results.addChangeListener(realmChangeListener);
        return results;
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
