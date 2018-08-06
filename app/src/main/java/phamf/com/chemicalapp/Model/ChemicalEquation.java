package phamf.com.chemicalapp.Model;

import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmModel;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;

public class ChemicalEquation {

    private int id;

    private String addingChemicals;

    private String product;

    // Use for caching equation field value to highlight the filtered equation without lost their beginning data;

    private String condition;

    private int total_balance_number;

    public ChemicalEquation () {
        //null
    }

    public ChemicalEquation(String addingChemicals, String product, String condition, int total_balance_number) {
        this.addingChemicals = addingChemicals;
        this.product = product;
        this.condition = condition;
        this.total_balance_number = total_balance_number;
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

    public String getEquation () {
        return getAddingChemicals() + " " + getProduct();
    }

    public RO_ChemicalEquation toRo_CE () {
        RO_ChemicalEquation ro_equation = new RO_ChemicalEquation();
        ro_equation.setId(this.id);
        ro_equation.setAddingChemicals(this.addingChemicals);
        ro_equation.setProduct(this.product);
        ro_equation.setCondition(this.condition);
        ro_equation.setTotal_balance_number(this.total_balance_number);
        return ro_equation;
    }
}
