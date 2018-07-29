package phamf.com.chemicalapp.Model;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import phamf.com.chemicalapp.RO_Model.RO_Lesson;

public class Lesson  {
    int id;

    int chapterId;

    String name;

    String content;


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


    public Lesson(int id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
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


    public RO_Lesson toRO_Lesson () {

        RO_Lesson ro_lesson = new RO_Lesson();
        ro_lesson.setId(this.id);
        ro_lesson.setChapterId(this.chapterId);
        ro_lesson.setName(this.name);
        ro_lesson.setContent(this.content);

        return ro_lesson;
    }
}
