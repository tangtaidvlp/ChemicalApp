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

    private long update_version;

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
                update_version = (long) dataSnapshot.getValue();
                // Plus one to make sure that the recursive loop start from
                // a version bigger than app version.
                // For example : if app version is 1 and the lasted version is 3,
                // the loop should start at 2 instead of 1
                try {
                    download_And_Process_UpdateFile((long) (app_version), update_version);
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

        if (version > last_version) {
            onASectionUpdated.onASectionUpdatedSuccess(last_version, true);
            return;
        }

        try {
            firebase_database.child(String.valueOf((int)version)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Download
                    UpdateFile updateFile = dataSnapshot.getValue(UpdateFile.class);

                    try {

                        // "Update File" is retrieved
                        processUpdateFile(updateFile);

                        // firebase database get data asynchronously So can't use for loop to get
                        // every update file on firebase
                        // Solution is using Recursive : Đệ quy,
                        download_And_Process_UpdateFile(version + 1, last_version);
                        onASectionUpdated.onASectionUpdatedSuccess(version + 1, false);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception ex) {

        }

    }

    private void processUpdateFile (UpdateFile updateFile) {

        if (updateFile == null) {
            Log.e("Update Error", "Update file is null");
            return;
        }

        try {
            //Warning : must update "lessons" before update "chapters" because chapter will find
            // lessons in database depend on the lesson_id which update_file contains in updatefile.lessons

            if (updateFile.getLessons() != null) {
                processAndUpdate_Lessons(updateFile.lessons);
            } else {
                Log.e("Lessons ", "null");
            }

            if (updateFile.getChapters()!= null) {
                processAndUpdate_Chapters(updateFile.chapters);
            } else {
                Log.e("Chapters ", "null");
            }

            if (updateFile.images != null) {
                processAndUpdate_Images(updateFile.images);
            } else {
                Log.e("Images ", "null");
            }

            if (updateFile.dpdps != null) {
                processAndUpdate_DPDPs(updateFile.dpdps);
            } else {
                Log.e("Dpdps ", "null");
            }

            if (updateFile.chemical_equations != null) {
                processAndUpdate_Chemical_Equations(updateFile.chemical_equations);
            } else {
                Log.e("Chemical equations ", "null");
            }

            if (updateFile.chemical_elements != null) {
                processAndUpdate_Chemical_Elements(updateFile.chemical_elements);
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
    private void processAndUpdate_Lessons (ArrayList<Lesson> chapter_list) {

        ArrayList<RO_Lesson> ro_lessons = ROConverter.toRO_Lessons(chapter_list);
        offlineDatabaseManager.addOrUpdateDataOf(RO_Lesson.class, ro_lessons);
    }

    private ArrayList<RO_Lesson> getRO_LessonListFromIds (ArrayList<Long> id_list) {

        if (id_list == null | id_list.size() == 0) {
            Log.e("Error happened", "Please check UpdateDatabaseManager.java");
            return null;
        }

        ArrayList<RO_Lesson> ro_lessons = new ArrayList<>();

        for (long id : id_list) {
            RO_Lesson ro_lesson = offlineDatabaseManager.readOneObjectOf(RO_Lesson.class, "id", (int) id);
            ro_lessons.add(ro_lesson);
        }

        return ro_lessons;
    }

    // Chapter
    private void processAndUpdate_Chapters (ArrayList<Chapter> chapter_list) {

        for (Chapter chapter : chapter_list){
            ArrayList<RO_Lesson> ro_lessons_list = getRO_LessonListFromIds(chapter.getLessons());
            chapter.setRo_lessons(ro_lessons_list);
        }

        ArrayList<RO_Chapter> ro_chapters = ROConverter.toRO_Chapters(chapter_list);
        offlineDatabaseManager.addOrUpdateDataOf(RO_Chapter.class, ro_chapters);
    }

    // Chemical Element
    private void processAndUpdate_Chemical_Elements (ArrayList<Chemical_Element> chemical_element_list) {
        ArrayList<RO_Chemical_Element> ro_chemical_elements = ROConverter.toRO_Chemical_Elements(chemical_element_list);
        offlineDatabaseManager.addOrUpdateDataOf(RO_Chemical_Element.class, ro_chemical_elements);
    }

    // Chemical Equation
    private void processAndUpdate_Chemical_Equations (ArrayList<ChemicalEquation> chemical_equations_list) {
        ArrayList<RO_ChemicalEquation> ro_chemicalEquations = ROConverter.toRO_CEs(chemical_equations_list);
        offlineDatabaseManager.addOrUpdateDataOf(RO_ChemicalEquation.class, ro_chemicalEquations);
    }

    // DPDP
    private void processAndUpdate_DPDPs (ArrayList<DPDP> dpdp_list) {
        ArrayList<RO_DPDP> ro_dpdps = ROConverter.toRO_DPDPs(dpdp_list);
        offlineDatabaseManager.addOrUpdateDataOf(RO_DPDP.class, ro_dpdps);
    }

    // Image
    private void processAndUpdate_Images(ArrayList<String> link_list) {
        downLoadImage(link_list, 0);
    }

    final long ONE_AND_HAFT_MEGABYTE = (long) (1024 * 1024 * 1.5);
    private void downLoadImage (ArrayList<String> link_list, int pointer) {
        if (pointer >= link_list.size()) return;

        String link = link_list.get(pointer);

        firebase_storage.child(link).getBytes(ONE_AND_HAFT_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] byte_code_resouces) {
                RO_Chemical_Image chemical_image = new RO_Chemical_Image(link, byte_code_resouces);
                offlineDatabaseManager.addOrUpdateDataOf(RO_Chemical_Image.class, chemical_image);

                // Recursive
                downLoadImage(link_list, pointer + 1);
            }
        });

    }

    public void setOnASectionUpdated(@NonNull OnASectionUpdated onASectionUpdated) {
        this.onASectionUpdated = onASectionUpdated;
    }

    // Update data
    private void processUpdateData(UpdateData update_data) {

    }


    public interface OnVersionChecked {
        void onVersionLoaded (long version);
    }

    public interface OnASectionUpdated {
        void onASectionUpdatedSuccess (long version, boolean isLastVersion);
    }

}
