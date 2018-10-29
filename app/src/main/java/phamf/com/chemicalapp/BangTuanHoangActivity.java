package phamf.com.chemicalapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Debug;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import phamf.com.chemicalapp.Abstraction.Interface.IBangTuanHoangActivity;
import phamf.com.chemicalapp.Adapter.BangTuanHoang_GrV_Adapter;
import phamf.com.chemicalapp.Adapter.Search_Chem_Element_Adapter;
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor;
import phamf.com.chemicalapp.Manager.AppThemeManager;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element;
import phamf.com.chemicalapp.Supporter.UnitConverter;

import static android.view.View.GONE;

/**
 * Presenter
 * Not created yet
 */

public class BangTuanHoangActivity extends FullScreenActivity implements IBangTuanHoangActivity.View {

    public static final String CHEM_ELEMENT = "chem_element";

    @BindView(R.id.grv_bang_tuan_hoan) GridView grv_bang_tuan_hoan;
    BangTuanHoang_GrV_Adapter grv_bth_adapter;

    @BindView(R.id.rcv_bangtuanhoan_chem_element_search) RecyclerView rcv_element_search;
    Search_Chem_Element_Adapter rcv_element_search_adapter;

    @BindView(R.id.ln_bth_gridview_Parent) LinearLayout grv_bth_parent;

    @BindView(R.id.parent_bangtuanhoan_activity) HorizontalScrollView base_view;

    @BindView(R.id.bg_night_mode_bang_tuan_hoan) TextView bg_night_mode;

    @BindView(R.id.btn_bangtuanhoan_back) Button btn_back;

    @BindView(R.id.btn_bottom_bangtuanhoan_search) Button btn_bottom_turn_on_search;

    @BindView(R.id.btn_top_bangtuanhoan_turn_off_search) Button btn_top_turnOff_search;

    @BindView(R.id.edt_bangtuanhoan_search) VirtualKeyBoardSensor edt_search;

    @BindView(R.id.bangtuanhoan_search_chem_element_view_parent) RelativeLayout search_view_parent;

    ArrayList<RO_Chemical_Element> grv_bth_list = new ArrayList<>();

    public ArrayList<String> list = new ArrayList<>();

    private Animation fade_out, fade_in;

