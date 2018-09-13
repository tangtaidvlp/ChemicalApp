package phamf.com.chemicalapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import phamf.com.chemicalapp.Abstraction.OnThemeChangeListener;
import phamf.com.chemicalapp.Adapter.Search_List_Adapter;
import phamf.com.chemicalapp.CustomAnimation.FadedInAnim;
import phamf.com.chemicalapp.CustomAnimation.FadedOutAnim;
import phamf.com.chemicalapp.Manager.AppThemeManager;
import phamf.com.chemicalapp.Model.ChemicalEquation;
import phamf.com.chemicalapp.Presenter.MainActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element;
import phamf.com.chemicalapp.Service.FloatingSearchIconService;
import phamf.com.chemicalapp.Manager.FontManager;
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor;
import phamf.com.chemicalapp.Manager.FullScreenManager;


/**
 * @see MainActivityPresenter
 */
public class MainActivity extends AppCompatActivity implements MainActivityPresenter.DataLoadListener, OnThemeChangeListener, MainActivityPresenter.OnUpdateCheckedListener {


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

    @BindView(R.id.txt_update_status) TextView txt_update_status;

    @BindView(R.id.txt_update_version) TextView txt_update_version;




    /**
     * This variable is for optimization, avoid calling hideNavigationAndStatusBar many times
     * though app has been moded to full screen
     */
    private boolean hasHiddenNavAndStatusBar;


    private InputMethodManager virtualKeyboardManager;


    private SearchModeManager searchModeManager;


    private FullScreenManager fullScreenManager;


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

        addNecessaryInfo();

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


    //Set up full screen mode for app when start
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {
          fullScreenManager.hideNavAndStatusBar();
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addControl () {
        rcv_search_adapter = new Search_List_Adapter(this);
        rcv_search_adapter.adaptFor(rcv_search);
        makeSearchEditTextFullscreenable();
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
        Log.e("color", color + "");
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

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchModeManager.turn_ON();
                showSoftKeyboard();
                rcv_search_adapter.isSearching(true);
            }
        });

        bg_escape_search_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchModeManager.turn_OFF();
                edt_search.setText("");
                rcv_search_adapter.isSearching(false);
                hideSoftKeyboard(MainActivity.this);
            }
        });

        btn_recent_lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecentLessonsActivity.class));
            }
        });


        btn_bangtuanhoang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BangTuanHoangActivity.class));
            }
        });


        btn_turnOff_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchModeManager.turn_OFF();
                edt_search.setText("");
                rcv_search_adapter.isSearching(false);
            }
        });


        btn_quick_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingSearchViewManager.init();
            }
        });


        btn_lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LessonMenuActivity.class));
            }
        });


        btn_dongphan_danhphap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DPDPMenuActivity.class));
            }
        });


        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_Layout.openDrawer(nav_view, true);
            }
        });


        edt_search.addLiteTextChangeListener(new VirtualKeyBoardSensor.OnTextChangeLite() {
            @Override
            public void onTextChange(CharSequence s, int start, int before, int count) {
                rcv_search_adapter.getFilter().filter(s);
            }
        });


        txt_lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LessonMenuActivity.class));
            }
        });


        rcv_search_adapter.setOnItemClickListener(new Search_List_Adapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(View view, RO_ChemicalEquation equation, int position) {
                rcv_search_adapter.getList().remove(equation);
                rcv_search_adapter.getList().add(0, equation);
                rcv_search_adapter.notifyDataSetChanged();
                activityPresenter.bringToTop(equation);
            }
        });

        btn_update.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityPresenter.update_CE_OFFDB();
                activityPresenter.update_Chapter_OFFDB();
//                activityPresenter.update_BangTuanHoan_OFFDB();
//                activityPresenter.update_DPDP_OFFDB();
            }
        });


    }


    private void addNecessaryInfo() {

        FontManager.createFont(getAssets());

        setFont();

        virtualKeyboardManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        searchModeManager = new SearchModeManager(this, btn_turnOff_search, btn_bangtuanhoang);
        searchModeManager.setViewsOfSearchMode(2, rcv_search, bg_escape_search_mode, btn_turnOff_search);
        searchModeManager.setStartButtonsOfSearchMode(btn_search);
        searchModeManager.setDuration(500);

        fullScreenManager = new FullScreenManager(MainActivity.this);
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


    public void hideSoftKeyboard(Activity activity) {
        virtualKeyboardManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void showSoftKeyboard () {
        virtualKeyboardManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    private void makeSearchEditTextFullscreenable () {

      edt_search.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
            Log.e("Click", "");
          if (!hasHiddenNavAndStatusBar)
          {
            fullScreenManager.hideNavAndStatusBar_After(1000);
            hasHiddenNavAndStatusBar = true;
            searchModeManager.turn_ON();
          }
        }
      });


      edt_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            Log.e("OnFocusChange", "");
          if (!hasHiddenNavAndStatusBar)
          {
              fullScreenManager.hideNavAndStatusBar_After(1000);
            hasHiddenNavAndStatusBar = true;
          }
        }
      });

      edt_search.setOnHideVirtualKeyboardListener(
          new VirtualKeyBoardSensor.OnHideVirtualKeyboardListener() {
            @Override public void onHide() {
              fullScreenManager.hideNavAndStatusBar();
              hasHiddenNavAndStatusBar = false;
            }
          });

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
                txt_update_status.setText("Newest version");
            } else if (version - activityPresenter.getDataVersion() == 1) {
                txt_update_status.setText("Available");
                btn_update.setVisibility(View.VISIBLE);
            } else if (version - activityPresenter.getDataVersion() > 1) {
                txt_update_status.setText("Available");
                btn_update.setVisibility(View.VISIBLE);
            }
        } else {
            txt_update_status.setText("Newest version");
        }

        txt_update_version.setText("1." + version);
    }





    // Turn ON/OFF Searching View, Hard to read, can skip
    private class SearchModeManager {

        private FadedInAnim showSearchButtonAnim, showSearchViewAnim;

        private FadedOutAnim hideSearchButtonAnim, hideSearchViewAnim;

        AnimationSet setOn, setOff;

        // Use this view to start Animation because animation just can be started by view on screen
        View startAnimview, startAnimView2;

        ArrayList<View> searchView = new ArrayList<>();
        ArrayList<View> buttonView = new ArrayList<>();


        SearchModeManager (Context context, View view, View view2) {
            setOn = new AnimationSet(context, null);
            setOff = new AnimationSet(context, null);
            startAnimview = view;
            startAnimView2 = view2;
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
            startAnimview.startAnimation(hideSearchButtonAnim);
        }


        public void turn_OFF () {
            for (View view : buttonView) {
                view.setVisibility(View.VISIBLE);
            }
            startAnimView2.startAnimation(showSearchButtonAnim);
            startAnimview.startAnimation(hideSearchViewAnim);
        }

    }




    // Create quick search view which floating on screen
    private class FloatingSearchViewManager {

        Activity activity;

        FloatingSearchViewManager (Activity activity) {
            this.activity = activity;
        }

        public void init () {
            activity.startService(new Intent(activity, FloatingSearchIconService.class));
        }
    }
}









