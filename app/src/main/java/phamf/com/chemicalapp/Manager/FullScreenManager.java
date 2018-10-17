package phamf.com.chemicalapp.Manager;

import android.app.Activity;
import android.view.View;

// Hide virtual navigation bar and status bar
public class FullScreenManager {

    private Activity activity;

    public FullScreenManager (Activity activity) {
        this.activity = activity;
    }

    public void hideNavAndStatusBar() {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                /*View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |*/ View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    public void hideNavAndStatusBar_After (final long timeMilis) {
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    Thread.sleep(timeMilis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideNavAndStatusBar();
                    }
                });
            }
        }).start();
    }
}
