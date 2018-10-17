package phamf.com.chemicalapp.Abstraction.Interface;

public interface IBangTuanHoangActivity {
    /**
     * @see  phamf.com.chemicalapp.BangTuanHoangActivity
     */
    interface View {
        void addControl ();

        void addEvent ();

        void loadAnim ();

        void setPeriodicTableWidth ();

        void setTheme ();
    }

    interface Presenter {

    }
}
