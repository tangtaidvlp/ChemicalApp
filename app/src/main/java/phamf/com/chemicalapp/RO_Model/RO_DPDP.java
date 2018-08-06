package phamf.com.chemicalapp.RO_Model;

import android.view.Menu;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import phamf.com.chemicalapp.Abstraction.Menuable;

public class RO_DPDP extends RealmObject implements Menuable{

    @PrimaryKey
    int id;

    String name;

    RealmList<RO_OrganicMolecule> organicMolecules;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<RO_OrganicMolecule> getOrganicMolecules() {

        return organicMolecules;
    }

    public void setOrganicMolecules(RealmList<RO_OrganicMolecule> organicMolecules) {
        this.organicMolecules = organicMolecules;
    }

    @Override
    public String getName() {
        return name;
    }
}
