package phamf.com.chemicalapp.Manager;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

import io.realm.RealmList;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.RO_Model.Recent_SearchingCEs;

public class RecentSearching_CE_Data_Manager {

    private Recent_SearchingCEs recent_searchingCEs;

    private RealmList<RO_ChemicalEquation> recent_CEs;

    private OfflineDatabaseManager offline_DBManager;

    public RecentSearching_CE_Data_Manager(OfflineDatabaseManager offline_DBManager) {
        this.offline_DBManager = offline_DBManager;

        recent_searchingCEs = offline_DBManager.readOneOf(Recent_SearchingCEs.class);
        recent_CEs = recent_searchingCEs.getRecent_searching_ces();

        if (recent_CEs.size() == 0) {
            offline_DBManager.beginTransaction();
            recent_CEs.addAll(offline_DBManager.readAllDataOf(RO_ChemicalEquation.class));
            offline_DBManager.commitTransaction();
        }

    }

    public void bringToTop (RO_ChemicalEquation ro_ce) {

        if (recent_CEs.contains(ro_ce) && !recent_CEs.get(0).equals(ro_ce)) {

            offline_DBManager.beginTransaction();

            recent_CEs.remove(ro_ce);
            recent_CEs.add(0, ro_ce);

            offline_DBManager.commitTransaction();

        } else {
            Log.e("Error happened", "ro_ce not found in list, MainActivityPresenter.java, line 139 "  + recent_searchingCEs.getRecent_searching_ces().size() );
        }

    }

    public void updateDB () {
        offline_DBManager.addOrUpdateDataOf(Recent_SearchingCEs.class, recent_searchingCEs);
    }

    public RealmList<RO_ChemicalEquation> getData() {
        return recent_CEs;
    }
}
