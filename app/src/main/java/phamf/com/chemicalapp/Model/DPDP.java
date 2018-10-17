package phamf.com.chemicalapp.Model;

import java.util.ArrayList;

public class DPDP {

    int id;

    String name;

    ArrayList<OrganicMolecule> organicMolecules;

    public DPDP(int id, String name, ArrayList<OrganicMolecule> organicMolecules) {
        this.id = id;
        this.name = name;
        this.organicMolecules = organicMolecules;
    }

    public DPDP() {

    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<OrganicMolecule> getOrganicMolecules() {
        return organicMolecules;
    }

    public void setOrganicMolecules(ArrayList<OrganicMolecule> organicMolecules) {
        this.organicMolecules = organicMolecules;
    }
}
