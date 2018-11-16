package phamf.com.chemicalapp.Model;

import java.util.ArrayList;

public class UpdateFile {

    public ArrayList<Chapter> chapters;

    public ArrayList<Lesson> lessons;

    public ArrayList<DPDP> dpdps;

    public ArrayList<Chemical_Element> chemical_elements;

    public ArrayList<ChemicalEquation> chemical_equations;

    public ArrayList<String> images;

    public UpdateData update_data;

    public UpdateFile(ArrayList<Chapter> chapters, ArrayList<Lesson> lessons, ArrayList<DPDP> dpdps, ArrayList<Chemical_Element> chemical_elements, ArrayList<ChemicalEquation> chemical_equations, ArrayList<String> images) {
        this.chapters = chapters;
        this.lessons = lessons;
        this.dpdps = dpdps;
        this.chemical_elements = chemical_elements;
        this.chemical_equations = chemical_equations;
        this.images = images;
    }

    public UpdateFile() {

    }

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<Chapter> chapters) {
        this.chapters = chapters;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public ArrayList<DPDP> getDpdps() {
        return dpdps;
    }

    public void setDpdps(ArrayList<DPDP> dpdps) {
        this.dpdps = dpdps;
    }

    public ArrayList<Chemical_Element> getChemical_elements() {
        return chemical_elements;
    }

    public void setChemical_elements(ArrayList<Chemical_Element> chemical_elements) {
        this.chemical_elements = chemical_elements;
    }

    public ArrayList<ChemicalEquation> getChemical_equations() {
        return chemical_equations;
    }

    public void setChemical_equations(ArrayList<ChemicalEquation> chemical_equations) {
        this.chemical_equations = chemical_equations;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public UpdateData getUpdate_data() {
        return update_data;
    }

    public void setUpdate_data(UpdateData update_data) {
        this.update_data = update_data;
    }
}
