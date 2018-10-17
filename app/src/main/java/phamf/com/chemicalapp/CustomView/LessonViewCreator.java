package phamf.com.chemicalapp.CustomView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import phamf.com.chemicalapp.Adapter.ViewPager_Lesson_Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import phamf.com.chemicalapp.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static phamf.com.chemicalapp.Supporter.UnitConverter.DpToPixel;

public class LessonViewCreator {

    private ViewPager_Lesson_Adapter viewPager_adapter;

    public static final String PART_DEVIDER = "<pa_divi>";

    public static final String BOLD_TEXT = "<<boldTxt>>";

    public static final String ITALICED_TEXT = "<<italTxt>>";

    public static final String NORMAL_TEXT = "<<normTxt>>";

    public LessonViewCreator (ViewPager_Lesson_Adapter viewPager_adapter) {

        this.viewPager_adapter = viewPager_adapter;
    }

    public void separatePart_And_BindDataToViewPG (String content) {

        String [] part_list = content.split(PART_DEVIDER);

        for (String part : part_list) {
            viewPager_adapter.addData(part);
        }

        viewPager_adapter.notifyDataSetChanged();
    }


    public static class ViewCreator {

        public static final String COMPONENT_DEVIDER = "<co_divi>";

        public static final String BIG_TITLE = "<<B_TITLE>>";

        public static final String SMALL_TITLE = "<<S_TITLE>>";

        public static final String SMALLER_TITLE = "<<s_TITLE>>";

        public static final String IMAGE = "<<PICTURE>>";

        public static final String CONTENT = "<<CONTENT>>";

        // Text data usually has form as follow : <<B_TITLE>><<BoldTxt>>Hello World
        // 11 is length of <<B_TITLE>> (Type) and the START position of <<BoldTxt>> (Text style) too
        // 22 is END position of <<BoldTxt>> and START position of content too
        private static final int BEGIN_TEXT_STYLE_POSITION = 11;

        private static final int END_TEXT_STYLE_POSITION = 22;

        private Context context;

        private LinearLayout parent;

        // Properties
        private static int bt_mLeft, bt_mTop, bt_mRight, bt_mBottom, bt_width = WRAP_CONTENT, bt_height = WRAP_CONTENT;

        private static int smt_mLeft, smt_mTop, smt_mRight, smt_mBottom, smt_width = WRAP_CONTENT, smt_height = WRAP_CONTENT;

        private static int content_mLeft, content_mTop, content_mRight, content_mBottom, content_width = WRAP_CONTENT, content_height = WRAP_CONTENT;


        // Constructor
        public ViewCreator (Context context, LinearLayout parent) {
            this.context = context;
            this.parent = parent;
        }


        // Setter
        public static void setMarginBigTitle (int mLeft,int  mTop,int  mRight,int  mBottom) {
            bt_mLeft = mLeft;
            bt_mTop = mTop;
            bt_mRight = mRight;
            bt_mBottom = mBottom;

        }

        public static void setMarginSmallTitle (int mLeft,int  mTop,int  mRight,int  mBottom) {
            smt_mLeft = mLeft;
            smt_mTop = mTop;
            smt_mRight = mRight;
            smt_mBottom = mBottom;
        }

        public static void setMarginContent (int mLeft,int  mTop,int  mRight,int  mBottom) {
            content_mLeft = mLeft;
            content_mTop = mTop;
            content_mRight = mRight;
            content_mBottom = mBottom;
        }


        // Functions
        public void addContent (String content, String style) {
            TextView textView = new TextView(context);
            textView.setTextSize(DpToPixel(10));
            textView.setTextColor(Color.parseColor("#222222"));
            textView.setText(content);
            textView.setTypeface(textView.getTypeface(), getTextStyle(style));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(content_width, content_height);
            params.setMargins(content_mLeft, content_mTop, content_mRight, content_mBottom);
            textView.setLayoutParams(params);
            parent.addView(textView);
        }

        public void addBigTitle (String title, String style) {
            TextView textView = new TextView(context);
            textView.setText(title);
            textView.setTypeface(textView.getTypeface(), getTextStyle(style));
            textView.setTextSize(DpToPixel(12));
            textView.setTextColor(Color.parseColor("#BA1308"));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(bt_width, bt_height);
            params.setMargins(bt_mLeft, bt_mTop, bt_mRight, bt_mBottom);
            textView.setLayoutParams(params);
            parent.addView(textView);
        }

        public void addSmallTitle (String title, String style) {
            TextView textView = new TextView(context);
            textView.setText(title);
            textView.setTextSize(DpToPixel(11));
            textView.setTextColor(Color.parseColor("#BA1308"));
            textView.setTypeface(textView.getTypeface(), getTextStyle(style));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(smt_width, smt_height);
            params.setMargins(smt_mLeft, smt_mTop, smt_mRight, smt_mBottom);
            textView.setLayoutParams(params);
            parent.addView(textView);
        }

        public void addSmallerTitle (String title, String style) {
            TextView textView = new TextView(context);
            textView.setText(title);
            textView.setTextSize(DpToPixel(11));
            textView.setTextColor(Color.parseColor("#BA1308"));
            textView.setTypeface(textView.getTypeface(), getTextStyle(style));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(smt_width, smt_height);
            params.setMargins(smt_mLeft, smt_mTop, smt_mRight, smt_mBottom);
            textView.setLayoutParams(params);
            parent.addView(textView);
        }

        public void addImageContent (int imageId, int width, int height,
                                     int mLeft, int mTop, int mRight, int mBottom) {
            Drawable background = context.getDrawable(imageId);
            ImageView imageView = new ImageView(context);
            imageView.setBackground(background);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.setMargins(mLeft, mTop, mRight, mBottom);
            imageView.setLayoutParams(params);
            parent.addView(imageView);
        }

        public void addView (String content) {
            String [] component_list = content.split(COMPONENT_DEVIDER);
            for (String component : component_list) {
                if (component != "") {
                    if (component.startsWith(BIG_TITLE)) {
                        // Get Type of Text
                        String text_style = component.substring(0, BEGIN_TEXT_STYLE_POSITION);
                        // Get Text style
                        String text_content = component.substring(END_TEXT_STYLE_POSITION);
                        addBigTitle(text_content, text_style);

                    } else if (component.startsWith(SMALL_TITLE)) {
                        String text_content = component.substring(END_TEXT_STYLE_POSITION);
                        String text_style = component.substring(0, BEGIN_TEXT_STYLE_POSITION);
                        addSmallTitle(text_content, text_style);

                    } else if (component.startsWith(IMAGE)) {

                        addImageContent(R.drawable.back_icon, DpToPixel(300), DpToPixel(300),
                                DpToPixel(10),
                                DpToPixel(10),
                                DpToPixel(10),
                                DpToPixel(10));

                    } else if (component.startsWith(CONTENT)) {
                        String text_content = component.substring(END_TEXT_STYLE_POSITION);
                        String text_style = component.substring(0, BEGIN_TEXT_STYLE_POSITION);
                        addContent(text_content, text_style);
                    }
                }
            }
        }

        private int getTextStyle (String type) {

            if (type == BOLD_TEXT) {
                return Typeface.BOLD;
            } else if (type == ITALICED_TEXT) {
                return Typeface.BOLD_ITALIC;
            } else {
                return Typeface.NORMAL;
            }

        }
    }
}
