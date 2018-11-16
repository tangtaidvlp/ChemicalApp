package phamf.com.chemicalapp.RO_Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Recent_LearningLessons extends RealmObject {

    int id;

    private RealmList<RO_Lesson> recent_learning_lessons;

    public Recent_LearningLessons() {

    }

    public Recent_LearningLessons(int id, RealmList<RO_Lesson> recent_learning_lessons) {
        this.recent_learning_lessons = recent_learning_lessons;
        this.id = id;
    }

    public RealmList<RO_Lesson> getRecent_learning_lessons() {
        return recent_learning_lessons;
    }

    public void setRecent_learning_lessons(RealmList<RO_Lesson> recent_learning_lessons) {
        this.recent_learning_lessons = recent_learning_lessons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
