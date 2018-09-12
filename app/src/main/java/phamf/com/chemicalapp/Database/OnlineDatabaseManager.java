package phamf.com.chemicalapp.Database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import phamf.com.chemicalapp.Model.Chapter;
import phamf.com.chemicalapp.Model.ChemicalEquation;
import phamf.com.chemicalapp.Model.Chemical_Element;
import phamf.com.chemicalapp.Model.DPDP;

/**
 * We have a class named DataSnapshotConverter to convert data get from firebase to Object.
 * When we get all list from firebase, it return a ArrayList<HashMap<String, Object>> not ArrayList<Object>
 *     That HashMap contain name of field of Object and its value and we have to convert it to Object that we need
 */

public class OnlineDatabaseManager {


    private static final String CHEMICAL = "ChemicalEquation";


    private static final String CHAPTER = "Chapter";


    private static final String UPDATE_DATA = "UpdateData";


    private final String DPDP = "DPDP";


    private final String DATABASE_VERSION = "Version";


    private final String UPDATE_STATUS = "UpdateStatus";



    private boolean isAvailable;

    DatabaseReference mRef;

    DatabaseReference updateDataRef;

    private OnDataLoaded onDataLoaded;



    public OnlineDatabaseManager () {
        mRef = FirebaseDatabase.getInstance().getReference();
        updateDataRef = mRef.child(UPDATE_DATA);
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
                /**
                 * Send data to MainActivityPresenter
                 */
                onDataLoaded.onChapterLoadedFromFirebase(dataSnapshot.getValue(Chapter.class));
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


    public void getAll_DPDP () {
        mRef.child("DPDP").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                /**
                 * Send data to MainActivityPresenter
                 */
                onDataLoaded.onDPDP_LoadedFromFirebase(dataSnapshot.getValue(DPDP.class));
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


    public void getAllBangTuanHoan () {
        mRef.child("PeriodicTable").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                onDataLoaded.onChemElement_LoadedFromFirebase(dataSnapshot.getValue(Chemical_Element.class));
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


    public void getUpdateStatus () {
        mRef.child(UPDATE_STATUS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                onDataLoaded.onStatusLoaded((Boolean) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void getVersionUpdate () {
        mRef.child(DATABASE_VERSION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                onDataLoaded.onVersionLoaded((Long) dataSnapshot.getValue());
            }

            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    int i = 0;
    public void getNeedingUpdateCE () {
        updateDataRef.child(CHEMICAL).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<Long> indexList = (ArrayList<Long>) dataSnapshot.getValue();
                final ArrayList<ChemicalEquation> result = new ArrayList<>();
                DoIt(result, indexList, 0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void DoIt (final ArrayList<ChemicalEquation> resultList, final ArrayList<Long> indexList, final int i) {
        if (i < indexList.size()) {
            mRef.child(CHEMICAL).child(indexList.get(i) + "").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    resultList.add(dataSnapshot.getValue(ChemicalEquation.class));

                    if (i == indexList.size() - 1) {
                        onDataLoaded.onCE_LoadedFromFirebase(resultList);
                        return;
                    }

                    DoIt(resultList, indexList, i + 1);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }


    public void setOnDataLoaded (OnDataLoaded onDataLoaded) {
        this.onDataLoaded = onDataLoaded;
    }


    /**
     * @see phamf.com.chemicalapp.Presenter.MainActivityPresenter
     * which implement this interface
     */

    public interface OnDataLoaded {

        void onChapterLoadedFromFirebase (Chapter chapter);

        void onCE_LoadedFromFirebase (ArrayList<ChemicalEquation> equations);

        void onDPDP_LoadedFromFirebase (DPDP dpdp);

        void onChemElement_LoadedFromFirebase (Chemical_Element element);

        void onVersionLoaded (long version);

        void onStatusLoaded (boolean isAvailable);

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
