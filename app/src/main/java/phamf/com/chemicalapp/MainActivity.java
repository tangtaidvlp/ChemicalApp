package phamf.com.chemicalapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import phamf.com.chemicalapp.Adapter.Search_List_Adapter;
import phamf.com.chemicalapp.CustomAnimation.FadedInAnim;
import phamf.com.chemicalapp.CustomAnimation.FadedOutAnim;
import phamf.com.chemicalapp.Model.Chapter;
import phamf.com.chemicalapp.Model.ChemicalEquation;
import phamf.com.chemicalapp.Presenter.MainActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.Service.FloatingSearchIconService;
import phamf.com.chemicalapp.Supporter.FontManager;
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor;
import phamf.com.chemicalapp.Supporter.ROConverter;

public class MainActivity extends AppCompatActivity {

    static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    @BindView(R.id.txt_lesson) TextView txt_lesson;


    @BindView(R.id.txt_recent_lesson) TextView txt_recent_lesson;


    @BindView(R.id.txt_dongphan_danhphap) TextView txt_dongphan_danhphap;


    @BindView(R.id.txt_bangtuanhoang) TextView txt_bangtuanhoang;


    @BindView(R.id.txt_quick_search) TextView txt_quick_search;


    @BindView(R.id.btn_bangtuanhoan) ImageButton btn_bangtuanhoang;


    @BindView(R.id.btn_dongphan_danhphap) ImageButton btn_dongphan_danhphap;


    @BindView(R.id.btn_lesson) ImageButton btn_lesson;


    @BindView(R.id.btn_old_lesson) Button btn_old_lesson;


    @BindView(R.id.btn_search) Button btn_search;


    @BindView(R.id.btn_setting) Button btn_setting;


    @BindView(R.id.edt_search) VirtualKeyBoardSensor edt_search;


    @BindView(R.id.btn_back) Button btn_back;


    @BindView(R.id.btn_quick_search) ImageButton btn_quick_search;


    @BindView(R.id.rcv_search) RecyclerView rcv_search;

    Search_List_Adapter rcv_search_adapter;


    /**
     * This variable is for optimization, avoid calling hideNavigationAndStatusBar many times
     * though app has been moded to full screen
     */
    private boolean hasHiddenNavAndStatusBar;


    private InputMethodManager virtualKeyboardManager;


    private SearchModeManager searchModeManager;


    private FullScreenManager fullScreenManager;


    private RequireOverlayPermissionManager requireOverlayPermissionManager;


    private FloatingSearchViewManager floatingSearchViewManager;


    private MainActivityPresenter activityPresenter;


    @SuppressLint("HandlerLeak")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        activityPresenter = new MainActivityPresenter(this);

        Realm.init(this);

        addNecessaryInfo();

        addControl();

        loadData();

        addEvent();

        requireOverlayPermissionManager.requirePermission(CODE_DRAW_OVER_OTHER_APP_PERMISSION);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityPresenter.closeDB();
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addControl () {
        rcv_search_adapter = new Search_List_Adapter(this);
        rcv_search_adapter.adaptFor(rcv_search);
        makeSearchEditTextFullscreenable();
    }


    private void loadData () {
        rcv_search_adapter.setData(activityPresenter.getCEFromDB());
    }


    private void addEvent () {

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchModeManager.turn_ON();
                virtualKeyboardManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
                rcv_search_adapter.isSearching(true);
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchModeManager.turn_OFF();
                rcv_search_adapter.isSearching(false);
            }
        });


        btn_quick_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingSearchViewManager.init();
            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rcv_search_adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void addNecessaryInfo() {

        FontManager.createFont(getAssets());

        setFont();

        virtualKeyboardManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        searchModeManager = new SearchModeManager(this, findViewById(R.id.btn_back), findViewById(R.id.btn_bangtuanhoan));
        searchModeManager.setViewsOfSearchMode(1, rcv_search, btn_back);
        searchModeManager.setStartButtonsOfSearchMode(btn_search);
        searchModeManager.setDuration(500);

        fullScreenManager = new FullScreenManager(MainActivity.this);
        requireOverlayPermissionManager = new RequireOverlayPermissionManager(MainActivity.this);
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


    private void makeSearchEditTextFullscreenable () {

      edt_search.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (!hasHiddenNavAndStatusBar)
          {
            fullScreenManager.hideNavAndStatusBar_After(1000);
            hasHiddenNavAndStatusBar = true;
            searchModeManager.turn_ON();
          }
        }
      });

      edt_search.addLiteTextChangeListener(new VirtualKeyBoardSensor.OnTextChangeLite() {
          @Override
          public void onTextChange(CharSequence s, int start, int before, int count) {
              rcv_search_adapter.getFilter().filter(s);
          }
      });

      edt_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
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

}




// Turn ON/OFF Searching View
class SearchModeManager {

    private FadedInAnim showSearchButton, showSearchView;

    private FadedOutAnim hideSearchButton, hideSearchView;

    AnimationSet setOn, setOff;

    View startAnimview, startAnimView2;

    int backButtonIndex;

    ArrayList<View> searchView = new ArrayList<>();
    ArrayList<View> buttonView = new ArrayList<>();

    SearchModeManager (Context context, View view, View view2) {
        setOn = new AnimationSet(context, null);
        setOff = new AnimationSet(context, null);
        startAnimview = view;
        startAnimView2 = view2;
    }

    public void setViewsOfSearchMode (final int backButtonIndex, final View... searching_views) {

        searchView.addAll(Arrays.asList(searching_views));
        showSearchView = new FadedInAnim(searching_views);
        hideSearchView = new FadedOutAnim(searching_views);
        hideSearchView.setOnAnimEndListener(new FadedOutAnim.FadedOutAnimListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onEnd() {
                searchView.get(backButtonIndex).setVisibility(View.GONE);
            }
        });

    }


    public void setStartButtonsOfSearchMode (View... button_views) {
        buttonView.addAll(Arrays.asList(button_views));
        showSearchButton = new FadedInAnim(button_views);
        hideSearchButton = new FadedOutAnim(button_views);
    }


    public void setDuration (int duration) {
        showSearchButton.setDuration(duration);
        showSearchView.setDuration(duration);
        hideSearchButton.setDuration(duration);
        hideSearchView.setDuration(duration);
    }


    public void turn_ON () {
        for (View view : searchView) {
            view.setVisibility(View.VISIBLE);
        }
        startAnimView2.startAnimation(showSearchView);
        startAnimview.startAnimation(hideSearchButton);
    }


    public void turn_OFF () {
        for (View view : buttonView) {
            view.setVisibility(View.VISIBLE);
        }
        startAnimView2.startAnimation(showSearchButton);
        startAnimview.startAnimation(hideSearchView);
    }

}


// Hide virtual navigation bar and status bar
class FullScreenManager {

    private Activity activity;

    FullScreenManager (Activity activity) {
        this.activity = activity;
    }

    public void hideNavAndStatusBar() {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    public void hideNavAndStatusBar_After (final long timeMilis) {
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    Thread.sleep(timeMilis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideNavAndStatusBar();
                    }
                });
            }
        }).start();
    }
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


// Create quick search view which floating on screen
class FloatingSearchViewManager {

    Activity activity;

    FloatingSearchViewManager (Activity activity) {
        this.activity = activity;
    }

    public void init () {
        activity.startService(new Intent(activity, FloatingSearchIconService.class));
    }
}

