package phamf.com.chemicalapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import phamf.com.chemicalapp.Abstraction.AbstractClass.RCV_Menu_Adapter;
import phamf.com.chemicalapp.R;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.Manager.FontManager;

public class Lesson_Menu_Adapter extends RCV_Menu_Adapter<RO_Lesson> {
    public Lesson_Menu_Adapter(Context context) {
        super(context);
    }

    @Override
    public View create_ViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_menu_view, parent, false);
        return view;
    }

    @Override
    public void bind_ViewHolder(@NonNull View itemView, RO_Lesson item) {
        TextView txt_index = itemView.findViewById(R.id.txt_item_index);
        TextView txt_name = itemView.findViewById(R.id.txt_item_name);

        txt_index.setTypeface(FontManager.arial);
        txt_name.setTypeface(FontManager.arial);

        txt_index.setText(String.valueOf(item.getId()));
        txt_name.setText(item.getName());
    }
}
