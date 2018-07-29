package phamf.com.chemicalapp.Abstraction;


import android.content.Context;

import java.util.Collection;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public abstract class OfflineDatabase <T> {
    public Context context;

    protected Realm realm;

    public OfflineDatabase (Context context) {
        Realm.init(context);
    }

    public abstract RealmResults<T> readList(String field, int value);

    public abstract void addOrUpdate(Collection<T> objects);

    public abstract void addOrUpdate(T object);

    public abstract void delete(String field, int value);

    public abstract void close();

    public abstract RealmResults<T> getAll();

    protected final Realm getInstance (String name, int version) {
        Realm realm = Realm.getInstance(new RealmConfiguration.Builder().name(name).schemaVersion(version).build());
        return realm;
    }

}
