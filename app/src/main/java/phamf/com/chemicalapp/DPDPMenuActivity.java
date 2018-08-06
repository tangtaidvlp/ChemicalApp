package phamf.com.chemicalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import phamf.com.chemicalapp.Presenter.DPDPMenuActivityPresenter;

public class DPDPMenuActivity extends AppCompatActivity {

    private @BindView(R.id.rcv_dpdp_menu) RecyclerView rcv_dpdp_menu;


    private @BindView(R.id.btn_dpdp_menu_back) Button btn_back;


    private @BindView(R.id.txt_dpdp_menu_title) TextView txt_title;


    private DPDPMenuActivityPresenter dpdpMenuActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dpdp_menu);

        dpdpMenuActivityPresenter = new DPDPMenuActivityPresenter(this);

        dpdpMenuActivityPresenter.loadData();
    }
}
