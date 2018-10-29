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

        // The empty chemical element present for a empty position
        if (list.get(position).getName() == "") {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_empty_chemical_element, parent, false);
            return convertView;
        }

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_chemical_element, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txt_name = convertView.findViewById(R.id.child_txt_ele_name);
            viewHolder.txt_symbol = convertView.findViewById(R.id.child_txt_ele_symbol);
            viewHolder.txt_proton = convertView.findViewById(R.id.child_txt_ele_proton);
            viewHolder.txt_mass = convertView.findViewById(R.id.child_txt_ele_mass);
            convertView.setTag(viewHolder);
            final View finalConvertView = convertView;
            // get item's with to calculate gridview's width
            convertView.getViewTreeObserver().addOnGlobalLayoutListener(() -> itemWidth = finalConvertView.getLayoutParams().width);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        RO_Chemical_Element element = list.get(position);

        viewHolder.txt_proton.setText(String.valueOf(element.getProton_count()));
        viewHolder.txt_symbol.setText(element.getSymbol());
        viewHolder.txt_name.setText(element.getName());
        String mass = (element.getMass() + "").endsWith(".0") ? ((int) element.getMass()) + "" : element.getMass() + "";
        viewHolder.txt_mass.setText(mass);
        convertView.setBackgroundColor(element.getBackground_color());

        return convertView;
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
    }

    public interface OnItemClickListener {
        void onItemClickListener (RO_Chemical_Element chem_element);
    }
}
