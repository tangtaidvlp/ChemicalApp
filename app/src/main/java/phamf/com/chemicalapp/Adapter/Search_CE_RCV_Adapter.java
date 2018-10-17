package phamf.com.chemicalapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.R;
import phamf.com.chemicalapp.Manager.FontManager;


public class Search_CE_RCV_Adapter extends RecyclerView.Adapter<Search_CE_RCV_Adapter.DataViewHolder> implements Filterable {

    Context context;

    Searcher filter;

    ArrayList<RO_ChemicalEquation> list;

    ArrayList<RO_ChemicalEquation> defaultList;

    OnItemClickListener itemClickListener;

    private RecyclerView rcv;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                int position = rcv.indexOfChild(v);

                if (position >= 0) {
                    RO_ChemicalEquation equation = getItem(position);
                    itemClickListener.OnItemClickListener(v, equation, position);
                }
            }
        }
    };

    public Search_CE_RCV_Adapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        defaultList = new ArrayList<>();
        filter = new Searcher();

    }

    public void adaptFor(RecyclerView rcv) {
        this.rcv = rcv;
        this.rcv.setAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        this.rcv.setLayoutManager(manager);
    }

    public void observe(EditText input) {
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void observe(VirtualKeyBoardSensor input) {
        input.addLiteTextChangeListener((s, start, before, count) -> getFilter().filter(s));
    }


    /**
     * I don't uses List.addAll(Collection collection) because that Realm Object can't be called by any thread except
     * thread creating it. If i use addAll, then in Filter class beneath, Realm Object will be called by thread of Filter,
     * that's not allowed.
     * So i have to clone their properties to new Realm Object and then add to the list
     * Thanks to Garbage Collection of Java, data that i get from db will be deleted because there's no reference to them anymore
     * after be setted to Search List Adapter
     */
    public void setData (Collection<RO_ChemicalEquation> ro_chemicalEquations) {
        list = new ArrayList<>();
        defaultList = new ArrayList<>();
        for (RO_ChemicalEquation equation : ro_chemicalEquations) {
            RO_ChemicalEquation ro_equation = new RO_ChemicalEquation();

            ro_equation.setCondition(equation.getCondition());
            ro_equation.setId(equation.getId());
            ro_equation.setAddingChemicals(equation.getAddingChemicals());
            ro_equation.setProduct(equation.getProduct());
            ro_equation.setTotal_balance_number(equation.getTotal_balance_number());

            list.add(ro_equation);
            defaultList.add(ro_equation);
        }
        notifyDataSetChanged();
    }


    private void cache () {

    }

    private void clearCache () {

    }


    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public void isSearching(boolean isSearching) {

    }


    public RO_ChemicalEquation getItem (int position) {
        return list.get(position);
    }


    public ArrayList<RO_ChemicalEquation> getList() {
        return list;
    }


    @NonNull
    @Override
    public Search_CE_RCV_Adapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_search_chemical_equation, parent, false);
        view.setOnClickListener(onClickListener);
        return new DataViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull Search_CE_RCV_Adapter.DataViewHolder holder, int position) {
        RO_ChemicalEquation ro_ce = list.get(position);
        String equation = ro_ce.getAddingChemicals() + " -> " + ro_ce.getProduct();
        holder.txt_view.setText(equation);
        holder.txt_view.setTypeface(FontManager.arial);
    }


    @Override
    public int getItemCount() { return list == null ? 0 : list.size(); }


    @Override
    public Filter getFilter() { return this.filter; }


    public class DataViewHolder extends RecyclerView.ViewHolder {
        TextView txt_view;

        DataViewHolder(View itemView) {
            super(itemView);
            txt_view = itemView.findViewById(R.id.txt_equation);
        }
    }


    private class Searcher extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence key) {

            if (key.length() == 0) return null;

            String [] sub_keys = key.toString().toUpperCase().split("\\+");

            FilterResults result = new FilterResults();

            ArrayList<RO_ChemicalEquation> newList = new ArrayList<>();

            for (RO_ChemicalEquation item : defaultList) {
                boolean isMatched = true;
                for (String sub_key : sub_keys) {
                    if (!(item.getAddingChemicals() + item.getProduct()).toUpperCase().contains(sub_key)) {
                        isMatched = false;
                        break;
                    }
                }
                if (isMatched) newList.add(item);
            }

            result.values = newList;

            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if ((results != null) && (results.values != null)){
                list = (ArrayList<RO_ChemicalEquation>) results.values;
            } else {
                list = defaultList;
            }

            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void OnItemClickListener(View view, RO_ChemicalEquation equation, int position);
    }

}



