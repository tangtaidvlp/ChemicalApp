package phamf.com.chemicalapp.Abstraction.Interface;

import android.app.Activity;

import phamf.com.chemicalapp.Presenter.ChemicalEquationActivityPresenter;

public interface IChemicalEquationActivity {
    /**
     * @see phamf.com.chemicalapp.ChemicalEquationActivity
     */

    interface View {

        void setUpNecessaryInfos ();

        void addControl ();

        void addEvent ();

        void loadAnim ();

        void setEdt_SearchAdvanceFunctions ();

        void hideSoftKeyboard(Activity activity);

        void showSofKeyboard ();

    }


    /**
     * @see phamf.com.chemicalapp.Presenter.ChemicalEquationActivityPresenter
     */
    interface Presenter {
        void loadData () ;

        void setOnDataLoadedListener(ChemicalEquationActivityPresenter.OnDataLoadedListener onDataLoadedListener) ;
    }
}
