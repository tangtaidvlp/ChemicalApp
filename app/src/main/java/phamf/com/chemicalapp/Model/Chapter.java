package phamf.com.chemicalapp.Model;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;

public class Chapter {

    int id;
    String name;
    ArrayList<Lesson> lessons;


    public Chapter(int id, String name, ArrayList<Lesson> lessons) {
        this.id = id;
        this.name = name;
        this.lessons = lessons;
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

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

}
