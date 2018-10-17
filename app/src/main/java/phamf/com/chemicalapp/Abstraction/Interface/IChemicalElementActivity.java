package phamf.com.chemicalapp.Abstraction.Interface;

import phamf.com.chemicalapp.Presenter.ChemicalElementActivityPresenter;

public interface IChemicalElementActivity {
    /**
     * @see phamf.com.chemicalapp.ChemicalElementActivity
     */
    interface View {
        void createNeccesaryInfo();

        void addControl();

        void addEvent();

        void loadAnimation ();

        void showSoftKeyboard ();

        void hideSoftKeyboard ();
    }

    /**
     * @see phamf.com.chemicalapp.Presenter.ChemicalElementActivityPresenter
     */
    interface Presenter {
        void loadData ();

        void setOnDataReceived (ChemicalElementActivityPresenter.OnDataReceived onDataReceived);
    }
}
