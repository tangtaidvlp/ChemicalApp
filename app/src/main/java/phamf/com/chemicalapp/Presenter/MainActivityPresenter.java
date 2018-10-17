package phamf.com.chemicalapp.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.RealmResults;
import phamf.com.chemicalapp.Abstraction.Interface.IMainActivity;
import phamf.com.chemicalapp.Abstraction.Interface.OnThemeChangeListener;
import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.Database.OnlineDatabaseManager;
import phamf.com.chemicalapp.MainActivity;
import phamf.com.chemicalapp.Manager.AppThemeManager;
import phamf.com.chemicalapp.Manager.RecentSearching_CE_Data_Manager;
import phamf.com.chemicalapp.Model.Chapter;
import phamf.com.chemicalapp.Model.ChemicalEquation;
import phamf.com.chemicalapp.Model.Chemical_Element;
import phamf.com.chemicalapp.Model.DPDP;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element;
import phamf.com.chemicalapp.RO_Model.RO_DPDP;
import phamf.com.chemicalapp.RO_Model.Recent_SearchingCEs;
import phamf.com.chemicalapp.Supporter.ROConverter;

import static android.content.Context.MODE_PRIVATE;

/**
 * @see MainActivity
 */
public class MainActivityPresenter extends Presenter<MainActivity> implements IMainActivity.Presenter, OnlineDatabaseManager.OnDataLoaded{


    private OnlineDatabaseManager onlineDB_manager;

    private OfflineDatabaseManager offlineDB_manager;

    private RequireOverlayPermissionManager requireOverlayPermissionManager;

    private MainActivityPresenter.DataLoadListener onDataLoadListener;

    private OnThemeChangeListener onThemeChangeListener;

    private RecentSearching_CE_Data_Manager recentSearching_ce_data_manager;

    private OnUpdateCheckedListener onUpdateChecked;

    public static final String APP_INFO = "app_info";

    public static final String DATABASE_VERSION = "db_vers";


    public MainActivityPresenter (@NonNull MainActivity view) {
        super(view);

        this.view = view;

        this.context = view.getBaseContext();

        onlineDB_manager = new OnlineDatabaseManager();

        onlineDB_manager.setOnDataLoaded(this);

        offlineDB_manager = new OfflineDatabaseManager(context);

        requireOverlayPermissionManager = new RequireOverlayPermissionManager(view);
    }



// Update Offline Database
    public void updateAll_CE_OFFDB () {
        onlineDB_manager.getAll_CE_Data();
    }

    public void updateAll_Chapter_OFFDB () {
        onlineDB_manager.getAll_Chapter_Data();
    }

    public void updateAll_DPDP_OFFDB() {
        onlineDB_manager.getAll_DPDP();
    }

    public void updateAll_BangTuanHoan_OFFDB () {onlineDB_manager.getAllBangTuanHoan();}


    public void update_new_version_CE_OFFDB () {
        onlineDB_manager.getNeedingUpdate_CE();
    }

    public void update_new_version_Chapter_OFFDB () {
        onlineDB_manager.getNeedingUpdate_Chapter();
    }

    public void update_new_version_DPDP_OFFDB() {
        onlineDB_manager.getNeedingUpdate_DPDP();
    }

    public void update_new_version_BangTuanHoan_OFFDB () {
        onlineDB_manager.getNeedingUpdate_BangTuanHoan();
    }

// Get data from Offline database
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

    public RealmResults<RO_Chemical_Element> get_All_Chemical_Element_FromDB () {
        return offlineDB_manager.readAllDataOf(RO_Chemical_Element.class);
    }




// Else

    public void loadTheme () {

        SharedPreferences sharedPreferences = view.getSharedPreferences(AppThemeManager.APP_THEME, MODE_PRIVATE);

        AppThemeManager.isCustomingTheme = sharedPreferences.getBoolean(AppThemeManager.IS_CUSTOMING, false);
        AppThemeManager.isUsingAvailableThemes = sharedPreferences.getBoolean(AppThemeManager.IS_USING_AVAILABLE_THEMES, false);
        AppThemeManager.isOnNightMode = sharedPreferences.getBoolean(AppThemeManager.IS_ON_NIGHT_MODE, false);
        Toast.makeText(view, AppThemeManager.isOnNightMode + "", Toast.LENGTH_SHORT).show();

        if (AppThemeManager.isCustomingTheme) {
            AppThemeManager.isUsingColorBackground = sharedPreferences.getBoolean(AppThemeManager.IS_USING_COLOR_BACKGROUND, true);
            AppThemeManager.widgetColor = sharedPreferences.getInt(AppThemeManager.WIDGET_COLOR, 0);
            AppThemeManager.textColor = sharedPreferences.getInt(AppThemeManager.TEXT_COLOR, 0);
            AppThemeManager.backgroundColor= sharedPreferences.getInt(AppThemeManager.BACKGROUND_COLOR, 0);
            AppThemeManager.backgroundDrawable= sharedPreferences.getInt(AppThemeManager.BACKGROUND_DRAWABLE, 0);
            onThemeChangeListener.onThemeChange();
        } else if (AppThemeManager.isUsingAvailableThemes) {
            AppThemeManager.setTheme(sharedPreferences.getInt(AppThemeManager.CURRENT_THEME, 0));
            onThemeChangeListener.onThemeChange();
        }
    }

