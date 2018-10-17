package phamf.com.chemicalapp.Abstraction.Interface;

public interface IDPDPMenuActivity {
    /**
     * @see phamf.com.chemicalapp.DPDPMenuActivity
     */
    interface View {

        void addControl ();

        void addEvent ();

        void setTheme ();
    }

    /**
     * @see phamf.com.chemicalapp.Presenter.DPDPMenuActivityPresenter
     */
    interface Presenter {
        void loadData ();
    }
}
