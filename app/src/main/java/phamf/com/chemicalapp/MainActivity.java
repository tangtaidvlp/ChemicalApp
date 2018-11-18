package phamf.com.chemicalapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import phamf.com.chemicalapp.Abstraction.Interface.IMainActivity;
import phamf.com.chemicalapp.Abstraction.Interface.OnThemeChangeListener;
import phamf.com.chemicalapp.Adapter.Search_CE_RCV_Adapter;
import phamf.com.chemicalapp.CustomAnimation.FadedInAnim;
import phamf.com.chemicalapp.CustomAnimation.FadedOutAnim;
import phamf.com.chemicalapp.CustomView.ViewPagerIndicator;
import phamf.com.chemicalapp.Database.UpdateDatabaseManager;
import phamf.com.chemicalapp.Manager.AppThemeManager;
import phamf.com.chemicalapp.Manager.FloatingSearchViewManager;
import phamf.com.chemicalapp.Presenter.MainActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.Service.FloatingSearchIconService;
import phamf.com.chemicalapp.Manager.FontManager;
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor;
import phamf.com.chemicalapp.Manager.FullScreenManager;


/**
 * Presenter
 * @see MainActivityPresenter
 */
public class MainActivity extends FullScreenActivity implements IMainActivity.View, MainActivityPresenter.DataLoadListener, OnThemeChangeListener, MainActivityPresenter.OnUpdateCheckedListener {

    static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    @BindView(R.id.txt_lesson) TextView txt_lesson;

    @BindView(R.id.txt_recent_lesson) TextView txt_recent_lesson;

    @BindView(R.id.txt_dongphan_danhphap) TextView txt_dongphan_danhphap;

    @BindView(R.id.txt_bangtuanhoang) TextView txt_bangtuanhoang;

    @BindView(R.id.txt_quick_search) TextView txt_quick_search;

    @BindView(R.id.bg_escape_search_mode) TextView bg_escape_search_mode;

    @BindView(R.id.btn_bangtuanhoan) ImageButton btn_bangtuanhoang;

    @BindView(R.id.btn_dongphan_danhphap) ImageButton btn_dongphan_danhphap;

    @BindView(R.id.btn_lesson) ImageButton btn_lesson;

    @BindView(R.id.btn_recent_lesson) ImageButton btn_recent_lesson;

    @BindView(R.id.btn_quick_search) ImageButton btn_quick_search;

    @BindView(R.id.btn_search) Button btn_search;

    @BindView(R.id.btn_setting) Button btn_setting;

    @BindView(R.id.btn_turnOff_search) Button btn_turnOff_search;

    @BindView(R.id.edt_search) VirtualKeyBoardSensor edt_search;

    @BindView(R.id.main_activity_drawer_layout) DrawerLayout drawer_Layout;

    @BindView(R.id.nav_view) NavigationView nav_view;

    @BindView(R.id.rcv_search) RecyclerView rcv_search;

    private Search_CE_RCV_Adapter rcv_search_adapter;



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

    @BindView(R.id.btn_update) Button btn_update;

    @BindView(R.id.btn_set_as_defaut) Button btn_set_as_defaut;

    @BindView(R.id.btn_top_turn_off_search) Button btn_top_turn_off_search_mode;

    @BindView(R.id.search_chem_equation_view_parent) RelativeLayout search_view_parent;

    @BindView(R.id.bg_night_mode) TextView bg_night_mode;

    @BindView(R.id.txt_update_status) TextView txt_update_status;

    @BindView(R.id.txt_update_version) TextView txt_update_version;

    @BindView(R.id.sw_night_mode) Switch sw_night_mode;


    /**
     * This variable is for optimization, avoid calling hideNavigationAndStatusBar many times
     * though app has been moded to full screen
     */
    private boolean hasHiddenNavAndStatusBar;

    private boolean isOnSearchMode;

    private InputMethodManager virtualKeyboardManager;

    private FloatingSearchViewManager floatingSearchViewManager;

    private MainActivityPresenter activityPresenter;

    Animation fade_out, fade_in;

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

        /**Mark to ez to see**/
        loadAnim();

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
    protected void onResume() {
        super.onResume();
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
        rcv_search_adapter = new Search_CE_RCV_Adapter(this);
        rcv_search_adapter.adaptFor(rcv_search);
        rcv_search_adapter.observe(edt_search);
        setEdt_SearchAdvanceFunctions();

        // isOnNightMode was set by Presenter in loadtheme()
        sw_night_mode.setChecked(AppThemeManager.isOnNightMode);
        if (AppThemeManager.isOnNightMode) {
            bg_night_mode.setVisibility(View.VISIBLE);
        }

    }


