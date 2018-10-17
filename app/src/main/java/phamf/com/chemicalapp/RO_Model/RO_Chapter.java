package phamf.com.chemicalapp.RO_Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collection;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import phamf.com.chemicalapp.Abstraction.Interface.Menuable;

public class RO_Chapter extends RealmObject implements Menuable, Parcelable{

    @PrimaryKey
    public int id;

    public String name;

    public RealmList<RO_Lesson> lessons = new RealmList<>();

    protected RO_Chapter(Parcel in) {
        id = in.readInt();
        name = in.readString();
        in.readList(lessons, getClass().getClassLoader());
    }

    public static final Creator<RO_Chapter> CREATOR = new Creator<RO_Chapter>() {
        @Override
        public RO_Chapter createFromParcel(Parcel in) {
            return new RO_Chapter(in);
        }

        @Override
        public RO_Chapter[] newArray(int size) {
            return new RO_Chapter[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeList(lessons);
    }
}
