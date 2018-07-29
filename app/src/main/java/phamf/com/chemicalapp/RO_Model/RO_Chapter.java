package phamf.com.chemicalapp.RO_Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RO_Chapter extends RealmObject{

    @PrimaryKey
    private int id;

    private String name;


    public RO_Chapter() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
