package phamf.com.chemicalapp.Database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.gms.common.data.ObjectExclusionFilterable;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import phamf.com.chemicalapp.Model.Chapter;
import phamf.com.chemicalapp.Model.ChemicalEquation;
import phamf.com.chemicalapp.Model.Lesson;
import phamf.com.chemicalapp.Model.Chapter;
import phamf.com.chemicalapp.Model.ChemicalEquation;
import phamf.com.chemicalapp.Model.Lesson;

/**
 * We have a class named DataSnapshotConverter to convert data get from firebase to Object.
 * When we get all list from firebase, it return a ArrayList<HashMap<String, Object>> not ArrayList<Object>
 *     That HashMap contain name of field of Object and its value and we have to convert it to Object that we need
 */

public class OnlineDatabaseManager {


    private static final String CHEMICAL = "ChemicalEquation";


    private static final String CHAPTER = "Chapter";


    DatabaseReference mRef;

    private OnDataLoaded onDataLoaded;


    public OnlineDatabaseManager () {
        mRef = FirebaseDatabase.getInstance().getReference();
    }


    public void getAll_CE_Data () {

        mRef.child(CHEMICAL).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<HashMap<String, Object>> data = (ArrayList) dataSnapshot.getValue();
                ArrayList<ChemicalEquation> equations = new ArrayList<>();

                for (HashMap<String, Object> object : data) {
                    ChemicalEquation equation = DataSnapshotConverter.toChemicalEquation(object);
                    equations.add(equation);
                }

                /**
                 * Send data to Presenter
                 * **/
                onDataLoaded.onCE_LoadedFromFirebase(equations);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void getAll_Chapter_Data () {
        mRef.child(CHAPTER).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Chapter chapter = dataSnapshot.getValue(Chapter.class);

                /**
                 * Sen data to MainActivityPresenter
                 */
                onDataLoaded.onChapterLoadedFromFirebase(chapter);
            }

            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void setOnDataLoaded (OnDataLoaded onDataLoaded) {
        this.onDataLoaded = onDataLoaded;
    }


    public interface OnDataLoaded {

        void onChapterLoadedFromFirebase (Chapter chapter);

        void onCE_LoadedFromFirebase (ArrayList<ChemicalEquation> equations);
    }

}

class DataSnapshotConverter {


    public static ChemicalEquation toChemicalEquation(HashMap<String, Object> data) {
        ChemicalEquation equation = new ChemicalEquation();

        long id = (Long) data.get("id");
        String addingChemical = (String) data.get("addingChemicals");
        String product = (String) data.get("product");
        String condition = (String) data.get("condition");
        long total_balance_number = (Long) data.get("total_balance_number");

        equation.setId((int) id);
        equation.setAddingChemicals(addingChemical);
        equation.setProduct(product);
        equation.setCondition(condition);
        equation.setTotal_balance_number((int) total_balance_number);

        return equation;
    }


}
