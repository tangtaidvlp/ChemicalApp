package phamf.com.chemicalapp.RO_Model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import phamf.com.chemicalapp.Abstraction.Interface.QuickChangeableItem;

public class RO_OrganicMolecule extends RealmObject implements Parcelable, QuickChangeableItem {

    @PrimaryKey
    int id;

    String molecule_formula = "";

    String normal_name = "";

    String replace_name = "";

    int structure_image_id = 0;

    int compact_structure_image_id = 0;

    public RealmList<RO_Isomerism> isomerisms = new RealmList<>();

    protected RO_OrganicMolecule(Parcel in) {
        id = in.readInt();
        molecule_formula = in.readString();
        normal_name = in.readString();
        replace_name = in.readString();
        structure_image_id = in.readInt();
        compact_structure_image_id = in.readInt();
        in.readList(isomerisms, this.getClass().getClassLoader());
    }

    public static final Creator<RO_OrganicMolecule> CREATOR = new Creator<RO_OrganicMolecule>() {
        @Override
        public RO_OrganicMolecule createFromParcel(Parcel in) {
            return new RO_OrganicMolecule(in);
        }

        @Override
        public RO_OrganicMolecule[] newArray(int size) {
            return new RO_OrganicMolecule[size];
        }
    };

    public String getMolecule_formula() {
        return molecule_formula;
    }

    public void setMolecule_formula(String molecule_formula) {
        this.molecule_formula = molecule_formula;
    }

    // For create List View
    @Override
    public String getName() {
        return getMolecule_formula();
    }

    public String getNormal_name() {return normal_name;}

    public void setNormal_name(String normal_name) {
        this.normal_name = normal_name;
    }

    public String getReplace_name() {
        return replace_name;
    }

    public void setReplace_name(String replace_name) {
        this.replace_name = replace_name;
    }

    public int getStructure_image_id() {
        return structure_image_id;
    }

    public void setStructure_image_id(int structure_image_id) {
        this.structure_image_id = structure_image_id;
    }

    public int getCompact_structure_image_id() {
        return compact_structure_image_id;
    }

    public void setCompact_structure_image_id(int compact_structure_image_id) {
        this.compact_structure_image_id = compact_structure_image_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<RO_Isomerism> getIsomerisms() {
        return isomerisms;
    }

    public void setIsomerisms(RealmList<RO_Isomerism> isomerisms) {
        this.isomerisms = isomerisms;
    }

    public RO_OrganicMolecule() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(molecule_formula);
        dest.writeString(normal_name);
        dest.writeString(replace_name);
        dest.writeInt(structure_image_id);
        dest.writeInt(compact_structure_image_id);
        dest.writeList(isomerisms);
    }
}
