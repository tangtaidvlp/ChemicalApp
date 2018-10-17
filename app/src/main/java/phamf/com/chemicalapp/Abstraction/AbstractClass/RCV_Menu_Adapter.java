package phamf.com.chemicalapp.Abstraction.AbstractClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import phamf.com.chemicalapp.Abstraction.Interface.Menuable;
import phamf.com.chemicalapp.R;
import phamf.com.chemicalapp.Manager.FontManager;

public abstract class RCV_Menu_Adapter<T extends Menuable> extends RecyclerView.Adapter<RCV_Menu_Adapter.DataViewHolder>{

    protected Context context;


    protected RecyclerView rcv_menu;


    protected List<T> list;


    protected OnItemClickListener<T> itemClickListener;


    public RCV_Menu_Adapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }



    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = rcv_menu.indexOfChild(v);
            itemClickListener.onItemClickListener(list.get(index));
        }
    };

    public abstract View create_ViewHolder (ViewGroup parent, int viewType);

    public abstract void bind_ViewHolder(@NonNull View itemView, T item);

    @NonNull
    @Override
    public RCV_Menu_Adapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.child_menu_view, parent, false);
//        view.setOnClickListener(onClickListener);
        View view = create_ViewHolder(parent, viewType);
        view.setOnClickListener(onClickListener);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RCV_Menu_Adapter.DataViewHolder holder, int position) {

//        T chapter = list.get(position);
//        holder.txt_index.setText(String.valueOf(chapter.getId()));
//        holder.txt_name.setText(chapter.getName());
        bind_ViewHolder(holder.itemView, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public void adaptFor (RecyclerView rcv_menu) {
        this.rcv_menu = rcv_menu;
        rcv_menu.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rcv_menu.setAdapter(this);
    }


    public void setData (Collection <T> items ) {
        list = (List<T>) items;
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public interface OnItemClickListener<T extends Menuable> {

        void onItemClickListener(T item);

    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name, txt_index;
        public View itemView;

        public DataViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            txt_index = itemView.findViewById(R.id.txt_item_index);
            txt_name = itemView.findViewById(R.id.txt_item_name);
            txt_index.setTypeface(FontManager.arial);
            txt_name.setTypeface(FontManager.arial);
        }
    }
}
