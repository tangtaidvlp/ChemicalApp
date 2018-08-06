package phamf.com.chemicalapp.RO_Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import phamf.com.chemicalapp.Abstraction.Menuable;

public class RO_Lesson extends RealmObject implements Menuable{

    @PrimaryKey
    private int id;

    private String name;

    private String content;

    public RO_Lesson() {

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
