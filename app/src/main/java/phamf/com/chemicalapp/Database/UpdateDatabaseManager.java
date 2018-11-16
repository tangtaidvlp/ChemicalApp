package phamf.com.chemicalapp.Database;

import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

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
import phamf.com.chemicalapp.Model.Chemical_Image;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.Supporter.ROConverter;

public class UpdateDatabaseManager {

    private final String IMAGEs = "images";

    private final String LASTED_UPDATE_VERSION = "lasted_update_version";

    private OfflineDatabaseManager offlineDatabaseManager;

    DatabaseReference firebase_database;

    StorageReference firebase_storage;

    private float update_version;

    private float app_version = 1;



    public UpdateDatabaseManager (Context context) {

        offlineDatabaseManager = new OfflineDatabaseManager(context);
        firebase_database = FirebaseDatabase.getInstance().getReference();
//        firebase_storage = FirebaseStorage.getInstance().getReference();



    }

    public void update () {
        firebase_database.child(LASTED_UPDATE_VERSION).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                update_version = (long) dataSnapshot.getValue();
                update_version = 1;
                download_And_Process_UpdateFile(app_version, update_version);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void download_And_Process_UpdateFile(float version, float last_version) {

        if (version > last_version) return;

        firebase_database.child(String.valueOf((int)version)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Download
                UpdateFile updateFile = dataSnapshot.getValue(UpdateFile.class);
                Log.e("Size ", (updateFile.getLessons() == null) + "");

                try {

                    // "Update File" retrieved
                    processUpdateFile(updateFile);

                    // firebase database get data asynchronously So can't use for loop to get
                    // every update file on firebase
                    // Solution is using Recursive : Đệ quy,
                    download_And_Process_UpdateFile(version + 1, last_version);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void processUpdateFile (UpdateFile updateFile) {

        if (updateFile == null) {
            Log.e("Update Error", "Update file is null");
            return;
        }

        try {
            //Warning : must update "lessons" before update "chapters" because chapter will find
            // lessons in database base on the lesson_id which update_file contains in updatefile.lessons

            if (updateFile.getLessons() != null) {
                processAndUpdate_Lessons(updateFile.lessons);
            } else {
                Log.e("Lessons ", "null");
            }

            if (updateFile.getChapters()!= null) {
                processAndUpdate_Chapters(updateFile.chapters);
            } else {
                Log.e("chapters ", "null");
            }

            if (updateFile.images != null) {
                processAndUpdate_Images(updateFile.images);
            } else {
                Log.e("Lessons ", "null");
            }

            if (updateFile.dpdps != null) {
                processAndUpdate_DPDPs(updateFile.dpdps);
            } else {
                Log.e("Lessons ", "null");
            }

            if (updateFile.chemical_equations != null) {
                processAndUpdate_Chemical_Equations(updateFile.chemical_equations);
            } else {
                Log.e("Lessons ", "null");
            }

            if (updateFile.chemical_elements != null) {
                processAndUpdate_Chemical_Elements(updateFile.chemical_elements);
            } else {
                Log.e("Lessons ", "null");
            }

            if (updateFile.update_data != null) {
                processUpdateData (updateFile.update_data);
            } else {
                Log.e("Lessons ", "null");
            }

        } catch (Exception ex) {
            Log.e("Error", "Error source: UpdateDatabaseManager.java");
        }
    }

    private void processAndUpdate_Chapters (ArrayList<Chapter> chapter_list) {

        for (Chapter chapter : chapter_list){
            ArrayList<RO_Lesson> ro_lessons_list = getRO_LessonListFromIds(chapter.getLessons());
            chapter.setRo_lessons(ro_lessons_list);
        }

        ArrayList<RO_Chapter> ro_chapters = ROConverter.toRO_Chapters(chapter_list);
        offlineDatabaseManager.addOrUpdateDataOf(RO_Chapter.class, ro_chapters);
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

    private void processAndUpdate_Lessons (ArrayList<Lesson> chapter_list) {
        ArrayList<RO_Lesson> ro_lessons = ROConverter.toRO_Lessons(chapter_list);
        offlineDatabaseManager.addOrUpdateDataOf(RO_Lesson.class, ro_lessons);
    }

    private void processAndUpdate_Chemical_Elements (ArrayList<Chemical_Element> chapter_list) {

    }

    private void processAndUpdate_Chemical_Equations (ArrayList<ChemicalEquation> chapter_list) {

    }

    private void processAndUpdate_DPDPs (ArrayList<DPDP> chapter_list) {

    }

    private void processAndUpdate_Images(ArrayList<String> link_list) {

    }

    private void processUpdateData(UpdateData update_data) {

    }

}
