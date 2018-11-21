package phamf.com.chemicalapp.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.wasabeef.richeditor.RichEditor;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Image;
import phamf.com.chemicalapp.Supporter.UnitConverter;

public class LessonHtmlViewCreator {

    private String simulated_content = "<..> Abc xyz <..>[*]image108_200_link[*]<..>uxyz<..>";

    private LinearLayout parent;

    private Context context;

    private OfflineDatabaseManager offlineDatabaseManager;

    public static final String PART_DEVIDER = "[part]";

    public static final String DEVIDER = "[*]";

    public static final String IMAGE = "image";

    public LessonHtmlViewCreator(Context context, LinearLayout parent) {
        this.parent = parent;
        this.context = context;
        offlineDatabaseManager = new OfflineDatabaseManager(context);
    }

    public void addView (String content) {
        String [] lesson_parts = content.split("\\[\\*]");
        for (String part : lesson_parts) {
            if (part.startsWith(IMAGE)) {
                addImage(part.substring(IMAGE.length() + 1));
            } else {
                addText(part);
            }
        }
    }

    private void addText (String html) {
        RichEditor text = new RichEditor(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);;
        params.setMargins(UnitConverter.DpToPixel(10), UnitConverter.DpToPixel(10), UnitConverter.DpToPixel(10), UnitConverter.DpToPixel(10));
        text.setLayoutParams(params);
        text.setHtml(html);
        parent.addView(text);
    }


    /**
     * Link has form like " <...> ..... </...> [*] image|(width)|(height)|link [*] <...> ...... </...> "
     */
    final int IMAGE_WIDTH = 0;
    final int IMAGE_HEIGHT = 1;
    final int IMAGE_LINK = 2;
    final String INFO_DEVIDER = "\\|";
    private void addImage (String image_info) {
        try {
            Log.e("IMAGE INFO", image_info + "CON CAC");
            String [] info = image_info.split(INFO_DEVIDER);
            int width = UnitConverter.DpToPixel(Integer.valueOf(info[IMAGE_WIDTH]));
            int height = UnitConverter.DpToPixel(Integer.valueOf(info[IMAGE_HEIGHT]));
            String link = info[IMAGE_LINK];

            byte [] image_resource = offlineDatabaseManager.readOneObjectOf(RO_Chemical_Image.class, "link", link).getByte_code_resouces();
            Bitmap image_bitmap = BitmapFactory.decodeByteArray(image_resource, 0, image_resource.length);

            ImageView image = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.setMargins(UnitConverter.DpToPixel(10), UnitConverter.DpToPixel(10), UnitConverter.DpToPixel(10), UnitConverter.DpToPixel(10));
            image.setLayoutParams(params);
            image.setBackground(new BitmapDrawable(context.getResources(), image_bitmap));
            Log.e("a", image_resource.length + "");
            parent.addView(image);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

