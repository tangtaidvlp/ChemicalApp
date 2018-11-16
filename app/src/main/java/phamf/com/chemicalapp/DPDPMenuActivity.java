package phamf.com.chemicalapp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import phamf.com.chemicalapp.Abstraction.AbstractClass.RCV_Menu_Adapter;
import phamf.com.chemicalapp.Abstraction.Interface.IDPDPMenuActivity;
import phamf.com.chemicalapp.Adapter.DPDP_Menu_Adapter;
import phamf.com.chemicalapp.Manager.AppThemeManager;
import phamf.com.chemicalapp.Presenter.DPDPMenuActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_DPDP;
import phamf.com.chemicalapp.Manager.FullScreenManager;

/**
 * Presenter
 * @see DPDPMenuActivityPresenter
 */
public class DPDPMenuActivity extends FullScreenActivity implements IDPDPMenuActivity.View, DPDPMenuActivityPresenter.OnDataLoadSuccess{

    public static final String DPDP_NAME = "dpdp_name";

    public static final String DPDP_CONTENT = "lesson_content";

    @BindView(R.id.rcv_dpdp_menu) RecyclerView rcv_dpdp_menu;
    public DPDP_Menu_Adapter rcv_dpdp_adapter;

    @BindView(R.id.btn_dpdp_menu_back) Button btn_back;

    @BindView(R.id.txt_dpdp_menu_title) TextView txt_title;

    @BindView(R.id.parent_dpdp_menu_activity) ConstraintLayout base_view;

    @BindView(R.id.bg_night_mode_dpdp_menu) TextView bg_night_mode;

    private DPDPMenuActivityPresenter dpdpMenuActivityPresenter;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dpdp_menu);

        ButterKnife.bind(this);

        dpdpMenuActivityPresenter = new DPDPMenuActivityPresenter(this);

        dpdpMenuActivityPresenter.setOnDataLoadListener(this);

        setTheme();

        addControl();

        addEvent();

        dpdpMenuActivityPresenter.loadData();
    }

    public void addControl () {
        rcv_dpdp_adapter = new DPDP_Menu_Adapter(this);
        rcv_dpdp_adapter.adaptFor(rcv_dpdp_menu);

        if (AppThemeManager.isOnNightMode) {
            bg_night_mode.setVisibility(View.VISIBLE);
        } else {
            bg_night_mode.setVisibility(View.INVISIBLE);
        }
    }

    public void addEvent () {
        rcv_dpdp_adapter.setOnItemClickListener(item -> {

            Intent intent = new Intent(DPDPMenuActivity.this, DPDPActivity.class);
            intent.putExtra(DPDP_NAME, item);
            startActivity(intent);
        });

        btn_back.setOnClickListener(v -> finish());
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
    public void onDataLoadSuccess(ArrayList<RO_DPDP> ro_dpdps) {
        rcv_dpdp_adapter.setData(ro_dpdps);
    }
}
