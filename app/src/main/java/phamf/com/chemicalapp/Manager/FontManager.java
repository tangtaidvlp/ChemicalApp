package phamf.com.chemicalapp.Manager;

import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Created by phamf on 04-May-18.
 */

public class FontManager {

    public static Typeface comfortaa_bold;

    public static Typeface comfortaa_regular;

    public static Typeface myriad_pro_bold;

    public static Typeface myriad_pro_regular;

    public static Typeface arial;

    public static void createFont (AssetManager assetManager) {

        comfortaa_bold = Typeface.createFromAsset(assetManager, "fonts/comfortaa_bold.ttf");

        comfortaa_regular = Typeface.createFromAsset(assetManager, "fonts/comfortaa_regular.ttf");

        myriad_pro_bold = Typeface.createFromAsset(assetManager, "fonts/myriad_pro_semi_bold.ttf");

        myriad_pro_regular = Typeface.createFromAsset(assetManager, "fonts/myriad_pro_regular.ttf");

        arial = Typeface.createFromAsset(assetManager, "fonts/arial.ttf");
    }


}
