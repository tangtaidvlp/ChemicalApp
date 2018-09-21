package phamf.com.chemicalapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import phamf.com.chemicalapp.Abstraction.Interface.OnThemeChangeListener;
import phamf.com.chemicalapp.Adapter.Search_List_Adapter;
import phamf.com.chemicalapp.CustomAnimation.FadedInAnim;
import phamf.com.chemicalapp.CustomAnimation.FadedOutAnim;
import phamf.com.chemicalapp.Manager.AppThemeManager;
import phamf.com.chemicalapp.Manager.FloatingSearchViewManager;
import phamf.com.chemicalapp.Presenter.MainActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.Service.FloatingSearchIconService;
import phamf.com.chemicalapp.Manager.FontManager;
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor;
import phamf.com.chemicalapp.Manager.FullScreenManager;


/**
 * @see MainActivityPresenter
 */
public class MainActivity extends FullScreenActivity implements MainActivityPresenter.DataLoadListener, OnThemeChangeListener, MainActivityPresenter.OnUpdateCheckedListener {


    static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;


    @BindView(R.id.txt_lesson) TextView txt_lesson;

    @BindView(R.id.txt_recent_lesson) TextView txt_recent_lesson;

    @BindView(R.id.txt_dongphan_danhphap) TextView txt_dongphan_danhphap;

    @BindView(R.id.txt_bangtuanhoang) TextView txt_bangtuanhoang;

    @BindView(R.id.txt_quick_search) TextView txt_quick_search;

    @BindView(R.id.btn_bangtuanhoan) ImageButton btn_bangtuanhoang;

    @BindView(R.id.btn_dongphan_danhphap) ImageButton btn_dongphan_danhphap;

    @BindView(R.id.btn_lesson) ImageButton btn_lesson;

    @BindView(R.id.btn_search) Button btn_search;

    @BindView(R.id.btn_recent_lesson) ImageButton btn_recent_lesson;

    @BindView(R.id.btn_setting) Button btn_setting;

    @BindView(R.id.edt_search) VirtualKeyBoardSensor edt_search;

    @BindView(R.id.btn_turnOff_search) Button btn_turnOff_search;

    @BindView(R.id.btn_quick_search) ImageButton btn_quick_search;

    @BindView(R.id.drawer_layout) DrawerLayout drawer_Layout;

    @BindView(R.id.nav_view) NavigationView nav_view;

    @BindView(R.id.bg_escape_search_mode) TextView bg_escape_search_mode;

    @BindView(R.id.rcv_search) RecyclerView rcv_search;

    private Search_List_Adapter rcv_search_adapter;



    // Navigation View

    @BindView(R.id.btn_widget_color_1) Button btn_widget_color_1;

    @BindView(R.id.btn_widget_color_2) Button btn_widget_color_2;

    @BindView(R.id.btn_widget_color_3) Button btn_widget_color_3;

    @BindView(R.id.btn_widget_color_4) Button btn_widget_color_4;

    @BindView(R.id.btn_widget_color_5) Button btn_widget_color_5;

    @BindView(R.id.btn_widget_color_6) Button btn_widget_color_6;

    @BindView(R.id.btn_background_color_1) Button btn_background_color_1;

    @BindView(R.id.btn_background_color_2) Button btn_background_color_2;

    @BindView(R.id.btn_background_color_3) Button btn_background_color_3;

    @BindView(R.id.btn_background_color_4) Button btn_background_color_4;

    @BindView(R.id.btn_background_color_5) Button btn_background_color_5;

    @BindView(R.id.btn_background_color_6) Button btn_background_color_6;

    @BindView(R.id.sw_night_mode) Switch sw_night_mode;

    @BindView(R.id.bg_night_mode) TextView bg_night_mode;

    @BindView(R.id.btn_set_as_defaut) Button btn_set_as_defaut;

    @BindView(R.id.btn_update) Button btn_update;

    @BindView(R.id.txt_update_status) TextView txt_update_status;

    @BindView(R.id.txt_update_version) TextView txt_update_version;




    /**
     * This variable is for optimization, avoid calling hideNavigationAndStatusBar many times
     * though app has been moded to full screen
     */
    private boolean hasHiddenNavAndStatusBar;


    private boolean isOnSearchMode;


    private InputMethodManager virtualKeyboardManager;


    private SearchModeManager searchModeManager;


    private FloatingSearchViewManager floatingSearchViewManager;


    private MainActivityPresenter activityPresenter;


