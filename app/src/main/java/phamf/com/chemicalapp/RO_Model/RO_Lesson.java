package phamf.com.chemicalapp.RO_Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RO_Lesson extends RealmObject {

    @PrimaryKey
    private int id;

    private int chapterId;

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

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
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
