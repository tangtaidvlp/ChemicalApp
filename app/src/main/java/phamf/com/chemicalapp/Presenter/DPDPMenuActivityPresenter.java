package phamf.com.chemicalapp.Presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import phamf.com.chemicalapp.Abstraction.Presenter;
import phamf.com.chemicalapp.DPDPMenuActivity;

public class DPDPMenuActivityPresenter extends Presenter<DPDPMenuActivity> {

    public DPDPMenuActivityPresenter(@NonNull DPDPMenuActivity view) {
        super(view);
    }

    @Override
    public void loadData () {
        Bundle bundle = view.getIntent().getExtras();


    }
}
