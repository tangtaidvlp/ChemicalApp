package phamf.com.chemicalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import phamf.com.chemicalapp.Abstraction.Interface.IChemicalElementActivity;
import phamf.com.chemicalapp.Adapter.Search_Chem_Element_Adapter;
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor;
import phamf.com.chemicalapp.Presenter.ChemicalElementActivityPresenter;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element;

/**
 * Presenter
 * @see ChemicalElementActivityPresenter
 */
public class ChemicalElementActivity extends FullScreenActivity implements IChemicalElementActivity.View, ChemicalElementActivityPresenter.OnDataReceived {

    @BindView(R.id.txt_chem_element_name) TextView txt_element_name;

    @BindView(R.id.txt_chem_element_symbol) TextView txt_element_symbol;

    @BindView(R.id.txt_chem_element_e_p_partical_count) TextView txt_e_p_count;

    @BindView(R.id.txt_chem_element_notron_count) TextView txt_notron_count;

    @BindView(R.id.txt_chem_element_element_type) TextView txt_element_type;

    @BindView(R.id.txt_specified_weight) TextView txt_specific_weight;

    @BindView(R.id.bg_chem_element_escape_search_mode) TextView bg_escape_search_mode;

    @BindView(R.id.btn_chem_element_back) Button btn_back;

    @BindView(R.id.btn_top_chemical_element_turn_Off_search) Button btn_top_turn_off_search_mode;

    @BindView(R.id.btn_bottom_chemical_element_turn_On_search) Button btn_bottom_turn_on_search_mode;

    @BindView(R.id.edt_chem_element_search) VirtualKeyBoardSensor edt_search;

    @BindView(R.id.chem_element_search_chemical_element_view_parent) RelativeLayout search_view_parent;

    @BindView(R.id.rcv_chem_element_search) RecyclerView rcv_chem_element_search;
    Search_Chem_Element_Adapter rcv_chem_element_search_adapter;
    ArrayList<RO_Chemical_Element> rcv_chem_element_search_list;

    ChemicalElementActivityPresenter activityPresenter;

    Animation fade_out, fade_in;

    InputMethodManager softKeyboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_element);

        ButterKnife.bind(this);

        activityPresenter = new ChemicalElementActivityPresenter(this);

        activityPresenter.setOnDataReceived(this);

        createNeccesaryInfo();

        loadAnimation();

        addControl();

        addEvent();

        activityPresenter.loadData();

    }


    public void createNeccesaryInfo() {
        softKeyboardManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

    }

    public void addControl() {
        addData();
        rcv_chem_element_search_adapter = new Search_Chem_Element_Adapter(this);
        rcv_chem_element_search_adapter.adaptFor(rcv_chem_element_search);
        rcv_chem_element_search_adapter.setData(rcv_chem_element_search_list);
        rcv_chem_element_search_adapter.observe(edt_search);
    }

    public void addEvent() {
        btn_back.setOnClickListener(v -> finish());

        btn_bottom_turn_on_search_mode.setOnClickListener(v -> search_view_parent.startAnimation(fade_in));

        btn_top_turn_off_search_mode.setOnClickListener(v -> {
            search_view_parent.startAnimation(fade_out);
            hideSoftKeyboard();
        });

        rcv_chem_element_search_adapter.setOnItemClickListener(element -> {
            onDataReceived(element);
            search_view_parent.startAnimation(fade_out);
            hideSoftKeyboard();
        });

        edt_search.setOnClickListener(v -> {
            search_view_parent.startAnimation(fade_in);
        });

        bg_escape_search_mode.setOnClickListener(v -> {
            search_view_parent.startAnimation(fade_out);
            edt_search.setText("");
        });
    }

    public void showSoftKeyboard () {
        softKeyboardManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideSoftKeyboard () {
        softKeyboardManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void loadAnimation () {
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

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

    @Override
    public void onDataReceived(RO_Chemical_Element chemical_element) {
        txt_element_name.setText(chemical_element.getName() + "");
        txt_element_symbol.setText(chemical_element.getSymbol());
        txt_e_p_count.setText(String.valueOf(chemical_element.getProton_count()));
        txt_notron_count.setText(String.valueOf(chemical_element.getProton_count()));
        txt_specific_weight.setText(String.valueOf(chemical_element.getMass()));
        txt_element_type.setText(chemical_element.getType());
    }

    private void addData () {
        rcv_chem_element_search_list = new ArrayList<>();
        rcv_chem_element_search_list.add(new RO_Chemical_Element(1, "Natri", "Na", 11, 11, 12, 23, "Kiềm", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(2, "Kali", "K", 12, 13, 20, 39, "Kiềm", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(3, "Canxi", "Ca", 20, 10, 10, 23, "Kiềm", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(4, "Magie", "Mg", 12, 10, 10, 23, "Kiềm Thổ", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(5, "Liti", "Li", 2, 10, 10, 23, "Kiềm", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(6, "Hidro", "H", 1, 10, 10, 23, "Khí", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(7, "Clo", "Cl", 17, 10, 10, 23, "Halogen", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(8, "Iot", "I", 50, 10, 10, 23, "Halogen", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(9, "Bari", "Ba", 65, 10, 10, 23, "Kiềm", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(10, "Xesi", "Ce", 16, 10, 10, 23, "Oxi", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(11, "Oxi", "O", 8, 10, 10, 23, "Halogen", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(12, "Nito", "N", 7, 10, 10, 23, "Halogen", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(12, "Neon", "Ne", 7, 10, 10, 23, "Halogen", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(12, "Niken", "Ni", 7, 10, 10, 23, "Halogen", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(13, "Cacbon", "C", 6, 10, 10, 23, "Halogen", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(14, "Luu huynh (Sulfur)", "S", 16, 10, 10, 23, "Oxi", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(15, "Sắt", "Fe", 16, 10, 10, 23, "Oxi", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(16, "Đồng", "Cu", 16, 10, 10, 23, "Oxi", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(17, "Franxi", "Fr", 16, 10, 10, 23, "Oxi", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(18, "Flo", "F", 16, 10, 10, 23, "Oxi", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(19, "Photpho", "P", 16, 10, 10, 23, "Oxi", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(20, "Paladi", "Pd", 16, 10, 10, 23, "Oxi", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(21, "Crom", "Cr", 16, 10, 10, 23, "Oxi", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(22, "Coban", "Co", 16, 10, 10, 23, "Oxi", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(23, "Brom", "Br", 16, 10, 10, 23, "Oxi", R.color.TextTitleColor));
        rcv_chem_element_search_list.add(new RO_Chemical_Element(24, "Bo", "B", 16, 10, 10, 23, "Oxi", R.color.TextTitleColor));

    }

}
