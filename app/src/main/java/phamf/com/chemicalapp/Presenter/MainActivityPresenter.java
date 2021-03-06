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

import io.realm.RealmList;
import io.realm.RealmResults;
import phamf.com.chemicalapp.Abstraction.Interface.IMainActivity;
import phamf.com.chemicalapp.Abstraction.Interface.OnThemeChangeListener;
import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.Database.UpdateDatabaseManager;
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
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.RO_Model.Recent_SearchingCEs;
import phamf.com.chemicalapp.Supporter.ROConverter;

import static android.content.Context.MODE_PRIVATE;

/**
 * @see MainActivity
 */
public class MainActivityPresenter extends Presenter<MainActivity> implements IMainActivity.Presenter{


    private UpdateDatabaseManager updateDB_Manager;

    private OfflineDatabaseManager offlineDB_manager;

    private RequireOverlayPermissionManager requireOverlayPermissionManager;

    private MainActivityPresenter.DataLoadListener onDataLoadListener;

    private OnThemeChangeListener onThemeChangeListener;

    private RecentSearching_CE_Data_Manager recentSearching_ce_data_manager;

    private OnUpdateCheckedListener onUpdateChecked;

    public static final String APP_INFO = "app_info";

    public static final String DATABASE_VERSION = "db_vers";

    public static final String HAS_DEFAULT_VALUE = "has_def_val";


    public MainActivityPresenter (@NonNull MainActivity view) {
        super(view);

        this.view = view;

        this.context = view.getBaseContext();

        updateDB_Manager = new UpdateDatabaseManager(view, getDataVersion());

        offlineDB_manager = new OfflineDatabaseManager(context);

        requireOverlayPermissionManager = new RequireOverlayPermissionManager(view);
    }

    public void update (OnUpdateSuccess onUpdateSuccess) {
        updateDB_Manager.setOnASectionUpdated((version, isLastVersion) -> {
            if (isLastVersion) {
                onUpdateSuccess.onUpdateSuccess();
            } else {
                saveDataVersion(version);
            }
        });

        updateDB_Manager.update();

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

    public void saveDataVersion (long version) {
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

    public void addDefaultDataOnceTime() {
        SharedPreferences app_info = context.getSharedPreferences(APP_INFO, MODE_PRIVATE);
        boolean has_default_value = app_info.getBoolean(HAS_DEFAULT_VALUE, false);
        if (!has_default_value) {
            try {
                // This data just for demo
                // Lessons
                RO_Lesson ro_lesson = new RO_Lesson();
                ro_lesson.setId(0);
                ro_lesson.setContent("<<b_title>><<boldTxt>>A. Tính chất hóa học<co_divi><<s_title>><<boldTxt>>1) Tác dụng với Bazo<co_divi><<content>><<boldTxt>>Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2. Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2.<co_divi><<picture<>Lessons/lesson1/image0.png<>400<>100<><co_divi><<content>><<boldTxt>>Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2. Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ...");
                ro_lesson.setName("Bai 1");

                RO_Lesson ro_lesson1 = new RO_Lesson();
                ro_lesson1.setId(1);
                ro_lesson1.setContent("<<b_title>><<boldTxt>>A. Tính chất hóa học<co_divi><<s_title>><<boldTxt>>1) Tác dụng với Bazo<co_divi><<content>><<boldTxt>>Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2. Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2.<co_divi><<picture<>Lessons/lesson1/image0.png<>400<>100<><co_divi><<content>><<boldTxt>>Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2. Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ...");
                ro_lesson1.setName("Bai 2");

                RO_Lesson ro_lesson2 = new RO_Lesson();
                ro_lesson2.setId(2);
                ro_lesson2.setContent("<<b_title>><<boldTxt>>A. Tính chất hóa học<co_divi><<s_title>><<boldTxt>>1) Tác dụng với Bazo<co_divi><<content>><<boldTxt>>Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2. Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2.<co_divi><<picture<>Lessons/lesson1/image0.png<>400<>100<><co_divi><<content>><<boldTxt>>Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ... Chất a tạo kết tủa trắng       đồng thời giải phóng 1 lượng lớn CO2. Khi tác dụng với các Bazo mạnh như NaOH, BaOH, ...");
                ro_lesson2.setName("Bai 3");

                ArrayList<RO_Lesson> ro_lessons = new ArrayList<>();
                ro_lessons.add(ro_lesson);
                ro_lessons.add(ro_lesson1);

                ArrayList<RO_Lesson> ro_lessons1 = new ArrayList<>();
                ro_lessons1.add(ro_lesson2);

                //Chapters
                RO_Chapter ro_chapter = new RO_Chapter();
                ro_chapter.setId(0);
                ro_chapter.setName("Chuong 1");
                ro_chapter.setLessons(ro_lessons);

                RO_Chapter ro_chapter1 = new RO_Chapter();
                ro_chapter1.setId(1);
                ro_chapter1.setName("Chuong 2");
                ro_chapter1.setLessons(ro_lessons1);

                offlineDB_manager.addOrUpdateDataOf(RO_Chapter.class, ro_chapter, ro_chapter1);

                SharedPreferences.Editor editor = app_info.edit();
                editor.putBoolean(HAS_DEFAULT_VALUE, true);
                editor.apply();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void loadData () {
        if (onDataLoadListener == null) return;

        if (offlineDB_manager.readOneOf(Recent_SearchingCEs.class) == null) {
            Recent_SearchingCEs recent_searchingCEs = new Recent_SearchingCEs();
            offlineDB_manager.addOrUpdateDataOf(Recent_SearchingCEs.class, recent_searchingCEs);
        }

        ArrayList <RO_ChemicalEquation> data = new ArrayList<>();

        recentSearching_ce_data_manager = new RecentSearching_CE_Data_Manager(offlineDB_manager);
        recentSearching_ce_data_manager.setOnGetDataSuccess(recent_Ces ->
        {
            data.addAll(recent_Ces);
            onDataLoadListener.onDataLoadSuccess(data);
        });

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

    @Override
    public void checkUpdateStatus() {
        updateDB_Manager.getLastestVersionUpdate(version -> {
            // if firebase database version is bigger than app version, there's at least
            // one update version available
            Log.e("App version", getDataVersion() + "");
            onUpdateChecked.onStatusChecked(version > getDataVersion(), version);
        });
    }


    /**
     * @see MainActivity
     * which implement this listeners to get Data
     */

    public interface DataLoadListener {

        void onDataLoadSuccess(ArrayList<RO_ChemicalEquation> ro_chemicalEquations);

    }

    public interface OnUpdateCheckedListener {

        void onStatusChecked (boolean isAvailable, long lasted_version);

    }

    public interface OnUpdateSuccess {

        void onUpdateSuccess ();

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

