package phamf.com.chemicalapp.Manager;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class AppThemeManager {

    public static final int ART_THEME = 0;

    public static final int DARK_THEME = 1;

    public static final int NORMAL_THEME = 2;

    public static final String APP_THEME = "app_theme";

    public static final String CURRENT_THEME = "current_theme";

    public static final String IS_USING_COLOR_BACKGROUND = "is_using_color_background";

    public static final String BACKGROUND_COLOR = "background_color";

    public static final String TEXT_COLOR = "text_color";

    public static final String BACKGROUND_DRAWABLE = "background_drawable";

    public static final String WIDGET_COLOR = "theme_color";

    public static final String IS_CUSTOMING = "is_customing";

    public static final String IS_USING_AVAILABLE_THEMES = "is_using_available_themes";

    public static final String IS_ON_NIGHT_MODE = "is_on_night_mode";


    public static int backgroundColor = 0;

    public static int backgroundDrawable = 0;

    public static int textColor = 0;

    public static int widgetColor = 0;

    public static int availableTheme = 0;

    public static boolean isCustomingTheme;

    public static boolean isUsingAvailableThemes;

    public static boolean isUsingColorBackground;

    public static boolean isOnNightMode;


    public static int [] backgroundColor_list = new int [6] ;

    public static int [] themeColor_list = new int [6];

    public static int [] textColor_list = new int[6];

    public static DefaultTheme [] defautTheme_list = new DefaultTheme [3];

    public static Drawable [] backgroundDrawable_list = new Drawable [6];


    static {
        backgroundColor_list[0] = android.R.color.holo_blue_dark;
        backgroundColor_list[1] = android.R.color.holo_orange_light;
        backgroundColor_list[2] = android.R.color.holo_purple;
        backgroundColor_list[3] = android.R.color.holo_red_dark;
        backgroundColor_list[4] = android.R.color.holo_green_light;
        backgroundColor_list[5] = android.R.color.holo_red_light;

        themeColor_list[0] = android.R.color.holo_blue_dark;
        themeColor_list[1] = android.R.color.holo_orange_light;
        themeColor_list[2] = android.R.color.holo_purple;
        themeColor_list[3] = android.R.color.holo_red_dark;
        themeColor_list[4] = android.R.color.holo_green_light;
        themeColor_list[5] = android.R.color.holo_red_light;

        textColor_list[0] = android.R.color.holo_blue_dark;
        textColor_list[1] = android.R.color.holo_orange_light;
        textColor_list[2] = android.R.color.holo_purple;
        textColor_list[3] = android.R.color.holo_red_dark;
        textColor_list[4] = android.R.color.holo_green_light;
        textColor_list[5] = android.R.color.holo_red_light;

        defautTheme_list[0] = DefaultTheme.ART_THEME;
        defautTheme_list[1] = DefaultTheme.DARK_THEME;
        defautTheme_list[2] = DefaultTheme.NORMAL_THEME;
    }


    public static int getBackgroundColor() {
        return backgroundColor_list[backgroundColor];
    }


    public static int getWidgetColor () {
        return themeColor_list[widgetColor];
    }


    public static Drawable getBackgroundDrawable() {
        return backgroundDrawable_list[backgroundDrawable];
    }


    public static void setTheme (@AppTheme int order) {

        DefaultTheme theme = defautTheme_list[order];
        switch (theme) {
            case ART_THEME:{
                backgroundColor = 1;
                textColor = 2;
                break;
            }
            case DARK_THEME:{
                backgroundColor = 4;
                textColor = 3;

                break;
            }
            case NORMAL_THEME: {
                textColor = 0;
                backgroundColor = 0;
                break;
            }
        }
    }


    public static int getTextColor() {
        return textColor_list[textColor];
    }

    public enum DefaultTheme {
        DARK_THEME,
        NORMAL_THEME,
        ART_THEME
    }

    @IntDef({ART_THEME, DARK_THEME, NORMAL_THEME})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AppTheme {}
}


