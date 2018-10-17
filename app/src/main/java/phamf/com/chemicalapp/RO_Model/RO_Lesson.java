package phamf.com.chemicalapp.RO_Model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import phamf.com.chemicalapp.Abstraction.Interface.Menuable;
import phamf.com.chemicalapp.Abstraction.Interface.QuickChangeableItem;

public class RO_Lesson extends RealmObject implements Menuable, Parcelable, QuickChangeableItem{

    @PrimaryKey
    private int id;

    private String name;

    private String content;

    public RO_Lesson() {

    }

    protected RO_Lesson(Parcel in) {
        id = in.readInt();
        name = in.readString();
        content = in.readString();
    }

    public static final Creator<RO_Lesson> CREATOR = new Creator<RO_Lesson>() {
        @Override
        public RO_Lesson createFromParcel(Parcel in) {
            return new RO_Lesson(in);
        }

        @Override
        public RO_Lesson[] newArray(int size) {
            return new RO_Lesson[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(content);
    }
}