    private boolean updateAvailable;


    @SuppressLint("HandlerLeak")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Needing Optimize **/
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        ButterKnife.bind(this);

        Realm.init(this);

        activityPresenter = new MainActivityPresenter(this);

        activityPresenter.setOnThemeChangeListener(this);

        activityPresenter.setOnDataLoadListener(this);

        activityPresenter.setOnUpdateStatusCheckedListener(this);

        activityPresenter.loadTheme();

        createNecessaryInfo();

        addControl();

        activityPresenter.loadData();

        addEvent();

        activityPresenter.requirePermission(CODE_DRAW_OVER_OTHER_APP_PERMISSION);

        activityPresenter.checkUpdateStatus();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            if (resultCode == RESULT_OK) {

                Toast.makeText(this, "You're now able to use floating search view", Toast.LENGTH_SHORT).show();

            } else {

                finish();

            }
        } else {

            super.onActivityResult(requestCode, resultCode, data);

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        activityPresenter.pushCachingDataToDB();
        activityPresenter.saveTheme();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void addControl () {
        rcv_search_adapter = new Search_List_Adapter(this);
        rcv_search_adapter.adaptFor(rcv_search);
        rcv_search_adapter.observe(edt_search);
        setEdt_SearchAdvanceFunctions();

        // isOnNightMode was set by Presenter in loadtheme()
        sw_night_mode.setChecked(AppThemeManager.isOnNightMode);
        if (AppThemeManager.isOnNightMode) {
            bg_night_mode.setVisibility(View.VISIBLE);
        }

        btn_set_as_defaut.setOnClickListener( v -> {});
    }


    @OnClick({R.id.btn_background_color_1,
             R.id.btn_background_color_2,
             R.id.btn_background_color_3,
             R.id.btn_background_color_4,
             R.id.btn_background_color_5,
             R.id.btn_background_color_6})
    public void onBackgroundColorButtonsClick (Button v) {
            int color = 0;
            AppThemeManager.isCustomingTheme = true;
            switch (v.getId()) {
                case R.id.btn_background_color_1 : {
                    color = 0;
                    break;
                }
                case R.id.btn_background_color_2 : {
                    color = 1;
                    break;
                }
                case R.id.btn_background_color_3 : {
                    color = 2;
                    break;
                }
                case R.id.btn_background_color_4 : {
                    color = 3;
                    break;
                }
                case R.id.btn_background_color_5 : {
                    color = 4;
                    break;
                }
                case R.id.btn_background_color_6 : {
                    color = 5;
                    break;
                }
            }
            AppThemeManager.backgroundColor = color;
            setTheme();
    }
    @OnClick({R.id.btn_widget_color_1,
            R.id.btn_widget_color_2,
            R.id.btn_widget_color_3,
            R.id.btn_widget_color_4,
            R.id.btn_widget_color_5,
            R.id.btn_widget_color_6})
    public void onWidgetsColorButtonClick (Button v) {
        int color = 0;
        AppThemeManager.isCustomingTheme = true;
        switch (v.getId()) {
            case R.id.btn_widget_color_1 : {
                color = 0;
                break;
            }
            case R.id.btn_widget_color_2 : {
                color = 1;
                break;
            }
            case R.id.btn_widget_color_3 : {
                color = 2;
                break;
            }
            case R.id.btn_widget_color_4 : {
                color = 3;
                break;
            }
            case R.id.btn_widget_color_5 : {
                color = 4;
                break;
            }
            case R.id.btn_widget_color_6 : {
                color = 5;
                break;
            }
        }
        AppThemeManager.textColor = color;
        setTheme();
    }


    @OnClick({R.id.btn_art_theme, R.id.btn_dark_theme, R.id.btn_normal_theme})
    public void onThemeButtonsClick (Button v) {
        int theme = 0;
        switch (v.getId()) {
            case R.id.btn_art_theme : {
                theme = 1;
                break;
            }

            case R.id.btn_dark_theme: {
                theme = 2;
                break;
            }

            case R.id.btn_normal_theme: {
                theme = 0;
                break;
            }
        }
        AppThemeManager.setTheme(theme);
        setTheme();
    }


    private void addEvent () {

        btn_search.setOnClickListener(v -> {
            if (!isOnSearchMode) {
                showSoftKeyboard();
                rcv_search_adapter.isSearching(true);
                searchModeManager.turn_ON();
                isOnSearchMode = true;
            }
        });

        bg_escape_search_mode.setOnClickListener(v -> {
            searchModeManager.turn_OFF();
            edt_search.setText("");
            rcv_search_adapter.isSearching(false);
            hideSoftKeyboard(MainActivity.this);
            isOnSearchMode = false;
        });

        btn_recent_lesson.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RecentLessonsActivity.class)));


        btn_bangtuanhoang.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, BangTuanHoangActivity.class)));


        btn_turnOff_search.setOnClickListener(v -> {
            searchModeManager.turn_OFF();
            edt_search.setText("");
            rcv_search_adapter.isSearching(false);
        });


        btn_quick_search.setOnClickListener(v -> floatingSearchViewManager.init());


        btn_lesson.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LessonMenuActivity.class)));


        btn_dongphan_danhphap.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DPDPMenuActivity.class)));


        btn_setting.setOnClickListener(v -> drawer_Layout.openDrawer(nav_view, true));


        txt_lesson.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LessonMenuActivity.class)));


        rcv_search_adapter.setOnItemClickListener((view, equation, position) -> {
            // Do this to bring the just chosen equation to top of the list
            rcv_search_adapter.getList().remove(equation);
            rcv_search_adapter.getList().add(0, equation);
            rcv_search_adapter.notifyDataSetChanged();
            // Update the top in database
            activityPresenter.bringToTop(equation);
            //
            bg_escape_search_mode.performClick();
            Intent intent = new Intent(MainActivity.this, ChemicalEquationActivity.class);
            intent.putExtra(ChemicalEquationActivity.CHEMICAL_EQUATION, equation);
            startActivity(intent);

        });


        btn_set_as_defaut.setOnClickListener(v -> activityPresenter.setThemeDefaut());


        btn_update.setOnClickListener (v -> {
            activityPresenter.update_CE_OFFDB();
            activityPresenter.update_Chapter_OFFDB();
        });


        sw_night_mode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bg_night_mode.setVisibility(View.VISIBLE);
                activityPresenter.turnOnNightMode();
            } else {
                bg_night_mode.setVisibility(View.GONE);
                activityPresenter.turnOffNightMode();
            }
        });


    }


    private void createNecessaryInfo() {
        FontManager.createFont(getAssets());

        setFont();

        virtualKeyboardManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        searchModeManager = new SearchModeManager(this, btn_turnOff_search, btn_bangtuanhoang);
        searchModeManager.setViewsOfSearchMode(2, rcv_search, bg_escape_search_mode, btn_turnOff_search);
        searchModeManager.setStartButtonsOfSearchMode(btn_search);
        searchModeManager.setDuration(500);

        floatingSearchViewManager = new FloatingSearchViewManager(MainActivity.this);
    }


    private void setFont () {
        txt_lesson.setTypeface(FontManager.myriad_pro_bold);
        txt_recent_lesson.setTypeface(FontManager.myriad_pro_bold);
        txt_dongphan_danhphap.setTypeface(FontManager.myriad_pro_bold);
        txt_bangtuanhoang.setTypeface(FontManager.myriad_pro_bold);
        txt_quick_search.setTypeface(FontManager.myriad_pro_bold);

        edt_search.setTypeface(FontManager.myriad_pro_bold);
    }


    public void setTheme() {
        if (AppThemeManager.isUsingAvailableThemes || AppThemeManager.isCustomingTheme) {
            txt_lesson.setTextColor(getColor(AppThemeManager.getTextColor()));
            txt_quick_search.setTextColor(getColor(AppThemeManager.getTextColor()));
            txt_bangtuanhoang.setTextColor(getColor(AppThemeManager.getTextColor()));
            txt_dongphan_danhphap.setTextColor(getColor(AppThemeManager.getTextColor()));
            txt_recent_lesson.setTextColor(getColor(AppThemeManager.getTextColor()));

            if (AppThemeManager.isUsingColorBackground) {
                drawer_Layout.setBackgroundColor(getColor(AppThemeManager.getBackgroundColor()));
            } else {
                drawer_Layout.setBackground(AppThemeManager.getBackgroundDrawable());
            }
        }
    }


    private void setEdt_SearchAdvanceFunctions () {

        edt_search.setOnClickListener(v -> {
            if (!hasHiddenNavAndStatusBar)
            {
//                    fullScreenManager.hideNavAndStatusBar_After(1000);
                makeFullScreenAfter(1000);
                hasHiddenNavAndStatusBar = true;
            }
            if (!isOnSearchMode) {
                searchModeManager.turn_ON();
                rcv_search_adapter.isSearching(true);
                isOnSearchMode = true;
            }
        });


        edt_search.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasHiddenNavAndStatusBar)
            {
//                    fullScreenManager.hideNavAndStatusBar_After(1000);
                makeFullScreenAfter(1000);
                hasHiddenNavAndStatusBar = true;
            }
        });

        edt_search.setOnHideVirtualKeyboardListener(
                () -> {
//                        fullScreenManager.hideNavAndStatusBar();
                    makeFullScreen();
                    hasHiddenNavAndStatusBar = false;
                });

    }


    public void hideSoftKeyboard(Activity activity) {
        virtualKeyboardManager.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
    }


    public void showSoftKeyboard () {
        virtualKeyboardManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }


    @Override
    public void onDataLoadSuccess(ArrayList<RO_ChemicalEquation> ro_chemicalEquations) {
        rcv_search_adapter.setData(ro_chemicalEquations);
    }

    @Override
    public void onThemeChange() {
        setTheme();
    }

    @Override
    public void onStatusChecked(boolean isAvailable) {
        this.updateAvailable = isAvailable;
        activityPresenter.getUpdateVersion();
    }

    @Override
    public void onVersionChecked(long version) {
        if (updateAvailable) {
            if (activityPresenter.getDataVersion() == version) {
                txt_update_status.setText(R.string.newest_version);
            } else if (version - activityPresenter.getDataVersion() == 1) {
                txt_update_status.setText(R.string.Available_For_Update);
                btn_update.setVisibility(View.VISIBLE);
            } else if (version - activityPresenter.getDataVersion() > 1) {
                txt_update_status.setText(R.string.Available_For_Update);
                btn_update.setVisibility(View.VISIBLE);
            }
        } else {
            txt_update_status.setText(R.string.newest_version);
        }

        txt_update_version.setText("1." + version);
    }


}


