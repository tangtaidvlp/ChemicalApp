package phamf.com.chemicalapp.Abstraction.SpecialDataType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import phamf.com.chemicalapp.Abstraction.Interface.QuickChangeableItem;
import phamf.com.chemicalapp.R;

public class QuickChangeItemListViewAdapter<T extends QuickChangeableItem> extends BaseAdapter{

    protected ArrayList<T> list;

    protected Context context;

    protected OnItemClickListener<T> onItemClickListener;

    public QuickChangeItemListViewAdapter( Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }

    public void setData(Collection<T> list) {
        for (T t : list) {
            this.list.add(t);

        }

        notifyDataSetChanged();
    }

    public void adaptFor(final ListView listView) {
        listView.setAdapter(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemClickListener != null) onItemClickListener.OnItemClickListener(list.get(position), view);
            }
        });
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.child_quick_change_item, parent, false);
        TextView txt_name = view.findViewById(R.id.txt_quick_change_item_name);
        txt_name.setText(list.get(position).getName());
        return view;
    }

    public interface OnItemClickListener<T extends QuickChangeableItem>  {

        void OnItemClickListener(T item, View view);

    }

    public void setOnItemClickListener (OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
