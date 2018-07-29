package phamf.com.chemicalapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import phamf.com.chemicalapp.Abstraction.Menuable;
import phamf.com.chemicalapp.Model.Chapter;
import phamf.com.chemicalapp.R;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;

public class RCV_Menu_Adapter extends RecyclerView.Adapter<RCV_Menu_Adapter.ViewHolder>{

    Context context;

    List<Menuable> list;

    RecyclerView rcv_menu;

    private OnItemClickListener itemClickListener;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = rcv_menu.indexOfChild(v);
            itemClickListener.onItemClickListener(index, list.get(index).getId());
        }
    };


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.child_menu_view, parent,false);
        view.setOnClickListener(onClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Menuable item = list.get(position);
        holder.txt_chapter_index.setText(item.getId());
        holder.txt_chapter_name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size() ;
    }

    public void setOnItemClickListener (OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void adaptFor (RecyclerView rcv_menu) {
        this.rcv_menu = rcv_menu;
        rcv_menu.setAdapter(this);
    }

    public void setData (Collection<Menuable> items) {
        list.addAll(items);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_chapter_index;

        TextView txt_chapter_name;

        ViewHolder(View itemView) {
            super(itemView);
            txt_chapter_index = itemView.findViewById(R.id.txt_index);
            txt_chapter_name = itemView.findViewById(R.id.txt_name);
        }
    }

    interface OnItemClickListener {

        void onItemClickListener (int index, int id);

    }
}
