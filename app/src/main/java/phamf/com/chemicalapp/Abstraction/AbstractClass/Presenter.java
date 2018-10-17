package phamf.com.chemicalapp.Abstraction.AbstractClass;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

public abstract class Presenter <E extends Activity>{

    protected E view;

    protected Context context;

    public Presenter (@NonNull E view) {

        this.view = view;

        context = view.getBaseContext();

    }
}
