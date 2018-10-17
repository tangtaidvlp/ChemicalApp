package phamf.com.chemicalapp.Presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Abstraction.Interface.IChemicalElementActivity;
import phamf.com.chemicalapp.BangTuanHoangActivity;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element;


/**
 * @see phamf.com.chemicalapp.ChemicalElementActivity
 */
public class ChemicalElementActivityPresenter extends Presenter implements IChemicalElementActivity.Presenter {
    private OnDataReceived onDataReceived;

    public ChemicalElementActivityPresenter(@NonNull Activity view) {
        super(view);
    }

    public void loadData () {
        Bundle bundle = view.getIntent().getExtras();
        RO_Chemical_Element chem_element = bundle.getParcelable(BangTuanHoangActivity.CHEM_ELEMENT);
        // Don't check null to show error when we forget to register to this listener
        onDataReceived.onDataReceived(chem_element);
    }

    public void setOnDataReceived (OnDataReceived onDataReceived) {
        this.onDataReceived = onDataReceived;
    }

    public interface OnDataReceived {
        void onDataReceived (RO_Chemical_Element chemical_element);
    }
}
