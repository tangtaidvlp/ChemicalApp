package phamf.com.chemicalapp.Database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import phamf.com.chemicalapp.Model.Chapter;
import phamf.com.chemicalapp.Model.ChemicalEquation;
import phamf.com.chemicalapp.Model.Chemical_Element;
import phamf.com.chemicalapp.Model.DPDP;
import phamf.com.chemicalapp.Model.Lesson;
import phamf.com.chemicalapp.Model.UpdateData;
import phamf.com.chemicalapp.Model.UpdateFile;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element;
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Image;
import phamf.com.chemicalapp.RO_Model.RO_DPDP;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.Supporter.ROConverter;

public class UpdateDatabaseManager {

    private final String IMAGEs = "images";

    private final String LASTED_UPDATE_VERSION = "lasted_update_version";

    private OfflineDatabaseManager offlineDatabaseManager;

    DatabaseReference firebase_database;

    StorageReference firebase_storage;

    private long lasted_update_version;

    private float app_version;

    private OnASectionUpdated onASectionUpdated;

    public UpdateDatabaseManager (Context context, long app_version) {

        offlineDatabaseManager = new OfflineDatabaseManager(context);
        firebase_database = FirebaseDatabase.getInstance().getReference();
        this.app_version = app_version;
    }

    public void update () {
        firebase_storage = FirebaseStorage.getInstance().getReference();

        firebase_database.child(LASTED_UPDATE_VERSION).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lasted_update_version = (long) dataSnapshot.getValue();

                try {
                    download_And_Process_UpdateFile((long) (app_version), lasted_update_version);
                } catch (Exception ex) {
                    Log.e("error", "UpdateDatabaseManage.java/71");
                    ex.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /** @see
     * phamf.com.chemicalapp.MainActivity
     * **/
    public void getLastestVersionUpdate (OnVersionChecked onVersionChecked) {
        firebase_database.child(LASTED_UPDATE_VERSION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                onVersionChecked.onVersionLoaded((Long) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void download_And_Process_UpdateFile(long version, long last_version) {
/*   ^   */
/*  /^\  */
/* / ^ \ */     long _version = version + 1;
/*   ^   */
/*   ^   */    if (_version > last_version) {
/*   ^   */        onASectionUpdated.on_A_Version_Updated_Success(last_version, true);
/*   ^   */        return;
/*   ^   */    }
/*   ^   */
/*   ^   */    firebase_database.child(String.valueOf((int)_version)).addValueEventListener(new ValueEventListener() {
/*   ^   */        @Override
/*   ^   */        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
/*   ^   */            //Download
/*   ^   */            UpdateFile updateFile = dataSnapshot.getValue(UpdateFile.class);
/*   ^   */
/*   ^   */            try {
/*   ^   */
/*   ^   */                // "Update File" is retrieved
/*   ^   */                assert updateFile != null;
/*   ^   */                processUpdateFile(updateFile);
/*   ^   */                Log.e("Chapters size", (updateFile.getChapters() != null) + "");
/*   ^   */                // firebase database get data asynchronously So can't use for loop to get
/*   ^   */                // every update file on firebase
/*   ^   */                // Solution is using Recursive : Đệ quy,
/*   ^<<<<<<<<<<<<<<<<<<<<<*/ download_And_Process_UpdateFile(_version, last_version);
                    onASectionUpdated.on_A_Version_Updated_Success(_version, false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    /**
     * Download data
     * Include functions:
     *   + process_Lessons()
     *   + getRO_LessonListFromIds()
     *   + process_Chapters()
     *   + process_Chemical_Elements()
     *   + process_Chemical_Equations()
     *   + process_DPDPs()
     *   + process_Images()
     *   + downLoadImage()
     *  **/
    private void processUpdateFile (UpdateFile updateFile) {

        if (updateFile == null) {
            Log.e("Update Error", "Update file is null");
            return;
        }

        try {
            //Warning : must update "lessons" before update "chapters" because chapter will find
            // lessons in database depend on the lesson_id which update_file contains in updatefile.lessons

            if (updateFile.getLessons() != null) {
                process_Lessons(updateFile.lessons);
            } else {
                Log.e("Lessons ", "null");
            }

            if (updateFile.getChapters()!= null) {
                process_Chapters(updateFile.chapters);
            } else {
                Log.e("Chapters ", "null");
            }

            if (updateFile.images != null) {
                process_Images(updateFile.images);
            } else {
                Log.e("Images ", "null");
            }

            if (updateFile.dpdps != null) {
                process_DPDPs(updateFile.dpdps);
            } else {
                Log.e("Dpdps ", "null");
            }

            if (updateFile.chemical_equations != null) {
                process_Chemical_Equations(updateFile.chemical_equations);
            } else {
                Log.e("Chemical equations ", "null");
            }

            if (updateFile.chemical_elements != null) {
                process_Chemical_Elements(updateFile.chemical_elements);
            } else {
                Log.e("Chemical Elements ", "null");
            }

            if (updateFile.update_data != null) {
                processUpdateData (updateFile.update_data);
            } else {
                Log.e("Update data", "null");
            }

        } catch (Exception ex) {
            Log.e("Error", "Error source: UpdateDatabaseManager.java");
        }
    }

    // Lesson
    private void process_Lessons (ArrayList<Lesson> chapter_list) {

        ArrayList<RO_Lesson> ro_lessons = ROConverter.toRO_Lessons(chapter_list);
        offlineDatabaseManager.addOrUpdateDataOf(RO_Lesson.class, ro_lessons);
    }

    private ArrayList<RO_Lesson> getRO_LessonListFromIds (ArrayList<Long> id_list) {

        if (id_list == null | id_list.size() == 0) {
            Log.e("Error happened", "Please check UpdateDatabaseManager.java, line 206");
            return null;
        }

        ArrayList<RO_Lesson> ro_lessons = new ArrayList<>();

        for (long id : id_list) {
            RO_Lesson ro_lesson = offlineDatabaseManager.readOneObjectOf(RO_Lesson.class, "id", (int) id);
            if (ro_lesson != null) ro_lessons.add(ro_lesson);
        }

        return ro_lessons;
    }



    // Chapter
    private void process_Chapters (ArrayList<Chapter> chapter_list) {

        // Bind RO_Lesson from db to Chapter by lesson's id
        for (Chapter chapter : chapter_list){
            ArrayList<RO_Lesson> ro_lessons_list = getRO_LessonListFromIds(chapter.getLessons());
            chapter.setRo_lessons(ro_lessons_list);
        }

        ArrayList<RO_Chapter> ro_chapters = ROConverter.toRO_Chapters(chapter_list);
        offlineDatabaseManager.addOrUpdateDataOf(RO_Chapter.class, ro_chapters);
    }



    // Chemical Element
    private void process_Chemical_Elements (ArrayList<Chemical_Element> chemical_element_list) {
        ArrayList<RO_Chemical_Element> ro_chemical_elements = ROConverter.toRO_Chemical_Elements(chemical_element_list);
        offlineDatabaseManager.addOrUpdateDataOf(RO_Chemical_Element.class, ro_chemical_elements);
    }



    // Chemical Equation
    private void process_Chemical_Equations (ArrayList<ChemicalEquation> chemical_equations_list) {
        ArrayList<RO_ChemicalEquation> ro_chemicalEquations = ROConverter.toRO_CEs(chemical_equations_list);
        offlineDatabaseManager.addOrUpdateDataOf(RO_ChemicalEquation.class, ro_chemicalEquations);
    }



    // DPDP
    private void process_DPDPs (ArrayList<DPDP> dpdp_list) {
        ArrayList<RO_DPDP> ro_dpdps = ROConverter.toRO_DPDPs(dpdp_list);
        offlineDatabaseManager.addOrUpdateDataOf(RO_DPDP.class, ro_dpdps);
    }



    // Image
    private void process_Images(ArrayList<String> link_list) {
        downLoadImage(link_list, 0);
    }



    final long ONE_AND_HAFT_MEGABYTE = (long) (1024 * 1024 * 1.5);
    private void downLoadImage (ArrayList<String> link_list, int pointer) {
        if (pointer >= link_list.size()) return;

        String link = link_list.get(pointer);

        // Get image
        firebase_storage.child(link).getBytes(ONE_AND_HAFT_MEGABYTE).addOnSuccessListener(byte_code_resouces -> {
            RO_Chemical_Image chemical_image = new RO_Chemical_Image(link, byte_code_resouces);
            offlineDatabaseManager.addOrUpdateDataOf(RO_Chemical_Image.class, chemical_image);

            // Recursive
            downLoadImage(link_list, pointer + 1);
        });

    }

    public void setOnASectionUpdated(@NonNull OnASectionUpdated onASectionUpdated) {
        this.onASectionUpdated = onASectionUpdated;
    }




    /**
     *  Update data
     *  Include methods:
     *    + updateLessons()
     *    + updateChapter()
     *    + updateChemical_Elements()
     *    + updateChemical_Equations()
     *    + updateDpdps()
     *  **/
    private void processUpdateData(UpdateData update_data) {

        if (update_data.getLessons() != null) {
            updateLessons(0, update_data.getLessons());
        }

        if (update_data.getChapters() != null) {
            updateChapter(0, update_data.getChapters());
        }

        if (update_data.getChemical_elements() != null) {
            updateChemical_Elements(0, update_data.getChemical_elements());

        }

        if (update_data.getChemical_equations() != null) {
            updateChemical_Equations(0, update_data.getChemical_equations());
        }

        if (update_data.getDpdps() != null) {
            updateDpdps(0, update_data.getDpdps());
        }
    }

    private void updateLessons(int pointer, ArrayList<String> lessons_link) {

        if (pointer >= lessons_link.size()) return;

        firebase_database.child(lessons_link.get(pointer)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Lesson lesson = dataSnapshot.getValue(Lesson.class);
                offlineDatabaseManager.addOrUpdateDataOf(RO_Lesson.class, ROConverter.toRO_Lesson(lesson));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateChapter (int pointer, ArrayList<String> chapters_link) {
        if (pointer >= chapters_link.size()) return;

        firebase_database.child(chapters_link.get(pointer)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Chapter chapter = dataSnapshot.getValue(Chapter.class);
                ArrayList<RO_Lesson> ro_lessons_list = getRO_LessonListFromIds(chapter.getLessons());
                chapter.setRo_lessons(ro_lessons_list);
                offlineDatabaseManager.addOrUpdateDataOf(RO_Chapter.class, ROConverter.toRO_Chapter(chapter));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateChemical_Elements (int pointer, ArrayList<String> chemical_elements_link) {
        if (pointer >= chemical_elements_link.size()) return;

        firebase_database.child(chemical_elements_link.get(pointer)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Chemical_Element chemical_element = dataSnapshot.getValue(Chemical_Element.class);
                offlineDatabaseManager.addOrUpdateDataOf(RO_Chemical_Element.class, ROConverter.toRO_Chemical_Element(chemical_element));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateChemical_Equations (int pointer, ArrayList<String> chemical_equations_link) {
        if (pointer >= chemical_equations_link.size()) return;

        firebase_database.child(chemical_equations_link.get(pointer)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ChemicalEquation chemical_equation = dataSnapshot.getValue(ChemicalEquation.class);
                offlineDatabaseManager.addOrUpdateDataOf(RO_ChemicalEquation.class, ROConverter.toRO_CE(chemical_equation));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateDpdps(int pointer, ArrayList<String> dpdps_link) {
        if (pointer >= dpdps_link.size()) return;

        firebase_database.child(dpdps_link.get(pointer)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DPDP dpdp = dataSnapshot.getValue(DPDP.class);
                offlineDatabaseManager.addOrUpdateDataOf(RO_DPDP.class, ROConverter.toRO_DPDP(dpdp));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public interface OnVersionChecked {
        void onVersionLoaded (long version);
    }

    public interface OnASectionUpdated {
        void on_A_Version_Updated_Success (long version, boolean isLastVersion);
    }

}
