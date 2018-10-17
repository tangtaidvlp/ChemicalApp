package phamf.com.chemicalapp.Manager;

import android.app.Activity;
import android.content.Intent;

import phamf.com.chemicalapp.Service.FloatingSearchIconService;

// Create quick search view which floating on screen
public class FloatingSearchViewManager {

    Activity activity;

    public FloatingSearchViewManager (Activity activity) {
        this.activity = activity;
    }

    public void init () {
        activity.startService(new Intent(activity, FloatingSearchIconService.class));
    }
}
