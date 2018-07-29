package phamf.com.chemicalapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.R;
import phamf.com.chemicalapp.Supporter.FontManager;


public class Search_List_Adapter extends RecyclerView.Adapter<Search_List_Adapter.DataViewHolder> implements Filterable {

    Context context;

    Searcher filter = new Searcher();

    ArrayList<RO_ChemicalEquation> list;

    ArrayList<RO_ChemicalEquation> firstSelectedList;

    ArrayList<RO_ChemicalEquation> defaultList;

    OnItemClickListener itemClickListener;

    private RecyclerView rcv;

    String previousKey = "";

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                int position = rcv.indexOfChild(v);
                if (position >= 0) {
                    itemClickListener.OnItemClickListener(v, position);
                }
            }
        }
    };

    public Search_List_Adapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        defaultList = new ArrayList<>();
    }

    public void adaptFor(RecyclerView rcv) {
        this.rcv = rcv;
        this.rcv.setAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        this.rcv.setLayoutManager(manager);
    }

    public void setData (Collection<RO_ChemicalEquation> ro_chemicalEquations) {
        list.addAll(ro_chemicalEquations);
        notifyDataSetChanged();
    }

    public void addEquations (RO_ChemicalEquation ro_chemicalEquation) {
        list.add(ro_chemicalEquation);
        notifyDataSetChanged();
    }

    private void cache () {
        defaultList = new ArrayList<>();
        firstSelectedList = new ArrayList<>();
        defaultList.addAll(list);
    }

    private void clearCache () {
        defaultList.clear();
        firstSelectedList.clear();
    }


    @NonNull
    @Override
    public Search_List_Adapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_chemical_equation, parent, false);
        view.setOnClickListener(onClickListener);
        return new DataViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull Search_List_Adapter.DataViewHolder holder, int position) {
        RO_ChemicalEquation ro_ce = list.get(position);
        String equation = ro_ce.getAddingChemicals() + " " + ro_ce.getProduct();
        holder.txt_view.setText(equation);
        holder.txt_view.setTypeface(FontManager.arial);
    }


    @Override
    public int getItemCount() { return list == null ? 0 : list.size(); }


    @Override
    public Filter getFilter() { return this.filter; }



    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public void isSearching(boolean isSearching) {
        if (isSearching) {
            cache();
        } else {
            clearCache();
        }
    }


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

            if (key.length() == 0) {
                return null;
            }

            String [] keys = key.toString().toUpperCase().split("\\+");
            FilterResults results = new FilterResults();
            ArrayList<RO_ChemicalEquation> results_list = new ArrayList<>();

            // key is deleted
            boolean isDeleting = key.length() < previousKey.length();

            for (RO_ChemicalEquation equation : isDeleting ? firstSelectedList : list) {
                if ((equation.getAddingChemicals() + " " + equation.getProduct()).toUpperCase().contains(lastKeyOf(keys).trim())) {
                    results_list.add(equation);
                }
            }

            // Previous Key's length equals zero mean that user starts enter first character
            // Now we cached the first result list for optimizing searching when user deleting key
            if (previousKey.length() == 0) firstSelectedList.addAll(results_list);

            results.values = results_list;

            previousKey = key.toString();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if ((results != null) && (results.values != null)){
                if (!(results.values instanceof ArrayList)) {
                    throw new ClassCastException("Parameter 'results.values' is not instance of ArrayList<RO_ChemicalEquation>");
                }
                list = (ArrayList<RO_ChemicalEquation>) results.values;
            } else {
                list = defaultList;
            }

            notifyDataSetChanged();
        }

        private String lastKeyOf(String [] array) {
            if (array != null) return array[array.length - 1];
            throw new NullPointerException("'array' params is null");
        }
    }

    public interface OnItemClickListener {
        void OnItemClickListener(View view, int position);
    }

    //    class Searcher extends Filter {
//
//        @Override
//        protected FilterResults performFiltering(CharSequence key) {
//            if ((key.length() <= 0) || (key.toString().trim().equals(""))) return null;
//            String key_word = key.toString().trim().toUpperCase();
//            String[] key_words = key_word.split("\\+");
//            FilterResults result = new FilterResults();
//            boolean condition;
//            boolean isKeyDeleted = false;
//
//            /** OPTIMIZATION
//             * I use a ArraySet to save all result of the first input key word to make sure that
//             * when user delete a character of the key , the filter just filter the optimize list
//             * it has gotten before instead of all list.
//             * I accept to exchange RAM memory to get the performance, i thought it is not substantial
//             * **/
//            ArrayList<RO_ChemicalEquation> result_list = new ArrayList<>();
//
//            if (key_word.length() < previous_key.length()) isKeyDeleted = true;
//
//            if (key_words.length == 1) {
//
//                if (key_word.endsWith("+")) key_word = key_word.replace("+","");
//
//                key_word = key_word.trim();
//
//                for (RO_ChemicalEquation equation : isKeyDeleted ? optimizeSearchList : list) {
//
//                    if (equation.getDefaultEquation().contains(key_word)) {
//                        equation.highLightKey(key_word);
//                        result_list.add(equation);
//                    }
//                }
//            } else if (key_words.length > 1) {
//                ArrayList<String> standard_keys = filtList(key_words);
//    /* foreach */ for (RO_ChemicalEquation equation : isKeyDeleted ? optimizeSearchList : list) {
//                    condition = true;
//                    for (int i = standard_keys.size() - 1; i > -1; i--) { //Filt from last to first to check for optimize performance
//                        if (!equation.getDefaultEquation().contains(standard_keys.get(i))) {
//                            condition = false;
//                            break;
//                        }
//                    }
//                    //Highlight Keys
//                    if (condition) {
//                        equation.highLightManyKey(standard_keys);
//                        result_list.add(equation);
//                    }
//                }
//            }
//
//            if ((!isKeyDeleted) && (key_word.length() == 1)) {
//                optimizeSearchList = new ArraySet<>();
//                optimizeSearchList.addAll(result_list);
//            }
//
//            previous_key = key_word;
//
//            result.count = result_list.size();
//
//            result.values = result_list;
//
//            return result;
//        }
//
//
//        // To make the keyword list doesn't have nest element (Ex: CH4 nest CH)
//        private ArrayList<String> filtList(String[] keyword_list) {
//            ArrayList<String> standardList = new ArrayList<>();
//            standardList.add(keyword_list[0]);
//            boolean notNested;
//            for (String ele_list : keyword_list) {
//                notNested = true;
//                for (String standard_ele : standardList ){
//                    if (standard_ele.contains(ele_list)) {
//                        notNested = false;
//                        break;
//                    }
//                }
//                if (notNested) standardList.add(ele_list);
//            }
//            return standardList;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            if ((results != null) && (results.values != null)) {
//                list = (ArrayList<RO_ChemicalEquation>) results.values;
//                notifyDataSetChanged();
//            } else {
//                list = new ArrayList<>();
//
//                for (RO_ChemicalEquation e : filterList) {
//                    RO_ChemicalEquation newEquation = new RO_ChemicalEquation(e.getEquation()
//                                                                      , e.getCondition()
//                                                                      , e.getTotal_balance_number());
//                    list.add(newEquation);
//                }
//
//                notifyDataSetChanged();
//            }
//        }
//    }

}



