package papka.pahan.testproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import papka.pahan.testproject.R;
import papka.pahan.testproject.adapter.XYZTAdapter;
import papka.pahan.testproject.data.DataXYZ;

/**
 * Created by admin on 15.06.2017.
 */

public class MyListFragment extends Fragment {

    private DatabaseReference mDatabase;

    @BindView(R.id.rv_xyzt)
    RecyclerView mXYZTRecyclerView;

    private List<DataXYZ> mDataXYZs = new ArrayList<>();

    private XYZTAdapter xyztAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        ButterKnife.bind(this, view);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        xyztAdapter = new XYZTAdapter(mDataXYZs);
        mXYZTRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mXYZTRecyclerView.setAdapter(xyztAdapter);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                /**Первый листенер нужен что бы достать название сессии при старте*/
                mDataXYZs.clear();
                mDatabase.child(dataSnapshot.getKey()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        DataXYZ dataXYZ = dataSnapshot.getValue(DataXYZ.class);
                        dataXYZ.setTime(dataSnapshot.getKey());
                        mDataXYZs.add(dataXYZ);
                        xyztAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
