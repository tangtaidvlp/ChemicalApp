package phamf.com.chemicalapp.CustomAnimation;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FadedOutAnim extends Animation {

    private FadedOutAnimListener listener;


    private ArrayList<View> views = new ArrayList<>();


    public FadedOutAnim(View... views) {
        this.views.addAll(Arrays.asList(views));
    }


    public FadedOutAnim(List<View> views) {
        this.views.addAll(views);
    }


    public void setOnAnimEndListener (FadedOutAnimListener listener) {
        this.listener = listener;
    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float alpha = 1 - interpolatedTime;

        for (View view : views) {
            view.setAlpha(alpha);
        }

        if (interpolatedTime >= 1){
            if (listener != null) listener.onEnd();
            for (View view : views) {
                view.setVisibility(View.GONE);
            }
        }

        super.applyTransformation(interpolatedTime, t);
    }


    @Override
    public void start() {
        super.start();
        if (listener != null) listener.onStart();
    }


    public interface FadedOutAnimListener {

        void onStart();

        void onEnd();
    }
}
