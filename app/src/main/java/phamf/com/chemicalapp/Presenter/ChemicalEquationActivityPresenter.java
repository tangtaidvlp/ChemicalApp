package phamf.com.chemicalapp.Presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import io.realm.RealmList;
import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Abstraction.Interface.IChemicalEquationActivity;
import phamf.com.chemicalapp.ChemicalEquationActivity;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.Manager.RecentSearching_CE_Data_Manager;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;

import static phamf.com.chemicalapp.ChemicalEquationActivity.CHEMICAL_EQUATION;

/**
 * @see ChemicalEquationActivity
 */
public class ChemicalEquationActivityPresenter extends Presenter<ChemicalEquationActivity> implements IChemicalEquationActivity.Presenter {

    private OnDataLoadedListener onDataLoadedListener;

    private OfflineDatabaseManager offlineDatabaseManager;

    private RecentSearching_CE_Data_Manager recent_searching_CE_Data_manager;

    public ChemicalEquationActivityPresenter(@NonNull ChemicalEquationActivity view) {
        super(view);
        offlineDatabaseManager = new OfflineDatabaseManager(view);
    }

    public void loadData () {
        Bundle bundle = view.getIntent().getExtras();
        RO_ChemicalEquation chemicalEquation = bundle.getParcelable(CHEMICAL_EQUATION);
        if (onDataLoadedListener != null) {
            onDataLoadedListener.onGettingChemicalEquation(chemicalEquation);

            ArrayList <RO_ChemicalEquation> data = new ArrayList<>();

            recent_searching_CE_Data_manager = new RecentSearching_CE_Data_Manager(offlineDatabaseManager);
            recent_searching_CE_Data_manager.setOnGetDataSuccess(
                    recent_Ces -> {
                        data.addAll(recent_Ces);
                        onDataLoadedListener.onDataLoadedFromDatabase(data);
                    }
            );

        }

    }

    public void setOnDataLoadedListener(OnDataLoadedListener onDataLoadedListener) {
        this.onDataLoadedListener = onDataLoadedListener;
    }

    public interface OnDataLoadedListener {

        void onGettingChemicalEquation (RO_ChemicalEquation chemicalEquation);

        void onDataLoadedFromDatabase(ArrayList<RO_ChemicalEquation>chemicalEquations);
    }
}
