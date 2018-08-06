package phamf.com.chemicalapp.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import io.realm.RealmResults;
import phamf.com.chemicalapp.Abstraction.Presenter;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.Database.OnlineDatabaseManager;
import phamf.com.chemicalapp.MainActivity;
import phamf.com.chemicalapp.Model.Chapter;
import phamf.com.chemicalapp.Model.ChemicalEquation;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.Supporter.ROConverter;

public class MainActivityPresenter extends Presenter<MainActivity> implements OnlineDatabaseManager.OnDataLoaded{


    private OnlineDatabaseManager onlineDB_manager;

    private OfflineDatabaseManager offlineDB_manager;

    private RequireOverlayPermissionManager requireOverlayPermissionManager;


    public MainActivityPresenter (@NonNull MainActivity view) {
        super(view);
        this.view = view;
        this.context = view.getBaseContext();

        onlineDB_manager = new OnlineDatabaseManager();
        onlineDB_manager.setOnDataLoaded(this);

        offlineDB_manager = new OfflineDatabaseManager(context);

        requireOverlayPermissionManager = new RequireOverlayPermissionManager(view);
    }



    public void update_CE_OFFDB () {
        onlineDB_manager.getAll_CE_Data();
    }

    public void update_Chapter_OFFDB () {
        onlineDB_manager.getAll_Chapter_Data();
    }


    public RealmResults<RO_Chapter> get_Chapters_FromDB (String field, int value) {
        return offlineDB_manager.readSomeDataOf(RO_Chapter.class, field, value);
    }

    public RealmResults<RO_ChemicalEquation> get_CEs_FromDB (String field, int value) {
        return offlineDB_manager.readSomeDataOf(RO_ChemicalEquation.class, field, value);
    }


    public RealmResults<RO_Chapter> get_All_Chapters_FromDB () {

        return offlineDB_manager.readAllDataOf(RO_Chapter.class);

    }

    public RealmResults<RO_ChemicalEquation> get_All_CEs_FromDB () {

        return offlineDB_manager.readAllDataOf(RO_ChemicalEquation.class);

    }


    public void closeDB() {
        offlineDB_manager.close();
    }


    @Override
    public void loadData () {
        view.rcv_search_adapter.setData(get_All_CEs_FromDB());
    }

    //Called by "update_****" function above
    @Override
    public void onChapterLoadedFromFirebase(Chapter chapter) {
        offlineDB_manager.addOrUpdateDataOf(RO_Chapter.class, ROConverter.toRO_Chapter(chapter));
    }

    @Override
    public void onCE_LoadedFromFirebase(ArrayList<ChemicalEquation> equations) {
        offlineDB_manager.addOrUpdateDataOf(RO_ChemicalEquation.class, ROConverter.toRO_CEs(equations));
    }

    public void requirePermission(int requestCode) {
        requireOverlayPermissionManager.requirePermission(requestCode);
    }


    class RequireOverlayPermissionManager {
        Activity activity;
        Context context;

        RequireOverlayPermissionManager (Activity activity) {
            this.activity = activity;
            this.context = activity.getBaseContext();
        }

        public void requirePermission (int request_code) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.getPackageName()));
                activity.startActivityForResult(intent, request_code);
            }
        }

    }
}

