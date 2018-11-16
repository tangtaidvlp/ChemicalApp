package phamf.com.chemicalapp.Model;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;

public class Chapter {

    int id;

    String name;

    // This list contains id of lessons used for finding them in database and then bind
    // to ro_lessons field below
    ArrayList<Long> lessons;

    ArrayList<RO_Lesson> ro_lessons = new ArrayList<>();


    public Chapter(int id, String name, ArrayList<Long> lessons) {
        this.id = id;
        this.name = name;
        this.lessons = lessons;
    }

    public Chapter () {

    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Long> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Long> lessons) {
        this.lessons = lessons;
    }

    public ArrayList<RO_Lesson> getRo_lessons() {
        return ro_lessons;
    }

    public void setRo_lessons(ArrayList<RO_Lesson> ro_lessons) {
        this.ro_lessons = ro_lessons;
    }
}
