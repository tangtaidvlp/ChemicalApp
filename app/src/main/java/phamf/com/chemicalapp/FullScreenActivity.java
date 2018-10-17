package phamf.com.chemicalapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import phamf.com.chemicalapp.Manager.FullScreenManager;

public class FullScreenActivity extends AppCompatActivity{


    private FullScreenManager fullScreenManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        makeFullScreen();
    }

    private void setUp () {
        fullScreenManager = new FullScreenManager(this);
    }

    public void makeFullScreen () {
        fullScreenManager.hideNavAndStatusBar();
    }

    public void makeFullScreenAfter (long milis) {
        fullScreenManager.hideNavAndStatusBar_After(milis);
    }

}
