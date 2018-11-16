package phamf.com.chemicalapp.Model;

import java.util.ArrayList;

public class UpdateData {
    ArrayList<String> chapters;
    ArrayList<String> lessons;
    ArrayList<String> chemical_elements;
    ArrayList<String> chemical_equations;
    ArrayList<String> dpdps;

    public UpdateData(ArrayList<String> chapters, ArrayList<String> lessons, ArrayList<String> chemical_elements, ArrayList<String> chemical_equations, ArrayList<String> dpdps) {
        this.chapters = chapters;
        this.lessons = lessons;
        this.chemical_elements = chemical_elements;
        this.chemical_equations = chemical_equations;
        this.dpdps = dpdps;
    }

    public UpdateData() {

    }

    public ArrayList<String> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<String> chapters) {
        this.chapters = chapters;
    }

    public ArrayList<String> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<String> lessons) {
        this.lessons = lessons;
    }

    public ArrayList<String> getChemical_elements() {
        return chemical_elements;
    }

    public void setChemical_elements(ArrayList<String> chemical_elements) {
        this.chemical_elements = chemical_elements;
    }

    public ArrayList<String> getChemical_equations() {
        return chemical_equations;
    }

    public void setChemical_equations(ArrayList<String> chemical_equations) {
        this.chemical_equations = chemical_equations;
    }

    public ArrayList<String> getDpdps() {
        return dpdps;
    }

    public void setDpdps(ArrayList<String> dpdps) {
        this.dpdps = dpdps;
    }
}
