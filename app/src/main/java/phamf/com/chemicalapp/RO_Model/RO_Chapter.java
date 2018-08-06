package phamf.com.chemicalapp.RO_Model;

import java.util.Collection;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import phamf.com.chemicalapp.Abstraction.Menuable;

public class RO_Chapter extends RealmObject implements Menuable{

    @PrimaryKey
    public int id;

    public String name;

    public RealmList<RO_Lesson> lessons = new RealmList<>();

    public RealmList<RO_Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Collection<RO_Lesson> lessons) {
        this.lessons.addAll(lessons);
    }

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
