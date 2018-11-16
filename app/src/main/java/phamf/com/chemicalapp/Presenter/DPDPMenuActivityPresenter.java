package phamf.com.chemicalapp.Presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Abstraction.Interface.IDPDPMenuActivity;
import phamf.com.chemicalapp.DPDPMenuActivity;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.RO_Model.RO_DPDP;
import phamf.com.chemicalapp.Supporter.ROConverter;

import static phamf.com.chemicalapp.Supporter.ROConverter.toRO_DPDPs_ArrayList;

/** @see phamf.com.chemicalapp.DPDPMenuActivity **/

public class DPDPMenuActivityPresenter extends Presenter<DPDPMenuActivity> implements IDPDPMenuActivity.Presenter {


    private OfflineDatabaseManager offline_DB_manager;

    private RealmResults<RO_DPDP> data;

    private OnDataLoadSuccess onDataLoadSuccess;

    public DPDPMenuActivityPresenter(@NonNull DPDPMenuActivity view) {

        super(view);

        offline_DB_manager = new OfflineDatabaseManager(context);

    }

    public void loadData () {
        data = offline_DB_manager.readAsyncAllDataOf(RO_DPDP.class, ro_dpdps ->
                onDataLoadSuccess.onDataLoadSuccess(toRO_DPDPs_ArrayList(ro_dpdps)));
    }

    public void setOnDataLoadListener(OnDataLoadSuccess onDataLoadListener) {
        this.onDataLoadSuccess = onDataLoadListener;
    }

    public interface OnDataLoadSuccess {
        void onDataLoadSuccess(ArrayList<RO_DPDP> ro_dpdps);
    }

}
