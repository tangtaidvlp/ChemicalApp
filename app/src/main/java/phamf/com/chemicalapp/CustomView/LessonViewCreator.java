package phamf.com.chemicalapp.CustomView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import phamf.com.chemicalapp.R;
import phamf.com.chemicalapp.Supporter.UnitConverter;

import static phamf.com.chemicalapp.Supporter.UnitConverter.D10;
import static phamf.com.chemicalapp.Supporter.UnitConverter.DpToPixel;

public class LessonViewCreator {

    public static final String DEVIDER = "<divider>";

    public static final String BIG_TITLE = "<<B_TITLE>>";

    public static final String SMALL_TITLE = "<<L_TITLE>>";

    public static final String IMAGE = "<<PICTURE>>";

    public static final String CONTENT = "<<CONTENT>>";


    // Because every TAG above has length is 11, the content is part from the end of Tag to end of Content
    private static final int BEGIN_CONTENT_POSITION = 11;

    // -2
    private static final int WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;

    // -1
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    private LinearLayout parent;

    private Context context;


    private int bt_mLeft, bt_mTop, bt_mRight, bt_mBottom, bt_width = WRAP_CONTENT, bt_height = WRAP_CONTENT;


    private int smt_mLeft, smt_mTop, smt_mRight, smt_mBottom, smt_width = WRAP_CONTENT, smt_height = WRAP_CONTENT;


    private int content_mLeft, content_mTop, content_mRight, content_mBottom, content_width = WRAP_CONTENT, content_height = WRAP_CONTENT;


    public LessonViewCreator (Context context, LinearLayout parent) {
        this.context = context;
        this.parent = parent;
    }

    public void setMarginBigTitle (int mLeft,int  mTop,int  mRight,int  mBottom) {
        bt_mLeft = mLeft;
        bt_mTop = mTop;
        bt_mRight = mRight;
        bt_mBottom = mBottom;
    }

    public void setMarginSmallTitle (int mLeft,int  mTop,int  mRight,int  mBottom) {
        smt_mLeft = mLeft;
        smt_mTop = mTop;
        smt_mRight = mRight;
        smt_mBottom = mBottom;
    }


    public void setMarginContent (int mLeft,int  mTop,int  mRight,int  mBottom) {
        content_mLeft = mLeft;
        content_mTop = mTop;
        content_mRight = mRight;
        content_mBottom = mBottom;
    }


    public void setParent (Context context, LinearLayout parent) {
        context = context;
        parent = parent;
    }


    private void addContent (String content) {
        TextView textView = new TextView(context);
        textView.setTextSize(DpToPixel(10));
        textView.setTextColor(Color.parseColor("#222222"));
        textView.setText(content);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(content_width, content_height);
        params.setMargins(content_mLeft, content_mTop, content_mRight, content_mBottom);
        textView.setLayoutParams(params);
        parent.addView(textView);
    }


    private void addBigTitle (String title) {
        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setTextSize(DpToPixel(12));
        textView.setTextColor(Color.parseColor("#BA1308"));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(bt_width, bt_height);
        params.setMargins(bt_mLeft, bt_mTop, bt_mRight, bt_mBottom);
        textView.setLayoutParams(params);
        parent.addView(textView);
    }


    private void addSmallTitle (String title) {
        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setTextSize(DpToPixel(11));
        textView.setTextColor(Color.parseColor("#BA1308"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(smt_width, smt_height);
        params.setMargins(smt_mLeft, smt_mTop, smt_mRight, smt_mBottom);
        textView.setLayoutParams(params);
        parent.addView(textView);
    }


    private void addImageContent (int imageId, int width, int height, int mLeft, int mTop, int mRight, int mBottom) {
        Drawable background = context.getDrawable(imageId);
        ImageView imageView = new ImageView(context);
        imageView.setBackground(background);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.setMargins(mLeft, mTop, mRight, mBottom);
        imageView.setLayoutParams(params);
        parent.addView(imageView);
    }


    public void addView (String content) {

        String [] component_list = content.split(DEVIDER);
        for (String component : component_list) {
            if (component.startsWith(BIG_TITLE)) {
                Log.e("BIG", "BIG GUYS");
                addBigTitle(component.substring(BEGIN_CONTENT_POSITION));

            } else if (component.startsWith(SMALL_TITLE)) {
                Log.e("BIG", "SMALL GUYS");
                addSmallTitle(component.substring(BEGIN_CONTENT_POSITION));

            } else if (component.startsWith(IMAGE)) {
                Log.e("BIG", "IMAGE");
                addImageContent(R.drawable.back_icon, DpToPixel(300), DpToPixel(300),
                        DpToPixel(10),
                        DpToPixel(10),
                        DpToPixel(10),
                        DpToPixel(10));

            } else if (component.startsWith(CONTENT)) {
                Log.e("BIG", "CONTENT");
                addContent(component.substring(BEGIN_CONTENT_POSITION));
            }
        }

    }


}
