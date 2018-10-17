package phamf.com.chemicalapp.Supporter;

import android.content.res.Resources;

public class UnitConverter {

    static float density;

    public static final int _5dp;

    public static final int _10dp;

    public static final int _20dp;

    public static final int _30dp;

    public static final int _50dp;

    public static final int _100dp;


    static {
        density = Resources.getSystem().getDisplayMetrics().density;
        _5dp = DpToPixel(5);
        _10dp = DpToPixel(10);
        _20dp = DpToPixel(20);
        _30dp = DpToPixel(30);
        _50dp = DpToPixel(50);
        _100dp = DpToPixel(100);
    }

    public static int DpToPixel (int dp) {
        int pixel = Math.round(dp * density);
        return pixel;
    }

    public static int PixelToDp (int pixel) {
        int dp = Math.round(pixel / density);
        return pixel;
    }

}
