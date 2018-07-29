package phamf.com.chemicalapp.RO_Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RO_ChemicalEquation extends RealmObject {

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
}
