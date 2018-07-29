package phamf.com.chemicalapp.CustomAnimation;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FadedInAnim extends Animation {

    private FadedInAnim.FadedInAnimListener listener;


    private ArrayList<View> views = new ArrayList<>();


    public FadedInAnim(View... views) {
        this.views.addAll(Arrays.asList(views));
    }

    public FadedInAnim(List<View> views) {
        this.views.addAll(views);
    }

    public void setOnAnimEndListener (FadedInAnim.FadedInAnimListener listener) {
        this.listener = listener;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float alpha = interpolatedTime;
        for (View view : views) {
            view.setAlpha(alpha);
        }

        if (interpolatedTime >= 1){
            if (listener != null) listener.onEnd();
        }

        super.applyTransformation(interpolatedTime, t);
    }


    @Override
    public void start() {
        super.start();
        if (listener != null) listener.onStart();
    }


    public interface FadedInAnimListener {

        void onStart();

        void onEnd();
    }
}
