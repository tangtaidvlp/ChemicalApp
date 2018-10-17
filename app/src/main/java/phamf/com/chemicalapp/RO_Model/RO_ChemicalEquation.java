package phamf.com.chemicalapp.RO_Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RO_ChemicalEquation extends RealmObject implements Cloneable, Parcelable {

    @PrimaryKey
    private int id;

    private String addingChemicals;

    private String product;

    // Use for caching equation field value to highlight the filtered equation without lost their beginning data;

    private String condition;

    private int total_balance_number;

    public RO_ChemicalEquation () {
        //null
    }


    protected RO_ChemicalEquation(Parcel in) {
        id = in.readInt();
        addingChemicals = in.readString();
        product = in.readString();
        condition = in.readString();
        total_balance_number = in.readInt();
    }

    public static final Creator<RO_ChemicalEquation> CREATOR = new Creator<RO_ChemicalEquation>() {
        @Override
        public RO_ChemicalEquation createFromParcel(Parcel in) {
            return new RO_ChemicalEquation(in);
        }

        @Override
        public RO_ChemicalEquation[] newArray(int size) {
            return new RO_ChemicalEquation[size];
        }
    };

    public String getAddingChemicals() {
        return addingChemicals;
    }

    public void setAddingChemicals(String addingChemicals) {
        this.addingChemicals = addingChemicals;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getTotal_balance_number() {
        return total_balance_number;
    }

    public void setTotal_balance_number(int total_balance_number) {
        this.total_balance_number = total_balance_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RO_ChemicalEquation) {
            if (((RO_ChemicalEquation) obj).getId() == this.id) return true;
        } else {
            Log.e("Class cast exeption", "");
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(addingChemicals);
        dest.writeString(product);
        dest.writeString(condition);
        dest.writeInt(total_balance_number);
    }
}
