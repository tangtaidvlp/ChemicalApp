package phamf.com.chemicalapp.RO_Model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RO_Chemical_Element extends RealmObject implements Parcelable{

    @PrimaryKey
    int id;

    String name = "";

    String symbol = "";

    int electron_count = 0;

    int proton_count = 0;

    int notron_count = 0;

    float mass = 0;

    String type = "";

    int background_color = 0;

    public RO_Chemical_Element() {

    }

    public RO_Chemical_Element(int id, String name, String symbol, int electron_count, int proton_count, int notron_count, float mass, String type, int background_color) {

        this.name = name;
        this.symbol = symbol;
        this.electron_count = electron_count;
        this.proton_count = proton_count;
        this.notron_count = notron_count;
        this.mass = mass;
        this.type = type;
        this.id = id;
        this.background_color = background_color;
    }

    protected RO_Chemical_Element(Parcel in) {
        id = in.readInt();
        name = in.readString();
        symbol = in.readString();
        electron_count = in.readInt();
        proton_count = in.readInt();
        notron_count = in.readInt();
        mass = in.readFloat();
        type = in.readString();
        background_color = in.readInt();
    }

    public static final Creator<RO_Chemical_Element> CREATOR = new Creator<RO_Chemical_Element>() {
        @Override
        public RO_Chemical_Element createFromParcel(Parcel in) {
            return new RO_Chemical_Element(in);
        }

        @Override
        public RO_Chemical_Element[] newArray(int size) {
            return new RO_Chemical_Element[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getElectron_count() {
        return electron_count;
    }

    public void setElectron_count(int electron_count) {
        this.electron_count = electron_count;
    }

    public int getProton_count() {
        return proton_count;
    }

    public void setProton_count(int proton_count) {
        this.proton_count = proton_count;
    }

    public int getNotron_count() {
        return notron_count;
    }

    public void setNotron_count(int notron_count) {
        this.notron_count = notron_count;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public int getBackground_color() {
        return background_color;
    }

    public void setBackground_color(int background_color) {
        this.background_color = background_color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(symbol);
        dest.writeInt(electron_count);
        dest.writeInt(proton_count);
        dest.writeInt(notron_count);
        dest.writeFloat(mass);
        dest.writeString(type);
        dest.writeInt(background_color);
    }
}

