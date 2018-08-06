package phamf.com.chemicalapp.Supporter;

import java.util.ArrayList;
import java.util.Collection;

import phamf.com.chemicalapp.Model.Chapter;
import phamf.com.chemicalapp.Model.ChemicalEquation;
import phamf.com.chemicalapp.Model.Lesson;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;

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

        for (int i = 0; i < equations.size(); i ++) {
            ChemicalEquation equation = equations.get(i);
            RO_ChemicalEquation ro_ce = new RO_ChemicalEquation();
            ro_ce.setId(equation.getId());
            ro_ce.setAddingChemicals(equation.getAddingChemicals());
            ro_ce.setProduct(equation.getProduct());
            ro_ce.setCondition(equation.getCondition());
            ro_ce.setTotal_balance_number(equation.getTotal_balance_number());
            ro_ces.add(ro_ce);
        }
        return ro_ces;
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











}