class SearchModeManager {

    private FadedInAnim showSearchButtonAnim, showSearchViewAnim;

    private FadedOutAnim hideSearchButtonAnim, hideSearchViewAnim;

    AnimationSet setOn, setOff;

    View startAnimview1, startAnimView2;

    ArrayList<View> searchView = new ArrayList<>();
    ArrayList<View> buttonView = new ArrayList<>();

    // Two of this params startAnimView1, startAnimView2 is using to start Animation
    // because animation just can be started by view on screen
    SearchModeManager (Context context, View startAnimView1, View startAnimView2) {
        setOn = new AnimationSet(context, null);
        setOff = new AnimationSet(context, null);
        this.startAnimview1 = startAnimView1;
        this.startAnimView2 = startAnimView2;
    }

    // the position of turn off button index in seaching_views params
    public void setViewsOfSearchMode (final int turnOffButtonIndex, final View... searching_views) {

        searchView.addAll(Arrays.asList(searching_views));
        showSearchViewAnim = new FadedInAnim(searching_views);
        hideSearchViewAnim = new FadedOutAnim(searching_views);
        hideSearchViewAnim.setOnAnimEndListener(new FadedOutAnim.FadedOutAnimListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onEnd() {
                searchView.get(turnOffButtonIndex).setVisibility(View.GONE);
            }
        });

    }


    public void setViewsOfSearchMode (final View... searching_views) {
        searchView.addAll(Arrays.asList(searching_views));
        showSearchViewAnim = new FadedInAnim(searching_views);
        hideSearchViewAnim = new FadedOutAnim(searching_views);
    }


    public void setStartButtonsOfSearchMode (View... button_views) {
        buttonView.addAll(Arrays.asList(button_views));
        showSearchButtonAnim = new FadedInAnim(button_views);
        hideSearchButtonAnim = new FadedOutAnim(button_views);
    }


    public void setDuration (int duration) {
        showSearchButtonAnim.setDuration(duration);
        showSearchViewAnim.setDuration(duration);
        hideSearchButtonAnim.setDuration(duration);
        hideSearchViewAnim.setDuration(duration);
    }


    public void turn_ON () {
        for (View view : searchView) {
            view.setVisibility(View.VISIBLE);
        }
        startAnimView2.startAnimation(showSearchViewAnim);
        startAnimview1.startAnimation(hideSearchButtonAnim);
    }


    public void turn_OFF () {
        for (View view : buttonView) {
            view.setVisibility(View.VISIBLE);
        }
        startAnimView2.startAnimation(showSearchButtonAnim);
        startAnimview1.startAnimation(hideSearchViewAnim);
    }

}







