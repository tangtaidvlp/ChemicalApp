package phamf.com.chemicalapp.Database;

import android.content.Context;

import java.util.Collection;

import io.realm.RealmResults;
import phamf.com.chemicalapp.Abstraction.OfflineDatabase;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;

public class ChemicalEquationDatabase extends OfflineDatabase<RO_ChemicalEquation> {


    private static final String DB_Name = "ChemicalE_DB.realm";


    private static final int VERSION = 2;


    public ChemicalEquationDatabase(Context context) {
        super(context);
        realm = getInstance(DB_Name, VERSION);
    }


    @Override
    public RealmResults<RO_ChemicalEquation> readList(String field, int value) {
        return realm.where(RO_ChemicalEquation.class).equalTo(field, value).findAll();
    }



    @Override
    public void addOrUpdate(Collection<RO_ChemicalEquation> equations) {
        realm.beginTransaction();
        realm.insertOrUpdate(equations);
        realm.commitTransaction();
    }


    @Override
    public void addOrUpdate(RO_ChemicalEquation equation) {
        realm.beginTransaction();
        realm.insertOrUpdate(equation);
        realm.commitTransaction();
    }


    @Override
    public void delete(String field, int value) {
        realm.where(RO_ChemicalEquation.class).equalTo(field, value).findAll().deleteAllFromRealm();
    }


    @Override
    public void close() {
        realm.close();
    }


    @Override
    public RealmResults<RO_ChemicalEquation> getAll() {
        return realm.where(RO_ChemicalEquation.class).findAll();
    }
}
