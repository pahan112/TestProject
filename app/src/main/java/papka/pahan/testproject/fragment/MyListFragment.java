package papka.pahan.testproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.list_fragment, null);
        ButterKnife.bind(this, view);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot sessionSnap : dataSnapshot.getChildren()) {
                    mDataXYZs.clear();
                    for (DataSnapshot postSnapshot : sessionSnap.getChildren()) {
                        DataXYZ dataXYZ = postSnapshot.getValue(DataXYZ.class);
                        dataXYZ.setTime(postSnapshot.getKey());
                        mDataXYZs.add(dataXYZ);
                    }
                }
                XYZTAdapter xyztAdapter = new XYZTAdapter(mDataXYZs);
                mXYZTRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mXYZTRecyclerView.setAdapter(xyztAdapter);
                xyztAdapter.notifyDataSetChanged();
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
