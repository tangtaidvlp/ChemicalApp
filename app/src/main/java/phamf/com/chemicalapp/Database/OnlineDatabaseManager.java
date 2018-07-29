package phamf.com.chemicalapp.Database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import phamf.com.chemicalapp.Model.Chapter;
import phamf.com.chemicalapp.Model.ChemicalEquation;
import phamf.com.chemicalapp.Model.Lesson;
import phamf.com.chemicalapp.Model.Chapter;
import phamf.com.chemicalapp.Model.ChemicalEquation;
import phamf.com.chemicalapp.Model.Lesson;


public class OnlineDatabaseManager {


    private static final String CHEMICAL = "ChemicalEquation";


    private static final String CHAPTER = "Chapter";


    ArrayList<Lesson> lesson_list_after_get_chapter_data = new ArrayList<>();


    DatabaseReference mRef;

    public OnlineDatabaseManager () {
        mRef = FirebaseDatabase.getInstance().getReference();
    }

    public ArrayList<ChemicalEquation> getAll_CE_Data () {

        final ArrayList<ChemicalEquation> data = new ArrayList<>();

        mRef.child(CHEMICAL).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                data.add(dataSnapshot.getValue(ChemicalEquation.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return data;
    }


    public ArrayList<Chapter> getAll_Chapter_Data () {

        final ArrayList<Chapter> data = new ArrayList<>();

        mRef.child(CHAPTER).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Chapter chapter = dataSnapshot.getValue(Chapter.class);
                data.add(chapter);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return data;
    }


    public ArrayList<Lesson> getAll_Lesson_Data() {
        return lesson_list_after_get_chapter_data;
    }
}
