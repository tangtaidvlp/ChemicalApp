package phamf.com.chemicalapp.Supporter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import io.realm.RealmList;
import phamf.com.chemicalapp.Model.Chapter;
import phamf.com.chemicalapp.Model.ChemicalEquation;
import phamf.com.chemicalapp.Model.Chemical_Element;
import phamf.com.chemicalapp.Model.DPDP;
import phamf.com.chemicalapp.Model.Isomerism;
import phamf.com.chemicalapp.Model.Lesson;
import phamf.com.chemicalapp.Model.OrganicMolecule;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element;
import phamf.com.chemicalapp.RO_Model.RO_DPDP;
import phamf.com.chemicalapp.RO_Model.RO_Isomerism;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.RO_Model.RO_OrganicMolecule;

//Realm Object Converter
public class ROConverter {


    public static ArrayList<RO_Lesson> toRO_Lessons (Collection<Lesson> lessons) {
        ArrayList<RO_Lesson> ro_lessons = new ArrayList<>();

        for ( Lesson lesson : lessons ) {
            RO_Lesson ro_lesson = new RO_Lesson();
            ro_lesson.setId(lesson.getId());
            ro_lesson.setName(lesson.getName());
            ro_lesson.setContent(lesson.getContent());
            ro_lessons.add(ro_lesson);
        }
        return ro_lessons;

    }

    public static ArrayList<RO_Chapter> toRO_Chapters (Collection<Chapter> chapters) {
        ArrayList<RO_Chapter> ro_chapters = new ArrayList<>();

        for ( Chapter chapter : chapters) {
            RO_Chapter ro_chapter = new RO_Chapter();
            ro_chapter.setId(chapter.getid());
            ro_chapter.setName(chapter.getName());
            ro_chapters.add(ro_chapter);
            ro_chapters.add(ro_chapter);
        }
        return ro_chapters;
    }

    public static ArrayList<RO_ChemicalEquation> toRO_CEs(ArrayList<ChemicalEquation> equations) {
        ArrayList<RO_ChemicalEquation> ro_ces = new ArrayList<>();
        for (ChemicalEquation chemicalEquation : equations) {
            ro_ces.add(toRO_CE(chemicalEquation));
        }
        return ro_ces;
    }

    public static RealmList<RO_OrganicMolecule> toRO_OrganicMolecules(Collection<OrganicMolecule> organicMolecules) {
        RealmList<RO_OrganicMolecule> ro_organicMolecules = new RealmList<>();
        for (OrganicMolecule organicMolecule : organicMolecules) {
            ro_organicMolecules.add(toRO_OrganicMolecule(organicMolecule));
        }
        return ro_organicMolecules;
    }

    public static RealmList<RO_Isomerism> toRO_Isomerisms (Collection<Isomerism> isomerisms) {

        RealmList<RO_Isomerism> ro_isomerisms = new RealmList<>();

        for (Isomerism isomerism : isomerisms) {
            ro_isomerisms.add(toRO_Isomerism(isomerism));
        }

        return ro_isomerisms;
    }




    public static RO_Chapter toRO_Chapter (Chapter chapter) {
        RO_Chapter ro_chapter = new RO_Chapter();
        ro_chapter.setName(chapter.getName());
        ro_chapter.setId(chapter.getid());
        ro_chapter.setLessons(ROConverter.toRO_Lessons(chapter.getLessons()));
        return ro_chapter;
    }

    public static RO_ChemicalEquation toRO_CE(ChemicalEquation equation) {
        RO_ChemicalEquation ro_ce = new RO_ChemicalEquation();

            ro_ce.setId(equation.getId());
            ro_ce.setAddingChemicals(equation.getAddingChemicals());
            ro_ce.setProduct(equation.getProduct());
            ro_ce.setCondition(equation.getCondition());
            ro_ce.setTotal_balance_number(equation.getTotal_balance_number());

            return ro_ce;
    }

    public static RO_OrganicMolecule toRO_OrganicMolecule (OrganicMolecule organicMolecule) {
        RO_OrganicMolecule ro_organicMolecule = new RO_OrganicMolecule();
        ro_organicMolecule.setId(organicMolecule.getId());
        ro_organicMolecule.setCompact_structure_image_id(organicMolecule.getCompact_structure_image_id());
        ro_organicMolecule.setMolecule_formula(organicMolecule.getMolecule_formula());
        ro_organicMolecule.setIsomerisms(toRO_Isomerisms(organicMolecule.getIsomerisms()));
        ro_organicMolecule.setId(organicMolecule.getId());
        return ro_organicMolecule;
    }

    public static RO_Isomerism toRO_Isomerism (Isomerism isomerism) {
        RO_Isomerism ro_isomerism = new RO_Isomerism();
        ro_isomerism.setId(isomerism.getId());
        ro_isomerism.setCompact_structure_image_id(isomerism.getCompact_structure_image_id());
        ro_isomerism.setMolecule_formula(isomerism.getMolecule_formula());
        ro_isomerism.setNormal_name(isomerism.getNormal_name());
        ro_isomerism.setReplace_name(isomerism.getReplace_name());
        ro_isomerism.setCompact_structure_image_id(isomerism.getCompact_structure_image_id());
        return ro_isomerism;
    }

    public static RO_DPDP toRO_DPDP(DPDP dpdp) {
        RO_DPDP ro_dpdp = new RO_DPDP();
        ro_dpdp.setId(dpdp.getId());
        ro_dpdp.setName(dpdp.getName());
        ro_dpdp.setOrganicMolecules(ROConverter.toRO_OrganicMolecules(dpdp.getOrganicMolecules()));

        return ro_dpdp;
    }

    public static RO_Chemical_Element toRO_Chemical_Element (Chemical_Element element) {
        RO_Chemical_Element ro_element = new RO_Chemical_Element();
        ro_element.setBackground_color(element.getBackgroundColor());
        ro_element.setElectron_count(element.getElectron_count());
        ro_element.setId(element.getId());
        ro_element.setMass(element.getMass());
        ro_element.setNotron_count(element.getNotron_count());
        ro_element.setName(element.getName());
        ro_element.setProton_count(element.getProton_count());
        ro_element.setType(element.getType());
        ro_element.setSymbol(element.getSymbol());
        return ro_element;
    }
}
