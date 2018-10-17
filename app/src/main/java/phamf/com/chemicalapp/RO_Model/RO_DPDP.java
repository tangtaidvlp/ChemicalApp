package phamf.com.chemicalapp.RO_Model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import phamf.com.chemicalapp.Abstraction.Interface.Menuable;
import phamf.com.chemicalapp.Abstraction.Interface.QuickChangeableItem;

public class RO_DPDP extends RealmObject implements Menuable, Parcelable, QuickChangeableItem{

    @PrimaryKey
    public int id;

    public String name;

    public RealmList<RO_OrganicMolecule> organicMolecules = new RealmList<>();

    protected RO_DPDP(Parcel in) {
        id = in.readInt();
        name = in.readString();
        in.readList(organicMolecules, this.getClass().getClassLoader());
    }

    public RO_DPDP() {

    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<RO_OrganicMolecule> getOrganicMolecules() {

        return organicMolecules;
    }

    public void setOrganicMolecules(RealmList<RO_OrganicMolecule> organicMolecules) {
        this.organicMolecules = organicMolecules;
    }



    public static final Creator<RO_DPDP> CREATOR = new Creator<RO_DPDP>() {
        @Override
        public RO_DPDP createFromParcel(Parcel in) {
            return new RO_DPDP(in);
        }

        @Override
        public RO_DPDP[] newArray(int size) {
            return new RO_DPDP[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeList(organicMolecules);
    }
}
