package phamf.com.chemicalapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import phamf.com.chemicalapp.R;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element;

public class BangTuanHoang_GrV_Adapter extends BaseAdapter {

    public ArrayList<RO_Chemical_Element> list;

    Context context;

    int itemWidth = 0;

    OnItemClickListener onCustomItemClickListener;

    GridView gridView;

    public BangTuanHoang_GrV_Adapter(Context context) {
        list = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;

        // The empty chemical element present for a empty position
        if (list.get(position).getName().equals("")) {
            view = layoutInflater.inflate(R.layout.child_empty_chemical_element, parent, false);
            return view;
        }

        view = layoutInflater.inflate(R.layout.child_chemical_element, parent, false);

        TextView txt_electron_structure = view.findViewById(R.id.child_txt_electron_structure);
        TextView txt_proton = view.findViewById(R.id.child_txt_ele_proton );
        TextView txt_symbol = view.findViewById(R.id.child_txt_ele_symbol );
        TextView txt_name = view.findViewById(R.id.child_txt_ele_name );
        TextView txt_mass = view.findViewById(R.id.child_txt_ele_mass );

        RO_Chemical_Element element = list.get(position);
        txt_proton.setText(String.valueOf(element.getProton_count()));
        txt_symbol.setText(element.getSymbol());
        txt_name.setText(element.getName());
        String mass = (element.getMass() + "").endsWith(".0") ? ((int) element.getMass()) + "" : element.getMass() + "";
        txt_mass.setText(mass);
        txt_electron_structure.setBackgroundColor(element.getBackground_color());
        view.setBackgroundColor(element.getBackground_color());

        return view;
    }

    public void adaptFor (GridView gridView) {
        this.gridView = gridView;
        gridView.setAdapter(this);
    }

    public void setData (Collection<RO_Chemical_Element> elements) {
        for (RO_Chemical_Element element : elements) {
            list.add(element);
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener (OnItemClickListener onCustomItemClickListener) {
        this.onCustomItemClickListener = onCustomItemClickListener;
        gridView.setOnItemClickListener((parent, view, position, id)
                -> onCustomItemClickListener.onItemClickListener(list.get(position)));
    }

    public class ViewHolder {
        TextView txt_proton;
        TextView txt_symbol;
        TextView txt_name;
        TextView txt_mass;
        TextView txt_electron_structure;
    }

    public interface OnItemClickListener {
        void onItemClickListener (RO_Chemical_Element chem_element);
    }
}
