package phamf.com.chemicalapp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import phamf.com.chemicalapp.Abstraction.Interface.IDPDPActivity;
import phamf.com.chemicalapp.Abstraction.SpecialDataType.QuickChangeItemListViewAdapter;
import phamf.com.chemicalapp.Adapter.QuickChange_Organic_Adapter;
import phamf.com.chemicalapp.CustomView.LessonViewCreator;
import phamf.com.chemicalapp.Manager.AppThemeManager;
import phamf.com.chemicalapp.Presenter.DPDPActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element;
import phamf.com.chemicalapp.RO_Model.RO_OrganicMolecule;
import phamf.com.chemicalapp.Manager.FullScreenManager;

import static phamf.com.chemicalapp.Supporter.UnitConverter.DpToPixel;

/**
 * Presenter
 * @see phamf.com.chemicalapp.Presenter.DPDPActivityPresenter
 */

public class DPDPActivity extends FullScreenActivity implements IDPDPActivity.View, DPDPActivityPresenter.DataLoadListener {


    @BindView(R.id.btn_dpdp_back) Button btn_back;

    @BindView(R.id.btn_dpdp_turn_on_quick_change) Button btn_turn_on_quick_change;

    @BindView(R.id.btn_dpdp_home) Button btn_back_to_home;

    @BindView(R.id.txt_dpdp_title) TextView txt_title;

    @BindView(R.id.bg_night_mode_dpdp) TextView bg_night_mode;

    @BindView(R.id.lv_dpdp_fast_scroll) ListView lv_quick_change_organic;

    @BindView(R.id.dpdp_board) LinearLayout board;

    @BindView(R.id.parent_dpdp_activity) ConstraintLayout base_view;

    public QuickChange_Organic_Adapter qc_organic_adapter;

    public LessonViewCreator.ViewCreator viewCreator;

    private boolean isTurnOnQCI;

    Animation fade_out_then_in, left_to_right, right_to_left;

    private DPDPActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dpdp);
        ButterKnife.bind(this);
        presenter = new DPDPActivityPresenter(this);

        setUpViewCreator();

        addAnimation();

        setTheme();

        addControl();

        addEvent();

        presenter.setOnDataLoadListener (this);

        presenter.loadData();

    }

    public void addControl () {
        qc_organic_adapter = new QuickChange_Organic_Adapter(this);
        qc_organic_adapter.adaptFor(lv_quick_change_organic);
        lv_quick_change_organic.setVisibility(View.GONE);

        if (AppThemeManager.isOnNightMode) {
            bg_night_mode.setVisibility(View.VISIBLE);
        } else {
            bg_night_mode.setVisibility(View.INVISIBLE);
        } }

    public void addAnimation () {
        fade_out_then_in = AnimationUtils.loadAnimation(this, R.anim.fade_out_then_fade_in);
        left_to_right = AnimationUtils.loadAnimation(this, R.anim.left_to_right);
        right_to_left = AnimationUtils.loadAnimation(this, R.anim.right_to_left);

        left_to_right.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                lv_quick_change_organic.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        right_to_left.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                lv_quick_change_organic.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void addEvent () {

        qc_organic_adapter.setOnItemClickListener((item, view) -> {
//            viewCreator.clearAll();
            board.startAnimation(fade_out_then_in);
//            viewCreator.addView(presenter.convertContent(item));
            lv_quick_change_organic.startAnimation(left_to_right);
            isTurnOnQCI = false;
        });

        board.setOnClickListener(v -> {
            if (isTurnOnQCI) {
                isTurnOnQCI = false;
                lv_quick_change_organic.startAnimation(left_to_right);
            }
        });

        btn_turn_on_quick_change.setOnClickListener(v -> {
            if (!isTurnOnQCI){
                lv_quick_change_organic.startAnimation(right_to_left);
                isTurnOnQCI = true;
            }
        });

        btn_back.setOnClickListener(v -> finish());

        btn_back_to_home.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
    }

    public void setUpViewCreator () {
        viewCreator = new LessonViewCreator.ViewCreator(this, board);
//        viewCreator.setMarginContent(10, 10, 10, 10);
//        viewCreator.setMarginSmallTitle(10, 10, 10, 10);
//        viewCreator.setMarginBigTitle(10, 10, 10, 10);
        LessonViewCreator.ViewCreator.setMarginBigTitle(DpToPixel(10),DpToPixel(7),DpToPixel(10),0);
        LessonViewCreator.ViewCreator.setMarginSmallTitle(DpToPixel(17),DpToPixel(4),DpToPixel(10),0);
        LessonViewCreator.ViewCreator.setMarginContent(DpToPixel(40),DpToPixel(4),DpToPixel(10),0);
        LessonViewCreator.ViewCreator.setBig_title_text_size(8);
        LessonViewCreator.ViewCreator.setSmall_title_text_size(6);
        LessonViewCreator.ViewCreator.setSmaller_title_text_size(5);
        LessonViewCreator.ViewCreator.setContent_text_size(5);

    }

    public void setTheme () {
        if (AppThemeManager.isCustomingTheme) {
            if (AppThemeManager.isUsingColorBackground)
                base_view.setBackgroundColor(AppThemeManager.getBackgroundColor());
            else
                base_view.setBackground(AppThemeManager.getBackgroundDrawable());
        }
    }

    @Override
    public void onDataLoadSuccess(String title, String content) {
        txt_title.setText(title);
        viewCreator.addView(content);
    }
}