    public long getDataVersion () {
        SharedPreferences databaseVersion = view.getSharedPreferences(APP_INFO, MODE_PRIVATE);
        return databaseVersion.getLong(DATABASE_VERSION, 1);
    }

    public void saveDataVersion (int version) {
        SharedPreferences databaseVersion = view.getSharedPreferences(APP_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = databaseVersion.edit();
        editor.putLong(DATABASE_VERSION, version);
        editor.apply();
    }

    public void saveTheme() {
        SharedPreferences sharedPreferences = view.getSharedPreferences(AppThemeManager.APP_THEME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Toast.makeText(view, AppThemeManager.isOnNightMode + "", Toast.LENGTH_SHORT).show();
        editor.putBoolean(AppThemeManager.IS_ON_NIGHT_MODE, AppThemeManager.isOnNightMode);
        if (AppThemeManager.isCustomingTheme) {

            editor.putInt(AppThemeManager.BACKGROUND_COLOR, AppThemeManager.backgroundColor);
            editor.putInt(AppThemeManager.BACKGROUND_DRAWABLE, AppThemeManager.backgroundDrawable);
            editor.putInt(AppThemeManager.TEXT_COLOR, AppThemeManager.textColor);
            editor.putInt(AppThemeManager.WIDGET_COLOR, AppThemeManager.widgetColor);
            editor.putBoolean(AppThemeManager.IS_USING_AVAILABLE_THEMES, AppThemeManager.isUsingAvailableThemes);
            editor.putBoolean(AppThemeManager.IS_CUSTOMING, AppThemeManager.isCustomingTheme);

        } else {
            Log.e("Don't save anything", "!!!");
        }
        editor.apply();
    }

    public void loadData () {
        if (onDataLoadListener == null) return;

        if (offlineDB_manager.readOneOf(Recent_SearchingCEs.class) == null) {
            Recent_SearchingCEs recent_searchingCEs = new Recent_SearchingCEs();
            offlineDB_manager.addOrUpdateDataOf(Recent_SearchingCEs.class, recent_searchingCEs);
        }

        ArrayList <RO_ChemicalEquation> data = new ArrayList<>();

        recentSearching_ce_data_manager = new RecentSearching_CE_Data_Manager(offlineDB_manager);

        data.addAll(recentSearching_ce_data_manager.getData());
        onDataLoadListener.onDataLoadSuccess(data);
    }

    public void turnOnNightMode () {
        AppThemeManager.isOnNightMode = true;
    }

    public void turnOffNightMode () {
        AppThemeManager.isOnNightMode = false;
    }

    public void setThemeDefaut() {
        AppThemeManager.setTheme(AppThemeManager.NORMAL_THEME);
        onThemeChangeListener.onThemeChange();
    }

    public void requirePermission(int requestCode) {
        requireOverlayPermissionManager.requirePermission(requestCode);
    }

    public void setOnDataLoadListener(DataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
    }

    public void setOnThemeChangeListener (OnThemeChangeListener theme) {
        this.onThemeChangeListener = theme;
    }

    public void setOnUpdateStatusCheckedListener (OnUpdateCheckedListener onUpdateChecked) {
        this.onUpdateChecked = onUpdateChecked;
    }

    /** Update recent searching chemical equation list into database **/
    public void pushCachingDataToDB () {
        recentSearching_ce_data_manager.updateDB();
    }

    /** Bring this Chemical Equation to top of recent learning lesson list in realm database  **/
    public void bringToTop(RO_ChemicalEquation ro_ce) {
        recentSearching_ce_data_manager.bringToTop(ro_ce);
    }

    public void checkUpdateStatus () {
        onlineDB_manager.getUpdateStatus();
    }

    public void getUpdateVersion () {
        onlineDB_manager.getVersionUpdate();
    }


    //Listener called by "onlineDB_manager.update_****()" function above to getData from Online Database
    @Override
    public void onChapterLoadedFromFirebase(Chapter chapter) {
        Log.e("NAME " , chapter.getName());
        offlineDB_manager.addOrUpdateDataOf(RO_Chapter.class, ROConverter.toRO_Chapter(chapter));
    }

    @Override
    public void onCE_LoadedFromFirebase(ArrayList<ChemicalEquation> equations) {
        offlineDB_manager.addOrUpdateDataOf(RO_ChemicalEquation.class, ROConverter.toRO_CEs(equations));
    }

    @Override
    public void onDPDP_LoadedFromFirebase(DPDP dpdp) {
        offlineDB_manager.addOrUpdateDataOf(RO_DPDP.class, ROConverter.toRO_DPDP(dpdp));
    }

    @Override
    public void onChemElement_LoadedFromFirebase(Chemical_Element element) {
        offlineDB_manager.addOrUpdateDataOf(RO_Chemical_Element.class, ROConverter.toRO_Chemical_Element(element));
    }

    @Override
    public void onStatusLoaded(boolean isAvailable) {
        onUpdateChecked.onStatusChecked(isAvailable);
    }

    @Override
    public void onVersionLoaded(long version) {
        onUpdateChecked.onVersionChecked(version);
    }

    /**
     * @see MainActivity
     * which implement all of this listeners to get Data
     */

    public interface DataLoadListener {

        void onDataLoadSuccess(ArrayList<RO_ChemicalEquation> ro_chemicalEquations);

    }

    public interface OnUpdateCheckedListener {

        void onStatusChecked (boolean isAvailable);

        void onVersionChecked (long version);

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

