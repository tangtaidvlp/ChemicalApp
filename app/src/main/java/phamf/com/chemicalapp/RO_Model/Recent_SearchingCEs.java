package phamf.com.chemicalapp.RO_Model;

import java.util.Collection;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Recent_SearchingCEs extends RealmObject implements Cloneable{


    int id;

    private RealmList<RO_ChemicalEquation> recent_searching_ces = new RealmList<>();

    public Recent_SearchingCEs() {

    }

    public Recent_SearchingCEs(int id,RealmList<RO_ChemicalEquation> recent_searching_ces) {
        this.recent_searching_ces = recent_searching_ces;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<RO_ChemicalEquation> getRecent_searching_ces() {
        return recent_searching_ces;
    }

    public void setRecent_searching_ces(Collection<RO_ChemicalEquation> recent_searching_ces) {
        this.recent_searching_ces.clear();
        this.recent_searching_ces.addAll(recent_searching_ces);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
