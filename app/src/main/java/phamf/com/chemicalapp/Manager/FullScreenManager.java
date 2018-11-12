package phamf.com.chemicalapp.Manager;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Hide virtual navigation bar and status bar
public class FullScreenManager {

    private Activity activity;

    public FullScreenManager (Activity activity) {
        this.activity = activity;
    }

    public void hideNavAndStatusBar() {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                //For something relating to the Navigation View, we can't swipe the NAV View if this
                // flag is set. I remember so
                /*View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |*/ View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    public void hideNavAndStatusBar_After (final long timeMilis) {
        ExecutorService fullscreenService = Executors.newFixedThreadPool(1);
        fullscreenService.execute(() -> {
            try {
                Thread.sleep(timeMilis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            activity.runOnUiThread(this::hideNavAndStatusBar);
        });
    }
}
