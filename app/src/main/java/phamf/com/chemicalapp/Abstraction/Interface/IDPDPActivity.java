package phamf.com.chemicalapp.Abstraction.Interface;

import phamf.com.chemicalapp.Presenter.DPDPActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_DPDP;
import phamf.com.chemicalapp.RO_Model.RO_OrganicMolecule;

public interface IDPDPActivity {
    /**
     * @see phamf.com.chemicalapp.DPDPActivity
     */
    interface View {
        void addControl ();

        void addAnimation ();

        void addEvent ();

        void setUpViewCreator ();

        void setTheme ();
    }

    /**
     * @see DPDPActivityPresenter
     */
    interface Presenter {
        void loadData();

        String convertContent (RO_OrganicMolecule orM);

        void convertObjectToData (RO_DPDP dpdp);

        void setOnDataLoadListener(DPDPActivityPresenter.DataLoadListener onDataLoadListener);
    }
}