    @OnClick({R.id.btn_background_color_1,
             R.id.btn_background_color_2,
             R.id.btn_background_color_3,
             R.id.btn_background_color_4,
             R.id.btn_background_color_5,
             R.id.btn_background_color_6})
    public void onBackgroundColorButtonsClick (Button v) {
            int color = 0;
            if (!AppThemeManager.isCustomingTheme) AppThemeManager.isCustomingTheme = true;
            if (!AppThemeManager.isUsingColorBackground) AppThemeManager.isUsingColorBackground= true;
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

    public void createNecessaryInfo() {
        FontManager.createFont(getAssets());

        setFont();

        virtualKeyboardManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        floatingSearchViewManager = new FloatingSearchViewManager(MainActivity.this);
    }

    public void addEvent () {

        btn_search.setOnClickListener(v -> {
            if (!isOnSearchMode) {
                showSoftKeyboard();
                rcv_search_adapter.isSearching(true);
                search_view_parent.startAnimation(fade_in);
                isOnSearchMode = true;
            }
        });

        bg_escape_search_mode.setOnClickListener(v -> {
            search_view_parent.startAnimation(fade_out);
            edt_search.setText("");
            rcv_search_adapter.isSearching(false);
            hideSoftKeyboard(MainActivity.this);
            makeFullScreen();
            isOnSearchMode = false;
        });

        btn_top_turn_off_search_mode.setOnClickListener(v -> bg_escape_search_mode.performClick());

        btn_recent_lesson.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RecentLessonsActivity.class)));

        btn_bangtuanhoang.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, BangTuanHoangActivity.class)));

        btn_quick_search.setOnClickListener(v -> {
            floatingSearchViewManager.init();
            finish();
        });

        btn_turnOff_search.setOnClickListener(v -> {
            search_view_parent.startAnimation(fade_out);
            edt_search.setText("");
            rcv_search_adapter.isSearching(false);
        });


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

            bg_escape_search_mode.performClick();
            Intent intent = new Intent(MainActivity.this, ChemicalEquationActivity.class);
            intent.putExtra(ChemicalEquationActivity.CHEMICAL_EQUATION, equation);
            startActivity(intent);
        });


        btn_set_as_defaut.setOnClickListener(v -> activityPresenter.setThemeDefaut());


        btn_update.setOnClickListener (v -> {
            activityPresenter.update(() -> {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            });
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

    public void loadAnim () {
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fade_out.setFillAfter(false);
        fade_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                search_view_parent.setVisibility(View.VISIBLE);
            }

            public void onAnimationEnd(Animation animation) {

            }
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fade_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                search_view_parent.setVisibility(View.GONE);
            }

            public void onAnimationStart(Animation animation) {

            }
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void setFont () {
        txt_lesson.setTypeface(FontManager.myriad_pro_bold);
        txt_recent_lesson.setTypeface(FontManager.myriad_pro_bold);
        txt_dongphan_danhphap.setTypeface(FontManager.myriad_pro_bold);
        txt_bangtuanhoang.setTypeface(FontManager.myriad_pro_bold);
        txt_quick_search.setTypeface(FontManager.myriad_pro_bold);

        edt_search.setTypeface(FontManager.myriad_pro_bold);
    }

    public void setTheme() {
        if ( AppThemeManager.isCustomingTheme | AppThemeManager.isUsingAvailableThemes) {
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

    public void setEdt_SearchAdvanceFunctions () {

        edt_search.setOnClickListener(v -> {
            if (!hasHiddenNavAndStatusBar)
            {
//                    fullScreenManager.hideNavAndStatusBar_After(1000);
                makeFullScreenAfter(1000);
                hasHiddenNavAndStatusBar = true;
            }
            if (!isOnSearchMode) {
                search_view_parent.startAnimation(fade_in);
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
    public void onStatusChecked(boolean isAvailable, long version) {
        if (isAvailable) {
            txt_update_status.setText("Available");
            txt_update_version.setText("1." + version);
            txt_update_version.setVisibility(View.VISIBLE);
            btn_update.setVisibility(View.VISIBLE);
            btn_update.setClickable(true);
        } else {
            txt_update_status.setText("up to date");
            txt_update_version.setVisibility(View.GONE);
            btn_update.setVisibility(View.GONE);
            btn_update.setClickable(false);
        }
    }



}

