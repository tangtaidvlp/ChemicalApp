package phamf.com.chemicalapp.Presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Abstraction.Interface.IDPDPActivity;
import phamf.com.chemicalapp.DPDPActivity;
import phamf.com.chemicalapp.DPDPMenuActivity;
import phamf.com.chemicalapp.RO_Model.RO_DPDP;
import phamf.com.chemicalapp.RO_Model.RO_Isomerism;
import phamf.com.chemicalapp.RO_Model.RO_OrganicMolecule;

import static phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.*;

/** Send Data
 * @see DPDPActivity
 * implement this listener
 */

public class DPDPActivityPresenter extends Presenter<DPDPActivity> implements IDPDPActivity.Presenter{

    private DataLoadListener onDataLoadListener;

    public DPDPActivityPresenter(@NonNull DPDPActivity view) {
        super(view);
    }

    public void loadData() {
        Intent intent = view.getIntent();

        RO_DPDP dpdp = intent.getParcelableExtra(DPDPMenuActivity.DPDP_NAME);

        if (dpdp != null) {
            view.qc_organic_adapter.setData(dpdp.getOrganicMolecules());
            convertObjectToData(dpdp);
        } else {
            Toast.makeText(view, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
        }
    }

    public String convertContent (RO_OrganicMolecule orM) {
        StringBuilder content = new StringBuilder();
        content.append(COMPONENT_DEVIDER).append(BIG_TITLE).append(orM.getId() + ") ")
                .append(orM.getMolecule_formula())
                .append(" - " + orM.getName()).append(COMPONENT_DEVIDER)
                .append(CONTENT).append(" - Tên thay thế: " + orM.getReplace_name()).append(COMPONENT_DEVIDER)
                .append(CONTENT).append(" - Công thức OSAMA BILADEN : ").append(COMPONENT_DEVIDER)
                .append(CONTENT).append(" - Công thức OSAMA BILADEN thu gọn: ").append(COMPONENT_DEVIDER)
                .append(CONTENT).append(" - Số đồng phân: " + orM.getIsomerisms().size()).append(COMPONENT_DEVIDER);

        for (RO_Isomerism iso : orM.getIsomerisms()) {
            content.append(CONTENT).append("   " + iso.getReplace_name()).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append("   - Công thức OSAMA BILADEN : ").append(COMPONENT_DEVIDER)
                    .append(CONTENT).append("   - Công thức OSAMA BILADEN thu gọn: ");
        }

        return content.toString();
    }

    //** Need ding it
    public void convertObjectToData (RO_DPDP dpdp) {
        StringBuilder content = new StringBuilder();
        String title = dpdp.getName();
        RO_OrganicMolecule orM = dpdp.getOrganicMolecules().get(0);
        content.append(COMPONENT_DEVIDER).append(BIG_TITLE).append(orM.getId() + ") ")
                .append(orM.getMolecule_formula())
                .append(" - " + orM.getName()).append(COMPONENT_DEVIDER)
                .append(CONTENT).append(" - Tên thay thế: " + orM.getReplace_name()).append(COMPONENT_DEVIDER)
                .append(CONTENT).append(" - Công thức cấu tạo: ").append(COMPONENT_DEVIDER)
                .append(CONTENT).append(" - Công thức cấu tạo thu gọn: ").append(COMPONENT_DEVIDER)
                .append(CONTENT).append(" - Số đồng phân: " + orM.getIsomerisms().size()).append(COMPONENT_DEVIDER);

        for (RO_Isomerism iso : orM.getIsomerisms()) {
            content.append(CONTENT).append("   " + iso.getReplace_name()).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append("   - Công thức cấu tạo: ").append(COMPONENT_DEVIDER)
                    .append(CONTENT).append("   - Công thức cấu tạo thu gọn: ");
        }

        /** Send Data
         * @see DPDPActivity
         * implement this listener
         */
        if (onDataLoadListener != null)
            onDataLoadListener.onDataLoadSuccess(title, content.toString());
    }

    public void setOnDataLoadListener(DataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
    }

    public interface DataLoadListener {

        void onDataLoadSuccess (String title, String content);

    }
}
