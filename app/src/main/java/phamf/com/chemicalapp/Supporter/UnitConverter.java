package phamf.com.chemicalapp.Supporter;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class UnitConverter {

    public static final int D5 = DpToPixel(5);

    public static final int D10 = DpToPixel(10);

    public static final int D15 = DpToPixel(15);

    public static final int D25 = DpToPixel(25);

    public static final int D30 = DpToPixel(30);

    public static final int D50 = DpToPixel(50);

    public static final int D100 = DpToPixel(100);



    static int density = (int) Resources.getSystem().getDisplayMetrics().density;

    public static int DpToPixel (int dp) {
        int pixel = dp * density;
        return pixel;
    }


}
