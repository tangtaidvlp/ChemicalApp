package phamf.com.chemicalapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor;
import phamf.com.chemicalapp.Model.Chemical_Element;
import phamf.com.chemicalapp.R;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element;

public class Search_Chem_Element_Adapter extends RecyclerView.Adapter<Search_Chem_Element_Adapter.DataViewHolder>
        implements Filterable{

    Context context;

    Search_Chem_Element_Adapter.Searcher filter;

    ArrayList<RO_Chemical_Element> list;

    ArrayList<RO_Chemical_Element> defaultList;

    Search_Chem_Element_Adapter.OnItemClickListener onCustomItemClickListener;

    private RecyclerView rcv;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onCustomItemClickListener != null) {
                int position = rcv.indexOfChild(v);
                onCustomItemClickListener.onItemClick(list.get(position));
            }
        }
    };


    public Search_Chem_Element_Adapter(Context context) {
        this.context = context;
        filter = new Searcher();
    }

    public void adaptFor (RecyclerView rcv) {
        this.rcv = rcv;
        rcv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rcv.setAdapter(this);
    }

    public void setData (Collection <RO_Chemical_Element> datas) {
        list = new ArrayList<>();
        defaultList = new ArrayList<>();
        list.addAll(datas);
        defaultList.addAll(list);
        notifyDataSetChanged();
    }

    public void observe (VirtualKeyBoardSensor virtualKeyBoardSensor) {
        virtualKeyBoardSensor.addLiteTextChangeListener((s, start, before, count) -> getFilter().filter(s));
    }

    public void setOnItemClickListener (OnItemClickListener onCustomItemClickListener) {
        this.onCustomItemClickListener = onCustomItemClickListener;
    }

    @NonNull
    @Override
    public Search_Chem_Element_Adapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_search_chemical_element, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Search_Chem_Element_Adapter.DataViewHolder holder, int position) {
        holder.itemView.setOnClickListener(onClickListener);
        RO_Chemical_Element element = list.get(position);
        holder.txt_chem_element_symbol.setText(element.getSymbol());
        holder.txt_chem_element_name.setText(element.getName() == null ? element.getName() + "" : element.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        TextView txt_chem_element_symbol, txt_chem_element_name;
        public DataViewHolder(View itemView) {
            super(itemView);
            txt_chem_element_name = itemView.findViewById(R.id.child_txt_chem_element_name);
            txt_chem_element_symbol = itemView.findViewById(R.id.child_txt_chem_element_symbol);
        }
    }

    public class Searcher extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence key) {

            if (key.length() == 0) return null;

            FilterResults result = new FilterResults();

            ArrayList<RO_Chemical_Element> newList = new ArrayList<>();

            for (RO_Chemical_Element element : defaultList) {
                if ((element.getSymbol() + element.getName()).toUpperCase().contains(key.toString().toUpperCase())) {
                    newList.add(element);
                }
            }


            result.values = newList;

            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if ((results != null) && (results.values != null)){
                list = (ArrayList<RO_Chemical_Element>) results.values;
            } else {
                list = defaultList;
            }

            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(RO_Chemical_Element element);
    }
}
