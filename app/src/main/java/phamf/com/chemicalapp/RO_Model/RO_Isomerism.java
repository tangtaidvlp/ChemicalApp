package phamf.com.chemicalapp.RO_Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RO_Isomerism extends RealmObject {

    @PrimaryKey
    int id;

    String molecule_formula;

    String normal_name;

    String replace_name;

    int structure_image_id;

    int compact_structure_image_id;

    public RO_Isomerism() {

    }

    public RO_Isomerism(String molecule_formula, String normal_name, String replace_name, int structure_image_id, int compact_structure_image_id) {
        this.molecule_formula = molecule_formula;
        this.normal_name = normal_name;
        this.replace_name = replace_name;
        this.structure_image_id = structure_image_id;
        this.compact_structure_image_id = compact_structure_image_id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMolecule_formula() {
        return molecule_formula;
    }

    public void setMolecule_formula(String molecule_formula) {
        this.molecule_formula = molecule_formula;
    }

    public String getNormal_name() {
        return normal_name;
    }

    public void setNormal_name(String normal_name) {
        this.normal_name = normal_name;
    }

    public String getReplace_name() {
        return replace_name;
    }

    public void setReplace_name(String replace_name) {
        this.replace_name = replace_name;
    }

    public int getStructure_image_id() {
        return structure_image_id;
    }

    public void setStructure_image_id(int structure_image_id) {
        this.structure_image_id = structure_image_id;
    }

    public int getCompact_structure_image_id() {
        return compact_structure_image_id;
    }

    public void setCompact_structure_image_id(int compact_structure_image_id) {
        this.compact_structure_image_id = compact_structure_image_id;
    }
}
