package phamf.com.chemicalapp.Database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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


    private static final String BANG_TUAN_HOAN = "PediodicTable";


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


    public void getNeedingUpdate_Chapter () {
        updateDataRef.child(CHAPTER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<Long> indexList = (ArrayList<Long>) dataSnapshot.getValue();
                for (long index : indexList) {
                    mRef.child(CHAPTER).child(index + "").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            onDataLoaded.onChapterLoadedFromFirebase(dataSnapshot.getValue(Chapter.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    // Not offical
    public void getNeedingUpdate_BangTuanHoan () {
        updateDataRef.child(BANG_TUAN_HOAN).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<Long> indexList = (ArrayList<Long>) dataSnapshot.getValue();
                for (long index : indexList) {
                    mRef.child(index + "").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            onDataLoaded.onChapterLoadedFromFirebase(dataSnapshot.getValue(Chapter.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void getNeedingUpdate_DPDP () {
        updateDataRef.child(DPDP).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<Long> indexList = (ArrayList<Long>) dataSnapshot.getValue();
                for (long index : indexList) {
                    mRef.child(DPDP).child(index + "").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            onDataLoaded.onChapterLoadedFromFirebase(dataSnapshot.getValue(Chapter.class));
                        }
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }

                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    public void getNeedingUpdate_CE () {
        updateDataRef.child(CHEMICAL).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<Long> indexList = (ArrayList<Long>) dataSnapshot.getValue();
                final ArrayList<ChemicalEquation> result = new ArrayList<>();
                addDataSuporting(result, indexList, 0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    /** This function is help adding Data to result list synchonously
     * because addValueEventListener works follow unsynchornous mechanism, we can't use for
     * loop to add Data to resultList
     *
     * We have to use recursive to do it by adding the next data when the previous value was returned
     * from firebase
     * and then check if the last data was added to the result list, published it to listener**/
    private void addDataSuporting (final ArrayList<ChemicalEquation> resultList, final ArrayList<Long> indexList, final int i) {
        if (i < indexList.size()) {
            mRef.child(CHEMICAL).child(indexList.get(i) + "").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    resultList.add(dataSnapshot.getValue(ChemicalEquation.class));

                    // is that the last data
                    if (i == indexList.size() - 1) {
                        onDataLoaded.onCE_LoadedFromFirebase(resultList);
                        return;
                    }

                    addDataSuporting(resultList, indexList, i + 1);

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