    InputMethodManager virtualKeyboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bang_tuan_hoang);

        ButterKnife.bind(this);

        setTheme();

        loadAnim();

        setUpManagers();

        addData();

        addControl();

        addEvent();

        setPeriodicTableWidth();

    }

    private void setUpManagers() {
        virtualKeyboardManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    private void addData () {

        grv_bth_list.add(new RO_Chemical_Element(1, "Natri", "Na", 11, 11, 12, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(2, "Kali", "K", 12, 13, 20, 39, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(3, "Canxi", "Ca", 20, 10, 10, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(4, "Magie", "Mg", 12, 10, 10, 23, "Kiềm Thổ", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(5, "Liti", "Li", 2, 10, 10, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(6, "Hidro", "H", 1, 10, 10, 23, "Khí", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(7, "Clo", "Cl", 17, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(8, "Iot", "I", 50, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(9, "Bari", "Ba", 65, 10, 10, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(10, "Xesi", "Ce", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(11, "Oxi", "O", 8, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Nito", "N", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Neon", "Ne", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Niken", "Ni", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(13, "Cacbon", "C", 6, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(14, "Luu huynh (Sulfur)", "S", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(15, "Sắt", "Fe", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(16, "Đồng", "Cu", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(17, "Franxi", "Fr", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(18, "Flo", "F", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(19, "Photpho", "P", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(20, "Paladi", "Pd", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(21, "Crom", "Cr", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(22, "Coban", "Co", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(23, "Brom", "Br", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(24, "Bo", "B", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(1, "Natri", "Na", 11, 11, 12, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(2, "Kali", "K", 12, 13, 20, 39, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(3, "Canxi", "Ca", 20, 10, 10, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(4, "Magie", "Mg", 12, 10, 10, 23, "Kiềm Thổ", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(5, "Liti", "Li", 2, 10, 10, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(6, "Hidro", "H", 1, 10, 10, 23, "Khí", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(7, "Clo", "Cl", 17, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(8, "Iot", "I", 50, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(9, "Bari", "Ba", 65, 10, 10, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(10, "Xesi", "Ce", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(11, "Oxi", "O", 8, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Nito", "N", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Neon", "Ne", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Niken", "Ni", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(13, "Cacbon", "C", 6, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(14, "Luu huynh (Sulfur)", "S", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(15, "Sắt", "Fe", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(16, "Đồng", "Cu", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(17, "Franxi", "Fr", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(18, "Flo", "F", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(19, "Photpho", "P", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(20, "Paladi", "Pd", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(21, "Crom", "Cr", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(22, "Coban", "Co", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(23, "Brom", "Br", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(24, "Bo", "B", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(1, "Natri", "Na", 11, 11, 12, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(2, "Kali", "K", 12, 13, 20, 39, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(3, "Canxi", "Ca", 20, 10, 10, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(4, "Magie", "Mg", 12, 10, 10, 23, "Kiềm Thổ", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(5, "Liti", "Li", 2, 10, 10, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(6, "Hidro", "H", 1, 10, 10, 23, "Khí", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(7, "Clo", "Cl", 17, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(8, "Iot", "I", 50, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(9, "Bari", "Ba", 65, 10, 10, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(10, "Xesi", "Ce", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(11, "Oxi", "O", 8, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Nito", "N", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Neon", "Ne", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Niken", "Ni", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(13, "Cacbon", "C", 6, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(14, "Luu huynh (Sulfur)", "S", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(15, "Sắt", "Fe", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(16, "Đồng", "Cu", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(17, "Franxi", "Fr", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(18, "Flo", "F", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(19, "Photpho", "P", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(20, "Paladi", "Pd", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(21, "Crom", "Cr", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(22, "Coban", "Co", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(23, "Brom", "Br", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(24, "Bo", "B", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(1, "Natri", "Na", 11, 11, 12, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(2, "Kali", "K", 12, 13, 20, 39, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(3, "Canxi", "Ca", 20, 10, 10, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(4, "Magie", "Mg", 12, 10, 10, 23, "Kiềm Thổ", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(5, "Liti", "Li", 2, 10, 10, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(6, "Hidro", "H", 1, 10, 10, 23, "Khí", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(7, "Clo", "Cl", 17, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(8, "Iot", "I", 50, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(9, "Bari", "Ba", 65, 10, 10, 23, "Kiềm", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(10, "Xesi", "Ce", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(11, "Oxi", "O", 8, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Nito", "N", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Neon", "Ne", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Niken", "Ni", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(13, "Cacbon", "C", 6, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(14, "Luu huynh (Sulfur)", "S", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(15, "Sắt", "Fe", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(16, "Đồng", "Cu", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(17, "Franxi", "Fr", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(18, "Flo", "F", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(19, "Photpho", "P", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(20, "Paladi", "Pd", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(21, "Crom", "Cr", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(22, "Coban", "Co", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(23, "Brom", "Br", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(24, "Bo", "B", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));

        grv_bth_list.add(new RO_Chemical_Element(10, "Xesi", "Ce", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(11, "Oxi", "O", 8, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Nito", "N", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Neon", "Ne", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Niken", "Ni", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(13, "Cacbon", "C", 6, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(14, "Luu huynh (Sulfur)", "S", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(15, "Sắt", "Fe", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(16, "Đồng", "Cu", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(17, "Franxi", "Fr", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(18, "Flo", "F", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(19, "Photpho", "P", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(20, "Paladi", "Pd", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(21, "Crom", "Cr", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(22, "Coban", "Co", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(23, "Brom", "Br", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(24, "Bo", "B", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));

        grv_bth_list.add(new RO_Chemical_Element(10, "Xesi", "Ce", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(11, "Oxi", "O", 8, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Nito", "N", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Neon", "Ne", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Niken", "Ni", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(13, "Cacbon", "C", 6, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(14, "Luu huynh (Sulfur)", "S", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(15, "Sắt", "Fe", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(16, "Đồng", "Cu", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(17, "Franxi", "Fr", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(18, "Flo", "F", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(19, "Photpho", "P", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(20, "Paladi", "Pd", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(21, "Crom", "Cr", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(22, "Coban", "Co", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(23, "Brom", "Br", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(24, "Bo", "B", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));

        grv_bth_list.add(new RO_Chemical_Element(10, "Xesi", "Ce", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(11, "Oxi", "O", 8, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Nito", "N", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Neon", "Ne", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(12, "Niken", "Ni", 7, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(13, "Cacbon", "C", 6, 10, 10, 23, "Halogen", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(14, "Luu huynh (Sulfur)", "S", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(15, "Sắt", "Fe", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(16, "Đồng", "Cu", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(17, "Franxi", "Fr", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(18, "Flo", "F", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(19, "Photpho", "P", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(20, "Paladi", "Pd", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(21, "Crom", "Cr", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(22, "Coban", "Co", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(23, "Brom", "Br", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));
        grv_bth_list.add(new RO_Chemical_Element(24, "Bo", "B", 16, 10, 10, 23, "Oxi", getColor(R.color.BackgroundWeakBlue)));

    }

    public void addControl () {
        grv_bth_adapter = new BangTuanHoang_GrV_Adapter( this);
        grv_bth_adapter.adaptFor(grv_bang_tuan_hoan);
        grv_bth_adapter.setData(grv_bth_list);

        rcv_element_search_adapter = new Search_Chem_Element_Adapter(this);
        rcv_element_search_adapter.adaptFor(rcv_element_search);
        rcv_element_search_adapter.setData(grv_bth_list);
        rcv_element_search_adapter.observe(edt_search);

        if (AppThemeManager.isOnNightMode) {
            bg_night_mode.setVisibility(View.VISIBLE);
        } else {
            bg_night_mode.setVisibility(View.INVISIBLE);
        }
    }

    public void addEvent () {

        btn_back.setOnClickListener(v -> finish());

        grv_bth_adapter.setOnItemClickListener(chem_element -> {
            Intent intent = new Intent(BangTuanHoangActivity.this, ChemicalElementActivity.class);
            intent.putExtra(CHEM_ELEMENT, chem_element);
            startActivity(intent);
            hideVirtualKeyboard();
        });

        rcv_element_search_adapter.setOnItemClickListener(element -> {
            search_view_parent.setVisibility(GONE);
            Intent intent = new Intent(BangTuanHoangActivity.this, ChemicalElementActivity.class);
            intent.putExtra(CHEM_ELEMENT, element);
            startActivity(intent);
            hideVirtualKeyboard();
        });

        btn_bottom_turn_on_search.setOnClickListener(v -> {
            search_view_parent.startAnimation(fade_in);
            edt_search.requestFocus();
            showVirtualKeyboard();
        });

        btn_top_turnOff_search.setOnClickListener(v -> {
            search_view_parent.startAnimation(fade_out);
            hideVirtualKeyboard();
            edt_search.setText("");
        });
    }

    public void loadAnim () {
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fade_in.setFillAfter(false);

        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fade_out.setFillAfter(false);

        fade_out.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                search_view_parent.setVisibility(View.GONE);
            }
            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }
        });

        fade_in.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                search_view_parent.setVisibility(View.VISIBLE);
            }
            public void onAnimationEnd(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void hideVirtualKeyboard () {
        virtualKeyboardManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void showVirtualKeyboard () {
        virtualKeyboardManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    private final int CHILD_ELEMENT_LAYOUT_WIDTH_IN_XML_FILE = 105; /* 105 dp*/
    public void setPeriodicTableWidth () {
        grv_bth_parent.getLayoutParams().width = 18 * UnitConverter.DpToPixel(CHILD_ELEMENT_LAYOUT_WIDTH_IN_XML_FILE) + 17 * UnitConverter.DpToPixel(3);
    }

    public void setTheme () {
        if (AppThemeManager.isCustomingTheme) {
            if (AppThemeManager.isUsingColorBackground)
                base_view.setBackgroundColor(AppThemeManager.getBackgroundColor());
            else
                base_view.setBackground(AppThemeManager.getBackgroundDrawable());
        }
    }


}
