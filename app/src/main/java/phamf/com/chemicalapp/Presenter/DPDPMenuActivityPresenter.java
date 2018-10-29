package phamf.com.chemicalapp.Presenter;

import android.support.annotation.NonNull;

import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Abstraction.Interface.IDPDPMenuActivity;
import phamf.com.chemicalapp.DPDPMenuActivity;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.RO_Model.RO_DPDP;

public class DPDPMenuActivityPresenter extends Presenter<DPDPMenuActivity> implements IDPDPMenuActivity.Presenter {


    private OfflineDatabaseManager offline_DB_manager;


    public DPDPMenuActivityPresenter(@NonNull DPDPMenuActivity view) {

        super(view);

        offline_DB_manager = new OfflineDatabaseManager(context);

    }

    public void loadData () {
        view.rcv_dpdp_adapter.setData(offline_DB_manager.readAllDataOf(RO_DPDP.class));
    }

//    interface OnDataLoadSuccess {
//        void onDataLoadSuccess()
//    }

}
